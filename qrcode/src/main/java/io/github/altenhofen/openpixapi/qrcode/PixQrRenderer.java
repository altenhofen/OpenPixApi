package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.common.BitMatrix;

public interface PixQrRenderer {
    PixQrOutput render(BitMatrix matrix) throws Exception;
}
