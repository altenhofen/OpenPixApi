package io.github.altenhofen.openpixapi.core.field;

import io.github.altenhofen.openpixapi.core.formatter.CharsetPolicy;
import io.github.altenhofen.openpixapi.core.formatter.StringFormatter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EMVFieldTest {

    @Test
    void emvField_calculatesCorrectLength() {
        EMVField<String> field = new EMVField<>(
                "Merchant Name",
                "59",
                "JOAO",
                new StringFormatter(25, CharsetPolicy.ALPHANUMERIC)
        );

        assertEquals("5904JOAO", field.serialize());
    }
}
