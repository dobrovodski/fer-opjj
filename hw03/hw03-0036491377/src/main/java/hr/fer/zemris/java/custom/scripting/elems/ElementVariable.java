package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class used to represent variables.
 */
public class ElementVariable extends Element {
	// Name of the variable
	private String name;

	/**
	 * Constructor which sets name of the variable to the given one.
	 *
	 * @param name name of the variable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}
}
