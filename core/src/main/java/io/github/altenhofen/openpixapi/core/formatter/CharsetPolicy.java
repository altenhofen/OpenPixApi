package io.github.altenhofen.openpixapi.core.formatter;
/**
 * Charsets for validation
 * Pix's charset is somewhat more flexible than the
 * specified by EMV standard, and we make the distinction here.
 *
 * @author Augusto Bussmann Altenhofen
 * @since 0.01-DEV
 */
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
        " -./:@+"
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
