package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.field.CompositeEmvField;
import io.github.altenhofen.openpixapi.core.field.EmvField;

import java.math.BigDecimal;

/**
 * Data structure that represents a StaticPixPayload object.
 * Studying this class is core to understanding how this library works.
 *
 *
 * @author Augusto Bussmann Altenhofen
 * @see PixPayloadFactory
 * @since 0.01-DEV
 */
public class StaticPixPayload extends PixPayload {


    StaticPixPayload(EmvField<Integer> payloadFormatIndicator,
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
                additionalData
        );
    }
}
