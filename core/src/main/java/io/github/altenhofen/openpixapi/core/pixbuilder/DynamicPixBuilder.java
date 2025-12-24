package io.github.altenhofen.openpixapi.core.pixbuilder;

import io.github.altenhofen.openpixapi.core.payload.DynamicPixPayload;
import io.github.altenhofen.openpixapi.core.payload.PixPayloadFactory;

public class DynamicPixBuilder extends AbstractPixBuilder<DynamicPixBuilder> {
    private String pspUrl;

    public DynamicPixBuilder(String pspUrl) {
        this.pspUrl = pspUrl;
    }

    public DynamicPixBuilder() {
    }

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
        return PixPayloadFactory.dynamicPix(
                pspUrl,
                super.merchantName,
                super.merchantCity,
                super.txid
        );
    }
}
