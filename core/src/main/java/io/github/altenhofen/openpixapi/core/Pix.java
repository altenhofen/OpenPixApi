package io.github.altenhofen.openpixapi.core;

import io.github.altenhofen.openpixapi.core.payload.*;
import io.github.altenhofen.openpixapi.core.payload.parser.EmvParseException;
import io.github.altenhofen.openpixapi.core.payload.parser.PixParser;
import java.math.BigDecimal;

/** Entrypoint for the Pix library API. */
public final class Pix {
  private Pix() {}

  /**
   * Used to build a PixPayload in a fluent way.
   *
   * @return the PixBuilder class
   */
  public static PixBuilder builder() {
    return new PixBuilder();
  }

  /**
   * Creates a dynamic pix with no amount specified.
   *
   * @param pspUrl payment service provider URL
   * @param merchantName name of the merchant/recebedor
   * @param merchantCity city of the merchant/recebedor
   * @param txid transaction id
   * @return the typed payload for pix
   */
  public static PixPayload newDynamic(
      String pspUrl, String merchantName, String merchantCity, String txid) {
    return PixPayloadFactory.dynamicPix(pspUrl, merchantName, merchantCity, null, txid);
  }

  /**
   * Creates a dynamic pix with a specified amount.
   *
   * @param pspUrl payment service provider URL
   * @param merchantName name of the merchant/recebedor
   * @param merchantCity city of the merchant/recebedor
   * @param amount the transaction amount
   * @param txid transaction id
   * @return the typed payload for pix
   */
  public static PixPayload newDynamic(
      String pspUrl, String merchantName, String merchantCity, BigDecimal amount, String txid) {
    return PixPayloadFactory.dynamicPix(pspUrl, merchantName, merchantCity, amount, txid);
  }

  /**
   * New static pix with no amount specified.
   *
   * @param pixKey can be random, e-mail, phone number (+000000000000) or CPF
   * @param merchantName name of merchant/recebedor
   * @param merchantCity city of merchant/recebedor
   * @param txid transaction id
   * @return a static pix's payload
   */
  public static PixPayload newStatic(
      String pixKey, String merchantName, String merchantCity, String txid) {
    return PixPayloadFactory.staticPix(pixKey, merchantName, merchantCity, null, txid);
  }

  /**
   * Creates a new static pix with specified amount.
   *
   * @param pixKey can be random, e-mail, phone number (+000000000000) or CPF
   * @param merchantName name of merchant/recebedor
   * @param merchantCity city of merchant/recebedor
   * @param amount value to be received
   * @param txid transaction id
   * @return a static pix's payload
   */
  public static PixPayload newStatic(
      String pixKey, String merchantName, String merchantCity, BigDecimal amount, String txid) {
    return PixPayloadFactory.staticPix(pixKey, merchantName, merchantCity, amount, txid);
  }

  /**
   * Parses a EMV QRCode/BRCode into a object.
   *
   * @param payload the string payload of a EMV QRCode.
   * @return the typed PixPayload object
   * @throws EmvParseException when parsing is not specified
   */
  public static PixPayload parse(String payload) throws EmvParseException {
    PixParser pixParser = new PixParser();
    return pixParser.fromPayloadString(payload);
  }
}
