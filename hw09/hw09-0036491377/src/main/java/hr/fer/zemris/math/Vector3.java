package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class represents a 3-dimensional vector and provides useful methods for performing unary and binary operations
 * using vectors.
 *
 * @author matej
 */
public class Vector3 {
    /**
     * X component of vector.
     */
    private double x;
    /**
     * Y component of vector.
     */
    private double y;
    /**
     * Z component of vector.
     */
    private double z;

    /**
     * Constructor for the Vector3 class.
     *
     * @param x x component of vector
     * @param y y component of vector
     * @param z z component of vector
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the norm of the vector.
     *
     * @return norm of the vector
     */
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Returns the normalized version of this vector.
     *
     * @return this vector normalized
     */
    public Vector3 normalized() {
        double norm = norm();
        return new Vector3(x / norm, y / norm, z / norm);
    }

    /**
     * Adds this and other vector together.
     *
     * @param other other vector
     *
     * @return this and other added
     *
     * @throws NullPointerException if other is null.
     */
    public Vector3 add(Vector3 other) {
        Objects.requireNonNull(other, "Other cannot be null.");
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Subtracts other vector from this vector.
     *
     * @param other other vector
     *
     * @return this subtracted by other
     *
     * @throws NullPointerException if other is null.
     */
    public Vector3 sub(Vector3 other) {
        Objects.requireNonNull(other, "Other cannot be null.");
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Returns the dot product of this vector and the given one
     *
     * @param other other vector
     *
     * @return dot product of this vector and the given one
     *
     * @throws NullPointerException if other is null.
     */
    public double dot(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    /**
     * Cross product between this vector and the given one.
     *
     * @param other other vector
     *
     * @return vector which is a cross product between this and other
     *
     * @throws NullPointerException if other is null.
     */
    public Vector3 cross(Vector3 other) {
        return new Vector3(
                y * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x
        );
    }

    /**
     * Creates a vector which is the result of scaling this vector with the given scalar.
     *
     * @param s scalar to scale with
     *
     * @return resulting vector after scaling
     */
    public Vector3 scale(double s) {
        return new Vector3(x * s, y * s, z * s);
    }

    /**
     * Returns the cosine of the angle between this vector and the given one.
     *
     * @param other other vector
     *
     * @return cosine of the angle between this vector and the given one
     *
     * @throws NullPointerException if other is null.
     */
    public double cosAngle(Vector3 other) {
        Objects.requireNonNull(other, "Other cannot be null.");
        return dot(other) / (norm() * other.norm());
    }

    /**
     * Returns x component.
     *
     * @return x component
     */
    public double getX() {
        return x;
    }

    /**
     * Returns y component.
     *
     * @return y component
     */
    public double getY() {
        return y;
    }

    /**
     * Returns z component.
     *
     * @return z component
     */
    public double getZ() {
        return z;
    }

    /**
     * Returns the components of this vector in the form of an array.
     *
     * @return array with the vector's components
     */
    public double[] toArray() {
        return new double[]{x, y, z};
    }

    @Override
    public String toString() {
        return String.format("(%f, %f, %f)", x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        final double EPS = 1E-6;
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Vector3 vector3 = (Vector3) o;
        return Math.abs(vector3.x - x) < EPS &&
               Math.abs(vector3.y - y) < EPS &&
               Math.abs(vector3.z - z) < EPS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
