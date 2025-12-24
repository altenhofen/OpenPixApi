package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public enum PixQrFormat implements PixQrRenderer {
    PNG {
        @Override
        public PixQrOutput render(BitMatrix matrix, Color foreground, Color background) throws Exception {
            final MatrixToImageConfig config = new MatrixToImageConfig(foreground.getRGB(), background.getRGB());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out, config);
            return PixQrOutput.png(out.toByteArray());
        }
    },

    SVG {
        @Override
        public PixQrOutput render(BitMatrix matrix, Color foreground, Color background) throws Exception {
            String svg = PixQrSvgWriter.toSvg(matrix, 4, foreground, background);
            return PixQrOutput.svg(svg);
        }
    },

    BASE64_PNG {
        @Override
        public PixQrOutput render(BitMatrix matrix, Color foreground, Color background) throws Exception {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            final MatrixToImageConfig config = new MatrixToImageConfig(foreground.getRGB(), background.getRGB());
            MatrixToImageWriter.writeToStream(matrix, "PNG", out, config);
            String base64 = Base64.getEncoder().encodeToString(out.toByteArray());
            return PixQrOutput.base64(base64);
        }
    };


}

