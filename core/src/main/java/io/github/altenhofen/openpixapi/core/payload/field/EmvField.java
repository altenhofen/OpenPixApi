package io.github.altenhofen.openpixapi.core.payload.field;

import io.github.altenhofen.openpixapi.core.payload.field.formatter.DigitFormatter;
import io.github.altenhofen.openpixapi.core.payload.field.formatter.EmvFormatter;
import io.github.altenhofen.openpixapi.core.payload.field.formatter.PaddingPolicy;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for a single field on EMV. I've chosen to keep it not abstract and extending it for
 * composite scenarios <br>
 * <br>
 * See the constructor annotation for more details on architectural choices for formatters.
 *
 * @param <T> type of EMVField's value field
 * @see CompositeEmvField
 */
public class EmvField<T> {
  private final String id;
  private final T value;
  @Nullable private final String fieldName;
  private final EmvFormatter<T> formatter;
  private final DigitFormatter digitFormatter;

  /**
   * @param fieldName used only for debugging situations. <br>
   *     It does not affect the {@link
   *     io.github.altenhofen.openpixapi.core.payload.StaticPixPayload} generation
   * @param id as specified in the EMV standard, it's usually a numeric string of 1 or 2 digits.
   * @param value the value of type T that can be represented by the Common Character Set as defined
   *     in <b>EMV Book 4</b>
   * @param formatter a class that implements {@link EmvFormatter}
   */
  public EmvField(@Nullable String fieldName, String id, T value, EmvFormatter<T> formatter) {
    this.fieldName = fieldName;
    this.id = id;
    this.value = value;
    this.formatter = formatter;
    this.digitFormatter = new DigitFormatter(2, PaddingPolicy.LEFT);
  }

  /**
   * Serializes this field using the EMV "ID + Length + Value" format.
   *
   * <p>Example:
   *
   * <pre>
   * ID       = "00"
   * Value    = "JA"
   * Result   = "0002JA"
   * </pre>
   *
   * <p>If the field is not present (field name is {@code null}),
   *
   * @return serialized value String
   */
  public String serialize() {
    if (this.fieldName == null) {
      return "";
    }

    String formatted = serializeValue();
    int length = formatted.length();

    return formatId() + digitFormatter.format(length) + formatted;
  }

  /**
   * This is where the actual serialization happens. It's done this way so inherited classes can
   * override just the serialization part
   *
   * @see CompositeEmvField
   * @return a String containing the formatted value
   */
  protected String serializeValue() {
    if (formatter == null) {
      return (String) value;
    }

    return this.formatter.format(this.value);
  }

  protected String formatId() {
    if (id.length() != 2) {
      throw new IllegalArgumentException(
          String.format("EMV id must be exactly 2 characters: %s", id));
    }
    return id;
  }

  /**
   * Get the field name or description of the EMV Field.
   *
   * @return the field name/description
   */
  public @Nullable String getFieldName() {
    return this.fieldName;
  }

  protected T getValue() {
    return this.value;
  }

  /**
   * Get the field id or tag of the EMV Field.
   *
   * @return the tag or id
   */
  public String getId() {
    return this.id;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof EmvField<?> other)) {
      return false;
    }
    return serialize().equals(other.serialize());
  }

  @Override
  public int hashCode() {
    return 31 * getId().hashCode() + getValue().hashCode();
  }
}
