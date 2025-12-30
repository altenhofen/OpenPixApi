package io.github.altenhofen.openpixapi.spring;

import io.github.altenhofen.openpixapi.core.Pix;
import io.github.altenhofen.openpixapi.core.PixPayload;
import java.math.BigDecimal;

/**
 * Injectable Pix() class with customization
 *
 * @see Pix
 */
public class PixClient {
  private final OpenPixApiProperties properties;

  /**
   * All args constructor.
   *
   * @param properties configuration for pixClient
   */
  public PixClient(OpenPixApiProperties properties) {
    this.properties = properties;
  }

  /**
   * overload using defaults
   *
   * @param pixKey the merchant/recebedor pix key
   * @return object with pix payload
   * @see io.github.altenhofen.openpixapi.core.DynamicPixPayload
   * @see io.github.altenhofen.openpixapi.core.StaticPixPayload
   * @see PixPayload
   */
  public PixPayload newStatic(String pixKey) {
    return Pix.newStatic(
        pixKey,
        require(properties.getMerchantName(), "merchantName"),
        require(properties.getMerchantCity(), "merchantCity"));
  }

  /**
   * overload using defaults
   *
   * @param pixKey the merchant/recebedor pix key
   * @param amount the amount to be received
   * @return object with pix payload
   * @see io.github.altenhofen.openpixapi.core.DynamicPixPayload
   * @see io.github.altenhofen.openpixapi.core.StaticPixPayload
   * @see PixPayload
   */
  public PixPayload newStatic(String pixKey, BigDecimal amount) {
    return Pix.newStatic(
        pixKey,
        require(properties.getMerchantName(), "merchantName"),
        require(properties.getMerchantCity(), "merchantCity"),
        amount);
  }

  /**
   * Full explicit version (always available)
   *
   * @param pixKey the merchant/recebedor pix key
   * @param merchantName the merchant/recebedor name
   * @param merchantCity the merchant/recebedor city
   * @return object with pix payload
   * @see io.github.altenhofen.openpixapi.core.DynamicPixPayload
   * @see io.github.altenhofen.openpixapi.core.StaticPixPayload
   * @see PixPayload
   */
  public PixPayload newStatic(String pixKey, String merchantName, String merchantCity) {
    return Pix.newStatic(pixKey, merchantName, merchantCity);
  }

  /**
   * Full explicit version (always available) with amount
   *
   * @param pixKey the merchant/recebedor pix key
   * @param merchantName the merchant/recebedor name
   * @param merchantCity the merchant/recebedor city
   * @param amount the amount to be received
   * @return object with pix payload
   * @see io.github.altenhofen.openpixapi.core.DynamicPixPayload
   * @see io.github.altenhofen.openpixapi.core.StaticPixPayload
   * @see PixPayload
   */
  public PixPayload newStatic(
      String pixKey, String merchantName, String merchantCity, BigDecimal amount) {
    return Pix.newStatic(pixKey, merchantName, merchantCity, amount);
  }

  /**
   * Dynamic payload using defaults.
   *
   * @param pspUrl payment service provider endpoint
   * @param txid transaction id
   * @return object with pix payload
   * @see io.github.altenhofen.openpixapi.core.DynamicPixPayload
   * @see io.github.altenhofen.openpixapi.core.StaticPixPayload
   * @see PixPayload
   */
  public PixPayload newDynamic(String pspUrl, String txid) {
    return Pix.newDynamic(
        pspUrl,
        require(properties.getMerchantName(), "merchantName"),
        require(properties.getMerchantCity(), "merchantCity"),
        txid);
  }

  private static String require(String value, String name) {
    if (value == null || value.isBlank()) {
      throw new IllegalStateException("openpix." + name + " must be configured");
    }
    return value;
  }
}
