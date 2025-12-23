package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.field.CompositeEMVField;
import io.github.altenhofen.openpixapi.core.field.EMVCRC16;
import io.github.altenhofen.openpixapi.core.field.EMVField;

import java.math.BigDecimal;

public class PixPayload {
    private final EMVField<Integer> payloadFormatIndicator;
    private final EMVField<Integer> pointOfInitiationMethod;
    private final CompositeEMVField merchantAccount;
    private final EMVField<Integer> merchantCategoryCode;
    private final EMVField<Integer> transactionCurrency;
    private final EMVField<BigDecimal> transactionAmount;
    private final EMVField<String> countryCode;
    private final EMVField<String> merchantName;
    private final EMVField<String> merchantCity;
    private final CompositeEMVField additionalData;


    PixPayload(EMVField<Integer> payloadFormatIndicator, EMVField<Integer> pointOfInitiationMethod, CompositeEMVField merchantAccount, EMVField<Integer> merchantCategoryCode, EMVField<Integer> transactionCurrency, EMVField<BigDecimal> transactionAmount, EMVField<String> countryCode, EMVField<String> merchantName, EMVField<String> merchantCity, CompositeEMVField additionalData) {
        this.payloadFormatIndicator = payloadFormatIndicator;
        this.pointOfInitiationMethod = pointOfInitiationMethod;
        this.merchantAccount = merchantAccount;
        this.merchantCategoryCode = merchantCategoryCode;
        this.transactionCurrency = transactionCurrency;
        this.transactionAmount = transactionAmount;
        this.countryCode = countryCode;
        this.merchantName = merchantName;
        this.merchantCity = merchantCity;
        this.additionalData = additionalData;
    }

    @Override
    public String toString() {
        return this.payloadFormatIndicator.serialize()
                + pointOfInitiationMethod.serialize()
                +  merchantAccount.serialize()
                + merchantCategoryCode.serialize()
                + transactionCurrency.serialize()
                + transactionAmount.serialize()
                + countryCode.serialize()
                + merchantName.serialize()
                + merchantCity.serialize()
                + additionalData.serialize();
    }
    // We chose not to include the CRC on toString for scalability reasons
    static String appendCRC(String payload) {
        String toSign = payload + "6304";
        String crc = EMVCRC16.calculate(toSign);
        return toSign + crc;
    }
}
