package io.github.altenhofen.openpixapi.core.field;

import io.github.altenhofen.openpixapi.core.formatter.CharsetPolicy;
import io.github.altenhofen.openpixapi.core.formatter.StringFormatter;

import java.util.List;

public interface MerchantAccountInfo {
    CompositeEMVField toEmvField();
}

