package io.github.altenhofen.openpixapi.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PaddingPolicyTest {
  @Test
  void paddingLeft_shouldPadWithSpacesOnTheLeft() {
    final PaddingPolicy paddingPolicy = PaddingPolicy.LEFT;
    final char padChar = ' ';
    final String word = "tests";
    final int targetLength = 8;
    final String expected = String.valueOf(padChar).repeat(targetLength - word.length()) + word;

    String result = paddingPolicy.pad(word, targetLength, padChar);

    assertEquals(expected, result);
  }

  @Test
  void paddingRight_shouldPadWithSpacesOnTheRight() {
    final PaddingPolicy paddingPolicy = PaddingPolicy.RIGHT;
    final char padChar = ' ';
    final String word = "tests";
    final int targetLength = 8;
    final String expected = word + String.valueOf(padChar).repeat(targetLength - word.length());

    String result = paddingPolicy.pad(word, targetLength, padChar);

    assertEquals(expected, result);
  }

  @Test
  void paddingNone_shouldReturnOriginalValue() {
    final PaddingPolicy paddingPolicy = PaddingPolicy.NONE;

    final char padChar = ' ';
    final String word = "tests";
    final int targetLength = word.length();

    String result = paddingPolicy.pad(word, targetLength, padChar);
    assertEquals(word, result);
  }

  @Test
  void shouldThrowWhenTargetLengthIsSmaller() {
    final PaddingPolicy paddingPolicy = PaddingPolicy.LEFT;
    final char padChar = 'a';
    final String word = "tests";
    final int targetLength = 1;

    assertThrows(
        IllegalArgumentException.class, () -> paddingPolicy.pad(word, targetLength, padChar));
  }

  @Test
  void shouldReturnIdenticalStringOnSameValue() {
    final PaddingPolicy paddingPolicy = PaddingPolicy.LEFT;
    final char padChar = 'a';
    final String word = "tests";
    final int targetLength = word.length();

    String result = paddingPolicy.pad(word, targetLength, padChar);
    assertEquals(word, result);
  }
}
