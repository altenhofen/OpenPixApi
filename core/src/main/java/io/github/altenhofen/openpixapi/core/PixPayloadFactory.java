package io.github.altenhofen.openpixapi.core;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * This class is used to create the payload based on the Manual de Padrões para Iniciação do Pix
 *
 * @author Augusto Bussmann Altenhofen
 * @see CompositeEmvField
 * @since 0.01-DEV
 */
final class PixPayloadFactory {
  private PixPayloadFactory() {}

  /**
   * @param pixKey the pixKey as a random key, e-mail, phone number or CPF
   * @param merchantName name of the person who's receiving the transaction
   * @param merchantCity city of the person who's receiving the transaction
   * @param amount value to be received, can be null
   * @return a payload to be used by other classes or serialized to String
   */
  public static StaticPixPayload staticPix(
      String pixKey, String merchantName, String merchantCity, BigDecimal amount) {

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
        ImmutableEmvFields.payloadFormatIndicator(),
        ImmutableEmvFields.pointOfInitiationMethod(),
        merchantAccountInfo.toEmvField(),
        ImmutableEmvFields.merchantCategoryCode(),
        ImmutableEmvFields.transactionCurrency(),
        ImmutableEmvFields.transactionAmount(amount),
        ImmutableEmvFields.countryCode(),
        ImmutableEmvFields.merchantName(merchantName),
        ImmutableEmvFields.merchantCity(merchantCity),
        ImmutableEmvFields.additionalData(null));
  }

  /**
   * @param pspUrl payment service provider URL
   * @param merchantName merchant/recebedor name
   * @param merchantCity merchant/recebedor city
   * @param txid transaction id
   * @return a DynamicPixPayload
   */
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
}
