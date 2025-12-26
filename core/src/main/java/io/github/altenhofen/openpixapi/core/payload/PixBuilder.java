package io.github.altenhofen.openpixapi.core.payload;

public class PixBuilder {
  public PixBuilder() {}

  public StaticPixBuilder staticPix() {
    return new StaticPixBuilder();
  }

  public DynamicPixBuilder dynamicPix() {
    return new DynamicPixBuilder();
  }

  public DynamicPixBuilder dynamicPix(String pspUrl) {
    return new DynamicPixBuilder(pspUrl);
  }
}
