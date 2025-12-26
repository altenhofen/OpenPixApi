package io.github.altenhofen.openpixapi.core.payload;

import java.math.BigDecimal;

abstract class AbstractPixBuilder<T extends AbstractPixBuilder<T>> {
  protected String merchantName;
  protected String merchantCity;
  protected BigDecimal amount;
  protected String txid;

  protected abstract T self();

  /**
   * Adds Merchant name as specified in the Pix manual.
   *
   * @param name name of the merchant, will be normalized
   * @return the builder
   */
  public T merchantName(String name) {
    this.merchantName = name;
    return self();
  }

  /**
   * Adds the merchant's city to the builder.
   *
   * @param city string that will be normalized
   * @return the builder object
   */
  public T merchantCity(String city) {
    this.merchantCity = city;
    return self();
  }

  /**
   * Adds the amount to the builder.
   *
   * @param amount the BigDecimal value representing the PixAmount, can be omitted
   * @return the builder
   */
  public T amount(BigDecimal amount) {
    this.amount = amount;
    return self();
  }

  /**
   * Adds the transaction ID to the builder
   *
   * @param txid transaction ID
   * @return the builder
   */
  public T txid(String txid) {
    this.txid = txid;
    return self();
  }

  protected void validateCommon() {
    if (merchantName == null || merchantName.isBlank())
      throw new IllegalStateException("Merchant name is required");

    if (merchantCity == null || merchantCity.isBlank())
      throw new IllegalStateException("Merchant city is required");
  }

  /**
   * Builds the Pix payload.
   *
   * @return the PixPayload object that has been build
   */
  public abstract PixPayload build();
}
