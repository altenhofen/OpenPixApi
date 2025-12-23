package io.github.altenhofen.openpixapi.core.formatter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharsetPolicyTest {

    @Test
    void charsetPolicy_ShouldAllowChar() {
        CharsetPolicy alphanumericPolicy = CharsetPolicy.ALPHANUMERIC;
        assertTrue(alphanumericPolicy.allows('a'));

        CharsetPolicy uppercaseAlphanumericPolicy = CharsetPolicy.UPPERCASE_ALPHANUMERIC;
        assertTrue(uppercaseAlphanumericPolicy.allows('A'));

        CharsetPolicy digitPolicy = CharsetPolicy.DIGITS_ONLY;
        assertTrue(digitPolicy.allows('0'));
    }

    @Test
    void charsetPolicy_ShouldNotAllowChar() {
        final CharsetPolicy charsetPolicy = CharsetPolicy.ALPHANUMERIC;
        assertFalse(charsetPolicy.allows('#'));

        CharsetPolicy uppercaseAlphanumericPolicy = CharsetPolicy.UPPERCASE_ALPHANUMERIC;
        assertFalse(uppercaseAlphanumericPolicy.allows('a'));

        CharsetPolicy digitPolicy = CharsetPolicy.DIGITS_ONLY;
        assertFalse(digitPolicy.allows('A'));
    }
}
