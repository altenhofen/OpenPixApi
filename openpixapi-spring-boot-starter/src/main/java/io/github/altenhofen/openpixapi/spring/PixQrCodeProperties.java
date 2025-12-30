package io.github.altenhofen.openpixapi.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** Configuration class for QrCode image */
@Component
@ConfigurationProperties(prefix = "openpix.qrcode")
public class PixQrCodeProperties {

  /** Default QR foreground color, in hex e.g. "#000000" */
  private String foreground = "#000000";

  /** Default QR background color, in hex e.g. "#FFFFFF" */
  private String background = "#FFFFFF";

  /** Default QR error correction level: L, M, Q, H */
  private String errorCorrectionLevel = "M";

  /** Default QR size in pixels */
  private int size = 300;

  // getters & setters

  /**
   * Get foregorund color.
   *
   * @return hexadecimal string with foreground color value
   */
  public String getForeground() {
    return foreground;
  }

  /**
   * Sets the foreground color
   *
   * @param foreground hex value
   */
  public void setForeground(String foreground) {
    this.foreground = foreground;
  }

  /**
   * Get the background color.
   *
   * @return hexadecimal string with background color value
   */
  public String getBackground() {
    return background;
  }

  /**
   * Sets the background color
   *
   * @param background hex value
   */
  public void setBackground(String background) {
    this.background = background;
  }

  /**
   * Gets the error correction level .
   *
   * @return the string for errorCorrectionLevel
   */
  public String getErrorCorrectionLevel() {
    return errorCorrectionLevel;
  }

  /**
   * Sets error correction level
   *
   * @param errorCorrectionLevel can be "L", "M", "Q", "H"
   */
  public void setErrorCorrectionLevel(String errorCorrectionLevel) {
    this.errorCorrectionLevel = errorCorrectionLevel;
  }

  /**
   * Getter for the size.
   *
   * @return integer with size value
   */
  public int getSize() {
    return size;
  }

  /**
   * Setter for the size.
   *
   * @param size integer with size value
   */
  public void setSize(int size) {
    this.size = size;
  }
}
