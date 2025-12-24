package io.github.altenhofen.openpixapi.qrcode;

import io.github.altenhofen.openpixapi.core.payload.PixPayload;
import io.github.altenhofen.openpixapi.core.payload.PixPayloadFactory;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PixQrSvgWriterTest {
    final PixQrConfig config = PixQrConfig.builder().foreground(Color.YELLOW).build();

    final PixPayload staticPayload = PixPayloadFactory.staticPix(
            "email@test.com",
            "JOAO SILVA",
            "SAO PAULO",
            new BigDecimal("10.00"),
            "TX123"
    );


    @Test
    void svg_containsSvgRoot() throws PixQrGenerationException {
        PixQrOutput output = PixQrCodeGenerator.generate(
                staticPayload.getEmv(),
                PixQrFormat.SVG,
                config);


        PixQrOutput.Svg svg = (PixQrOutput.Svg) output;
        assertTrue(svg.toString().contains("<svg"));
    }

}