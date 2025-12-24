package io.github.altenhofen.openpixapi.core.pixbuilder;

public final class PixBuilder {
    private PixBuilder() {
    }

    public static StaticPixBuilder staticPix() {
        return new StaticPixBuilder();
    }

    public static DynamicPixBuilder dynamicPix() {
        return new DynamicPixBuilder();
    }

    public static DynamicPixBuilder dynamicPix(String pspUrl) {
        return new DynamicPixBuilder(pspUrl);
    }
}
