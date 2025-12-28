package io.github.altenhofen.openpixapi.core;

/**
 * Generic formatter interface for EMV fields.
 *
 * @param <T> type of Data you want to format
 * @see EmvField
 */
interface EmvFormatter<T> {
  /**
   * @param value to be formatted
   * @return String with formatted value
   */
  String format(T value);
}
