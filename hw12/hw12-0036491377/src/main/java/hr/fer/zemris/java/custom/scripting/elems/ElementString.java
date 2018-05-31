package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class used to represent strings.
 */
public class ElementString extends Element {
    // Value of string
    private String value;

    /**
     * Constructor which sets the value of the stringto the given one.
     *
     * @param value value of the string
     */
    public ElementString(String value) {
        this.value = value;
    }

    @Override
    public String asText() {
        return "\"" + value + "\"";
    }
}
