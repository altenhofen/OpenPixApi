package io.github.altenhofen.openpixapi.core.pixbuilder;

import io.github.altenhofen.openpixapi.core.payload.DynamicPixPayload;
import io.github.altenhofen.openpixapi.core.payload.PixPayloadFactory;

public class DynamicPixBuilder extends AbstractPixBuilder<DynamicPixBuilder> {
    private String pspUrl;

    public DynamicPixBuilder from(String pspUrl) {
       this.pspUrl = pspUrl;

       return this;
    }

    @Override
    protected DynamicPixBuilder self() {
        return this;
    }

    @Override
    public DynamicPixPayload build() {
        validateCommon();
        return PixPayloadFactory.dynamicPix(
                pspUrl,
                super.merchantName,
                super.merchantCity,
                super.txid
        );
    }
}
