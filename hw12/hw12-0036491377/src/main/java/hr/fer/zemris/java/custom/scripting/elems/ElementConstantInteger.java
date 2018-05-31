package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class used to represent constant integers.
 */
public class ElementConstantInteger extends Element {
    // Value of the integer
    private int value;

    /**
     * Constructor which sets the value of the element to the given value.
     *
     * @param value value to be set to
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    @Override
    public String asText() {
        return String.valueOf(value);
    }
}
