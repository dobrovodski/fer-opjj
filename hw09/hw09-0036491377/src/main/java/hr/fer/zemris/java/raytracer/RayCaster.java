package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

public class RayCaster {
    public static void main(String[] args) {
        RayTracerViewer.show(getIRayTracerProducer(),
                new Point3D(10,0,0),
                new Point3D(0,0,0),
                new Point3D(0,0,10),
                20, 20);
    }
    private static IRayTracerProducer getIRayTracerProducer() {
        return new IRayTracerProducer() {
            @Override
            public void produce(Point3D eye, Point3D view, Point3D viewUp,
                    double horizontal, double vertical, int width, int height,
                    long requestNo, IRayTracerResultObserver observer) {
                System.out.println("Započinjem izračune...");
                short[] red = new short[width*height];
                short[] green = new short[width*height];
                short[] blue = new short[width*height];
                Point3D zAxis = new Point3D(0, 0, 1);
                Point3D yAxis = new Point3D(0, 1, 0);
                Point3D xAxis = new Point3D(1, 0, 0);
                Point3D screenCorner = new Point3D(1, 1, 0);
                Scene scene = RayTracerViewer.createPredefinedScene();
                short[] rgb = new short[3];
                int offset = 0;
                for(int y = 0; y < height; y++) {
                    for(int x = 0; x < width; x++) {
                        Point3D screenPoint = new Point3D(1.0 * x / width, 1.0 * y / height, 1);
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
            }
        };
    }

    private static void tracer(Scene scene, Ray ray, short[] rgb) {
        
    }
}
