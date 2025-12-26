package io.github.altenhofen.openpixapi.core.payload.field.formatter;

public interface EmvFormatter<T> {
  String format(T value);
}
