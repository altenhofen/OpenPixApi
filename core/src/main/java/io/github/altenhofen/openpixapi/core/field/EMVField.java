package io.github.altenhofen.openpixapi.core.field;

import io.github.altenhofen.openpixapi.core.formatter.EMVFormatter;
import io.github.altenhofen.openpixapi.core.formatter.LengthFormatter;
import io.github.altenhofen.openpixapi.core.formatter.PaddingPolicy;

abstract class EMVField<T> {
    private final String id;
    private final T value;
    final private EMVFormatter<T> formatter;
    final private LengthFormatter lengthFormatter;

    EMVField(String id, T value, EMVFormatter<T> formatter) {
        this.id = id;
        this.value = value;
        this.formatter = formatter;
        this.lengthFormatter = new LengthFormatter(2, PaddingPolicy.LEFT);
    }

    public String serialize() {
        String formatted = this.formatter.format(this.value);
        int length = formatted.length();

        return id + lengthFormatter.format(length) + formatted;
    }
}

