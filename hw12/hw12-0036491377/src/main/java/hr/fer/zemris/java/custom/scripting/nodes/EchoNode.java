package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Node representing a command which generates some textual output dynamically.
 *
 * @author matej
 */
public class EchoNode extends Node {
    private Element[] elements;

    /**
     * Constructor which takes in an array of {@code Element}s which the node holds
     *
     * @param elements elements to add
     */
    public EchoNode(Element[] elements) {
        if (elements == null) {
            throw new NullPointerException("Cannot set elements to null.");
        }

        this.elements = elements;
    }

    /**
     * Returns elements of this node
     *
     * @return elements
     */
    public Element[] getElements() {
        return elements;
    }

    @Override
    public String asText() {
        StringBuilder sb = new StringBuilder();
        for (Element el : elements) {
            sb.append(el.asText()).append(" ");
        }
        return "{$= " + sb.toString() + "$}";
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitEchoNode(this);
    }
}
