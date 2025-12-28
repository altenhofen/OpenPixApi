package io.github.altenhofen.openpixapi.core;

import java.util.List;

/**
 * Composite EMV field that has been parsed
 *
 * @param id or 'tag' of the EMV Field
 * @param children children nodes for the composite field
 */
record EmvComposite(String id, List<EmvNode> children) implements EmvNode {}
