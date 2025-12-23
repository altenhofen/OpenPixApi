package io.github.altenhofen.openpixapi.core.formatter;

public interface EMVFormatter<T> {
    String format(T value);
}

