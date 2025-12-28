package io.github.altenhofen.openpixapi.core;

/**
 * The leaf of the parsed tree.
 *
 * @param id or tag of the EMV field.
 * @param value the value of the field
 */
record EmvLeaf(String id, String value) implements EmvNode {}
