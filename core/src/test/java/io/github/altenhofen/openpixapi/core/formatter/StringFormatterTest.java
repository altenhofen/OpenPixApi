package io.github.altenhofen.openpixapi.core.formatter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class StringFormatterTest {
    @Test
    void shouldFormatWithLeftPadding() {
        StringFormatter formatter = new StringFormatter(
                5,
                CharsetPolicy.DIGITS_ONLY,
                PaddingPolicy.LEFT
        );

        String result = formatter.format("12");

        assertEquals("00012", result);
    }

    @Test
    void shouldFormatWithRightPadding() {
        StringFormatter formatter = new StringFormatter(
                5,
                CharsetPolicy.DIGITS_ONLY,
                PaddingPolicy.RIGHT
        );

        String result = formatter.format("12");
        assertEquals("12000", result);
    }

    @Test
    void shouldReturnSameValueWhenPaddingIsNone() {
        StringFormatter formatter = new StringFormatter(
                2,
                CharsetPolicy.DIGITS_ONLY,
                PaddingPolicy.NONE
        );

        String result = formatter.format("12");
        assertEquals("12", result);
    }

    @Test
    void shouldThrowWhenCharacterIsNotAllowed() {
        StringFormatter formatter = new StringFormatter(
                5,
                CharsetPolicy.DIGITS_ONLY,
                PaddingPolicy.LEFT
        );

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> formatter.format("12A")
        );

        assertTrue(ex.getMessage().contains("A"));
        assertTrue(ex.getMessage().contains("position"));
    }

    @Test
    void shouldThrowWhenValueExceedsMaxLength() {
        StringFormatter formatter = new StringFormatter(
                3,
                CharsetPolicy.ALPHANUMERIC,
                PaddingPolicy.NONE
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> formatter.format("TEST")
        );
    }

    @Test
    void shouldThrowWhenValueIsNull() {
        StringFormatter formatter = new StringFormatter(
                5,
                CharsetPolicy.ALPHANUMERIC,
                PaddingPolicy.NONE
        );

        assertThrows(
                NullPointerException.class,
                () -> formatter.format(null)
        );
    }
}
