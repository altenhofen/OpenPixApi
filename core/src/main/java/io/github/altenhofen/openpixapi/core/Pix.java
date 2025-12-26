package io.github.altenhofen.openpixapi.core;

import io.github.altenhofen.openpixapi.core.parser.EmvParseException;
import io.github.altenhofen.openpixapi.core.payload.*;
import java.math.BigDecimal;

public final class Pix {
  private Pix() {}

  public static PixBuilder builder() {
    return new PixBuilder();
  }

  public static PixPayload newDynamic(
      String pspUrl, String merchantName, String merchantCity, String txid) {
    return PixPayloadFactory.dynamicPix(pspUrl, merchantName, merchantCity, txid);
  }

  public static PixPayload newStatic(
      String pixKey, String merchantName, String merchantCity, BigDecimal amount, String txid) {
    return PixPayloadFactory.staticPix(pixKey, merchantName, merchantCity, amount, txid);
  }

  public static PixPayload parse(String payload) throws EmvParseException {
    PixReader pixReader = new PixReader();
    return pixReader.fromPayloadString(payload);
  }
}
