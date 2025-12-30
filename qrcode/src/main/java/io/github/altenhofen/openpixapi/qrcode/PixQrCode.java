package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/** Entrypoint for bulding QRCodes from a pix payload string. */
public class PixQrCode {
  private final String emvPayload;
  private Color foreground = Color.black;
  private Color background = Color.white;
  private ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.M;
  private int size = 300;

  /**
   * PixQrCode builder constructor
   *
   * @param emvPayload the string of the payload
   */
  public PixQrCode(String emvPayload) {
    this.emvPayload = emvPayload;
  }

  /**
   * Factory method for the PixQrCode class
   *
   * @param payload the string representing an EMV payload
   * @return this builder
   */
  public static PixQrCode from(String payload) {
    return new PixQrCode(payload);
  }

  /**
   * Sets the size of the QRCode
   *
   * @param size in pixels
   * @return this builder
   */
  public PixQrCode size(int size) {
    this.size = size;
    return this;
  }

  /**
   * Sets the foreground of the QRCode
   *
   * @param foreground color
   * @return this builder
   */
  public PixQrCode foreground(Color foreground) {
    this.foreground = foreground;
    return this;
  }

  /**
   * Sets the backround of the QRCode
   *
   * @param background color
   * @return this builder
   */
  public PixQrCode background(Color background) {
    this.background = background;
    return this;
  }

  /**
   * Sets the error correction level of the QRCode
   *
   * @param errorCorrectionLevel sets the QRCode error correction level
   * @return this builder
   */
  public PixQrCode errorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
    this.errorCorrectionLevel = errorCorrectionLevel;
    return this;
  }

  /**
   * Builds the object.
   *
   * @return the PNG bytes of the QRCode image
   * @throws PixQrGenerationException if image creation failed
   */
  public byte[] toBytes() throws PixQrGenerationException {
    PixQrOutput output = build(PixQrFormat.PNG);
    PixQrOutput.Png png = (PixQrOutput.Png) output;
    return png.bytes();
  }

  /**
   * Builds the object.
   *
   * @return the PNG Image object of the QRCode image
   * @throws PixQrGenerationException if image creation failed at generation stage
   * @throws IOException if image creation failed at export stage
   */
  public Image toPng() throws PixQrGenerationException, IOException {
    PixQrOutput output = build(PixQrFormat.PNG);
    PixQrOutput.Png png = (PixQrOutput.Png) output;

    return ImageIO.read(new ByteArrayInputStream(png.bytes()));
  }

  /**
   * Builds the object.
   *
   * @return the SVG tags string of the QRCode image
   * @throws PixQrGenerationException if image creation failed
   */
  public String toSvg() throws PixQrGenerationException {
    PixQrOutput output = build(PixQrFormat.SVG);
    PixQrOutput.Svg svg = (PixQrOutput.Svg) output;
    return svg.svg();
  }

  public String toBase64Encode() throws PixQrGenerationException {
    PixQrOutput output = build(PixQrFormat.BASE64_PNG);
    PixQrOutput.Base64 base64 = (PixQrOutput.Base64) output;
    return base64.base64();
  }

  private PixQrOutput build(PixQrFormat format) throws PixQrGenerationException {
    PixQrConfig config =
        PixQrConfig.builder()
            .foreground(this.foreground)
            .background(this.background)
            .size(this.size)
            .errorCorrectionLevel(this.errorCorrectionLevel)
            .build();
    return PixQrCodeGenerator.generate(this.emvPayload, format, config);
  }
  ;
}
