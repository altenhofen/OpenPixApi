package io.github.altenhofen.openpixapi.core.payload.parser;

/**
 * The leaf of the parsed tree.
 *
 * @param id or tag of the EMV field.
 * @param value the value of the field
 */
public record EmvLeaf(String id, String value) implements EmvNode {}
