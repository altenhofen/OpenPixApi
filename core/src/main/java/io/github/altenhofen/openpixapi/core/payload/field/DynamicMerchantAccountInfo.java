package io.github.altenhofen.openpixapi.core.payload.field;

import io.github.altenhofen.openpixapi.core.payload.field.formatter.CharsetPolicy;
import io.github.altenhofen.openpixapi.core.payload.field.formatter.StringFormatter;
import java.util.List;

/** Implementation of {@link MerchantAccountInfo} for Dynamic Pix. */
public final class DynamicMerchantAccountInfo implements MerchantAccountInfo {

  private final String url;

  /**
   * Constructs and validates the Dynamic Pix {@link MerchantAccountInfo}.
   *
   * @param url the url for the payment service provider endpoint
   */
  public DynamicMerchantAccountInfo(String url) {
    if (url == null || url.isBlank()) {
      throw new IllegalArgumentException("Dynamic Pix URL must not be null or blank");
    }
    if (!url.startsWith("https://")) {
      throw new IllegalArgumentException("Dynamic Pix URL must use HTTPS");
    }
    this.url = url;
  }

  /**
   * Converts into an EMV representation of the MerchantAccountInfo.
   *
   * @return a EMV Field representation of the Dynamic Pix {@link MerchantAccountInfo}
   */
  @Override
  public CompositeEmvField toEmvField() {
    EmvField<String> gui =
        new EmvField<>(
            "Globally Unique Identifier",
            "00",
            "br.gov.bcb.pix",
            new StringFormatter(14, CharsetPolicy.EMV_COMMON));

    EmvField<String> urlField =
        new EmvField<>(
            "Dynamic Pix URL", "25", url, new StringFormatter(99, CharsetPolicy.EMV_COMMON));

    return new CompositeEmvField("Merchant Account Information", "26", List.of(gui, urlField));
  }
}
