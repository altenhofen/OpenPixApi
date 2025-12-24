package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import io.github.altenhofen.openpixapi.core.payload.StaticPixPayload;
import io.github.altenhofen.openpixapi.core.payload.PixPayloadFactory;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class PixQrCodeGeneratorTest {
    final StaticPixPayload payload = PixPayloadFactory.staticPix(
            "email@test.com",
            "JOAO SILVA",
            "SAO PAULO",
            new BigDecimal("10.00"),
            "TX123"
    );

    @Test
    void qrGeneration_works() {
        PixQrOutput output = assertDoesNotThrow(() ->
                PixQrCodeGenerator.generate(payload, PixQrFormat.PNG, 300)
        );
    }

    @Test
    void png_isValidImage() throws Exception {
        PixQrOutput output = PixQrCodeGenerator
                .generate(payload, PixQrFormat.PNG, 300);

        PixQrOutput.Png png = (PixQrOutput.Png) output;

        BufferedImage image = ImageIO.read(
                new ByteArrayInputStream(png.bytes())
        );

        assertNotNull(image);
        assertEquals(300, image.getWidth());
        assertEquals(300, image.getHeight());
    }


    @Test
    void png_decodesBackToPayload() throws Exception {
        PixQrOutput.Png png = (PixQrOutput.Png)
                PixQrCodeGenerator
                        .generate(payload, PixQrFormat.PNG, 300);

        BufferedImage image = ImageIO.read(
                new ByteArrayInputStream(png.bytes())
        );

        BinaryBitmap bitmap = new BinaryBitmap(
                new HybridBinarizer(new BufferedImageLuminanceSource(image))
        );

        Result result = new MultiFormatReader().decode(bitmap);
        assertEquals(payload.toString(), result.getText());
    }


    @Test
    void base64Png_decodesToValidImage() throws Exception {
        PixQrOutput.Base64 base64 = (PixQrOutput.Base64)
                PixQrCodeGenerator.generate(payload, PixQrFormat.BASE64_PNG, 300);

        byte[] bytes = Base64.getDecoder().decode(base64.value());

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
        assertNotNull(image);
    }


}
