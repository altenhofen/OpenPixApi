package io.github.altenhofen.openpixapi.core.field;

import io.github.altenhofen.openpixapi.core.formatter.EMVFormatter;
import io.github.altenhofen.openpixapi.core.formatter.LengthFormatter;
import io.github.altenhofen.openpixapi.core.formatter.PaddingPolicy;
import org.jetbrains.annotations.Nullable;

public class EMVField<T> {
    private final String id;
    private final T value;
    @Nullable
    private final String fieldName;
    private final EMVFormatter<T> formatter;
    private final LengthFormatter lengthFormatter;

    public EMVField(@Nullable String fieldName, String id, T value, EMVFormatter<T> formatter) {
        this.fieldName = fieldName;
        this.id = id;
        this.value = value;
        this.formatter = formatter;
        this.lengthFormatter = new LengthFormatter(2, PaddingPolicy.LEFT);
    }

    public String serialize() {
        String formatted = serializeValue();
        int length = formatted.length();

        return formatId() + lengthFormatter.format(length) + formatted;
    }

    protected String serializeValue() {
        if (formatter == null) {
            throw new IllegalStateException("formatter is null");
        }

        return this.formatter.format(this.value);
    }

    protected String formatId() {
        if (id.length() != 2) {
            throw new IllegalArgumentException(
                    String.format("EMV id must be exactly 2 characters: %s", id)
            );
        }
        return id;
    }


    public @Nullable String getFieldName() {
        return this.fieldName;
    }

    protected T getValue() {
        return this.value;
    }

    public String getId() {
        return this.id;
    }
}

