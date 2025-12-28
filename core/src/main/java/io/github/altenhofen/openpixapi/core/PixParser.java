package io.github.altenhofen.openpixapi.core;

import static io.github.altenhofen.openpixapi.core.EmvParser.fromNodes;

import java.util.List;

/** Represents the exposed methods for parsing Pix payloads. */
class PixParser {

  /** Empty constructor for Pix parser. */
  public PixParser() {}

  /**
   * Gets a typed object for the input payload.
   *
   * @param payloadString pix paylaod string
   * @return the typed payload object
   * @throws EmvParseException if parsing has failed
   */
  public PixPayload fromPayloadString(String payloadString) throws EmvParseException {
    List<EmvNode> nodes = EmvParser.parse(payloadString);
    return fromNodes(nodes);
  }
}
