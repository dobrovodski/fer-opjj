package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Nodes are used for representation of structured documents in the parsing process.
 *
 * @author matej
 */
public class Node {

	// Array of children nodes of this node
	ArrayIndexedCollection children;

	/**
	 * Adds child node to this node.
	 *
	 * @param child node to be added as a child
	 */
	public void addChildNode(Node child) {
		if (child == null) {
			throw new NullPointerException("Cannot add null as child");
		}

		if (children == null) {
			children = new ArrayIndexedCollection();
		}

		children.add(child);
	}

	/**
	 * Returns the number of children this node has.
	 *
	 * @return number of children
	 */
	public int numberOfChildren() {
		if (children == null) {
			return 0;
		}

		return children.size();
	}

	/**
	 * Gets child at specified index
	 *
	 * @param index index to catch child at.
	 * @return child node at index
	 * @throws IndexOutOfBoundsException if trying to access non-existent child.
	 */
	public Node getChild(int index) {
		if (children == null) {
			throw new IndexOutOfBoundsException("No children have been added yet.");
		}

		return (Node) children.get(index);
	}

	/**
	 * Returns string representation of this node.
	 *
	 * @return string representation of node
	 */
	public String asText() {
		return "";
	}

	/**
	 * Returns {@code true} if the node is meant to be followed by an {$END$} tag.
	 *
	 * @return {@code true} if followed by {$END$} tag, {@code false} otherwise
	 */
	public boolean hasEndTag() {
		return false;
	}
}
