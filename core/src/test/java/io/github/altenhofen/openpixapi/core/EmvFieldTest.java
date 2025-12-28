package io.github.altenhofen.openpixapi.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EmvFieldTest {

  @Test
  void emvField_calculatesCorrectLength() {
    EmvField<String> field =
        new EmvField<>(
            "Merchant Name", "59", "JOAO", new StringFormatter(25, CharsetPolicy.EMV_COMMON));

    assertEquals("5904JOAO", field.serialize());
  }
}
