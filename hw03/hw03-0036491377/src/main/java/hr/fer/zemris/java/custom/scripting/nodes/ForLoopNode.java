package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node {
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;

	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
		if (variable == null) {
			throw new NullPointerException("Cannot set variable to null.");
		}

		if (startExpression == null) {
			throw new NullPointerException("Cannot set startExpression to null.");
		}

		if (stepExpression == null) {
			throw new NullPointerException("Cannot set endExpression to null.");
		}

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
}
