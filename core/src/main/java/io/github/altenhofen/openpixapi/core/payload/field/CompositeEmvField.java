package io.github.altenhofen.openpixapi.core.payload.field;

import java.util.List;

/**
 * Class for fields that contain other fields There is no formatter because the serialization is
 * done to the children
 *
 * @author Augusto Bussmann Altenhofen
 * @since v.01-DEV
 * @see EmvField
 */
public class CompositeEmvField extends EmvField<List<EmvField<?>>> {

  public CompositeEmvField(String fieldName, String id, List<EmvField<?>> value) {
    super(fieldName, id, value, null);
  }

  /**
   * @return the serialization of each field
   */
  @Override
  protected String serializeValue() {
    StringBuilder sb = new StringBuilder();
    for (EmvField<?> field : getValue()) {
      sb.append(field.serialize());
    }

    return sb.toString();
  }
}
