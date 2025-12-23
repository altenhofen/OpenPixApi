package io.github.altenhofen.openpixapi.core.formatter;

import java.text.Normalizer;
import java.util.Locale;

public final class StringFormatter implements EMVFormatter<String> {
    final private int maxLength;
    final private CharsetPolicy permittedCharset;


    public StringFormatter(int maxLength, CharsetPolicy permittedCharset) {
        this.maxLength = maxLength;
        this.permittedCharset = permittedCharset;
    }

    private String normalize(
            String input,
            int maxLength,
            String fieldName
    ) {
        if (input == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }

        // Unicode NFD normalization
        String normalized = Normalizer.normalize(
                input,
                Normalizer.Form.NFD
        );

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
                throw new IllegalArgumentException(String.format("%s contains invalid characters: %s at position %d", fieldName, input, i));
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

        if (value.length() > maxLength) {
            throw new IllegalArgumentException(
                    String.format("Invalid length for %s, expected %d got %d", value, maxLength, value.length()));
        }

        return normalize(value, maxLength, value);
    }
}
