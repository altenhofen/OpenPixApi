package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.parser.*;
import io.github.altenhofen.openpixapi.core.payload.field.*;
import io.github.altenhofen.openpixapi.core.payload.field.formatter.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class is used to create the payload based on the Manual de Padrões para Iniciação do Pix
 *
 * @author Augusto Bussmann Altenhofen
 * @see CompositeEmvField
 * @since 0.01-DEV
 */
public final class PixPayloadFactory {
  private PixPayloadFactory() {}

  /**
   * @param pixKey the pixKey as a random key, e-mail, phone number or CPF
   * @param merchantName name of the person who's receiving the transaction
   * @param merchantCity city of the person who's receiving the transaction
   * @param amount value to be received, can be null
   * @param txid used in dynamic pix, it's basically ignored for now
   * @return a payload to be used by other classes or serialized to String
   */
  public static StaticPixPayload staticPix(
      String pixKey, String merchantName, String merchantCity, BigDecimal amount, String txid) {

    Objects.requireNonNull(pixKey, "pixKey");
    Objects.requireNonNull(merchantName, "merchantName");
    Objects.requireNonNull(merchantCity, "merchantCity");

    if (merchantName.isBlank()) {
      throw new IllegalArgumentException("Merchant name cannot be blank");
    }

    if (merchantCity.isBlank()) {
      throw new IllegalArgumentException("Merchant city cannot be blank");
    }

    MerchantAccountInfo merchantAccountInfo = new StaticMerchantAccountInfo(pixKey);

    return new StaticPixPayload(
        ImmutableFields.payloadFormatIndicator(),
        ImmutableFields.pointOfInitiationMethod(),
        merchantAccountInfo.toEmvField(),
        ImmutableFields.merchantCategoryCode(),
        ImmutableFields.transactionCurrency(),
        ImmutableFields.transactionAmount(amount),
        ImmutableFields.countryCode(),
        ImmutableFields.merchantName(merchantName),
        ImmutableFields.merchantCity(merchantCity),
        ImmutableFields.additionalData(txid));
  }

  public static DynamicPixPayload dynamicPix(
      String pspUrl, String merchantName, String merchantCity, String txid) {
    Objects.requireNonNull(merchantName, "merchantName");
    Objects.requireNonNull(merchantCity, "merchantCity");

    if (merchantName.isBlank()) {
      throw new IllegalArgumentException("Merchant name cannot be blank");
    }

    if (merchantCity.isBlank()) {
      throw new IllegalArgumentException("Merchant city cannot be blank");
    }
    MerchantAccountInfo merchantAccountInfo = new DynamicMerchantAccountInfo(pspUrl);

    return new DynamicPixPayload(
        ImmutableFields.payloadFormatIndicator(),
        ImmutableFields.pointOfInitiationMethod(),
        merchantAccountInfo.toEmvField(),
        ImmutableFields.merchantCategoryCode(),
        ImmutableFields.transactionCurrency(),
        ImmutableFields.countryCode(),
        ImmutableFields.merchantName(merchantName),
        ImmutableFields.merchantCity(merchantCity),
        ImmutableFields.additionalData(txid));
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
        comp.children().stream().map(PixPayloadFactory::toEmvField).toList();

    return new CompositeEmvField(null, comp.id(), (List<EmvField<?>>) children);
  }

  private static CompositeEmvField composite(
      Map<String, EmvNode> map, String id, String fieldName) {
    EmvNode node = map.get(id);
    if (!(node instanceof EmvComposite comp)) {
      throw new IllegalArgumentException("Expected composite field " + id);
    }

    List<? extends EmvField> children =
        comp.children().stream().map(PixPayloadFactory::toEmvField).toList();

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
