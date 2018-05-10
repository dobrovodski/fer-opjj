package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * This class is used to demonstrate Newton-Raphson fractal generation. The user provides complex-valued roots of a
 * polynomial and the algorithm displays the fractal calculated through repeated iteration.
 *
 * @author matej
 */
public class Newton {
    /**
     * Complex rooted polynomial.
     */
    private static ComplexRootedPolynomial rootedPolynomial;
    /**
     * Complex polynomial.
     */
    private static ComplexPolynomial polynomial;
    /**
     * Convergence threshold.
     */
    private static final double CONV_THRESHOLD = 1E-3;
    /**
     * Closest root threshold.
     */
    private static final double ROOT_THRESHOLD = 2E-3;
    /**
     * Maximum iteration count.
     */
    private static final int MAX_ITERATIONS = 16 * 16 * 16;

    /**
     * Main entry point.
     *
     * @param args cl arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Newton-Raphson iteration-based fractal viewer.");
        System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
        Scanner sc = new Scanner(System.in);

        int rootCount = 1;
        List<Complex> roots = new ArrayList<>();
        while (true) {
            System.out.print(String.format("Root %d> ", rootCount));
            String root = sc.nextLine().toLowerCase();

            if (root.equals("done")) {
                System.out.println("Image of the fractal will appear shortly.");
                sc.close();
                break;
            }

            try {
                Complex c = Complex.fromString(root);
                roots.add(c);
                System.out.println(c);
            } catch (IllegalArgumentException ex) {
                System.out.println("Could not parse as complex number: " + root);
                continue;
            }

            rootCount++;
        }

        Complex[] rootsArr = roots.toArray(new Complex[0]);
        rootedPolynomial = new ComplexRootedPolynomial(rootsArr);
        polynomial = rootedPolynomial.toComplexPolynom();

        FractalViewer.show(new FractalProducer());
    }

    /**
     * A single task that the thread needs to calculate - a band bound by a lower and upper y value.
     *
     * @author matej.
     */
    public static class Task implements Callable<Void> {
        /**
         * Minimum real value.
         */
        double reMin;
        /**
         * Maximum real value.
         */
        double reMax;
        /**
         * Minimum imaginary value.
         */
        double imMin;
        /**
         * Maximum imaginary value.
         */
        double imMax;
        /**
         * Total width.
         */
        int width;
        /**
         * Total height.
         */
        int height;
        /**
         * Lower y bound.
         */
        int yMin;
        /**
         * Upper y bound.
         */
        int yMax;
        /**
         * Data.
         */
        short[] data;

        /**
         * Constructor for the task
         *
         * @param reMin min real value
         * @param reMax max real value
         * @param imMin min imag value
         * @param imMax max imag value
         * @param width width
         * @param height height
         * @param yMin min y bound
         * @param yMax max y bound
         * @param data image data
         */
        public Task(double reMin, double reMax, double imMin, double imMax,
                int width, int height, int yMin, int yMax, short[] data) {
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.data = data;
        }

        @Override
        public Void call() {
            Newton.calculate(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data);
            return null;
        }
    }

    /**
     * Implementation of {@link IFractalProducer}.
     *
     * @author matej
     */
    public static class FractalProducer implements IFractalProducer {
        /**
         * Thread pool.
         */
        private ExecutorService pool;
        /**
         * Task count = 8 * available_processors.
         */
        private static final int TASK_COUNT = Runtime.getRuntime().availableProcessors() * 8;

        /**
         * Constructor.
         */
        FractalProducer() {
            this.pool = Executors.newFixedThreadPool(TASK_COUNT, r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            });
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                int width, int height, long requestNo, IFractalResultObserver observer) {

            short[] data = new short[width * height];
            List<Future<Void>> results = new ArrayList<>();

            int yCount = (int) Math.ceil(1.0 * height / TASK_COUNT);
            for (int i = 0; i < TASK_COUNT; i++) {
                int yMin = i * yCount;
                int yMax = (i + 1) * yCount;
                if (i == TASK_COUNT - 1) {
                    yMax = height - 1;
                }

                Task task = new Task(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data);
                results.add(pool.submit(task));
            }

            for (Future<Void> task : results) {
                try {
                    task.get();
                } catch (InterruptedException | ExecutionException ignored) {
                }
            }

            observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);
        }
    }

    /**
     * Fills the data array with calculated values.
     */
    public static void calculate(double reMin, double reMax, double imMin, double imMax, int width, int height, int
            yMin, int yMax, short[] data) {
        int offset = yMin * width;

        for (int y = yMin; y <= yMax; y++) {
            for (int x = 0; x < width; x++) {
                double cre = (double) x / ((double) width - 1.0D) * (reMax - reMin) + reMin;
                double cim = (double) (height - 1 - y) / ((double) height - 1.0D) * (imMax - imMin) + imMin;
                Complex zn = new Complex(cre, cim);
                ComplexPolynomial derived = polynomial.derive();
                int iterations = 0;
                double modulus;

                do {
                    Complex numerator = polynomial.apply(zn);
                    Complex denominator = derived.apply(zn);
                    Complex fraction = numerator.divide(denominator);
                    Complex zn1 = zn.sub(fraction);
                    modulus = zn1.sub(zn).module();
                    zn = zn1;
                    iterations++;
                } while (iterations < MAX_ITERATIONS && modulus > CONV_THRESHOLD);

                int index = rootedPolynomial.indexOfClosestRootFor(zn, ROOT_THRESHOLD);

                data[offset++] = (short) (index + 1);
            }
        }
    }
}
