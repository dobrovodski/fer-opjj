package hr.fer.zemris.raytracer.model;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;

public class Sphere extends GraphicalObject {
    private Point3D center;
    private double radius;
    private double kdr;
    private double kdg;
    private double kdb;
    private double krr;
    private double krg;
    private double krb;
    private double krn;

    public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double
            krb, double krn) {
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
        return null;
    }
}
