package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.common.BitMatrix;

import java.awt.*;

public interface PixQrRenderer {
    PixQrOutput render(BitMatrix matrix, PixQrConfig config) throws Exception;
}
