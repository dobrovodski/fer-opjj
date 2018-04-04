package hr.fer.zemris.math;

import java.util.Objects;

/**
 *
 */
public class Vector2D {
	//
	private double x;
	//
	private double y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 *
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 *
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 *
	 * @param offset
	 */
	public void translate(Vector2D offset) {
		Objects.requireNonNull(offset, "Cannot pass null to translate.");
		this.x += offset.x;
		this.y += offset.y;
	}

	/**
	 *
	 * @param offset
	 * @return
	 */
	public Vector2D translated(Vector2D offset) {
		Objects.requireNonNull(offset, "Cannot pass null to translated.");
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}

	/**
	 *
	 * @param angle
	 */
	public void rotate(double angle) {
		double radians = angle * Math.PI / 180;
		double x2 = Math.cos(radians) * this.x - Math.sin(radians) * this.y;
		double y2 = Math.sin(radians) * this.x + Math.cos(radians) * this.y;

		this.x = x2;
		this.y = y2;
	}

	/**
	 *
	 * @param angle
	 * @return
	 */
	public Vector2D rotated(double angle) {
		double radians = angle * Math.PI / 180;
		double x2 = Math.cos(radians) * this.x - Math.sin(radians) * this.y;
		double y2 = Math.sin(radians) * this.x + Math.cos(radians) * this.y;

		return new Vector2D(x2, y2);
	}

	/**
	 *
	 * @param scalar
	 */
	public void scale(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
	}

	/**
	 *
	 * @param scalar
	 * @return
	 */
	public Vector2D scaled(double scalar) {
		return new Vector2D(this.x * scalar, this.y * scalar);
	}

	/**
	 *
	 * @return
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	/**
	 *
	 */
	public void normalize() {
		double len = Math.sqrt(x * x + y * y);
		x = x / len;
		y = y / len;
	}

	/**
	 *
	 * @return
	 */
	public Vector2D normalized() {
		double len = Math.sqrt(x*x + y*y);
		return new Vector2D(x / len, y / len);
	}

	@Override
	public boolean equals(Object obj) {
		final double EPS = 1E-6;

		if (this == obj) {
			return true;
		}

		return obj instanceof Vector2D
				&& Math.abs(this.x - ((Vector2D) obj).getX()) < EPS
				&& Math.abs(this.y - ((Vector2D) obj).getY()) < EPS;
	}
}
