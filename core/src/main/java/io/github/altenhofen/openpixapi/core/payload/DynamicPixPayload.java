package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.payload.field.CompositeEmvField;
import io.github.altenhofen.openpixapi.core.payload.field.EmvField;
import java.math.BigDecimal;

class DynamicPixPayload extends PixPayload {
  DynamicPixPayload(
      EmvField<Integer> payloadFormatIndicator,
      EmvField<Integer> pointOfInitiationMethod,
      CompositeEmvField merchantAccount,
      EmvField<Integer> merchantCategoryCode,
      EmvField<Integer> transactionCurrency,
      EmvField<BigDecimal> amount,
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
        amount,
        countryCode,
        merchantName,
        merchantCity,
        additionalData);
  }
}
