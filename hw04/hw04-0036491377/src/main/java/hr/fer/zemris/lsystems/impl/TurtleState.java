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
		this.direction = direction;
		this.color = color;
		this.length = length;
	}

	public TurtleState copy() {
		return new TurtleState(position, direction, color, length);
	}
}
