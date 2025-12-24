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
        public PixQrOutput render(BitMatrix matrix, PixQrConfig config) throws Exception {
            final MatrixToImageConfig imageConfig = new MatrixToImageConfig(
                    config.getForeground().getRGB(),
                    config.getBackground().getRGB());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out, imageConfig);
            return PixQrOutput.png(out.toByteArray());
        }
    },

    SVG {
        @Override
        public PixQrOutput render(BitMatrix matrix, PixQrConfig config) throws Exception {
            String svg = PixQrSvgWriter.toSvg(matrix, 4, config);
            return PixQrOutput.svg(svg);
        }
    },

    BASE64_PNG {
        @Override
        public PixQrOutput render(BitMatrix matrix, PixQrConfig config) throws Exception {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            final MatrixToImageConfig imageConfig = new MatrixToImageConfig(
                    config.getForeground().getRGB(),
                    config.getBackground().getRGB());

            MatrixToImageWriter.writeToStream(matrix, "PNG", out, imageConfig);
            String base64 = Base64.getEncoder().encodeToString(out.toByteArray());
            return PixQrOutput.base64(base64);
        }
    };


}

