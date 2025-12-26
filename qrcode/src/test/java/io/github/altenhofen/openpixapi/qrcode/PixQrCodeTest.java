package io.github.altenhofen.openpixapi.qrcode;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PixQrCodeTest {
    private final String payload = "00020101021126570014BR.GOV.BCB.PIX2535HTTPS://PIX.EXAMPLE.COM/API/WEBHOOK5204000053039865802BR5908JOHN DOE6012PORTO ALEGRE621905150512TX1234567896304B7DF";

    @Test
    public void buildPng() throws IOException, PixQrGenerationException {
        Image image = PixQrCode.from(payload)
                .background(Color.BLACK)
                .foreground(Color.YELLOW)
                .size(1000)
                .toPng();

        assertNotNull(image);
    }

}