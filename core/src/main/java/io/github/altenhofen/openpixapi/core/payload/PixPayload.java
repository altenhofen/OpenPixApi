package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.field.CompositeEMVField;
import io.github.altenhofen.openpixapi.core.field.EMVCRC16;
import io.github.altenhofen.openpixapi.core.field.EMVField;

import java.math.BigDecimal;

public abstract class PixPayload {

    private final EMVField<Integer> payloadFormatIndicator;
    protected final EMVField<Integer> pointOfInitiationMethod;
    protected final CompositeEMVField merchantAccount;
    protected final EMVField<Integer> merchantCategoryCode;
    protected final EMVField<Integer> transactionCurrency;
    protected final EMVField<BigDecimal> transactionAmount;
    protected final EMVField<String> countryCode;
    protected final EMVField<String> merchantName;
    protected final EMVField<String> merchantCity;
    protected final CompositeEMVField additionalData;
    protected final String crc;


    public PixPayload(EMVField<Integer> payloadFormatIndicator,
                      EMVField<Integer> pointOfInitiationMethod,
                      CompositeEMVField merchantAccount,
                      EMVField<Integer> merchantCategoryCode,
                      EMVField<Integer> transactionCurrency,
                      EMVField<BigDecimal> transactionAmount,
                      EMVField<String> countryCode,
                      EMVField<String> merchantName,
                      EMVField<String> merchantCity,
                      CompositeEMVField additionalData
    ) {
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

    static String appendCRC(String payloadUntilCrc) {
        String toSign = payloadUntilCrc + "6304";
        String crc = EMVCRC16.calculate(toSign);
        return toSign + crc;
    }

    /**
     *
     * @return The String representation of payload,
     * can be pasted on websites that generate the QRCode
     * such as <code>pix-qr-decoder/</code>
     */
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

    public String emvRepresentation() {
        return appendCRC(this.NoCrcString());
    }
}
