package io.github.altenhofen.openpixapi.core.field;

/**
 * Basic EMVCRC16 implementation.
 *
 * @author Augusto Bussmann Altenhofen
 * @see io.github.altenhofen.openpixapi.core.payload.PixPayload
 * @since 0.01-DEV
 */
public final class EMVCRC16 {

    private static final int POLYNOMIAL = 0x1021;
    private static final int INITIAL_VALUE = 0xFFFF;

    private EMVCRC16() {}

    public static String calculate(String payload) {
        int crc = INITIAL_VALUE;

        byte[] bytes = payload.getBytes(java.nio.charset.StandardCharsets.UTF_8);

        for (byte b : bytes) {
            crc ^= (b & 0xFF) << 8;
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = (crc << 1) ^ POLYNOMIAL;
                } else {
                    crc <<= 1;
                }
                crc &= 0xFFFF;
            }
        }

        return String.format("%04X", crc);
    }
}
