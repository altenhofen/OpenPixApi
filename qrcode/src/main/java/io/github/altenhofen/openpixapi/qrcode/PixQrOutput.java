package io.github.altenhofen.openpixapi.qrcode;

/** Represents the different output types for a Pix QRCode. */
public sealed interface PixQrOutput
    permits PixQrOutput.Png, PixQrOutput.Svg, PixQrOutput.Base64, PixQrOutput.Braille {

  /**
   * .png file representation
   *
   * @param bytes of the png image
   */
  record Png(byte[] bytes) implements PixQrOutput {}

  /**
   * .svg file representation
   *
   * @param svg tag with QRCode content
   */
  record Svg(String svg) implements PixQrOutput {}

  /**
   * base64 encoded image representation
   *
   * @param value base64 encoded png image
   */
  record Base64(String value) implements PixQrOutput {}

  /**
   * Unicode (braille) text representation
   *
   * @param art the braille Unicode art
   */
  record Braille(String art) implements PixQrOutput {}

  /**
   * .png file representation
   *
   * @param bytes of the png image
   * @return the PNG bytes for image
   */
  static Png png(byte[] bytes) {
    return new Png(bytes);
  }

  /**
   * .svg file representation
   *
   * @param svg tag with QRCode content
   * @return the SVG tags for image
   */
  static Svg svg(String svg) {
    return new Svg(svg);
  }

  /**
   * base64 encoded image representation
   *
   * @param value base64 encoded png image
   * @return the QRCode base64 encoded png
   */
  static Base64 base64(String value) {
    return new Base64(value);
  }

  /**
   * Unicode (braille) text representation
   *
   * @param art the braile Unicode art
   * @return the QRCode string
   */
  static Braille braille(String art) {
    return new Braille(art);
  }
}
