package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

public class EchoNode extends Node {
	private Element[] elements;

	public EchoNode(Element[] elements) {
		if (elements == null) {
			throw new NullPointerException("Cannot set elements to null.");
		}

		this.elements = elements;
	}

	public Element[] getElements() {
		return elements;
	}
}
