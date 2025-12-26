package io.github.altenhofen.openpixapi.core.payload.field.formatter;

/**
 * Generic formatter interface for EMV fields.
 *
 * @param <T> type of Data you want to format
 * @see io.github.altenhofen.openpixapi.core.payload.field.EmvField
 */
public interface EmvFormatter<T> {
  /**
   * @param value to be formatted
   * @return String with formatted value
   */
  String format(T value);
}
