package io.github.altenhofen.openpixapi.qrcode;

import static org.junit.jupiter.api.Assertions.*;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import io.github.altenhofen.openpixapi.core.Pix;
import io.github.altenhofen.openpixapi.core.PixPayload;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;

public class PixQrCodeGeneratorTest {
  final PixQrConfig config = PixQrConfig.defaultConfig();
  final PixPayload staticPixPayload =
      Pix.newStatic("email@test.com", "JOAO SILVA", "SAO PAULO", new BigDecimal("10.00"));

  final PixPayload dynamicPixPayload =
      Pix.newDynamic("pix.example.com/api/webhook", "JOAO SILVA", "SAO PAULO", "TX123");

  @Test
  void static_qrGeneration_works() {
    PixQrOutput output =
        assertDoesNotThrow(
            () -> PixQrCodeGenerator.generate(staticPixPayload.getEmv(), PixQrFormat.PNG, config));
  }

  @Test
  void dynamic_qrGeneration_works() throws IOException {
    PixQrOutput output =
        assertDoesNotThrow(
            () -> PixQrCodeGenerator.generate(dynamicPixPayload.getEmv(), PixQrFormat.PNG, config));

    PixQrOutput.Png png = (PixQrOutput.Png) output;

    BufferedImage image = ImageIO.read(new ByteArrayInputStream(png.bytes()));

    assertNotNull(image);
  }

  @Test
  void png_isValidImage() throws Exception {
    PixQrOutput output =
        PixQrCodeGenerator.generate(staticPixPayload.getEmv(), PixQrFormat.PNG, config);

    PixQrOutput.Png png = (PixQrOutput.Png) output;

    BufferedImage image = ImageIO.read(new ByteArrayInputStream(png.bytes()));

    assertNotNull(image);
    assertEquals(300, image.getWidth());
    assertEquals(300, image.getHeight());
  }

  @Test
  void png_decodesBackToPayload() throws Exception {
    PixQrOutput.Png png =
        (PixQrOutput.Png)
            PixQrCodeGenerator.generate(staticPixPayload.getEmv(), PixQrFormat.PNG, config);

    BufferedImage image = ImageIO.read(new ByteArrayInputStream(png.bytes()));

    BinaryBitmap bitmap =
        new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

    Result result = new MultiFormatReader().decode(bitmap);
    assertEquals(staticPixPayload.getEmv(), result.getText());
  }

  @Test
  void braile_Works() throws Exception {
    PixQrOutput.Braille braille =
        (PixQrOutput.Braille)
            PixQrCodeGenerator.generate(staticPixPayload.getEmv(), PixQrFormat.BRAILLE, config);

    assertNotNull(braille);
  }

  @Test
  void base64Png_decodesToValidImage() throws Exception {
    PixQrOutput.Base64 base64 =
        (PixQrOutput.Base64)
            PixQrCodeGenerator.generate(staticPixPayload.getEmv(), PixQrFormat.BASE64_PNG, config);

    byte[] bytes = Base64.getDecoder().decode(base64.value());

    BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
    assertNotNull(image);
  }
}
