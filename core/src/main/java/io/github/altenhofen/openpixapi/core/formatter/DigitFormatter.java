package io.github.altenhofen.openpixapi.core.formatter;

public class DigitFormatter implements EMVFormatter<Integer>{
    final private int maxLength;
    final private CharsetPolicy charsetPolicy;
    final private PaddingPolicy paddingPolicy;

    public DigitFormatter(int maxLength, PaddingPolicy paddingPolicy) {
        this.maxLength = maxLength;
        this.charsetPolicy = CharsetPolicy.DIGITS_ONLY;
        this.paddingPolicy = paddingPolicy;
    }

    @Override
    public String format(Integer value) {
        String valueString = value.toString();
        int valueStringLength = valueString.length();
        if (valueStringLength > maxLength) {
            throw new IllegalArgumentException(
                    String.format("Length exceeds maximum of %d", maxLength)
            );
        }

        for(int i = 0; i < valueString.length(); i++) {
            char c =  valueString.charAt(i);
            if (!charsetPolicy.allows(c)) {
                throw new IllegalArgumentException(
                        String.format("Illegal character '%c' in string '%s' position %d", c, value, i)
                );
            }
        }
        return paddingPolicy.pad(valueString, maxLength, '0');
    }
}
