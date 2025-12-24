package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.common.BitMatrix;

import java.awt.*;

final class PixQrSvgWriter {

    private PixQrSvgWriter() {
    }

    public static String toSvg(BitMatrix matrix, int moduleSize, Color foreground, Color background) {

        int width = matrix.getWidth();
        int height = matrix.getHeight();
        String fgHex = toHex(foreground);
        String bgHex = toHex(background);

        StringBuilder sb = new StringBuilder();

        sb.append("""
                <svg xmlns="http://www.w3.org/2000/svg"
                     shape-rendering="crispEdges"
                     viewBox="0 0 %d %d">
                """.formatted(width * moduleSize, height * moduleSize));

        sb.append("<rect width=\"100%\" height=\"100%\" fill=\"%s\"/>".formatted(bgHex));

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    sb.append(
                            "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"%s\"/>"
                                    .formatted(
                                            x * moduleSize,
                                            y * moduleSize,
                                            moduleSize,
                                            moduleSize,
                                            fgHex
                                    )
                    );
                }
            }
        }

        sb.append("</svg>");
        return sb.toString();
    }

    private static String toHex(Color color) {
        return "#" + Integer.toHexString(color.getRGB()).substring(2);
    }

}

