package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class used to represent functions.
 */
public class ElementFunction extends Element {
	// Name of the function
	private String name;

	/**
	 * Constructor which sets the function name to the given one.
	 *
	 * @param name name be set to
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return "@" + name;
	}
}
