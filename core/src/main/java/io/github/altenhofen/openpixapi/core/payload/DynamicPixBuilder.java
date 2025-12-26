package io.github.altenhofen.openpixapi.core.payload;

/** Builder class for Dynamic Pix, extends the {@link AbstractPixBuilder}. */
public class DynamicPixBuilder extends AbstractPixBuilder<DynamicPixBuilder> {
  private String pspUrl;

  /**
   * Constructor for the Dynamic Pix builder that receives a pspUrl
   *
   * @param pspUrl payment service provider URL
   */
  public DynamicPixBuilder(String pspUrl) {
    this.pspUrl = pspUrl;
  }

  /** Empty constructor for Dynamic Pix builder */
  public DynamicPixBuilder() {}

  /**
   * Constructor for Dynamic Pix builder with a payment service provider URL.
   *
   * @param pspUrl payment service provider URL
   * @return the builder
   */
  public DynamicPixBuilder pspUrl(String pspUrl) {
    this.pspUrl = pspUrl;
    return this;
  }

  @Override
  public DynamicPixBuilder txid(String txid) {
    return super.txid(txid);
  }

  @Override
  protected DynamicPixBuilder self() {
    return this;
  }

  @Override
  public DynamicPixPayload build() {
    validateCommon();

    if (pspUrl == null) {
      throw new IllegalArgumentException("pspUrl is required");
    }

    if (txid == null) {
      throw new IllegalArgumentException("txid needs to be present for a dynamic pix");
    }
    return PixPayloadFactory.dynamicPix(pspUrl, super.merchantName, super.merchantCity, super.txid);
  }
}
