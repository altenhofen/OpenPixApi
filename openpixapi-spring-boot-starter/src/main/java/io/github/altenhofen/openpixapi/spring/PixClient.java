package io.github.altenhofen.openpixapi.spring;

import io.github.altenhofen.openpixapi.core.Pix;
import io.github.altenhofen.openpixapi.core.PixPayload;

import java.math.BigDecimal;

public class PixClient {
  private final OpenPixApiProperties properties;

  public PixClient(OpenPixApiProperties properties) {
    this.properties = properties;
  }


  // Convenience overload using defaults
  public PixPayload newStatic(String pixKey) {
    return Pix.newStatic(
      pixKey,
      require(properties.getMerchantName(), "merchantName"),
      require(properties.getMerchantCity(), "merchantCity")
    );
  }

  public PixPayload newStatic(String pixKey, BigDecimal amount) {
    return Pix.newStatic(
      pixKey,
      require(properties.getMerchantName(), "merchantName"),
      require(properties.getMerchantCity(), "merchantCity"),
      amount
    );
  }

  // Full explicit version (always available)
  public PixPayload newStatic(
    String pixKey, String merchantName, String merchantCity) {
    return Pix.newStatic(pixKey, merchantName, merchantCity);
  }

  public PixPayload newDynamic(
    String pspUrl, String txid) {
    return Pix.newDynamic(
      pspUrl,
      require(properties.getMerchantName(), "merchantName"),
      require(properties.getMerchantCity(), "merchantCity"),
      txid
    );
  }

  private static String require(String value, String name) {
    if (value == null || value.isBlank()) {
      throw new IllegalStateException(
        "openpix." + name + " must be configured"
      );
    }
    return value;
  }
}

