package io.github.altenhofen.openpixapi.core.payload;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class StaticPixPayloadFactoryTest {

    @Test
    void staticPix_isDeterministic() {
        StaticPixPayload pix1 = PixPayloadFactory.staticPix(
                "email@test.com",
                "JOAO SILVA",
                "SAO PAULO",
                new BigDecimal("12.59"),
                "TX123"
        );

        StaticPixPayload pix2 = PixPayloadFactory.staticPix(
                "email@test.com",
                "JOAO SILVA",
                "SAO PAULO",
                new BigDecimal("12.59"),
                "TX123"
        );

        assertEquals(pix1.emvRepresentation(), pix2.emvRepresentation());
    }

    @Test
    void staticPix_withoutAmount_doesNotContainField54() {
        StaticPixPayload payload = PixPayloadFactory.staticPix(
                "email@test.com",
                "JOAO SILVA",
                "SAO PAULO",
                null,
                "TX123"
        );

        assertFalse(payload.emvRepresentation().contains("54"));
    }

    @Test
    void staticPix_withoutTxid_doesNotContainField62() {
        StaticPixPayload payload = PixPayloadFactory.staticPix(
                "email@test.com",
                "JOAO SILVA",
                "SAO PAULO",
                new BigDecimal("10.00"),
                null
        );

        assertFalse(payload.emvRepresentation().contains("62"));
    }
}