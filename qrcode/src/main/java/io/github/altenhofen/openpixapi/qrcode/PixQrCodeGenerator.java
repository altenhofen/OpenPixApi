package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.common.BitMatrix;
import java.awt.*;
import java.util.Objects;

/** Contains factory methods for Pix QRCode generation, */
public final class PixQrCodeGenerator {

  /**
   * Factory method for generation of Pix QRCode from EMV string payload
   *
   * @param emvRepresentation the QRCode payload
   * @param format the output format
   * @param config the QRCode configuration options
   * @return PixQrOutput with typed outputs
   * @throws PixQrGenerationException if output creation failed
   */
  public static PixQrOutput generate(
      String emvRepresentation, PixQrFormat format, PixQrConfig config)
      throws PixQrGenerationException {
    validate(emvRepresentation, format, config);

    BitMatrix matrix;
    try {
      matrix = PixQrMatrixFactory.generate(emvRepresentation, config);
    } catch (Exception e) {
      throw new PixQrGenerationException("Failed to generate QR matrix %e", e);
    }

    try {
      return format.render(matrix, config);
    } catch (Exception e) {
      throw new PixQrGenerationException("Failed to render QR code", e);
    }
  }

  private static void validate(String emvRepresentation, PixQrFormat format, PixQrConfig config) {
    Objects.requireNonNull(emvRepresentation);
    Objects.requireNonNull(format);

    if (config.getSize() <= 0) {
      throw new IllegalArgumentException(
          String.format("Size must be greater than zero, is %d", config.getSize()));
    }
  }
}
