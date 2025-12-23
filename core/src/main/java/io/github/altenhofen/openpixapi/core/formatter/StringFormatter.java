package io.github.altenhofen.openpixapi.core.formatter;

public final class StringFormatter implements EMVFormatter<String> {
    final private int maxLength;
    final private CharsetPolicy permittedCharset;
    final private PaddingPolicy paddingPolicy;


    public StringFormatter(int maxLength, CharsetPolicy permittedCharset, PaddingPolicy paddingPolicy) {
        this.maxLength = maxLength;
        this.permittedCharset = permittedCharset;
        this.paddingPolicy = paddingPolicy;
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

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (!permittedCharset.allows(c)) {
                throw new IllegalArgumentException(
                        String.format("Invalid character '%c' at position %d", c, i)
                );
            }
        }

        char padChar = '0';
        return paddingPolicy.pad(value, maxLength, padChar);
    }
}
