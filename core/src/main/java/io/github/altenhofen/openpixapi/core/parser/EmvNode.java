package io.github.altenhofen.openpixapi.core.parser;

import java.util.List;

public sealed interface EmvNode permits EmvLeaf, EmvComposite {
    public static void print(EmvNode node, int indent) {
        String pad = " ".repeat(indent);

        if (node instanceof EmvLeaf leaf) {
            System.out.println(pad + leaf.id() + " = " + leaf.value());
        }

        if (node instanceof EmvComposite comp) {
            System.out.println(pad + comp.id() + " {");
            for (EmvNode child : comp.children()) {
                print(child, indent + 2);
            }
            System.out.println(pad + "}");
        }
    }

}

