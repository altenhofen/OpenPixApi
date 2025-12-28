package io.github.altenhofen.openpixapi.core;

/**
 * Implements the PaddingPolicy strategy for formatting Strings
 *
 * @author Augusto Bussmann Altenhofen
 * @see PixPayloadFactory
 * @since 0.01-DEV
 */
enum PaddingPolicy {
  /** Will check targetLength and not apply padding. */
  NONE {
    public String pad(String value, int targetLength, char padChar)
        throws IllegalArgumentException {
      checkLength(value.length(), targetLength);
      return value;
    }
  },

  /** Will check targetLength and apply padding to the start of the String */
  LEFT {
    public String pad(String value, int targetLength, char padChar)
        throws IllegalArgumentException {
      checkLength(value.length(), targetLength);

      StringBuilder sb = new StringBuilder(value);
      while (sb.length() < targetLength) {
        sb.insert(0, padChar);
      }
      return sb.toString();
    }
  },

  /** Will check targetLength and apply padding to the end of the String */
  RIGHT {
    public String pad(String value, int targetLength, char padChar)
        throws IllegalArgumentException {
      checkLength(value.length(), targetLength);

      StringBuilder sb = new StringBuilder(value);
      while (sb.length() < targetLength) {
        sb.append(padChar);
      }

      return sb.toString();
    }
  };

  protected void checkLength(int valueLength, int targetLength) throws IllegalArgumentException {
    if (targetLength < valueLength) {
      throw new IllegalArgumentException(
          String.format("Invalid padding length: %d, expected %d", valueLength, targetLength));
    }
  }

  /**
   * @param value to be padded
   * @param targetLength amount of padded characters
   * @param padChar the character to be used at padding, ex: '0' or ' '
   * @return the padded string
   * @throws IllegalArgumentException if targetLength is smaller than valueLength
   */
  public abstract String pad(String value, int targetLength, char padChar)
      throws IllegalArgumentException;
}
