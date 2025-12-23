package io.github.altenhofen.openpixapi.core.pixbuilder;


import io.github.altenhofen.openpixapi.core.field.CompositeEMVField;
import io.github.altenhofen.openpixapi.core.field.EMVCRC16;
import io.github.altenhofen.openpixapi.core.field.EMVField;
import io.github.altenhofen.openpixapi.core.formatter.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public final class PixPayloadFactory {

    private PixPayloadFactory() {
    }

    public static String staticPix(
            String pixKey,
            String merchantName,
            String merchantCity,
            BigDecimal amount,
            String txid
    ) {

        Objects.requireNonNull(pixKey, "pixKey");
        Objects.requireNonNull(merchantName, "merchantName");
        Objects.requireNonNull(merchantCity, "merchantCity");

        if (merchantName.isBlank()) {
            throw new IllegalArgumentException("Merchant name cannot be blank");
        }

        if (merchantCity.isBlank()) {
            throw new IllegalArgumentException("Merchant city cannot be blank");
        }


        String payload =
                payloadFormatIndicator()
                        + pointOfInitiationMethod()
                        + merchantAccount(pixKey)
                        + merchantCategoryCode()
                        + transactionCurrency()
                        + transactionAmount(amount)
                        + countryCode()
                        + merchantName(merchantName)
                        + merchantCity(merchantCity)
                        + additionalData(txid);

        return appendCRC(payload);
    }


    private static String appendCRC(String payload) {
        String toSign = payload + "6304";
        String crc = EMVCRC16.calculate(toSign);
        return toSign + crc;
    }

    private static String payloadFormatIndicator() {
        return new EMVField<Integer>(
                "Payload Format Indicator",
                "00",
                1,
                new LengthFormatter(2, PaddingPolicy.LEFT)
        ).serialize();
    }

    private static String pointOfInitiationMethod() {
        return new EMVField<Integer>(
                "Point Initiation Method",
                "01",
                11,
                new LengthFormatter(2, PaddingPolicy.LEFT)
        ).serialize();
    }

    private static String merchantAccount(String pixKey) {
        EMVField<String> globallyUniqueIdentifier = new EMVField<String>(
                "Globally Unique Identifier",
                "00",
                "br.gov.bcb.pix",
                new StringFormatter(14, CharsetPolicy.ALPHANUMERIC, PaddingPolicy.NONE)
        );
        EMVField<String> additionalInformation = new EMVField<String>(
                "Aditional Information",
                "02",
                "",
                new StringFormatter(4, CharsetPolicy.ALPHANUMERIC, PaddingPolicy.NONE)
        );

        EMVField<String> pixKeyField = new EMVField<String>(
                "Chave PIX",
                "01",
                pixKey,
                new StringFormatter(36, CharsetPolicy.ALPHANUMERIC, PaddingPolicy.NONE)
        );

        return new CompositeEMVField(
                "Merchant Account Information",
                "26",
                List.of(globallyUniqueIdentifier, pixKeyField) // ,additionalInformation) we remove this because most banks do
        ).serialize();
    }

    private static String merchantCategoryCode() {
        return new EMVField<Integer>(
                "Merchant Category Code",
                "52",
                0,
                new LengthFormatter(4, PaddingPolicy.LEFT)
        ).serialize();
    }

    private static String transactionCurrency() {
        return new EMVField<Integer>(
                "Transaction Currency",
                "53",
                986,
                new LengthFormatter(3, PaddingPolicy.LEFT)
        ).serialize();
    }

    private static String transactionAmount(BigDecimal amount) {
        if (amount == null) {
            return ""; // allowed for static Pix
        }

        BigDecimal normalized = amount
                .setScale(2, RoundingMode.UNNECESSARY)
                .stripTrailingZeros();

        return new EMVField<BigDecimal>(
                "Transaction Amount",
                "54",
                normalized,
                new AmountFormatter()
        ).serialize();
    }

    private static String countryCode() {
        return new EMVField<String>(
                "Country Code",
                "58",
                "BR",
                new StringFormatter(2, CharsetPolicy.UPPERCASE_ALPHANUMERIC, PaddingPolicy.NONE)
        ).serialize();
    }

    private static String merchantName(String name) {
        return new EMVField<String>(
                "Merchant Name",
                "59",
                name,
                new StringFormatter(25, CharsetPolicy.ALPHANUMERIC, PaddingPolicy.NONE)
        ).serialize();
    }

    private static String merchantCity(String merchantCity) {
        String normalized = merchantCity
                .trim()
                .toUpperCase();
        return new EMVField<String>(
                "Merchant City",
                "60",
                normalized,
                new StringFormatter(15, CharsetPolicy.ALPHANUMERIC, PaddingPolicy.NONE)
        ).serialize();
    }

    private static String additionalData(String txid) {
        if (txid == null || txid.isBlank()) {
            return "";
        }

        return new CompositeEMVField(
                "Additional Data",
                "62",
                List.of(
                        new EMVField<>("TXID", "05", txid,
                                new StringFormatter(25, CharsetPolicy.ALPHANUMERIC, PaddingPolicy.NONE
                                )))).serialize();
    }


}

