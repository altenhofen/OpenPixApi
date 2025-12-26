package io.github.altenhofen.openpixapi.core.payload;

import static io.github.altenhofen.openpixapi.core.payload.PixPayloadFactory.fromNodes;

import io.github.altenhofen.openpixapi.core.parser.EmvNode;
import io.github.altenhofen.openpixapi.core.parser.EmvParseException;
import io.github.altenhofen.openpixapi.core.parser.EmvParser;
import java.util.List;

public class PixReader {
  public PixReader() {}

  public PixPayload fromPayloadString(String payloadString) throws EmvParseException {
    List<EmvNode> nodes = EmvParser.parse(payloadString);
    return fromNodes(nodes);
  }
}
