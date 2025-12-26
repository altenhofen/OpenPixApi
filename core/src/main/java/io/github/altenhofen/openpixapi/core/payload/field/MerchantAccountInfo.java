package io.github.altenhofen.openpixapi.core.payload.field;

/**
 * Represents a merchant account info EMV field. <br>
 * Can be {@link DynamicMerchantAccountInfo} or {@link StaticMerchantAccountInfo}
 */
public interface MerchantAccountInfo {
  /**
   * Turns the merchant account info into a valid EMV composite field.
   *
   * @return the CompositeEmvField
   */
  CompositeEmvField toEmvField();
}
