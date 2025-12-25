package io.github.altenhofen.openpixapi.core.parser;

public record EmvLeaf(
        String id,
        String value
) implements EmvNode {}
