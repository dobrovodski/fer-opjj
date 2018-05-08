package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.mandelbrot.Mandelbrot;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {
    public static void main(String[] args) {
        FractalViewer.show(new MojProducer());
    }

    public static class MojProducer implements IFractalProducer {
        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                int width, int height, long requestNo, IFractalResultObserver observer) {

            System.out.println("Zapocinjem izracun...");

            int m = 16 * 16 * 16;
            short[] data = new short[width * height];

            Newton.calculate(reMin, reMax, imMin, imMax, width, height, 4, 0, height - 1, data);

            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            observer.acceptResult(data, (short) 5, requestNo);
        }
    }

    public static void calculate(double reMin, double reMax, double imMin, double imMax, int width, int height, int
            m, int ymin, int ymax, short[] data) {
        int offset = ymin * width;

        for (int y = ymin; y <= ymax; ++y) {
            for (int x = 0; x < width; ++x) {
                double cre = (double) x / ((double) width - 1.0D) * (reMax - reMin) + reMin;
                double cim = (double) (height - 1 - y) / ((double) height - 1.0D) * (imMax - imMin) + imMin;
                Complex zn = new Complex(cre, cim);
                ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(
                        new Complex(1, 0),
                        new Complex(-1, 0),
                        new Complex(0, 1),
                        new Complex(0, -1)
                );
                ComplexPolynomial derived = polynomial.toComplexPolynom().derive();
                int iters = 0;
                double modulus;

                do {
                    Complex numerator = polynomial.apply(zn);
                    Complex denominator = derived.apply(zn);
                    Complex fraction = numerator.divide(denominator);
                    Complex zn1 = zn.sub(fraction);
                    modulus = zn1.sub(zn).module();
                    zn = zn1;
                    ++iters;
                } while (iters < 16 && modulus > 1e-3);

                int index = polynomial.indexOfClosestRootFor(zn, 1e-3);
                data[offset] = (short) (index + 1);
                ++offset;
            }
        }
    }
}
