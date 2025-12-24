package io.github.altenhofen.openpixapi.qrcode;

public class PixQrGenerationException extends Exception {

    public PixQrGenerationException() {
    }

    public PixQrGenerationException(String message) {
        super(message);
    }

    public PixQrGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PixQrGenerationException(Throwable cause) {
        super(cause);
    }

    public PixQrGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
