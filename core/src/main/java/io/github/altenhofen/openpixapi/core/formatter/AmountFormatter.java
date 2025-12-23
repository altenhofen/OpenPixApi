package io.github.altenhofen.openpixapi.core.formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class AmountFormatter implements EMVFormatter<BigDecimal> {

    @Override
    public String format(BigDecimal value) {
        if (value == null)
            throw new IllegalArgumentException("Amount cannot be null");

        BigDecimal normalized = value
            .setScale(2, RoundingMode.HALF_UP);

        if (normalized.signum() < 0)
            throw new IllegalArgumentException("Amount must be positive");

        return normalized.toPlainString();
    }
}
