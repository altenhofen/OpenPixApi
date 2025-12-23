package io.github.altenhofen.openpixapi.core.formatter;

public enum CharsetPolicy {
    ALPHANUMERIC("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz./+-:* "),
    UPPERCASE_ALPHANUMERIC("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    DIGITS_ONLY("0123456789"),
    ;

    final private String charset;

    CharsetPolicy(String s) {
        charset =  s;
    }

    public boolean allows(char c) {
        return charset.indexOf(c) >= 0;
    }
}
