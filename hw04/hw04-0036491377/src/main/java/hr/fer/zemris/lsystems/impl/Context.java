package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 *
 */
public class Context {
	private ObjectStack states;

	public Context() {
		states = new ObjectStack();
	}

	/**
	 *
	 * @return
	 */
	public TurtleState getCurrentState() {
		return (TurtleState)states.peek();
	}

	/**
	 *
	 * @param state
	 */
	public void pushState(TurtleState state) {
		if (state == null) {
			throw new LSystemBuilderException("Cannot push null to context.");
		}

		states.push(state);
	}

	/**
	 *
	 */
	public void popState() {
		states.pop();
	}
}
