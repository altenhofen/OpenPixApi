package io.github.altenhofen.openpixapi.core.field;

import io.github.altenhofen.openpixapi.core.formatter.EMVFormatter;

import java.util.List;

public class CompositeEMVField extends EMVField<List<EMVField<?>>> {


    public CompositeEMVField(String fieldName, String id, List<EMVField<?>> value) {
        super(fieldName, id, value, null);
    }

    @Override
    protected String serializeValue() {
        StringBuilder sb = new StringBuilder();
        for (EMVField<?> field : getValue()) {
            sb.append(field.serialize());
        }

        return sb.toString();
    }
}

