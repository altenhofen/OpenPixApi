package io.github.altenhofen.openpixapi.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "openpix.qrcode")
public class PixQrCodeProperties {

  /**
   * Default QR foreground color, in hex e.g. "#000000"
   */
  private String foreground = "#000000";

  /**
   * Default QR background color, in hex e.g. "#FFFFFF"
   */
  private String background = "#FFFFFF";

  /**
   * Default QR error correction level: L, M, Q, H
   */
  private String errorCorrectionLevel = "M";

  /**
   * Default QR size in pixels
   */
  private int size = 300;


  // getters & setters
  public String getForeground() {
    return foreground;
  }

  public void setForeground(String foreground) {
    this.foreground = foreground;
  }

  public String getBackground() {
    return background;
  }

  public void setBackground(String background) {
    this.background = background;
  }

  public String getErrorCorrectionLevel() {
    return errorCorrectionLevel;
  }

  public void setErrorCorrectionLevel(String errorCorrectionLevel) {
    this.errorCorrectionLevel = errorCorrectionLevel;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
