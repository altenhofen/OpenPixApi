package io.github.altenhofen.openpixapi.core.pixbuilder;

import io.github.altenhofen.openpixapi.core.payload.StaticPixPayload;
import io.github.altenhofen.openpixapi.core.payload.PixPayloadFactory;

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
    public StaticPixPayload build() {
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
