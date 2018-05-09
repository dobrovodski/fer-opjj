package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

public class RayCaster {
    private final static double TOLERANCE = 1E-6;

    public static void main(String[] args) {
        RayTracerViewer.show(getIRayTracerProducer(),
                new Point3D(10, 0, 0),
                new Point3D(0, 0, 0),
                new Point3D(0, 0, 10),
                20, 20);
    }

    private static IRayTracerProducer getIRayTracerProducer() {
        return (eye, view, viewUp, horizontal, vertical, width, height, requestNo, observer) -> {
            System.out.println("Započinjem izračune...");

            short[] red = new short[width * height];
            short[] green = new short[width * height];
            short[] blue = new short[width * height];

            Point3D VUV = viewUp.normalize();

            Point3D zAxis = view.sub(eye).normalize();
            Point3D yAxis = VUV.sub(zAxis.scalarMultiply(zAxis.scalarProduct(VUV))).normalize();
            Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

            Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
                                       .add(yAxis.scalarMultiply(vertical / 2));
            Scene scene = RayTracerViewer.createPredefinedScene();

            short[] rgb = new short[3];
            int offset = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1)))
                                                      .sub(yAxis.scalarMultiply(vertical * y / (height - 1)));

                    Ray ray = Ray.fromPoints(eye, screenPoint);
                    tracer(scene, ray, rgb);

                    red[offset] = rgb[0] > 255 ? 255 : rgb[0];
                    green[offset] = rgb[1] > 255 ? 255 : rgb[1];
                    blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

                    offset++;
                }
            }

            System.out.println("Izračuni gotovi...");
            observer.acceptResult(red, green, blue, requestNo);
            System.out.println("Dojava gotova...");
        };
    }

    private static void tracer(Scene scene, Ray ray, short[] rgb) {
        rgb[0] = 0;
        rgb[1] = 0;
        rgb[2] = 0;
        RayIntersection closest = findClosestIntersection(scene, ray);
        if (closest == null) {
            return;
        }

        determineColorFor(closest, scene, ray, rgb);
    }

    private static void determineColorFor(RayIntersection s, Scene scene, Ray ray, short[] rgb) {
        rgb[0] = 15;
        rgb[1] = 15;
        rgb[2] = 15;

        for (LightSource ls : scene.getLights()) {
            Ray rdash = Ray.fromPoints(ls.getPoint(), s.getPoint());
            RayIntersection sdash = findClosestIntersection(scene, rdash);

            if (sdash == null) {
                continue;
            }

            double d1 = sdash.getPoint().sub(ls.getPoint()).norm();
            double d2 = s.getPoint().sub(ls.getPoint()).norm();

            if (d1 + TOLERANCE > d2) {
                calculateDiffuseComponent(rgb, ls, sdash);
                calculateReflectiveComponent(rgb, ls, sdash, ray);
            }
        }
    }

    /**
     * Calculates the diffuse RGB components given the light source and intersection.
     *
     * @param rgb color data
     * @param ls light source
     * @param s intersection between source and object
     */
    private static void calculateDiffuseComponent(short[] rgb, LightSource ls, RayIntersection s) {
        Point3D l = ls.getPoint().sub(s.getPoint()).normalize();
        Point3D n = s.getNormal();

        double ln = l.scalarProduct(n);
        if (ln < 0) {
            ln = 0;
        }

        // Id = Ii * kd * cos(fi)
        rgb[0] += ls.getR() * s.getKdr() * ln;
        rgb[1] += ls.getG() * s.getKdg() * ln;
        rgb[2] += ls.getB() * s.getKdb() * ln;
    }

    /**
     * Calculates the reflective RGB components given the light source and intersection.
     *
     * @param rgb color data
     * @param ls light source
     * @param s intersection between source and object
     * @param vRay view ray
     */
    private static void calculateReflectiveComponent(short[] rgb, LightSource ls, RayIntersection s, Ray vRay) {
        Point3D l = s.getPoint().sub(ls.getPoint()).normalize();
        Point3D n = s.getNormal().normalize();

        double ln = l.scalarProduct(n);

        // Reflected ray - r = l - 2 * n * (l*n)
        Point3D r = l.sub(n.scalarMultiply(2 * ln)).normalize();
        // Ray from eye to intersection - v = eye - s
        Point3D v = vRay.start.sub(s.getPoint()).normalize();

        double rv = r.scalarProduct(v);
        if (rv < 0) {
            rv = 0;
        }

        double rvn = Math.pow(rv, s.getKrn());

        rgb[0] += ls.getR() * s.getKrr() * rvn;
        rgb[1] += ls.getG() * s.getKrg() * rvn;
        rgb[2] += ls.getB() * s.getKrb() * rvn;
    }

    /**
     * Finds the closest intersection between the ray and all the objects in the scene.
     *
     * @param scene scene with objects
     * @param ray ray to check intersections with
     *
     * @return closest intersection in front of observer
     */
    private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
        RayIntersection closest = null;

        for (GraphicalObject obj : scene.getObjects()) {
            RayIntersection current = obj.findClosestRayIntersection(ray);

            if (current == null) {
                continue;
            }

            if (closest == null || current.getDistance() < closest.getDistance()) {
                closest = current;
            }
        }

        return closest;
    }
}
