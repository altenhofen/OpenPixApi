package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.common.BitMatrix;

final class PixQrSvgWriter {

    private PixQrSvgWriter() {
    }

    public static String toSvg(BitMatrix matrix, int moduleSize) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();

        StringBuilder sb = new StringBuilder();

        sb.append("""
                <svg xmlns="http://www.w3.org/2000/svg"
                     shape-rendering="crispEdges"
                     viewBox="0 0 %d %d">
                """.formatted(width * moduleSize, height * moduleSize));

        sb.append("<rect width=\"100%\" height=\"100%\" fill=\"white\"/>");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    sb.append(
                            "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"black\"/>"
                                    .formatted(
                                            x * moduleSize,
                                            y * moduleSize,
                                            moduleSize,
                                            moduleSize
                                    )
                    );
                }
            }
        }

        sb.append("</svg>");
        return sb.toString();
    }
}

