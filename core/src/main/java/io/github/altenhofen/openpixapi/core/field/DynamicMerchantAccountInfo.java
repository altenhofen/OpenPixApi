package io.github.altenhofen.openpixapi.core.field;

import io.github.altenhofen.openpixapi.core.formatter.CharsetPolicy;
import io.github.altenhofen.openpixapi.core.formatter.StringFormatter;

import java.util.List;

public final class DynamicMerchantAccountInfo implements MerchantAccountInfo {

    private final String url;

    public DynamicMerchantAccountInfo(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("Dynamic Pix URL must not be null or blank");
        }
        if (!url.startsWith("https://")) {
            throw new IllegalArgumentException("Dynamic Pix URL must use HTTPS");
        }
        this.url = url;
    }

    @Override
    public CompositeEmvField toEmvField() {
        EmvField<String> gui = new EmvField<>(
                "Globally Unique Identifier",
                "00",
                "br.gov.bcb.pix",
                new StringFormatter(14, CharsetPolicy.EMV_COMMON)
        );

        EmvField<String> urlField = new EmvField<>(
                "Dynamic Pix URL",
                "25",
                url,
                new StringFormatter(99, CharsetPolicy.EMV_COMMON)
        );

        return new CompositeEmvField(
                "Merchant Account Information",
                "26",
                List.of(gui, urlField)
        );
    }
}
