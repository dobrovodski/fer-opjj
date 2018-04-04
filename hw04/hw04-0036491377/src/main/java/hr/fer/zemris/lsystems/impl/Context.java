package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class Context {
	private ObjectStack states;

	public Context() {
		states = new ObjectStack();
	}

	public TurtleState getCurrentState() {
		return (TurtleState)states.peek();
	}

	public void pushState(TurtleState state) {
		states.push(state);
	}

	public void popState() {
		states.pop();
	}
}
