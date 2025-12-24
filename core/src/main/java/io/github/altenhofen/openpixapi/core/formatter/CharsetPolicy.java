package io.github.altenhofen.openpixapi.core.formatter;

public enum CharsetPolicy {
    EMV_COMMON(
            "0123456789" +
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "abcdefghijklmnopqrstuvwxyz" +
                    " -./:"
    ),
    EMV_COMMON_UPPER(
            "0123456789" +
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    " -./:"
    ),

    PIX_KEY_RELAXED(
        "0123456789" +
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
        "abcdefghijklmnopqrstuvwxyz" +
        " -./:@"
    ),
    DIGITS_ONLY("0123456789"),
    ;

    final private String charset;

    CharsetPolicy(String s) {
        charset = s;
    }

    public boolean allows(char c) {
        return charset.indexOf(c) >= 0;
    }
}
