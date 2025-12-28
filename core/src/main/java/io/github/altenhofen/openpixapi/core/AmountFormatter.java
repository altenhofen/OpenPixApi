package io.github.altenhofen.openpixapi.core;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Implements the EMVFormatter interface to format currency (BigDecimal) values.
 *
 * @author Augusto Bussmann Altenhofen
 * @see PixPayloadFactory
 * @see EmvFormatter
 * @since 0.01-DEV
 */
final class AmountFormatter implements EmvFormatter<BigDecimal> {

  @Override
  public String format(BigDecimal value) {
    if (value == null) throw new IllegalArgumentException("Amount cannot be null");

    BigDecimal normalized = value.setScale(2, RoundingMode.HALF_UP);

    if (normalized.signum() < 0) throw new IllegalArgumentException("Amount must be positive");

    return normalized.toPlainString();
  }
}
