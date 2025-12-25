package io.github.altenhofen.openpixapi.core.parser;

import io.github.altenhofen.openpixapi.core.payload.PixPayload;
import io.github.altenhofen.openpixapi.core.payload.PixPayloadFactory;
import org.junit.jupiter.api.Test;

class EmvParserTest {

    @Test
    public void parse_Works() throws EmvParseException {
        final String payload = "00020101021126570014BR.GOV.BCB.PIX2535HTTPS://PIX.EXAMPLE.COM/API/WEBHOOK5204000053039865802BR5908JOHN DOE6012PORTO ALEGRE621905150512TX1234567896304B7DF";
        PixPayload payloadObj = PixPayloadFactory.fromPayloadString(payload);
    }
}