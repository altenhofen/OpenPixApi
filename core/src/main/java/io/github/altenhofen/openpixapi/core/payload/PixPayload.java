package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.field.CompositeEMVField;
import io.github.altenhofen.openpixapi.core.field.EMVCRC16;
import io.github.altenhofen.openpixapi.core.field.EMVField;

import java.math.BigDecimal;

/**
 * Data structure that represents a PixPayload object.
 * Studying this class is core to understanding how this library works.
 *
 *
 * @author Augusto Bussmann Altenhofen
 * @see PixPayloadFactory
 * @since 0.01-DEV
 */
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
    private final String crc;

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
        this.crc = appendCRC(this.NoCrcString());
    }

    /**
     * It is required to have all fields other than CRC calculated
     *
     * @return a String with all serialized values, but the CRC
     */
    private String NoCrcString() {
        StringBuilder sb = new StringBuilder();

        sb.append(payloadFormatIndicator.serialize());
        sb.append(pointOfInitiationMethod.serialize());
        sb.append(merchantAccount.serialize());
        sb.append(merchantCategoryCode.serialize());
        sb.append(transactionCurrency.serialize());

        if (transactionAmount != null) {
            sb.append(transactionAmount.serialize());
        }

        sb.append(countryCode.serialize());
        sb.append(merchantName.serialize());
        sb.append(merchantCity.serialize());
        
        if (additionalData != null) {
            sb.append(additionalData.serialize());
        }

        return sb.toString();
    }

    /**
     *
     * @return The String representation of payload,
     * can be pasted on websites that generate the QRCode
     * such as <code>pix-qr-decoder/</code>
     */
    @Override
    public String toString() {
        return appendCRC(this.NoCrcString());
    }

    static String appendCRC(String payloadUntilCrc) {
        String toSign = payloadUntilCrc + "6304";
        String crc = EMVCRC16.calculate(toSign);
        return toSign + crc;
    }

    public EMVField<Integer> getPayloadFormatIndicator() {
        return payloadFormatIndicator;
    }

    public EMVField<Integer> getPointOfInitiationMethod() {
        return pointOfInitiationMethod;
    }

    public CompositeEMVField getMerchantAccount() {
        return merchantAccount;
    }

    public EMVField<Integer> getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public EMVField<Integer> getTransactionCurrency() {
        return transactionCurrency;
    }

    public EMVField<BigDecimal> getTransactionAmount() {
        return transactionAmount;
    }

    public EMVField<String> getCountryCode() {
        return countryCode;
    }

    public EMVField<String> getMerchantName() {
        return merchantName;
    }

    public EMVField<String> getMerchantCity() {
        return merchantCity;
    }

    public CompositeEMVField getAdditionalData() {
        return additionalData;
    }

    public String getCrc() {
        return crc;
    }
}
