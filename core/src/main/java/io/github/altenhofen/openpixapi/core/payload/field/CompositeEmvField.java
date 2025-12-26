package io.github.altenhofen.openpixapi.core.payload.field;

import java.util.List;
import org.jetbrains.annotations.Nullable;

/**
 * Class for fields that contain other fields There is no formatter because the serialization is
 * done to the children
 *
 * @see EmvField
 */
public class CompositeEmvField extends EmvField<List<EmvField<?>>> {

  /**
   * @param fieldName name/description of the field, used for debugging only
   * @param id or tag of the EMV field
   * @param value collection of EMV fields
   */
  public CompositeEmvField(@Nullable String fieldName, String id, List<EmvField<?>> value) {
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
