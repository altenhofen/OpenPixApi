package io.github.altenhofen.openpixapi.qrcode;

import static org.junit.jupiter.api.Assertions.*;

import io.github.altenhofen.openpixapi.core.Pix;
import io.github.altenhofen.openpixapi.core.PixPayload;
import java.awt.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PixQrSvgWriterTest {
  final PixQrConfig config = PixQrConfig.builder().foreground(Color.YELLOW).build();

  final PixPayload staticPayload =
      Pix.newStatic("email@test.com", "JOAO SILVA", "SAO PAULO", new BigDecimal("10.00"));

  @Test
  void svg_containsSvgRoot() throws PixQrGenerationException {
    PixQrOutput output =
        PixQrCodeGenerator.generate(staticPayload.getEmv(), PixQrFormat.SVG, config);

    PixQrOutput.Svg svg = (PixQrOutput.Svg) output;
    assertTrue(svg.toString().contains("<svg"));
  }
}
