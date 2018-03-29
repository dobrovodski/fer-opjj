package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node representing a piece of textual data
 *
 * @author matej
 */
public class TextNode extends Node {
	// Textual data stored in node
	private String text;

	/**
	 * Constructor which takes in the tex for this node to hold
	 *
	 * @param text text to store
	 */
	public TextNode(String text) {
		if (text == null) {
			throw new NullPointerException("Cannot set text to null");
		}
		this.text = text;
	}

	@Override
	public String asText() {
		return text;
	}
}
