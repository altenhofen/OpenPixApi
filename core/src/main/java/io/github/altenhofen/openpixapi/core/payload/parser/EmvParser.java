package io.github.altenhofen.openpixapi.core.payload.parser;

import io.github.altenhofen.openpixapi.core.payload.PixPayload;
import io.github.altenhofen.openpixapi.core.payload.StaticPixPayload;
import io.github.altenhofen.openpixapi.core.payload.field.CompositeEmvField;
import io.github.altenhofen.openpixapi.core.payload.field.EmvField;
import io.github.altenhofen.openpixapi.core.payload.field.formatter.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/** EMVParser that will parse a payload String */
public final class EmvParser {
  // Pix-specific composite fields
  static final Set<String> COMPOSITE_FIELDS = Set.of("26", "62");

  static List<EmvNode> parse(String emvPayload) throws EmvParseException {
    List<EmvNode> nodes = new ArrayList<>();
    int payloadLength = emvPayload.length();
    int i = 0;
    while (i < payloadLength) {
      if (i + 2 > payloadLength) {
        throw new EmvParseException(String.format("unexpected end while reading id %d", i));
      }

      String tag = emvPayload.substring(i, i + 2);
      i += 2;

      String lengthString = emvPayload.substring(i, i + 2);
      int length = Integer.parseInt(lengthString);
      i += 2;

      String value = emvPayload.substring(i, i + length);
      i += length;

      if (COMPOSITE_FIELDS.contains(tag)) {
        List<EmvNode> children = parse(value);
        nodes.add(new EmvComposite(tag, children));
      } else {
        nodes.add(new EmvLeaf(tag, value));
      }
    }

    return nodes;
  }

  // treating nodes
  private static Map<String, EmvNode> index(List<EmvNode> nodes) {
    return nodes.stream()
        .collect(
            Collectors.toMap(
                n -> (n instanceof EmvLeaf l ? l.id() : ((EmvComposite) n).id()), n -> n));
  }

  private static String leafValue(Map<String, EmvNode> map, String id) {
    EmvNode node = map.get(id);
    if (!(node instanceof EmvLeaf leaf)) {
      throw new IllegalArgumentException("Expected leaf field " + id);
    }
    return leaf.value();
  }

  private static EmvField<?> toEmvField(EmvNode node) {
    if (node instanceof EmvLeaf leaf) {
      return new EmvField<>(null, leaf.id(), leaf.value(), null);
    }

    EmvComposite comp = (EmvComposite) node;

    List<? extends EmvField<?>> children =
        comp.children().stream().map(EmvParser::toEmvField).toList();

    return new CompositeEmvField(null, comp.id(), (List<EmvField<?>>) children);
  }

  private static CompositeEmvField composite(
      Map<String, EmvNode> map, String id, String fieldName) {
    EmvNode node = map.get(id);
    if (!(node instanceof EmvComposite comp)) {
      throw new IllegalArgumentException("Expected composite field " + id);
    }

    List<? extends EmvField> children =
        comp.children().stream().map(EmvParser::toEmvField).toList();

    return new CompositeEmvField(fieldName, id, (List<EmvField<?>>) children);
  }

  static PixPayload fromNodes(List<EmvNode> nodes) {
    Map<String, EmvNode> map = index(nodes);

    return new StaticPixPayload(
        new EmvField<>(
            "Payload Format",
            "00",
            Integer.parseInt(leafValue(map, "00")),
            new DigitFormatter(2, PaddingPolicy.LEFT)),
        new EmvField<>(
            "POI Method",
            "01",
            Integer.parseInt(leafValue(map, "01")),
            new DigitFormatter(2, PaddingPolicy.LEFT)),
        composite(map, "26", "Merchant Account"),
        new EmvField<>(
            "MCC",
            "52",
            Integer.parseInt(leafValue(map, "52")),
            new DigitFormatter(4, PaddingPolicy.LEFT)),
        new EmvField<>(
            "Currency",
            "53",
            Integer.parseInt(leafValue(map, "53")),
            new DigitFormatter(4, PaddingPolicy.LEFT)),
        map.containsKey("54")
            ? new EmvField<>(
                "Amount", "54", new BigDecimal(leafValue(map, "54")), new AmountFormatter())
            : null,
        new EmvField<>(
            "Country",
            "58",
            leafValue(map, "58"),
            new StringFormatter(2, CharsetPolicy.EMV_COMMON)),
        new EmvField<>(
            "Merchant Name",
            "59",
            leafValue(map, "59"),
            new StringFormatter(25, CharsetPolicy.EMV_COMMON)),
        new EmvField<>(
            "Merchant City",
            "60",
            leafValue(map, "60"),
            new StringFormatter(15, CharsetPolicy.EMV_COMMON)),
        map.containsKey("62") ? composite(map, "62", "Additional Data") : null);
  }
}
