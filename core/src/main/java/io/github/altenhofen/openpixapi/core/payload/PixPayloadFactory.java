package io.github.altenhofen.openpixapi.core.payload;


import io.github.altenhofen.openpixapi.core.field.CompositeEMVField;
import io.github.altenhofen.openpixapi.core.field.EMVField;
import io.github.altenhofen.openpixapi.core.formatter.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to create the payload based on the
 * Manual de Padrões para Iniciação do Pix
 *
 * @author Augusto Bussmann Altenhofen
 * @see io.github.altenhofen.openpixapi.core.field.CompositeEMVField
 * @since 0.01-DEV
 */
public final class PixPayloadFactory {

    private PixPayloadFactory() {
    }

    /**
     *
     * @param pixKey the pixKey as a random key, e-mail, phone number or CPF
     * @param merchantName name of the person who's receiving the transaction
     * @param merchantCity city of the person who's receiving the transaction
     * @param amount value to be received, can be null
     * @param txid used in dynamic pix, it's basically ignored for now
     * @return a payload to be used by other classes or serialized to String
     */
    public static PixPayload staticPix(
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


        return new PixPayload(
                payloadFormatIndicator()
                , pointOfInitiationMethod()
                , merchantAccount(pixKey)
                , merchantCategoryCode()
                , transactionCurrency()
                , transactionAmount(amount)
                , countryCode()
                , merchantName(merchantName)
                , merchantCity(merchantCity)
                , additionalData(txid)
        );
    }


    private static EMVField<Integer> payloadFormatIndicator() {
        return new EMVField<Integer>(
                "Payload Format Indicator",
                "00",
                1,
                new DigitFormatter(2, PaddingPolicy.LEFT)
        );
    }

    private static EMVField<Integer> pointOfInitiationMethod() {
        return new EMVField<Integer>(
                "Point Initiation Method",
                "01",
                11,
                new DigitFormatter(2, PaddingPolicy.LEFT)
        );
    }

    private static CompositeEMVField merchantAccount(String pixKey) {
        EMVField<String> globallyUniqueIdentifier = new EMVField<String>(
                "Globally Unique Identifier",
                "00",
                "br.gov.bcb.pix",
                new StringFormatter(14, CharsetPolicy.EMV_COMMON)
        );
        EMVField<String> additionalInformation = new EMVField<String>(
                "Aditional Information",
                "02",
                "",
                new StringFormatter(4, CharsetPolicy.EMV_COMMON)
        );

        EMVField<String> pixKeyField = new EMVField<String>(
                "Chave PIX",
                "01",
                pixKey,
                new StringFormatter(36, CharsetPolicy.PIX_KEY_RELAXED)
        );

        return new CompositeEMVField(
                "Merchant Account Information",
                "26",
                List.of(globallyUniqueIdentifier, pixKeyField) // ,additionalInformation) we remove this because most banks do
        );
    }

    private static EMVField<Integer> merchantCategoryCode() {
        return new EMVField<Integer>(
                "Merchant Category Code",
                "52",
                0,
                new DigitFormatter(4, PaddingPolicy.LEFT)
        );
    }

    private static EMVField<Integer> transactionCurrency() {
        return new EMVField<Integer>(
                "Transaction Currency",
                "53",
                986,
                new DigitFormatter(3, PaddingPolicy.LEFT)
        );
    }

    private static EMVField<BigDecimal> transactionAmount(BigDecimal amount) {
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

    private static EMVField<String> countryCode() {
        return new EMVField<String>(
                "Country Code",
                "58",
                "BR",
                new StringFormatter(2, CharsetPolicy.EMV_COMMON_UPPER)
        );
    }

    private static EMVField<String> merchantName(String name) {
        return new EMVField<String>(
                "Merchant Name",
                "59",
                name,
                new StringFormatter(25, CharsetPolicy.EMV_COMMON)
        );
    }

    private static EMVField<String> merchantCity(String merchantCity) {
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

    private static CompositeEMVField additionalData(String txid) {
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

