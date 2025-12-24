package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

final class PixQrMatrixFactory {

    public static BitMatrix generate(String pixPayload, int size) throws Exception {
        QRCodeWriter writer = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        return writer.encode(
                pixPayload,
                BarcodeFormat.QR_CODE,
                size,
                size,
                hints
        );
    }
}

