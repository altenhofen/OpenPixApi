package io.github.altenhofen.openpixapi.core.payload;

import io.github.altenhofen.openpixapi.core.parser.EmvNode;
import io.github.altenhofen.openpixapi.core.parser.EmvParseException;
import io.github.altenhofen.openpixapi.core.parser.EmvParser;

import java.util.List;

import static io.github.altenhofen.openpixapi.core.payload.PixPayloadFactory.fromNodes;


public class PixReader {
    public PixReader() {
    }

    public PixPayload fromPayloadString(String payloadString) throws EmvParseException {
        List<EmvNode> nodes = EmvParser.parse(payloadString);
        return fromNodes(nodes);
    }
}
