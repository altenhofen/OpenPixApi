package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PixQrCode {
    private final String emvPayload;
    private Color foreground = Color.black;
    private Color background = Color.white;
    private ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.M;

    private int size = 300;
    private int width, height = size;


    public PixQrCode(String emvPayload) {
        this.emvPayload = emvPayload;
    }

    public static PixQrCode from(String payload) {
        return new PixQrCode(payload);
    }

    public PixQrCode size(int size) {
        this.size = size;
        return this;
    }

    public PixQrCode foreground(Color foreground) {
        this.foreground = foreground;
        return this;
    }

    public PixQrCode background(Color background) {
        this.background = background;
        return this;
    }

    public PixQrCode errorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        this.errorCorrectionLevel = errorCorrectionLevel;
        return this;
    }

    public byte[] toBytes() throws PixQrGenerationException {
        PixQrOutput output = build(PixQrFormat.PNG);
        PixQrOutput.Png png = (PixQrOutput.Png) output;
        return png.bytes();
    }

    public Image toPng() throws PixQrGenerationException, IOException {
        PixQrOutput output = build(PixQrFormat.PNG);
        PixQrOutput.Png png = (PixQrOutput.Png) output;

        return ImageIO.read(
                new ByteArrayInputStream(png.bytes())
        );
    }

    public String toSvg() throws PixQrGenerationException {
        PixQrOutput output = build(PixQrFormat.SVG);
        PixQrOutput.Svg svg = (PixQrOutput.Svg) output;
        return svg.svg();
    }

    private PixQrOutput build(PixQrFormat format) throws PixQrGenerationException {
        PixQrConfig config = PixQrConfig.builder()
                .foreground(this.foreground)
                .background(this.background)
                .size(this.size)
                .errorCorrectionLevel(this.errorCorrectionLevel)
                .build();
        return PixQrCodeGenerator.generate(
                this.emvPayload,
                format,
                config
        );
    };

}
