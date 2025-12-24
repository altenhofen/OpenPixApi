package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;

public class PixQrConfig {
    private final Color foreground;
    private final Color background;
    private final ErrorCorrectionLevel errorCorrectionLevel;
    private final int size;

    public PixQrConfig(Color foreground, Color background, ErrorCorrectionLevel errorCorrectionLevel, int size) {
        this.foreground = foreground;
        this.background = background;
        this.errorCorrectionLevel = errorCorrectionLevel;
        this.size = size;
    }

    public static PixQrConfig defaultConfig() {
        return new PixQrConfigBuilder().build();
    }

    public static PixQrConfigBuilder builder() {
        return new PixQrConfigBuilder();
    }

    public Color getForeground() {
        return foreground;
    }

    public Color getBackground() {
        return background;
    }

    public ErrorCorrectionLevel getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    public int getSize() {
        return size;
    }

    static class PixQrConfigBuilder {
        private Color foreground = Color.BLACK;
        private Color background = Color.WHITE;
        private ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.M;
        private int size = 300;

        public PixQrConfig build() {
            return new PixQrConfig(
                    foreground,
                    background,
                    errorCorrectionLevel,
                    size
            );
        }

        public PixQrConfigBuilder foreground(Color foreground) {
            this.foreground = foreground;
            return this;
        }

        public PixQrConfigBuilder background(Color background) {
            this.background = background;
            return this;
        }

        public PixQrConfigBuilder size(int size) {
            this.size = size;
            return this;
        }

        public PixQrConfigBuilder errorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
            this.errorCorrectionLevel = errorCorrectionLevel;
            return this;
        }
    }
}
