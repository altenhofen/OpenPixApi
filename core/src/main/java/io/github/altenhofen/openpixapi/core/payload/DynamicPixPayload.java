package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.field.CompositeEMVField;
import io.github.altenhofen.openpixapi.core.field.EMVField;

public class DynamicPixPayload extends PixPayload {
    DynamicPixPayload(EMVField<Integer> payloadFormatIndicator,
                      EMVField<Integer> pointOfInitiationMethod,
                      CompositeEMVField merchantAccount,
                      EMVField<Integer> merchantCategoryCode,
                      EMVField<Integer> transactionCurrency,
                      EMVField<String> countryCode,
                      EMVField<String> merchantName,
                      EMVField<String> merchantCity,
                      CompositeEMVField additionalData) {
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
