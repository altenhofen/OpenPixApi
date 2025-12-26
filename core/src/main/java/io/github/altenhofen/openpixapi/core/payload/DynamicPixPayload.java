package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.payload.field.CompositeEmvField;
import io.github.altenhofen.openpixapi.core.payload.field.EmvField;

/** Represents a Dynamic pix as specified on II_ManualdePadroesparaIniciacaodoPix.pdf */
public class DynamicPixPayload extends PixPayload {
  /**
   * All args constructor for dynamic pix.
   *
   * @param payloadFormatIndicator tag/id 00
   * @param pointOfInitiationMethod tag/id 01
   * @param merchantAccount tag/id 26
   * @param merchantCategoryCode tag/id 52
   * @param transactionCurrency tag/id 53
   * @param countryCode tag/id 58
   * @param merchantName tag/id 59
   * @param merchantCity tag/id 60
   * @param additionalData tag/id 62
   */
  public DynamicPixPayload(
      EmvField<Integer> payloadFormatIndicator,
      EmvField<Integer> pointOfInitiationMethod,
      CompositeEmvField merchantAccount,
      EmvField<Integer> merchantCategoryCode,
      EmvField<Integer> transactionCurrency,
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
        null,
        countryCode,
        merchantName,
        merchantCity,
        additionalData);
  }
}
