package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.math.Vector2D;

import java.awt.Color;
import java.util.Objects;

/**
 * This class models a single state of the system which will be used to calculate the next step in creating the
 * L-system (such as color, length of the line, position, direction).
 * @author matej
 */
public class TurtleState {
	// Current position
	private Vector2D position;
	// Current direction in which the state will move to when it draws/skips
	private Vector2D direction;
	// Color of the line to be drawn
	private Color color;
	// Length of the next line to be drawn
	private double length;

	/**
	 * Constructor for TurtleState.
	 * @param position position of state
	 * @param direction direction of state
	 * @param color color of state
	 * @param length length of state
	 * @throws NullPointerException if a state is set to null
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double length) {
		if (position == null || direction == null || color == null) {
			throw new NullPointerException("Cannot set state properties to null.");
		}
		this.position = position;
		this.direction = direction.normalized();
		this.color = color;
		this.length = length;
	}

	/**
	 * Sets the position of the state.
	 * @param position position of state
	 * @throws NullPointerException if position is null
	 */
	public void setPosition(Vector2D position) {
		Objects.requireNonNull(position, "Cannot set position to null.");
		this.position = position;
	}

	/**
	 * Sets the direction of the state.
	 * @param direction direction of state
	 * @throws NullPointerException if direction is null
	 */
	public void setDirection(Vector2D direction) {
		Objects.requireNonNull(position, "Cannot set direction to null.");
		direction.normalize();
		this.direction = direction;
	}

	/**
	 * Sets the color of the state.
	 * @param color color of state
	 * @throws NullPointerException if color is null
	 */
	public void setColor(Color color) {
		Objects.requireNonNull(position, "Cannot set color to null.");
		this.color = color;
	}

	/**
	 * Sets the direction of the state.
	 * @param length length of state
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * Returns the position of the state.
	 * @return position of state
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Returns the direction of the state.
	 * @return direction of state
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Returns the color of the state.
	 * @return color of state
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns the length of the state.
	 * @return length of state
	 */
	public double getLength() {
		return length;
	}

	/**
	 * Returns a deep-copy of the current state.
	 * @return copy of the current state
	 */
	public TurtleState copy() {
		return new TurtleState(position, direction, color, length);
	}
}
