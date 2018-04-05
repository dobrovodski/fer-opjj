package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class models a 2-dimensional vector as 2 real components (x, y) and provides methods to modify the vector.
 *
 * @author matej
 */
public class Vector2D {
	// x-component
	private double x;
	// y-component
	private double y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns x component of vector.
	 *
	 * @return x component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns y component of vector
	 *
	 * @return y component
	 */
	public double getY() {
		return y;
	}

	/**
	 * Translates current vector by {@code offset} vector.
	 *
	 * @param offset vector to offset by
	 * @throws NullPointerException if {@code offset} is null
	 */
	public void translate(Vector2D offset) {
		Objects.requireNonNull(offset, "Cannot pass null to translate.");
		this.x += offset.x;
		this.y += offset.y;
	}

	/**
	 * Returns translated vector which is translated by {@code offset} vector. Does not modify {@code this} vector.
	 *
	 * @param offset vector to offset by
	 * @return newly created translated vector
	 * @throws NullPointerException if {@code offset} is null
	 */
	public Vector2D translated(Vector2D offset) {
		Objects.requireNonNull(offset, "Cannot pass null to translated.");
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}

	/**
	 * Rotates current vector by {@code angle}.
	 *
	 * @param angle angle to rotate by
	 */
	public void rotate(double angle) {
		double radians = angle * Math.PI / 180;
		double x2 = Math.cos(radians) * this.x - Math.sin(radians) * this.y;
		double y2 = Math.sin(radians) * this.x + Math.cos(radians) * this.y;

		this.x = x2;
		this.y = y2;
	}

	/**
	 * Returns rotated vector which is rotated by {@code angle}. Does not modify {@code this} vector.
	 *
	 * @param angle angle to rotate by
	 * @return newly created rotated vector
	 */
	public Vector2D rotated(double angle) {
		double radians = angle * Math.PI / 180;
		double x2 = Math.cos(radians) * this.x - Math.sin(radians) * this.y;
		double y2 = Math.sin(radians) * this.x + Math.cos(radians) * this.y;

		return new Vector2D(x2, y2);
	}

	/**
	 * Scales current vector by {@code scalar}.
	 *
	 * @param scalar scalar value to scale {@code this} vector by.
	 */
	public void scale(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
	}

	/**
	 * Returns scaled vector which is scaled by {@code scalar}. Does not modify {@code this} vector.
	 *
	 * @param scalar scalar value to scale by
	 * @return newly created scaled vector
	 */
	public Vector2D scaled(double scalar) {
		return new Vector2D(this.x * scalar, this.y * scalar);
	}

	/**
	 * Returns a copy of this vector.
	 *
	 * @return copy of this vector
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}

	/**
	 * Normalizes this vector.
	 */
	public void normalize() {
		double len = Math.sqrt(x * x + y * y);
		x = x / len;
		y = y / len;
	}

	/**
	 * Returns normalized version of this vector
	 *
	 * @return normalized vector
	 */
	public Vector2D normalized() {
		double len = Math.sqrt(x * x + y * y);
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
