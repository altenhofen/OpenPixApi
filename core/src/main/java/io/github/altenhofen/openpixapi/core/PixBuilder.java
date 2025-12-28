package io.github.altenhofen.openpixapi.core;

/** Builder for Pix payload. */
class PixBuilder {
  /** Empty constructor for builder. */
  public PixBuilder() {}

  /**
   * Creates a static Pix builder object
   *
   * @return a static pix builder
   */
  public StaticPixBuilder staticPix() {
    return new StaticPixBuilder();
  }

  /**
   * Creates a dynamic Pix builder object
   *
   * @return a dynamic pix builder
   */
  public DynamicPixBuilder dynamicPix() {
    return new DynamicPixBuilder();
  }

  /**
   * Creates a dynamic Pix builder object
   *
   * @param pspUrl the payment service provider URL
   * @return a dynamic pix builder
   */
  public DynamicPixBuilder dynamicPix(String pspUrl) {
    return new DynamicPixBuilder(pspUrl);
  }
}
