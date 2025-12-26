package io.github.altenhofen.openpixapi.core.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class EmvParser {
    // Pix-specific composite fields
    static final Set<String> COMPOSITE_FIELDS = Set.of("26", "62");

    public static List<EmvNode> parse(String emvPayload) throws EmvParseException {
        List<EmvNode> nodes = new ArrayList<>();
        int payloadLength = emvPayload.length();
        int i = 0;
        while (i < payloadLength) {
            if (i + 2 > payloadLength) {
                throw new EmvParseException(String.format("unexpected end while reading id %d", i));
            }

            String tag = emvPayload.substring(i, i + 2);
            i += 2;


            String lengthString = emvPayload.substring(i, i + 2);
            int length = Integer.parseInt(lengthString);
            i += 2;

            String value = emvPayload.substring(i, i + length);
            i += length;

            if (COMPOSITE_FIELDS.contains(tag)) {
                List<EmvNode> children = parse(value);
                nodes.add(new EmvComposite(tag, children));
            } else {
                nodes.add(new EmvLeaf(tag, value));
            }
        }

        return nodes;
    }
}
