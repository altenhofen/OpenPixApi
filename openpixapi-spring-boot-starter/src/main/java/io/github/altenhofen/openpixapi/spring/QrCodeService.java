package io.github.altenhofen.openpixapi.spring;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.github.altenhofen.openpixapi.qrcode.PixQrCode;
import io.github.altenhofen.openpixapi.qrcode.PixQrGenerationException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;

@Service
public class QrCodeService {
  private final PixQrCodeProperties properties;

  public QrCodeService(PixQrCodeProperties properties) {
    this.properties = properties;
  }

  /**
   * Generate a PNG QR code from an EMV payload
   */
  public byte[] generatePng(String payload) throws PixQrGenerationException {
    return PixQrCode.from(payload).toBytes();
  }

  public String generateBase64(String payload)  throws PixQrGenerationException {
    return PixQrCode.from(payload).toBase64Encode();
  }

  /**
   * Generate an Image object
   */
  public Image generateImage(String payload) throws PixQrGenerationException, IOException {
    return PixQrCode.from(payload).toPng();
  }

  /**
   * Generate an SVG string
   */
  public String generateSvg(String payload) throws PixQrGenerationException {
    return PixQrCode.from(payload).toSvg();
  }

  /**
   * Builder-style access
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
