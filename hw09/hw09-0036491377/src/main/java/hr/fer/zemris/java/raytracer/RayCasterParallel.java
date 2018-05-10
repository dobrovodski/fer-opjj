package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * This class performs and displays a ray casted scene using only a single thread.
 *
 * @author matej
 */
public class RayCasterParallel {
    /**
     * Tolerance for comparing two intersections.
     */
    private final static double TOLERANCE = 1E-6;

    /**
     * Main entry point into the program.
     *
     * @param args cl arguments
     */
    public static void main(String[] args) {
        RayTracerViewer.show(getIRayTracerProducer(),
                new Point3D(10, 0, 0),
                new Point3D(0, 0, 0),
                new Point3D(0, 0, 10),
                20, 20);
    }

    private static class Task extends RecursiveAction {

        /**
		 * Default serial version UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
         * Fork threshold.
         */
        private final static int THRESHOLD = 16;

        // screen information
        /**
         * Screen width.
         */
        private int width;
        /**
         * Screen height.
         */
        private int height;
        /**
         * Horizontal width of observed space.
         */
        private double horizontal;
        /**
         * Vertical height of observed space.
         */
        private double vertical;

        // range to draw
        /**
         * Lower bound.
         */
        private int yMin;
        /**
         * Upper bound
         */
        private int yMax;

        // scene information
        /**
         * Scene.
         */
        private Scene scene;
        /**
         * Screen corner.
         */
        private Point3D screenCorner;
        /**
         * Y axis direction vector.
         */
        private Point3D yAxis;
        /**
         * X axis direction vector.
         */
        private Point3D xAxis;
        /**
         * Observer position.
         */
        private Point3D eye;

        // color data
        /**
         * Red component.
         */
        private short[] red;
        /**
         * Green component.
         */
        private short[] green;
        /**
         * Blue component.
         */
        private short[] blue;

        private Task(int width, int height, double horizontal, double vertical, int yMin, int yMax, Scene scene,
                Point3D screenCorner, Point3D yAxis, Point3D xAxis, Point3D eye, short[] red, short[] green, short[]
                blue) {
            this.width = width;
            this.height = height;
            this.horizontal = horizontal;
            this.vertical = vertical;
            this.yMin = yMin;
            this.yMax = yMax;
            this.scene = scene;
            this.screenCorner = screenCorner;
            this.yAxis = yAxis;
            this.xAxis = xAxis;
            this.eye = eye;
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        @Override
        protected void compute() {
            if (yMax - yMin + 1 <= THRESHOLD) {
                computeDirect();
                return;
            }
            invokeAll(
                    new Task(width, height, horizontal, vertical, yMin, yMin + (yMax - yMin) / 2, scene,
                            screenCorner, yAxis, xAxis, eye, red, green, blue),
                    new Task(width, height, horizontal, vertical, yMin + (yMax - yMin) / 2 + 1, yMax, scene,
                            screenCorner, yAxis, xAxis, eye, red, green, blue)
            );
        }

        private void computeDirect() {
            short[] rgb = new short[3];
            int offset = yMin * width;
            for (int y = yMin; y <= yMax; y++) {
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
        }

    }

    /**
     * Returns implementation of {@link IRayTracerProducer} with respect to the current scene settings (eye, view,
     * viewUp and window).
     *
     * @return implemented {@link IRayTracerProducer}
     */
    private static IRayTracerProducer getIRayTracerProducer() {
        return (eye, view, viewUp, horizontal, vertical, width, height, requestNo, observer) -> {
            System.out.println("Započinjem izračune...");

            short[] red = new short[width * height];
            short[] green = new short[width * height];
            short[] blue = new short[width * height];

            Point3D VUV = viewUp.normalize();

            // z-axis orientation
            Point3D zAxis = view.sub(eye).normalize();
            // y-axis orientation
            Point3D yAxis = VUV.sub(zAxis.scalarMultiply(zAxis.scalarProduct(VUV))).normalize();
            // x-axis orientation
            Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

            // corner
            Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
                                       .add(yAxis.scalarMultiply(vertical / 2));
            Scene scene = RayTracerViewer.createPredefinedScene();


            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(new Task(width, height, horizontal, vertical, 0, height - 1, scene, screenCorner, yAxis,
                    xAxis, eye, red, green, blue));
            pool.shutdown();

            System.out.println("Izračuni gotovi...");
            observer.acceptResult(red, green, blue, requestNo);
            System.out.println("Dojava gotova...");
        };
    }

    /**
     * Finds the closest intersection for the given ray and determines the colors for it.
     *
     * @param scene scene
     * @param ray ray to check
     * @param rgb color data for pixel
     */
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

    /**
     * Determines the color for the given ray intersection by calculating both the diffuse and reflective components.
     *
     * @param s intersection
     * @param scene scene
     * @param ray ray to check
     * @param rgb color data for pixel
     */
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
                calculateSpecularComponent(rgb, ls, sdash, ray);
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
    private static void calculateSpecularComponent(short[] rgb, LightSource ls, RayIntersection s, Ray vRay) {
        Point3D l = s.getPoint().sub(ls.getPoint()).normalize();
        Point3D n = s.getNormal();

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
