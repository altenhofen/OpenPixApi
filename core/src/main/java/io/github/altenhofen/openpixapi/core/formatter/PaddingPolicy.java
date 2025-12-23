package io.github.altenhofen.openpixapi.core.formatter;

public enum PaddingPolicy {
    NONE {
        public String pad(String value, int targetLength, char padChar) throws IllegalArgumentException {
            checkLength(value.length(),  targetLength);
            return value;
        }
    },
    LEFT {
        public String pad(String value, int targetLength, char padChar) throws IllegalArgumentException {
            checkLength(value.length(),  targetLength);

            StringBuilder sb = new StringBuilder(value);
            while (sb.length() < targetLength) {
                sb.insert(0, padChar);
            }
            return sb.toString();
        }
    },
    RIGHT {
        public String pad(String value, int targetLength, char padChar) throws IllegalArgumentException {
            checkLength(value.length(),  targetLength);

            StringBuilder sb = new StringBuilder(value);
            while (sb.length() < targetLength) {
                sb.append(padChar);
            }

            return sb.toString();
        }
    }
    ;

    protected void checkLength(int valueLength, int targetLength) throws IllegalArgumentException {
        if (targetLength < valueLength) {
            throw new IllegalArgumentException(
                    String.format("Invalid padding length: %d, expected %d", valueLength, targetLength)
            );
        }
    }

    public abstract String pad(String value, int targetLength, char padChar) throws IllegalArgumentException;
}
