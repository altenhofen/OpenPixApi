package io.github.altenhofen.openpixapi.core.payload.field.formatter;

import io.github.altenhofen.openpixapi.core.payload.field.CompositeEmvField;
import java.text.Normalizer;
import java.util.Locale;

/**
 * Implements EMVFormatter for String, normalizing the text as of the EMV specifications.
 *
 * @author Augusto Bussmann Altenhofen
 * @see CompositeEmvField
 * @see EmvFormatter
 * @since 0.01-DEV
 */
public final class StringFormatter implements EmvFormatter<String> {
  private final int maxLength;
  private final CharsetPolicy permittedCharset;

  /**
   * Constructor for the StringFormatter class.
   *
   * @param maxLength will be used to trim the string if needed
   * @param permittedCharset charset for validation
   */
  public StringFormatter(int maxLength, CharsetPolicy permittedCharset) {
    this.maxLength = maxLength;
    this.permittedCharset = permittedCharset;
  }

  /**
   * @param input String to be normalized
   * @param maxLength if <code>input.length</code> is larget, we truncate based on this
   * @param fieldName passed for debugging reasons
   * @return normalized string
   */
  private String normalize(String input, int maxLength, String fieldName) {
    if (input == null) {
      throw new IllegalArgumentException(fieldName + " cannot be null");
    }

    // Unicode NFD normalization
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

    // Remove diacritics (combining marks) SÃO PAULO -> SAO PAULO
    normalized = normalized.replaceAll("\\p{M}", "");

    // Uppercase (locale-safe)
    normalized = normalized.toUpperCase(Locale.ROOT).trim();

    if (normalized.isEmpty()) {
      throw new IllegalArgumentException(String.format("%s cannot be empty", fieldName));
    }

    // Validate allowed characters
    for (int i = 0; i < normalized.length(); i++) {
      if (!this.permittedCharset.allows(normalized.charAt(i))) {
        throw new IllegalArgumentException(
            String.format(
                "%s contains invalid characters: %s at position %d", fieldName, input, i));
      }
    }

    // Enforce max length, i.e. truncate
    if (normalized.length() > maxLength) {
      normalized = normalized.substring(0, maxLength);
    }

    return normalized;
  }

  @Override
  public String format(String value) {
    if (value == null) {
      throw new NullPointerException("Variable 'value' is null");
    }

    return normalize(value, maxLength, value);
  }
}
