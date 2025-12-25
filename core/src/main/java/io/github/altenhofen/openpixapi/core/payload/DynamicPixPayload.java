package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.field.CompositeEmvField;
import io.github.altenhofen.openpixapi.core.field.EmvField;

public class DynamicPixPayload extends PixPayload {
    DynamicPixPayload(EmvField<Integer> payloadFormatIndicator,
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
                additionalData
        );
    }
}
