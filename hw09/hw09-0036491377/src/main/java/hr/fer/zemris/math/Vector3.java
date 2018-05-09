package hr.fer.zemris.math;

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
     */
    public Vector3 add(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Subtracts other vector from this vector.
     *
     * @param other other vector
     *
     * @return this subtracted by other
     */
    public Vector3 sub(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Returns the dot product of this vector and the given one
     *
     * @param other other vector
     *
     * @return dot product of this vector and the given one
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
     */
    public Vector3 cross(Vector3 other) {
        return new Vector3(
                y * other.z - z * other.y,
                z * other.x - x * other.z,
                x * other.y - y * other.x
        );
    }

    public Vector3 scale(double s) {
        return new Vector3(x * s, y * s, z * s);
    }

    /**
     * Returns the cosine of the angle between this vector and the given one.
     *
     * @param other other vector
     *
     * @return cosine of the angle between this vector and the given one
     */
    public double cosAngle(Vector3 other) {
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
}
