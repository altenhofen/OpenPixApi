package io.github.altenhofen.openpixapi.qrcode;

import io.github.altenhofen.openpixapi.core.payload.PixPayload;
import io.github.altenhofen.openpixapi.core.payload.PixPayloadFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PixQrCodeGeneratorTest {

    @Test
    void qrGeneration_works() {
        PixPayload payload = PixPayloadFactory.staticPix(
                "email@test.com",
                "JOAO SILVA",
                "SAO PAULO",
                new BigDecimal("10.00"),
                "TX123"
        );

        PixQrOutput output = assertDoesNotThrow(() ->
                PixQrCodeGenerator.generate(payload, PixQrFormat.PNG, 300)
        );
    }


}
