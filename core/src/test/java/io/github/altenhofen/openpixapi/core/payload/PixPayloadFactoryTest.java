package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.field.EMVCRC16;
import io.github.altenhofen.openpixapi.core.field.EMVField;
import io.github.altenhofen.openpixapi.core.formatter.StringFormatter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PixPayloadFactoryTest {

    @Test
    void staticPix_isDeterministic() {
        PixPayload pix1 = PixPayloadFactory.staticPix(
                "email@test.com",
                "JOAO SILVA",
                "SAO PAULO",
                new BigDecimal("12.59"),
                "TX123"
        );

        PixPayload pix2 = PixPayloadFactory.staticPix(
                "email@test.com",
                "JOAO SILVA",
                "SAO PAULO",
                new BigDecimal("12.59"),
                "TX123"
        );

        assertEquals(pix1.toString(), pix2.toString());
    }

    @Test
    void staticPix_withoutAmount_doesNotContainField54() {
        PixPayload payload = PixPayloadFactory.staticPix(
                "email@test.com",
                "JOAO SILVA",
                "SAO PAULO",
                null,
                "TX123"
        );

        assertFalse(payload.toString().contains("54"));
    }

    @Test
    void staticPix_withoutTxid_doesNotContainField62() {
        PixPayload payload = PixPayloadFactory.staticPix(
                "email@test.com",
                "JOAO SILVA",
                "SAO PAULO",
                new BigDecimal("10.00"),
                null
        );

        assertFalse(payload.toString().contains("62"));
    }
}