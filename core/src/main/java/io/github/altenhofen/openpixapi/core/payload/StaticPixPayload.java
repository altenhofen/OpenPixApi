package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.field.CompositeEMVField;
import io.github.altenhofen.openpixapi.core.field.EMVCRC16;
import io.github.altenhofen.openpixapi.core.field.EMVField;

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
public class StaticPixPayload extends AbstractPixPayload {


    StaticPixPayload(EMVField<Integer> payloadFormatIndicator,
                     EMVField<Integer> pointOfInitiationMethod,
                     CompositeEMVField merchantAccount,
                     EMVField<Integer> merchantCategoryCode,
                     EMVField<Integer> transactionCurrency,
                     EMVField<BigDecimal> transactionAmount,
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
                transactionAmount,
                countryCode,
                merchantName,
                merchantCity,
                additionalData
        );
    }
}
