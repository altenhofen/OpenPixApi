package io.github.altenhofen.openpixapi.core.field;

import io.github.altenhofen.openpixapi.core.formatter.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ImmutableFields {
    public static EMVField<Integer> payloadFormatIndicator() {
        return new EMVField<Integer>(
                "Payload Format Indicator",
                "00",
                1,
                new DigitFormatter(2, PaddingPolicy.LEFT)
        );
    }

    public static EMVField<Integer> pointOfInitiationMethod() {
        return new EMVField<Integer>(
                "Point Initiation Method",
                "01",
                11,
                new DigitFormatter(2, PaddingPolicy.LEFT)
        );
    }

    public static EMVField<Integer> merchantCategoryCode() {
        return new EMVField<Integer>(
                "Merchant Category Code",
                "52",
                0,
                new DigitFormatter(4, PaddingPolicy.LEFT)
        );
    }

    public static EMVField<Integer> transactionCurrency() {
        return new EMVField<Integer>(
                "Transaction Currency",
                "53",
                986,
                new DigitFormatter(3, PaddingPolicy.LEFT)
        );
    }

    public static EMVField<BigDecimal> transactionAmount(BigDecimal amount) {
        if (amount == null) {
            return null; // allowed by pix standard
        }

        BigDecimal normalized = amount
                .setScale(2, RoundingMode.UNNECESSARY)
                .stripTrailingZeros();

        return new EMVField<BigDecimal>(
                "Transaction Amount",
                "54",
                normalized,
                new AmountFormatter()
        );
    }

    public static EMVField<String> countryCode() {
        return new EMVField<String>(
                "Country Code",
                "58",
                "BR",
                new StringFormatter(2, CharsetPolicy.EMV_COMMON_UPPER)
        );
    }

    public static EMVField<String> merchantName(String name) {
        return new EMVField<String>(
                "Merchant Name",
                "59",
                name,
                new StringFormatter(25, CharsetPolicy.EMV_COMMON)
        );
    }

    public static EMVField<String> merchantCity(String merchantCity) {
        String normalized = merchantCity
                .trim()
                .toUpperCase();
        return new EMVField<String>(
                "Merchant City",
                "60",
                normalized,
                new StringFormatter(15, CharsetPolicy.EMV_COMMON)
        );
    }

    public static CompositeEMVField additionalData(String txid) {
        if (txid == null || txid.isBlank()) {
            return null;
        }

        return new CompositeEMVField(
                "Additional Data",
                "62",
                List.of(
                        new EMVField<>("TXID", "05", txid,
                                new StringFormatter(25, CharsetPolicy.EMV_COMMON)
                        )
                )
        );
    }
}
