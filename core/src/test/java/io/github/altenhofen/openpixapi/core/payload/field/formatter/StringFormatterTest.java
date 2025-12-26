package io.github.altenhofen.openpixapi.core.payload.field.formatter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StringFormatterTest {
  @Test
  void shouldFormatWithLeftPadding() {
    DigitFormatter formatter = new DigitFormatter(5, PaddingPolicy.LEFT);

    String result = formatter.format(12);

    assertEquals("00012", result);
  }

  @Test
  void shouldFormatWithRightPadding() {
    DigitFormatter formatter = new DigitFormatter(5, PaddingPolicy.RIGHT);

    String result = formatter.format(12);
    assertEquals("12000", result);
  }

  @Test
  void shouldReturnSameValueWhenPaddingIsNone() {
    DigitFormatter formatter = new DigitFormatter(2, PaddingPolicy.NONE);

    String result = formatter.format(12);
    assertEquals("12", result);
  }

  @Test
  void shouldThrowWhenCharacterIsNotAllowed() {
    StringFormatter formatter = new StringFormatter(5, CharsetPolicy.DIGITS_ONLY);

    IllegalArgumentException ex =
        assertThrows(IllegalArgumentException.class, () -> formatter.format("12A"));

    assertTrue(ex.getMessage().contains("A"));
    assertTrue(ex.getMessage().contains("position"));
  }

  @Test
  void shouldTrimWhenValueExceedsMaxLength() {
    String value = "TEST";
    StringFormatter formatter = new StringFormatter(3, CharsetPolicy.EMV_COMMON);
    String trimmed = formatter.format(value);

    assertNotEquals(value.length(), trimmed.length());
  }

  @Test
  void shouldThrowWhenValueIsNull() {
    StringFormatter formatter = new StringFormatter(5, CharsetPolicy.EMV_COMMON);

    assertThrows(NullPointerException.class, () -> formatter.format(null));
  }
}
