package io.github.altenhofen.openpixapi.core;

/**
 * Implement the EMVFormatter interface for Integer values
 *
 * @see PixPayloadFactory
 * @see EmvFormatter
 */
final class DigitFormatter implements EmvFormatter<Integer> {
  private final int length;
  private final CharsetPolicy charsetPolicy;
  private final PaddingPolicy paddingPolicy;

  /**
   * Receives configuration for formatting Digits.
   *
   * @param length length of the field, it's the value that's padded by paddingPolicy
   * @param paddingPolicy can be LEFT, RIGHT or NONE, dictates the padding
   */
  public DigitFormatter(int length, PaddingPolicy paddingPolicy) {
    this.length = length;
    this.charsetPolicy = CharsetPolicy.DIGITS_ONLY;
    this.paddingPolicy = paddingPolicy;
  }

  /**
   * Formats a digit value for EMV Field.
   *
   * @param value value to be formatted
   * @return the padded value
   */
  @Override
  public String format(Integer value) {
    String valueString = value.toString();
    int valueStringLength = valueString.length();
    if (valueStringLength > length) {
      throw new IllegalArgumentException(String.format("Length exceeds maximum of %d", length));
    }

    for (int i = 0; i < valueString.length(); i++) {
      char c = valueString.charAt(i);
      if (!charsetPolicy.allows(c)) {
        throw new IllegalArgumentException(
            String.format("Illegal character '%c' in string '%s' position %d", c, value, i));
      }
    }
    return paddingPolicy.pad(valueString, length, '0');
  }
}
