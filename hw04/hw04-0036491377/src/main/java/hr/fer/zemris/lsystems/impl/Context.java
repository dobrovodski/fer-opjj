package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Provides a class used to store the current state (such as color, direction ...)
 * @author matej
 */
public class Context {
	// Stack to keep track of states
	private ObjectStack states;

	public Context() {
		states = new ObjectStack();
	}

	/**
	 * Retrieves the current state without removing it.
	 * @return current state
	 */
	public TurtleState getCurrentState() {
		return (TurtleState)states.peek();
	}

	/**
	 * Pushes state onto the context stack
	 * @param state state to be pushed
	 */
	public void pushState(TurtleState state) {
		if (state == null) {
			throw new LSystemBuilderException("Cannot push null to context.");
		}

		states.push(state);
	}

	/**
	 * Removes the last state added onto the stack.
	 */
	public void popState() {
		if (states.size() == 0) {
			throw new LSystemBuilderException("Cannot pop state from empty state stack.");
		}
		states.pop();
	}
}
