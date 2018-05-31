package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class used to represent constant doubles.
 */
public class ElementConstantDouble extends Element {
	// Value of the double
	private double value;

	/**
	 * Constructor which sets the value of the element to the given value.
	 *
	 * @param value value to be set to
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
