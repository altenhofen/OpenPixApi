package io.github.altenhofen.openpixapi.core.field;

import io.github.altenhofen.openpixapi.core.formatter.EMVFormatter;
import io.github.altenhofen.openpixapi.core.formatter.DigitFormatter;
import io.github.altenhofen.openpixapi.core.formatter.PaddingPolicy;
import io.github.altenhofen.openpixapi.core.payload.StaticPixPayload;
import org.jetbrains.annotations.Nullable;

/**
 * Base class for a single field on EMV.
 * I've chosen to keep it not abstract and extending it for composite scenarios
 * <br><br>
 * See the constructor annotation for more details on architectural choices for
 * formatters.
 *
 * @param <T> type of EMVField's value field
 * @author Augusto Bussmann Altenhofen
 * @see io.github.altenhofen.openpixapi.core.field.CompositeEMVField
 * @since 0.01-DEV
 */
public class EMVField<T> {
    private final String tag;
    private final T value;
    @Nullable
    private final String fieldName;
    private final EMVFormatter<T> formatter;
    private final DigitFormatter digitFormatter;

    /**
     *
     * @param fieldName used only for debugging situations.
     *                  <br>It does not affect the {@link StaticPixPayload} generation
     * @param tag       as specified in the EMV standard, it's usually a numeric string of 1 or 2 digits.
     * @param value     the value of type T that can be represented by the
     *                  Common Character Set as defined in <b>EMV Book 4</b>
     * @param formatter a class that implements {@link EMVFormatter}
     */
    public EMVField(@Nullable String fieldName, String tag, T value, EMVFormatter<T> formatter) {
        this.fieldName = fieldName;
        this.tag = tag;
        this.value = value;
        this.formatter = formatter;
        this.digitFormatter = new DigitFormatter(2, PaddingPolicy.LEFT);
    }


    /**
     * Serializes this field using the EMV "ID + Length + Value" format.
     *
     * <p>Example:</p>
     * <pre>
     * ID       = "00"
     * Value    = "JA"
     * Result   = "0002JA"
     * </pre>
     *
     * <p>If the field is not present (field name is {@code null}),
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
     * This is where the actual serialization happens.
     * It's done this way so inherited classes can override just the serialization part
     *
     * @see CompositeEMVField
     * @return a String containing the formatted value
     */
    protected String serializeValue() {
        if (formatter == null) {
            throw new IllegalStateException("formatter is null");
        }

        return this.formatter.format(this.value);
    }

    protected String formatId() {
        if (tag.length() != 2) {
            throw new IllegalArgumentException(
                    String.format("EMV id must be exactly 2 characters: %s", tag)
            );
        }
        return tag;
    }


    public @Nullable String getFieldName() {
        return this.fieldName;
    }

    protected T getValue() {
        return this.value;
    }

    public String getTag() {
        return this.tag;
    }
}

