package io.github.altenhofen.openpixapi.core.field;

import io.github.altenhofen.openpixapi.core.formatter.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ImmutableFields {
    public static EmvField<Integer> payloadFormatIndicator() {
        return new EmvField<Integer>(
                "Payload Format Indicator",
                "00",
                1,
                new DigitFormatter(2, PaddingPolicy.LEFT)
        );
    }

    public static EmvField<Integer> pointOfInitiationMethod() {
        return new EmvField<Integer>(
                "Point Initiation Method",
                "01",
                11,
                new DigitFormatter(2, PaddingPolicy.LEFT)
        );
    }

    public static EmvField<Integer> merchantCategoryCode() {
        return new EmvField<Integer>(
                "Merchant Category Code",
                "52",
                0,
                new DigitFormatter(4, PaddingPolicy.LEFT)
        );
    }

    public static EmvField<Integer> transactionCurrency() {
        return new EmvField<Integer>(
                "Transaction Currency",
                "53",
                986,
                new DigitFormatter(3, PaddingPolicy.LEFT)
        );
    }

    public static EmvField<BigDecimal> transactionAmount(BigDecimal amount) {
        if (amount == null) {
            return null; // allowed by pix standard
        }

        BigDecimal normalized = amount
                .setScale(2, RoundingMode.UNNECESSARY)
                .stripTrailingZeros();

        return new EmvField<BigDecimal>(
                "Transaction Amount",
                "54",
                normalized,
                new AmountFormatter()
        );
    }

    public static EmvField<String> countryCode() {
        return new EmvField<String>(
                "Country Code",
                "58",
                "BR",
                new StringFormatter(2, CharsetPolicy.EMV_COMMON_UPPER)
        );
    }

    public static EmvField<String> merchantName(String name) {
        return new EmvField<String>(
                "Merchant Name",
                "59",
                name,
                new StringFormatter(25, CharsetPolicy.EMV_COMMON)
        );
    }

    public static EmvField<String> merchantCity(String merchantCity) {
        String normalized = merchantCity
                .trim()
                .toUpperCase();
        return new EmvField<String>(
                "Merchant City",
                "60",
                normalized,
                new StringFormatter(15, CharsetPolicy.EMV_COMMON)
        );
    }

    public static CompositeEmvField additionalData(String txid) {
        if (txid == null || txid.isBlank()) {
            return null;
        }

        return new CompositeEmvField(
                "Additional Data",
                "62",
                List.of(
                        new EmvField<>("TXID", "05", txid,
                                new StringFormatter(25, CharsetPolicy.EMV_COMMON)
                        )
                )
        );
    }
}
