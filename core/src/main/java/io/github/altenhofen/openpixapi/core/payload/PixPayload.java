package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.field.CompositeEmvField;
import io.github.altenhofen.openpixapi.core.field.EMVCRC16;
import io.github.altenhofen.openpixapi.core.field.EmvField;

import java.math.BigDecimal;

public abstract class PixPayload {

    private final EmvField<Integer> payloadFormatIndicator;
    protected final EmvField<Integer> pointOfInitiationMethod;
    protected final CompositeEmvField merchantAccount;
    protected final EmvField<Integer> merchantCategoryCode;
    protected final EmvField<Integer> transactionCurrency;
    protected final EmvField<BigDecimal> transactionAmount;
    protected final EmvField<String> countryCode;
    protected final EmvField<String> merchantName;
    protected final EmvField<String> merchantCity;
    protected final CompositeEmvField additionalData;
    protected final String crc;


    public PixPayload(EmvField<Integer> payloadFormatIndicator,
                      EmvField<Integer> pointOfInitiationMethod,
                      CompositeEmvField merchantAccount,
                      EmvField<Integer> merchantCategoryCode,
                      EmvField<Integer> transactionCurrency,
                      EmvField<BigDecimal> transactionAmount,
                      EmvField<String> countryCode,
                      EmvField<String> merchantName,
                      EmvField<String> merchantCity,
                      CompositeEmvField additionalData
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

    public EmvField<Integer> getPayloadFormatIndicator() {
        return payloadFormatIndicator;
    }

    public EmvField<Integer> getPointOfInitiationMethod() {
        return pointOfInitiationMethod;
    }

    public CompositeEmvField getMerchantAccount() {
        return merchantAccount;
    }

    public EmvField<Integer> getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public EmvField<Integer> getTransactionCurrency() {
        return transactionCurrency;
    }

    public EmvField<BigDecimal> getTransactionAmount() {
        return transactionAmount;
    }

    public EmvField<String> getCountryCode() {
        return countryCode;
    }

    public EmvField<String> getMerchantName() {
        return merchantName;
    }

    public EmvField<String> getMerchantCity() {
        return merchantCity;
    }

    public CompositeEmvField getAdditionalData() {
        return additionalData;
    }

    public String getCrc() {
        return crc;
    }


    /**
     *
     * @return The String representation of payload,
     * can be pasted on websites that generate the QRCode
     * such as <code>pix-qr-decoder/</code>
     */
    public String getEmv() {
        return appendCRC(this.NoCrcString());
    }
}
