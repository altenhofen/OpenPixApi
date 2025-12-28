package io.github.altenhofen.openpixapi.core;

import java.util.List;

/** Merchant account info for a static Pix */
final class StaticMerchantAccountInfo implements MerchantAccountInfo {
  private final String pixKey;

  /**
   * Constructor that receives
   *
   * @param pixKey can be random, e-mail, phone number (+000000000000) or CPF
   */
  public StaticMerchantAccountInfo(String pixKey) {
    if (pixKey == null || pixKey.isBlank()) {
      throw new IllegalArgumentException("Pix key must not be null or blank");
    }
    this.pixKey = pixKey;
  }

  @Override
  public CompositeEmvField toEmvField() {
    EmvField<String> gui =
        new EmvField<>(
            "Globally Unique Identifier",
            "00",
            "br.gov.bcb.pix",
            new StringFormatter(14, CharsetPolicy.EMV_COMMON));

    EmvField<String> pixKeyField =
        new EmvField<>(
            "Pix Key", "01", pixKey, new StringFormatter(36, CharsetPolicy.PIX_KEY_RELAXED));

    return new CompositeEmvField("Merchant Account Information", "26", List.of(gui, pixKeyField));
  }
}
