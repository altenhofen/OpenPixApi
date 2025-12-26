package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public enum PixQrFormat implements PixQrRenderer {
  PNG {
    @Override
    public PixQrOutput render(BitMatrix matrix, PixQrConfig config) throws Exception {
      final MatrixToImageConfig imageConfig =
          new MatrixToImageConfig(config.getForeground().getRGB(), config.getBackground().getRGB());

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      MatrixToImageWriter.writeToStream(matrix, "PNG", out, imageConfig);
      return PixQrOutput.png(out.toByteArray());
    }
  },

  SVG {
    @Override
    public PixQrOutput render(BitMatrix matrix, PixQrConfig config) throws Exception {
      String svg = PixQrSvgWriter.toSvg(matrix, 4, config);
      return PixQrOutput.svg(svg);
    }
  },

  BASE64_PNG {
    @Override
    public PixQrOutput render(BitMatrix matrix, PixQrConfig config) throws Exception {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      final MatrixToImageConfig imageConfig =
          new MatrixToImageConfig(config.getForeground().getRGB(), config.getBackground().getRGB());

      MatrixToImageWriter.writeToStream(matrix, "PNG", out, imageConfig);
      String base64 = Base64.getEncoder().encodeToString(out.toByteArray());
      return PixQrOutput.base64(base64);
    }
  },

  BRAILE {
    @Override
    public PixQrOutput render(BitMatrix matrix, PixQrConfig config) {
      int width = matrix.getWidth();
      int height = matrix.getHeight();

      StringBuilder out = new StringBuilder();

      for (int y = 0; y < height; y += 4) {
        for (int x = 0; x < width; x += 2) {

          int braille = 0;

          // Left column
          if (get(matrix, x, y)) braille |= 1 << 0; // dot 1
          if (get(matrix, x, y + 1)) braille |= 1 << 1; // dot 2
          if (get(matrix, x, y + 2)) braille |= 1 << 2; // dot 3
          if (get(matrix, x, y + 3)) braille |= 1 << 6; // dot 7

          // Right column
          if (get(matrix, x + 1, y)) braille |= 1 << 3; // dot 4
          if (get(matrix, x + 1, y + 1)) braille |= 1 << 4; // dot 5
          if (get(matrix, x + 1, y + 2)) braille |= 1 << 5; // dot 6
          if (get(matrix, x + 1, y + 3)) braille |= 1 << 7; // dot 8

          out.append((char) (0x2800 + braille));
        }
        out.append('\n');
      }

      return PixQrOutput.braile(out.toString());
    }

    private static boolean get(BitMatrix m, int x, int y) {
      return x < m.getWidth() && y < m.getHeight() && m.get(x, y);
    }
  }
}
