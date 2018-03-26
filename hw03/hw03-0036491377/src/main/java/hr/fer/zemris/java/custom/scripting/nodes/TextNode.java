package hr.fer.zemris.java.custom.scripting.nodes;

public class TextNode extends Node {
	private String text;

	public TextNode(String text) {
		if (text == null) {
			throw new NullPointerException("Cannot set text to null");
		}
		this.text = text;
	}

	public String asText() {
		return text;
	}
}
