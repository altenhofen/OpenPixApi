package io.github.altenhofen.openpixapi.core;

import java.math.BigDecimal;
import java.util.*;

/** EMVParser that will parse a payload String */
final class EmvParser {
  // Pix-specific composite fields
  static final Set<String> COMPOSITE_FIELDS = Set.of("26", "62");

  static List<EmvNode> parse(String emvPayload) throws EmvParseException {
    List<EmvNode> nodes = new ArrayList<>();
    int payloadLength = emvPayload.length();
    int i = 0;
    while (i < payloadLength) {
      if (i + 2 > payloadLength) {
        throw new EmvParseException(String.format("Unexpected end while reading at %d", i));
      }

      // tag, check it to be digit
      String tag = emvPayload.substring(i, i + 2);
      i += 2;

      // length, check it to be number only
      String lengthString = emvPayload.substring(i, i + 2);
      int length = 0;
      try {
        length = Integer.parseInt(lengthString);
      } catch (NumberFormatException e) {
        throw new EmvParseException(
            String.format(
                "Invalid length while reading at position %d, tried to format '%s' as Integer",
                i, lengthString));
      }
      i += 2;

      String value = emvPayload.substring(i, i + length);
      //      if (value.length() != length) {
      //        throw new EmvParseException(String.format("Invalid length while reading at position
      // %d, value length is %d, expected %d", i, value.length(), length));
      //      }
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
  /// turns a  list of nodes into a map where you can get by ID
  private static Map<String, EmvNode> index(List<EmvNode> nodes) {
    Map<String, EmvNode> map = new HashMap<>();
    for (EmvNode n : nodes) {
      if (map.put((n instanceof EmvLeaf l ? l.id() : ((EmvComposite) n).id()), n) != null) {
        throw new IllegalStateException("Duplicate key");
      }
    }
    return map;
  }

  ///  gets the value of the node on map
  private static String leafValue(Map<String, EmvNode> map, String id) {
    EmvNode node = map.get(id);
    if (!(node instanceof EmvLeaf leaf)) {
      throw new IllegalArgumentException("Expected leaf field " + id);
    }
    return leaf.value();
  }

  /// transforms into typed EMVField object
  private static EmvField<?> toEmvField(EmvNode node) {
    if (node instanceof EmvLeaf leaf) {
      return new EmvField<>(null, leaf.id(), leaf.value(), null);
    }

    EmvComposite comp = (EmvComposite) node;

    List<EmvField<?>> list = new ArrayList<>();
    for (EmvNode emvNode : comp.children()) {
      EmvField<?> emvField = toEmvField(emvNode);
      list.add(emvField);
    }
    List<? extends EmvField<?>> children = list;

    return new CompositeEmvField(null, comp.id(), (List<EmvField<?>>) children);
  }

  private static CompositeEmvField composite(
      Map<String, EmvNode> map, String id, String fieldName) {
    EmvNode node = map.get(id);
    if (!(node instanceof EmvComposite comp)) {
      throw new IllegalArgumentException("Expected composite field " + id);
    }

    List<EmvField<?>> list = new ArrayList<>();
    for (EmvNode emvNode : comp.children()) {
      EmvField<?> emvField = toEmvField(emvNode);
      list.add(emvField);
    }
    List<? extends EmvField> children = list;

    return new CompositeEmvField(fieldName, id, (List<EmvField<?>>) children);
  }

  static PixPayload fromNodes(List<EmvNode> nodes) {
    Map<String, EmvNode> map = index(nodes);
    String merchantName = leafValue(map, "59");
    String merchantCity = leafValue(map, "60");

    // merchant account info fields (composite)
    EmvComposite mai = (EmvComposite) map.get("26");
    if (mai == null) {
      throw new IllegalArgumentException("Missing Merchant Account Information (26)");
    }
    // get those fields
    Map<String, EmvNode> maiFields = index(mai.children());

    // additional data field template (composite)
    EmvComposite adft = (EmvComposite) map.get("62");
    // get fields
    Map<String, EmvNode> adftFields = adft != null ? index(adft.children()) : Map.of();

    // "Os campos Valor e Identificador da Transação (txid) não devem ser preenchidos no QR Code
    // dinâmico.
    // Se preenchidos, seu conteúdo deve ser ignorado, prevalecendo sempre os campos obtidos através
    // da
    // URL (payload JSON)"

    boolean isDynamicPix =
        maiFields.containsKey("25")
            && // PSP URL
            adftFields.containsKey("05"); // TXID

    // validate
    if (isDynamicPix && map.containsKey("54")) {
      throw new IllegalArgumentException("Dynamic Pix QR must not include transaction amount");
    }

    // return dynamic pix
    if (isDynamicPix) {
      String pspUrl = leafValue(maiFields, "25");
      String txid = leafValue(adftFields, "05");

      DynamicMerchantAccountInfo merchantAccountInfo = new DynamicMerchantAccountInfo(pspUrl);
      // txid is present on 62.05

      return new DynamicPixPayload(
          ImmutableEmvFields.payloadFormatIndicator(),
          ImmutableEmvFields.pointOfInitiationMethod(),
          merchantAccountInfo.toEmvField(),
          ImmutableEmvFields.merchantCategoryCode(),
          ImmutableEmvFields.transactionCurrency(),
          ImmutableEmvFields.countryCode(),
          ImmutableEmvFields.merchantName(merchantName),
          ImmutableEmvFields.merchantCity(merchantCity),
          ImmutableEmvFields.additionalData(txid));
    }
    // return static pix payload
    String pixKey = leafValue(maiFields, "01");
    String amount = leafValue(map, "54");
    StaticMerchantAccountInfo merchantAccountInfo = new StaticMerchantAccountInfo(pixKey);

    return new StaticPixPayload(
        ImmutableEmvFields.payloadFormatIndicator(),
        ImmutableEmvFields.pointOfInitiationMethod(),
        merchantAccountInfo.toEmvField(),
        ImmutableEmvFields.merchantCategoryCode(),
        ImmutableEmvFields.transactionCurrency(),
        ImmutableEmvFields.transactionAmount(new BigDecimal(amount)),
        ImmutableEmvFields.countryCode(),
        ImmutableEmvFields.merchantName(merchantName),
        ImmutableEmvFields.merchantCity(merchantCity),
        ImmutableEmvFields.additionalData(null));
  }
}
