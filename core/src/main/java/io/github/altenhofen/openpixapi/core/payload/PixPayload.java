package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.payload.field.CompositeEmvField;
import io.github.altenhofen.openpixapi.core.payload.field.EmvField;
import java.math.BigDecimal;

/** Abstract implementation of the Pix Payload as specified in the manual. */
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

  PixPayload(
      EmvField<Integer> payloadFormatIndicator,
      EmvField<Integer> pointOfInitiationMethod,
      CompositeEmvField merchantAccount,
      EmvField<Integer> merchantCategoryCode,
      EmvField<Integer> transactionCurrency,
      EmvField<BigDecimal> transactionAmount,
      EmvField<String> countryCode,
      EmvField<String> merchantName,
      EmvField<String> merchantCity,
      CompositeEmvField additionalData) {
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
   * Getter for PayloadFormatIndicator
   *
   * @return the EMV Field with the integer value
   */
  public EmvField<Integer> getPayloadFormatIndicator() {
    return payloadFormatIndicator;
  }

  /**
   * Getter for PointOfInitiationMethod.
   *
   * @return the EMV Field with the integer value
   */
  public EmvField<Integer> getPointOfInitiationMethod() {
    return pointOfInitiationMethod;
  }

  /**
   * Getter for MerchantAccount.
   *
   * @return the EMV composite Field with the integer value
   */
  public CompositeEmvField getMerchantAccount() {
    return merchantAccount;
  }

  /**
   * Getter for MerchantCategoryCode.
   *
   * @return the EMV field with the integer value
   */
  public EmvField<Integer> getMerchantCategoryCode() {
    return merchantCategoryCode;
  }

  /**
   * Getter for TransactionCurrency.
   *
   * @return the EMV field with the integer value
   */
  public EmvField<Integer> getTransactionCurrency() {
    return transactionCurrency;
  }

  /**
   * Getter for TransactionAmount.
   *
   * @return the EMV field with the BigDecimal value
   */
  public EmvField<BigDecimal> getTransactionAmount() {
    return transactionAmount;
  }

  /**
   * Getter for CountryCode.
   *
   * @return the EMV field with the string value
   */
  public EmvField<String> getCountryCode() {
    return countryCode;
  }

  /**
   * Getter for MerchantName.
   *
   * @return the EMV field with the string value
   */
  public EmvField<String> getMerchantName() {
    return merchantName;
  }

  /**
   * Getter for MerchantCity.
   *
   * @return the EMV field with the string value
   */
  public EmvField<String> getMerchantCity() {
    return merchantCity;
  }

  /**
   * Getter for Additional Data.
   *
   * @return the EMV composite field with the value
   */
  public CompositeEmvField getAdditionalData() {
    return additionalData;
  }

  /**
   * Getter for the Crc field.
   *
   * @return the String for the crc field (it's not an EMV value!)
   * @see EMVCRC16
   */
  public String getCrc() {
    return crc;
  }

  /**
   * @return The String representation of payload, can be pasted on websites that generate the
   *     QRCode such as <code>pix-qr-decoder/</code>
   */
  public String getEmv() {
    return appendCRC(this.NoCrcString());
  }
}
