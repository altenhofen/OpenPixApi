package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.github.altenhofen.openpixapi.core.payload.PixPayload;

import java.awt.*;
import java.util.Objects;

public final class PixQrCodeGenerator {
    private final static Color FG_DEFAULT_COLOR = Color.BLACK;
    private final static Color BG_DEFAULT_COLOR = Color.WHITE;
    private final static ErrorCorrectionLevel DEFAULT_ERROR_CORRECTION_LEVEL = ErrorCorrectionLevel.M;
    private final static int DEFAULT_SIZE = 300;

    public static PixQrOutput generate(String emvRepresentation, PixQrFormat format, Color foreground, Color background) throws PixQrGenerationException {
        return generate(emvRepresentation, format, DEFAULT_SIZE, FG_DEFAULT_COLOR, BG_DEFAULT_COLOR, DEFAULT_ERROR_CORRECTION_LEVEL);
    }

    public static PixQrOutput generate(String emvRepresentation, PixQrFormat format, int size, Color foreground, Color background) throws PixQrGenerationException {
        return generate(emvRepresentation, format, size, foreground, background, DEFAULT_ERROR_CORRECTION_LEVEL);
    }

    public static PixQrOutput generate(String emvRepresentation, PixQrFormat format, int size) throws PixQrGenerationException {
        return generate(emvRepresentation, format, size, FG_DEFAULT_COLOR, BG_DEFAULT_COLOR, DEFAULT_ERROR_CORRECTION_LEVEL);
    }

    public static PixQrOutput generate(String emvRepresentation,
                                       PixQrFormat format,
                                       int size,
                                       Color foreground,
                                       Color background,
                                       ErrorCorrectionLevel  errorCorrectionLevel) throws PixQrGenerationException {
        validate(emvRepresentation, format, size);
        return generatePixQrOutput(emvRepresentation, format, size, foreground, background, errorCorrectionLevel);
    }

    private static void validate(String emvRepresentation, PixQrFormat format, int size) {
        Objects.requireNonNull(emvRepresentation);
        Objects.requireNonNull(format);

        if (size <= 0) {
            throw new IllegalArgumentException(String.format("Size must be greater than zero, is %d", size));
        }
    }

    private static PixQrOutput generatePixQrOutput(
            String emvRepresentation,
            PixQrFormat format,
            int size,
            Color foreground,
            Color background,
            ErrorCorrectionLevel errorCorrectionLevel
    ) throws PixQrGenerationException {
        BitMatrix matrix = getBitMatrix(emvRepresentation, size, errorCorrectionLevel);
        try {
            return format.render(matrix, foreground, background);
        } catch (Exception e) {
            throw new PixQrGenerationException("Failed to render QR code", e);
        }
    }

    private static BitMatrix getBitMatrix(String emvRepresentation, int size, ErrorCorrectionLevel errorCorrectionLevel) throws PixQrGenerationException {
        BitMatrix matrix;
        try {
            matrix = PixQrMatrixFactory.generate(emvRepresentation, size, errorCorrectionLevel);
        } catch (Exception e) {
            throw new PixQrGenerationException("Failed to generate QR matrix %e", e);
        }
        return matrix;
    }
}
