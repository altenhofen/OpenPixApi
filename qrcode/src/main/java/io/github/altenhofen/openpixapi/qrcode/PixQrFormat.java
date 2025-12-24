package io.github.altenhofen.openpixapi.qrcode;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public enum PixQrFormat implements PixQrRenderer {
    PNG {
        @Override
        public PixQrOutput render(BitMatrix matrix) throws Exception {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            return PixQrOutput.png(out.toByteArray());
        }
    },

    SVG {
        @Override
        public PixQrOutput render(BitMatrix matrix) {
            String svg = PixQrSvgWriter.toSvg(matrix, 4);
            return PixQrOutput.svg(svg);
        }
    },

    BASE64_PNG {
        @Override
        public PixQrOutput render(BitMatrix matrix) throws Exception {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            String base64 = Base64.getEncoder().encodeToString(out.toByteArray());
            return PixQrOutput.base64(base64);
        }
    };
}

