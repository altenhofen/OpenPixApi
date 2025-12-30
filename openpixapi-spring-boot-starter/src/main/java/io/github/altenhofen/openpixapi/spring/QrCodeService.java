package io.github.altenhofen.openpixapi.spring;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.github.altenhofen.openpixapi.qrcode.PixQrCode;
import io.github.altenhofen.openpixapi.qrcode.PixQrGenerationException;
import java.awt.*;
import java.io.IOException;
import org.springframework.stereotype.Service;

/** Injectable service for QrCode generation based on Pix payload string. */
@Service
public class QrCodeService {
  private final PixQrCodeProperties properties;

  /**
   * All args constructor for this service.
   *
   * @param properties configuration for QrCode image
   */
  public QrCodeService(PixQrCodeProperties properties) {
    this.properties = properties;
  }

  /**
   * Generate a PNG QR code from an EMV payload
   *
   * @param payload EMV Pix payload string
   * @throws PixQrGenerationException if image generation failed
   * @return the Png image bytes
   */
  public byte[] generatePng(String payload) throws PixQrGenerationException {
    return PixQrCode.from(payload).toBytes();
  }

  /**
   * Generate a Base64-encoded PNG QR code from an EMV payload
   *
   * @param payload EMV Pix payload string
   * @throws PixQrGenerationException if image generation failed
   * @return the Base64-encoded image
   */
  public String generateBase64(String payload) throws PixQrGenerationException {
    return PixQrCode.from(payload).toBase64Encode();
  }

  /**
   * Generate an Image object
   *
   * @param payload Pix's EMV string payload
   * @throws PixQrGenerationException if image generation failed
   * @throws IOException if image generation I/O failed
   * @return an image object
   */
  public Image generateImage(String payload) throws PixQrGenerationException, IOException {
    return PixQrCode.from(payload).toPng();
  }

  /**
   * Generate an SVG string
   *
   * @param payload Pix's EMV string payload
   * @throws PixQrGenerationException if image generation failed
   * @return an SVG html tag
   */
  public String generateSvg(String payload) throws PixQrGenerationException {
    return PixQrCode.from(payload).toSvg();
  }

  /**
   * Builder-style access.
   *
   * @param payload the EMV Pix payload.
   * @return the builder class
   */
  public PixQrCode builder(String payload) {
    return PixQrCode.from(payload)
        .size(properties.getSize())
        .foreground(Color.decode(properties.getForeground()))
        .background(Color.decode(properties.getBackground()))
        .errorCorrectionLevel(parseErrorCorrectionLevel(properties.getErrorCorrectionLevel()));
  }

  // this is needed because we're not exposing ZXing
  private ErrorCorrectionLevel parseErrorCorrectionLevel(String level) {
    return switch (level.toUpperCase()) {
      case "L" -> ErrorCorrectionLevel.L;
      case "M" -> ErrorCorrectionLevel.M;
      case "Q" -> ErrorCorrectionLevel.Q;
      case "H" -> ErrorCorrectionLevel.H;
      default -> ErrorCorrectionLevel.M;
    };
  }
}
