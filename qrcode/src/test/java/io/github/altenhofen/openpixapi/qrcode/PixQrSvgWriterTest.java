package io.github.altenhofen.openpixapi.qrcode;

import io.github.altenhofen.openpixapi.core.payload.StaticPixPayload;
import io.github.altenhofen.openpixapi.core.payload.PixPayloadFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PixQrSvgWriterTest {


    @Test
    void svg_containsSvgRoot() throws PixQrGenerationException {
        StaticPixPayload payload = PixPayloadFactory.staticPix(
                "email@test.com",
                "JOAO SILVA",
                "SAO PAULO",
                new BigDecimal("10.00"),
                "TX123"
        );
        PixQrOutput output = PixQrCodeGenerator.generate(
                payload,
                PixQrFormat.SVG,
                300
        );

        PixQrOutput.Svg svg = (PixQrOutput.Svg) output;
        assertTrue(svg.toString().contains("<svg"));
    }
}