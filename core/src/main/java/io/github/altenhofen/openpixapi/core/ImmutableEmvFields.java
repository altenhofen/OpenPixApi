package io.github.altenhofen.openpixapi.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** Utility for getting EMV Fields. */
class ImmutableEmvFields {
  /**
   * Gets default pix field for Payload Format Indicator (tag=0, value=1).
   *
   * @return the EMV field for default pix field for Payload Format Indicator
   */
  public static EmvField<Integer> payloadFormatIndicator() {
    return new EmvField<Integer>(
        "Payload Format Indicator", "00", 1, new DigitFormatter(2, PaddingPolicy.LEFT));
  }

  /**
   * Gets default pix field for Point of Initiation Method (tag=01, value=11)
   *
   * @return the EMV field for default pix field for Point of Initiation Method
   */
  public static EmvField<Integer> pointOfInitiationMethod() {
    return new EmvField<Integer>(
        "Point Initiation Method", "01", 11, new DigitFormatter(2, PaddingPolicy.LEFT));
  }

  /**
   * Gets the default Merchant Category Code (tag=52, value=0).
   *
   * @return the EMV field for default Merchant Category Code
   */
  public static EmvField<Integer> merchantCategoryCode() {
    return new EmvField<Integer>(
        "Merchant Category Code", "52", 0, new DigitFormatter(4, PaddingPolicy.LEFT));
  }

  /**
   * Gets the default Transaction currency (tag=53, value=986)
   *
   * @return the EMV Field for default Transaction Currency
   */
  public static EmvField<Integer> transactionCurrency() {
    return new EmvField<Integer>(
        "Transaction Currency", "53", 986, new DigitFormatter(3, PaddingPolicy.LEFT));
  }

  /**
   * Gets the amount EMV field for Pix (tag=54)
   *
   * @param amount the monetary value to be considered
   * @return an EMV field as big decimal
   */
  public static EmvField<BigDecimal> transactionAmount(BigDecimal amount) {
    if (amount == null) {
      return null; // allowed by pix standard
    }

    BigDecimal normalized = amount.setScale(2, RoundingMode.UNNECESSARY).stripTrailingZeros();

    return new EmvField<BigDecimal>("Transaction Amount", "54", normalized, new AmountFormatter());
  }

  /**
   * Gets the default country code for Pix
   *
   * @return an EMV field with tag/id 58 and value "BR"
   */
  public static EmvField<String> countryCode() {
    return new EmvField<String>(
        "Country Code", "58", "BR", new StringFormatter(2, CharsetPolicy.EMV_COMMON_UPPER));
  }

  /**
   * Gets the merchant name (normalized)
   *
   * @param name the name (max 25 chars)
   * @return the EMV field with said string
   */
  public static EmvField<String> merchantName(String name) {
    return new EmvField<String>(
        "Merchant Name", "59", name, new StringFormatter(25, CharsetPolicy.EMV_COMMON));
  }

  /**
   * Gets the field for Merchant City (60).
   *
   * @param merchantCity string for city, will be normalized
   * @return the EMV field object
   */
  public static EmvField<String> merchantCity(String merchantCity) {
    String normalized = merchantCity.trim().toUpperCase();
    return new EmvField<String>(
        "Merchant City", "60", normalized, new StringFormatter(15, CharsetPolicy.EMV_COMMON));
  }

  /**
   * Gets the composite field for Additional Data (62) with optional Transaction ID.
   *
   * @param txid Transaction ID (62.05)
   * @return the composite field
   */
  public static CompositeEmvField additionalData(String txid) {
    if (txid == null || txid.isBlank()) {
      return null;
    }

    return new CompositeEmvField(
        "Additional Data",
        "62",
        List.of(
            new EmvField<>("TXID", "05", txid, new StringFormatter(25, CharsetPolicy.EMV_COMMON))));
  }
}
