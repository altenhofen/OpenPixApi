package io.github.altenhofen.openpixapi.core.formatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.altenhofen.openpixapi.core.payload.field.formatter.CharsetPolicy;
import org.junit.jupiter.api.Test;

public class CharsetPolicyTest {

  @Test
  void charsetPolicy_ShouldAllowChar() {
    CharsetPolicy alphanumericPolicy = CharsetPolicy.EMV_COMMON;
    assertTrue(alphanumericPolicy.allows('a'));

    CharsetPolicy uppercaseAlphanumericPolicy = CharsetPolicy.EMV_COMMON_UPPER;
    assertTrue(uppercaseAlphanumericPolicy.allows('A'));

    CharsetPolicy digitPolicy = CharsetPolicy.DIGITS_ONLY;
    assertTrue(digitPolicy.allows('0'));
  }

  @Test
  void charsetPolicy_ShouldNotAllowChar() {
    final CharsetPolicy charsetPolicy = CharsetPolicy.EMV_COMMON;
    assertFalse(charsetPolicy.allows('#'));

    CharsetPolicy uppercaseAlphanumericPolicy = CharsetPolicy.EMV_COMMON_UPPER;
    assertFalse(uppercaseAlphanumericPolicy.allows('a'));

    CharsetPolicy digitPolicy = CharsetPolicy.DIGITS_ONLY;
    assertFalse(digitPolicy.allows('A'));
  }
}
