package io.github.altenhofen.openpixapi.core;

import java.math.BigDecimal;

/** Static Pix builder for AbstractPixBuilder */
class StaticPixBuilder extends AbstractPixBuilder<StaticPixBuilder> {

  private String pixKey;
  private BigDecimal amount;

  @Override
  protected StaticPixBuilder self() {
    return this;
  }

  /**
   * @param pixKey can be random, e-mail, phone number (+000000000000) or CPF
   * @return this builder
   */
  public StaticPixBuilder pixKey(String pixKey) {
    this.pixKey = pixKey;
    return this;
  }

  /**
   * Adds the amount to the builder.
   *
   * @param amount the BigDecimal value representing the PixAmount, can be omitted
   * @return the builder
   */
  public StaticPixBuilder amount(BigDecimal amount) {
    this.amount = amount;
    return self();
  }

  @Override
  public StaticPixPayload build() {
    validateCommon();

    if (pixKey == null || pixKey.isBlank()) {
      throw new IllegalStateException("Pix key is required");
    }

    return PixPayloadFactory.staticPix(
        this.pixKey, super.merchantName, super.merchantCity, this.amount);
  }
}
