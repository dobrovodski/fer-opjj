package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.math.Vector2D;

import java.awt.Color;

public class TurtleState {
	private Vector2D position;
	private Vector2D direction;
	private Color color;
	private double length;

	public TurtleState(Vector2D position, Vector2D direction, Color color, double length) {
		this.position = position;
		this.direction = direction.normalized();
		this.color = color;
		this.length = length;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public void setDirection(Vector2D direction) {
		direction.normalize();
		this.direction = direction;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public Vector2D getPosition() {
		return position;
	}

	public Vector2D getDirection() {
		return direction;
	}

	public Color getColor() {
		return color;
	}

	public double getLength() {
		return length;
	}

	public TurtleState copy() {
		return new TurtleState(position, direction, color, length);
	}
}
