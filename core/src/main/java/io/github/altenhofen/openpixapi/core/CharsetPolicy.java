package io.github.altenhofen.openpixapi.core;

/**
 * Charsets for validation Pix's charset is somewhat more flexible than the specified by EMV
 * standard, and we make the distinction here.
 *
 * @author Augusto Bussmann Altenhofen
 * @since 0.01-DEV
 */
enum CharsetPolicy {
  /**
   * EMV's common charset, contains uppercase, lowercase and some restrictive punctuations
   *
   * @see #PIX_KEY_RELAXED
   */
  EMV_COMMON("0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + " -./:"),
  /** EMV's common charset, uppercase only */
  EMV_COMMON_UPPER("0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + " -./:"),
  /**
   * EMV's common charset with extra punctuation
   *
   * @see #EMV_COMMON
   */
  PIX_KEY_RELAXED(
      "0123456789" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + " -./:@+"),
  /** Charset containing only digits (0 to 9) */
  DIGITS_ONLY("0123456789"),
  ;

  private final String charset;

  CharsetPolicy(String s) {
    charset = s;
  }

  /**
   * Checks for allowed character in charset.
   *
   * @param c char to be verified
   * @return a boolean saying if char is allowed
   */
  public boolean allows(char c) {
    return charset.indexOf(c) >= 0;
  }
}
