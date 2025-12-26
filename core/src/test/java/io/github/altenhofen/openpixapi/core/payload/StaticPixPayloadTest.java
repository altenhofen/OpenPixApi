package io.github.altenhofen.openpixapi.core.payload;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class StaticPixPayloadTest {
    @Test
    void crc_isValid() {
        StaticPixPayload payload = PixPayloadFactory.staticPix(
                "email@test.com",
                "JOAO SILVA",
                "SAO PAULO",
                new BigDecimal("10.00"),
                "TX123"
        );

        String payloadString = payload.getEmv();
        String withoutCrc = payloadString.substring(0, payloadString.length() - 4);
        String crc = payloadString.substring(payloadString.length() - 4);

        String recalculated = EMVCRC16.calculate(withoutCrc);

        assertEquals(crc, recalculated);
    }
}