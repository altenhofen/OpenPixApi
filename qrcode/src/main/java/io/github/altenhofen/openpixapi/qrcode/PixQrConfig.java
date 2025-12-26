package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.*;

/** Represents the QRCode configuration. */
public class PixQrConfig {
  private final Color foreground;
  private final Color background;
  private final ErrorCorrectionLevel errorCorrectionLevel;
  private final int size;

  /**
   * All args constructor for PixQRConfig.
   *
   * @param foreground QRCode color
   * @param background QRCode color
   * @param errorCorrectionLevel QRCode level correction
   * @param size of the image
   */
  public PixQrConfig(
      Color foreground, Color background, ErrorCorrectionLevel errorCorrectionLevel, int size) {
    this.foreground = foreground;
    this.background = background;
    this.errorCorrectionLevel = errorCorrectionLevel;
    this.size = size;
  }

  /**
   * Default configuration for QRCode genenation <br>
   * 300x300 Black foreground White background
   *
   * @return this class
   */
  public static PixQrConfig defaultConfig() {
    return new PixQrConfigBuilder().build();
  }

  /**
   * Creates a fluent builder for config.
   *
   * @return the builder
   */
  public static PixQrConfigBuilder builder() {
    return new PixQrConfigBuilder();
  }

  /**
   * Getter for foreground color.
   *
   * @return the Color object configured
   */
  public Color getForeground() {
    return foreground;
  }

  /**
   * Getter for background color.
   *
   * @return the Color object configured
   */
  public Color getBackground() {
    return background;
  }

  /**
   * Getter for the error correction level
   *
   * @return the ErrorCorrectionLevel object configured
   */
  public ErrorCorrectionLevel getErrorCorrectionLevel() {
    return errorCorrectionLevel;
  }

  /**
   * Getter for the size
   *
   * @return the int with size configured
   */
  public int getSize() {
    return size;
  }

  static class PixQrConfigBuilder {
    private Color foreground = Color.BLACK;
    private Color background = Color.WHITE;
    private ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.M;
    private int size = 300;

    public PixQrConfig build() {
      return new PixQrConfig(foreground, background, errorCorrectionLevel, size);
    }

    public PixQrConfigBuilder foreground(Color foreground) {
      this.foreground = foreground;
      return this;
    }

    public PixQrConfigBuilder background(Color background) {
      this.background = background;
      return this;
    }

    public PixQrConfigBuilder size(int size) {
      this.size = size;
      return this;
    }

    /**
     * Builder method for error correction level of the QRCode.
     *
     * @return the builder
     */
    public PixQrConfigBuilder errorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
      this.errorCorrectionLevel = errorCorrectionLevel;
      return this;
    }
  }
}
