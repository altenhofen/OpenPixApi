package io.github.altenhofen.openpixapi.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration for creating a Pix
 *
 * @see PixClient
 */
@ConfigurationProperties(prefix = "openpixapi")
public class OpenPixApiProperties {
  private String merchantName;
  private String merchantCity;

  /**
   * Getter for merchant name.
   *
   * @return merchantName
   */
  public String getMerchantName() {
    return merchantName;
  }

  /**
   * Setter for merchant name.
   *
   * @param merchantName name of merchant/recebedor
   */
  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  /**
   * Getter for merchant city.
   *
   * @return merchantCity
   */
  public String getMerchantCity() {
    return merchantCity;
  }

  /**
   * Setter for merchant city.
   *
   * @param merchantCity city of merchant/recebedor
   */
  public void setMerchantCity(String merchantCity) {
    this.merchantCity = merchantCity;
  }
}
