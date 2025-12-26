package io.github.altenhofen.openpixapi.core.payload.parser;

import io.github.altenhofen.openpixapi.core.Pix;
import io.github.altenhofen.openpixapi.core.payload.PixPayload;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PixParserTest {

  @Test
  void invalidPayload_fromPayloadString() throws EmvParseException {
    String invalidPayload = "0001BR-sdfi0jdasjigfi";
    PixParser parser = new PixParser();
    Exception exception = assertThrows(Exception.class, () -> parser.fromPayloadString(invalidPayload));

    assertNotNull(exception.getMessage());
  }

  @Test
  void static_payloadGeneratedIsReversible_fromPayloadString() throws EmvParseException {
    final PixPayload payload = Pix.newStatic("test@email.com",
      "JOAO DA SILVA",
      "PORTO ALEGRE",
      BigDecimal.valueOf(123.99));

    String validPayload = payload.getEmv();

    PixParser parser = new PixParser();
    PixPayload generatedPayload = assertDoesNotThrow(() -> parser.fromPayloadString(validPayload));

    assertNotNull(payload.getMerchantAccount());
    assertEquals(payload, generatedPayload);
    assertEquals(payload.getMerchantAccount(), generatedPayload.getMerchantAccount());
  }

  @Test
  void dynamic_payloadGeneratedIsReversible_fromPayloadString() throws EmvParseException {
    final PixPayload payload = Pix.newDynamic("pix.example.com/api/webhook",
      "JOAO DA SILVA",
      "porto alegre",
      "txifda");

    String validPayload = payload.getEmv();

    PixParser parser = new PixParser();
    PixPayload generatedPayload = assertDoesNotThrow(() -> parser.fromPayloadString(validPayload));

    assertNotNull(payload.getMerchantAccount());
    assertEquals(payload, generatedPayload);
    assertEquals(payload.getMerchantAccount(), generatedPayload.getMerchantAccount());
  }
}
