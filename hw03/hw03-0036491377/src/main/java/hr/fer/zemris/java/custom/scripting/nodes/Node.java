package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

public class Node {

	ArrayIndexedCollection children;

	public void addChildNode(Node child) {
		if (child == null) {
			throw new NullPointerException("Cannot add null as child");
		}

		if (children == null) {
			children = new ArrayIndexedCollection();
		}

		children.add(child);
	}

	public int numberOfChildren() {
		if (children == null) {
			return 0;
		}

		return children.size();
	}

	public Node getChild(int index) {
		if (children == null) {
			throw new IndexOutOfBoundsException("No children have been added yet.");
		}

		return (Node)children.get(index);
	}

	public String asText() {
		return "";
	}

	public boolean hasEndTag() {
		return false;
	}
}
