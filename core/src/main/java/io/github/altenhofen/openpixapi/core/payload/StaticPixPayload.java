package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.payload.field.CompositeEmvField;
import io.github.altenhofen.openpixapi.core.payload.field.EmvField;
import java.math.BigDecimal;

/**
 * Data structure that represents the static pix PixPayload object.
 *
 * @see PixPayloadFactory
 */
public class StaticPixPayload extends PixPayload {
  /**
   * Super constructor for static pix payload
   *
   * @param payloadFormatIndicator as specified on EMV QRCode Pix spec
   * @param pointOfInitiationMethod as specified on EMV QRCode Pix spec
   * @param merchantAccount as specified on EMV QRCode Pix spec
   * @param merchantCategoryCode as specified on EMV QRCode Pix spec
   * @param transactionCurrency as specified on EMV QRCode Pix spec
   * @param transactionAmount as specified on EMV QRCode Pix spec
   * @param countryCode as specified on EMV QRCode Pix spec
   * @param merchantName as specified on EMV QRCode Pix spec
   * @param merchantCity as specified on EMV QRCode Pix spec
   * @param additionalData as specified on EMV QRCode Pix spec
   */
  public StaticPixPayload(
      EmvField<Integer> payloadFormatIndicator,
      EmvField<Integer> pointOfInitiationMethod,
      CompositeEmvField merchantAccount,
      EmvField<Integer> merchantCategoryCode,
      EmvField<Integer> transactionCurrency,
      EmvField<BigDecimal> transactionAmount,
      EmvField<String> countryCode,
      EmvField<String> merchantName,
      EmvField<String> merchantCity,
      CompositeEmvField additionalData) {
    super(
        payloadFormatIndicator,
        pointOfInitiationMethod,
        merchantAccount,
        merchantCategoryCode,
        transactionCurrency,
        transactionAmount,
        countryCode,
        merchantName,
        merchantCity,
        additionalData);
  }
}
