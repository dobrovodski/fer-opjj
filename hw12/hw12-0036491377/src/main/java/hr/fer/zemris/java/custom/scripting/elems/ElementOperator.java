package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class used to represent operators.
 */
public class ElementOperator extends Element {
	// String representation of operator
	private String symbol;

	/**
	 * Constructor which sets the symbol of the operator to the given one.
	 *
	 * @param symbol symbol of the operator
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}
}
