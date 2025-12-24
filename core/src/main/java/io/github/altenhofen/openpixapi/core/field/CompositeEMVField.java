package io.github.altenhofen.openpixapi.core.field;

import java.util.List;

/**
 * Class for fields that contain other fields
 * There is no formatter because the serialization is done to the children
 *
 * @author Augusto Bussmann Altenhofen
 * @since v.01-DEV
 * @see EMVField
 */
public class CompositeEMVField extends EMVField<List<EMVField<?>>> {

    public CompositeEMVField(String fieldName, String id, List<EMVField<?>> value) {
        super(fieldName, id, value, null);
    }

    /**
     * @return the serialization of each field
     */
    @Override
    protected String serializeValue() {
        StringBuilder sb = new StringBuilder();
        for (EMVField<?> field : getValue()) {
            sb.append(field.serialize());
        }

        return sb.toString();
    }
}

