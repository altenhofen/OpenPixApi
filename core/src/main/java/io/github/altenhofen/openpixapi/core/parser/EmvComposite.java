package io.github.altenhofen.openpixapi.core.parser;

import java.util.List;

public record EmvComposite(String id, List<EmvNode> children) implements EmvNode {}
