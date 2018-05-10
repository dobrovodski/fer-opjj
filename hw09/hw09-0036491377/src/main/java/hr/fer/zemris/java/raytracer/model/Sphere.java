package hr.fer.zemris.java.raytracer.model;

import java.util.Objects;

/**
 * This class represents a sphere and provides the findClosestRayIntersection to find the closest intersection between a
 * ray and the sphere.
 *
 * @author matej
 */
public class Sphere extends GraphicalObject {
    /**
     * Center of the sphere.
     */
    private Point3D center;
    /**
     * Radius of the sphere.
     */
    private double radius;
    /**
     * Diffuse component for the color red.
     */
    private double kdr;
    /**
     * Diffuse component for the color green.
     */
    private double kdg;
    /**
     * Diffuse component for the color blue.
     */
    private double kdb;
    /**
     * Reflection component for the color red.
     */
    private double krr;
    /**
     * Reflection component for the color green.
     */
    private double krg;
    /**
     * Reflection component for the color blue.
     */
    private double krb;
    /**
     * Reflection component coefficient.
     */
    private double krn;

    /**
     * The constructor for {@link Sphere}.
     *
     * @param center center of the sphere
     * @param radius radius of the sphere
     * @param kdr diffuse component for the color red
     * @param kdg diffuse component for the color green
     * @param kdb diffuse component for the color blue
     * @param krr reflection component for the color red
     * @param krg reflection component for the color green
     * @param krb reflection component for the color blue
     * @param krn reflection component coefficient
     */
    public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double
            krb, double krn) {
        Objects.requireNonNull(center, "Center cannot be null.");

        this.center = center;
        this.radius = radius;
        this.kdr = kdr;
        this.kdg = kdg;
        this.kdb = kdb;
        this.krr = krr;
        this.krg = krg;
        this.krb = krb;
        this.krn = krn;
    }

    @Override
    public RayIntersection findClosestRayIntersection(Ray ray) {
        Objects.requireNonNull(ray, "Ray cannot be null");

        // ||x-c||^2 = r^2 - sphere
        // x = o + dl - ray
        // o, l, c, x are vectors
        // ||o + dl - c||^2 = r^2
        // d^2*l*l + 2*d*l*(o-c) + (o-c)^2 = r^2
        // ad^2 + bd + c = 0

        Point3D OC = ray.start.sub(center);

        double a = ray.direction.scalarProduct(ray.direction);
        double b = 2 * ray.direction.scalarProduct(OC);
        double c = OC.scalarProduct(OC) - radius * radius;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return null;
        }

        double discSqrt = Math.sqrt(discriminant);
        double x1 = (-b + discSqrt) / (2 * a);
        double x2 = (-b - discSqrt) / (2 * a);

        double x = x1 < x2 ? x1 : x2;
        Point3D intersectionPoint = ray.start.add(ray.direction.scalarMultiply(x));
        return new RayIntersectionImpl(
                intersectionPoint,
                x,
                ray.start.sub(center).norm() < ray.start.sub(intersectionPoint).norm()
        );
    }

    /**
     * {@link RayIntersection} implementation.
     *
     * @author matej
     */
    private class RayIntersectionImpl extends RayIntersection {

        /**
         * Constructor for RayIntersectionImpl.
         *
         * @param point point of intersection
         * @param distance distance of intersection
         * @param outer true if the intersection is on the outside of the object
         */
        RayIntersectionImpl(Point3D point, double distance, boolean outer) {
            super(point, distance, outer);
        }

        @Override
        public Point3D getNormal() {
            return getPoint().sub(center).normalize();
        }

        @Override
        public double getKdr() {
            return kdr;
        }

        @Override
        public double getKdg() {
            return kdg;
        }

        @Override
        public double getKdb() {
            return kdb;
        }

        @Override
        public double getKrr() {
            return krr;
        }

        @Override
        public double getKrg() {
            return krg;
        }

        @Override
        public double getKrb() {
            return krb;
        }

        @Override
        public double getKrn() {
            return krn;
        }
    }
}
