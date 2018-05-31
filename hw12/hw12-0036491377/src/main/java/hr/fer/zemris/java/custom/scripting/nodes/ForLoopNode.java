package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Node representing a single for-loop construct
 *
 * @author matej
 */
public class ForLoopNode extends Node {
    // Variable of for loop
    private ElementVariable variable;
    // Start expression
    private Element startExpression;
    // End expression
    private Element endExpression;
    // Step expression
    private Element stepExpression;

    /**
     * Constructor for loop node
     *
     * @param variable variable
     * @param startExpression start expression
     * @param endExpression end expression
     * @param stepExpression step expression
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element
            stepExpression) {
        if (variable == null) {
            throw new NullPointerException("Cannot set variable to null.");
        }

        if (startExpression == null) {
            throw new NullPointerException("Cannot set startExpression to null.");
        }

        if (endExpression == null) {
            throw new NullPointerException("Cannot set endExpression to null.");
        }

        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * Returns the variable.
     *
     * @return variable of for loop
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Returns the start expression.
     *
     * @return start expression of for loop
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Returns the end expression.
     *
     * @return end expression of for loop
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Returns the step expression.
     *
     * @return step expression of for loop
     */
    public Element getStepExpression() {
        return stepExpression;
    }

    @Override
    public String asText() {
        String step = getStepExpression() == null ? "" : getStepExpression().asText();
        return "{$ FOR " + getVariable().asText() + " "
               + getStartExpression().asText() + " "
               + getEndExpression().asText() + " "
               + step + " $}";
    }

    @Override
    public boolean hasEndTag() {
        return true;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitForLoopNode(this);
    }
}
