package io.github.altenhofen.openpixapi.core.payload.field;

import java.util.ArrayList;
import java.util.List;

import io.github.altenhofen.openpixapi.core.payload.field.formatter.CharsetPolicy;
import io.github.altenhofen.openpixapi.core.payload.field.formatter.EmvFormatter;
import io.github.altenhofen.openpixapi.core.payload.field.formatter.StringFormatter;
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
   * @param id        or tag of the EMV field
   * @param value     collection of EMV fields
   */
  public CompositeEmvField(@Nullable String fieldName, String id, List<EmvField<?>> value) {
    super(fieldName, id, normalize(value), null);
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

  private static List<EmvField<?>> normalize(List<EmvField<?>> fields) {
    List<EmvField<?>> result = new ArrayList<>(fields.size());

    for (EmvField<?> field : fields) {
      result.add(
        new EmvField<>(
          field.getFieldName(),
          field.getId(),
          field.serializeValue(), // already formatted
          null                     // formatter no longer needed
        )
      );
    }

    return List.copyOf(result); // immutable + thread-safe
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof CompositeEmvField other)) {
      return false;
    }

    // EMV identity: tag + ordered children
    return getId().equals(other.getId())
      && getValue().equals(other.getValue());
  }

  @Override
  public int hashCode() {
    return 31 * getId().hashCode() + getValue().hashCode();
  }
}
