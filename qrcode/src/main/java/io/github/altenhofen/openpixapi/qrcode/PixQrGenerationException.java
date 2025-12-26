package io.github.altenhofen.openpixapi.qrcode;

/** Represents an error on image creation */
public class PixQrGenerationException extends Exception {
  /**
   * String message and cause arguments constructor for PixQrGenerationException.
   *
   * @param message the message of the exception
   * @param cause the throwable of the exception
   */
  public PixQrGenerationException(String message, Throwable cause) {
    super(message, cause);
  }
}
