package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.payload.field.CompositeEmvField;
import io.github.altenhofen.openpixapi.core.payload.field.EmvField;
import java.math.BigDecimal;

public class DynamicPixPayload extends PixPayload {
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
