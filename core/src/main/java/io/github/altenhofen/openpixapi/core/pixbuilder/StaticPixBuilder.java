package io.github.altenhofen.openpixapi.core.pixbuilder;

public class StaticPixBuilder extends AbstractPixBuilder<StaticPixBuilder> {

    private String pixKey;

    @Override
    protected StaticPixBuilder self() {
        return this;
    }

    public StaticPixBuilder pixKey(String pixKey) {
        this.pixKey = pixKey;
        return this;
    }

    @Override
    public String build() {
        validateCommon();

        if (pixKey == null || pixKey.isBlank()) {
            throw new IllegalStateException("Pix key is required");
        }

        return PixPayloadFactory.staticPix(
                this.pixKey,
                super.merchantName,
                super.merchantCity,
                super.merchantAmount,
                super.txid
        );
    }
}
