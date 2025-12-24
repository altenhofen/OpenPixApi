package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.common.BitMatrix;
import io.github.altenhofen.openpixapi.core.payload.PixPayload;

import java.util.Objects;

public final class PixQrCodeGenerator {

    public static PixQrOutput generate(
            PixPayload pixPayload,
            PixQrFormat format,
            int size
    ) throws PixQrGenerationException {
        Objects.requireNonNull(pixPayload);
        Objects.requireNonNull(format);

        if (size <= 0) {
            throw new IllegalArgumentException(String.format("Size must be greater than zero, is %d", size));
        }

        String payloadString = pixPayload.toString();
        BitMatrix matrix;
        try {
            matrix = PixQrMatrixFactory.generate(payloadString, size);
        } catch (Exception e) {
            throw new PixQrGenerationException("Failed to generate QR matrix %e", e);
        }

        try {
            return format.render(matrix);
        } catch (Exception e) {
            throw new PixQrGenerationException("Failed to render QR code", e);
        }
    }
}
