package io.github.altenhofen.openpixapi.qrcode;

public sealed interface PixQrOutput
        permits PixQrOutput.Png, PixQrOutput.Svg, PixQrOutput.Base64 {

    record Png(byte[] bytes) implements PixQrOutput {}
    record Svg(String svg) implements PixQrOutput {}
    record Base64(String value) implements PixQrOutput {}

    static Png png(byte[] b) { return new Png(b); }
    static Svg svg(String s) { return new Svg(s); }
    static Base64 base64(String s) { return new Base64(s); }
}
