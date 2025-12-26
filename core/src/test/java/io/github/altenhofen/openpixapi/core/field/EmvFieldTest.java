package io.github.altenhofen.openpixapi.core.field;

import io.github.altenhofen.openpixapi.core.payload.field.formatter.CharsetPolicy;
import io.github.altenhofen.openpixapi.core.payload.field.formatter.StringFormatter;
import io.github.altenhofen.openpixapi.core.payload.field.EmvField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmvFieldTest {

    @Test
    void emvField_calculatesCorrectLength() {
        EmvField<String> field = new EmvField<>(
                "Merchant Name",
                "59",
                "JOAO",
                new StringFormatter(25, CharsetPolicy.EMV_COMMON)
        );

        assertEquals("5904JOAO", field.serialize());
    }
}
