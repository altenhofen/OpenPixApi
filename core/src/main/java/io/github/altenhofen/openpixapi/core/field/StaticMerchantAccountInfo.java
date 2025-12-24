package io.github.altenhofen.openpixapi.core.field;

import io.github.altenhofen.openpixapi.core.formatter.CharsetPolicy;
import io.github.altenhofen.openpixapi.core.formatter.StringFormatter;

import java.util.List;

public final class StaticMerchantAccountInfo implements MerchantAccountInfo {
    private final String pixKey;
    public StaticMerchantAccountInfo(String pixKey) {
        if (pixKey == null || pixKey.isBlank()) {
            throw new IllegalArgumentException("Pix key must not be null or blank");
        }
        this.pixKey = pixKey;
    }
    @Override
    public CompositeEMVField toEmvField() {
        EMVField<String> gui = new EMVField<>(
                "Globally Unique Identifier",
                "00",
                "br.gov.bcb.pix",
                new StringFormatter(14, CharsetPolicy.EMV_COMMON)
        );

        EMVField<String> pixKeyField = new EMVField<>(
                "Pix Key",
                "01",
                pixKey,
                new StringFormatter(36, CharsetPolicy.PIX_KEY_RELAXED)
        );

        return new CompositeEMVField(
                "Merchant Account Information",
                "26",
                List.of(gui, pixKeyField)
        );
    }
}
