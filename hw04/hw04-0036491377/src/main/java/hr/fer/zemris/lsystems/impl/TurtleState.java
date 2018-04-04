package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.math.Vector2D;

import java.awt.Color;

/**
 *
 */
public class TurtleState {
	//
	private Vector2D position;
	//
	private Vector2D direction;
	//
	private Color color;
	//
	private double length;

	public TurtleState(Vector2D position, Vector2D direction, Color color, double length) {
		this.position = position;
		this.direction = direction.normalized();
		this.color = color;
		this.length = length;
	}

	/**
	 *
	 * @param position
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 *
	 * @param direction
	 */
	public void setDirection(Vector2D direction) {
		direction.normalize();
		this.direction = direction;
	}

	/**
	 *
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 *
	 * @param length
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 *
	 * @return
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 *
	 * @return
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 *
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 *
	 * @return
	 */
	public double getLength() {
		return length;
	}

	/**
	 *
	 * @return
	 */
	public TurtleState copy() {
		return new TurtleState(position, direction, color, length);
	}
}
