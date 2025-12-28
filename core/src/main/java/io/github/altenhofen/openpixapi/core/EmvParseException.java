package io.github.altenhofen.openpixapi.core;

/** Represents an Exception of EMV parsing. */
public class EmvParseException extends Exception {
  /**
   * Create exception with message.
   *
   * @param message to be thrown
   */
  public EmvParseException(String message) {
    super(message);
  }
}
