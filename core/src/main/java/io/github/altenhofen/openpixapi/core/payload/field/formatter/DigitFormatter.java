package io.github.altenhofen.openpixapi.core.payload.field.formatter;

/**
 * Implement the EMVFormatter interface for Integer values
 *
 * @author Augusto Bussmann Altenhofen
 * @see io.github.altenhofen.openpixapi.core.payload.PixPayloadFactory
 * @see EmvFormatter
 * @since 0.01-DEV
 */
public final class DigitFormatter implements EmvFormatter<Integer> {
  private final int maxLength;
  private final CharsetPolicy charsetPolicy;
  private final PaddingPolicy paddingPolicy;

  public DigitFormatter(int maxLength, PaddingPolicy paddingPolicy) {
    this.maxLength = maxLength;
    this.charsetPolicy = CharsetPolicy.DIGITS_ONLY;
    this.paddingPolicy = paddingPolicy;
  }

  @Override
  public String format(Integer value) {
    String valueString = value.toString();
    int valueStringLength = valueString.length();
    if (valueStringLength > maxLength) {
      throw new IllegalArgumentException(String.format("Length exceeds maximum of %d", maxLength));
    }

    for (int i = 0; i < valueString.length(); i++) {
      char c = valueString.charAt(i);
      if (!charsetPolicy.allows(c)) {
        throw new IllegalArgumentException(
            String.format("Illegal character '%c' in string '%s' position %d", c, value, i));
      }
    }
    return paddingPolicy.pad(valueString, maxLength, '0');
  }
}
