package io.github.altenhofen.openpixapi.core.pixbuilder;

import io.github.altenhofen.openpixapi.core.payload.AbstractPixPayload;

import javax.swing.*;
import java.math.BigDecimal;

abstract class AbstractPixBuilder<T extends AbstractPixBuilder<T>> {
    protected String merchantName;
    protected String merchantCity;
    protected BigDecimal merchantAmount;
    protected String txid;

    protected abstract T self();

    public T merchantName(String name) {
        this.merchantName = name;
        return self();
    }

    public T merchantCity(String city) {
        this.merchantCity = city;
        return self();
    }

    public T merchantAmount(BigDecimal amount) {
        this.merchantAmount = amount;
        return self();
    }

    public T txid(String txid) {
        this.txid = txid;
        return self();
    }

    protected void validateCommon() {
        if (merchantName == null || merchantName.isBlank())
            throw new IllegalStateException("Merchant name is required");

        if (merchantCity == null || merchantCity.isBlank())
            throw new IllegalStateException("Merchant city is required");
    }

    public abstract AbstractPixPayload build();
}
