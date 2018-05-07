package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

public class Complex {
    private double re;
    private double im;

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex ONE_NEG = new Complex(-1, 0);
    public static final Complex IM = new Complex(0, 1);
    public static final Complex IM_NEG = new Complex(0, -1);

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex() {
        this.re = 0;
        this.im = 0;
    }

    public double module() {
        return modulus();
    }

    private double modulus() {
        return Math.sqrt(re * re + im * im);
    }

    public Complex multiply(Complex other) {
        return new Complex(re * other.re - im * other.im, re * other.im + im * other.re);
    }

    public Complex divide(Complex other) {
        double divisor = other.modulus() * other.modulus();
        return new Complex((re * other.re + im * other.im) / divisor, (im * other.re - re * other.im) / divisor);
    }

    public Complex add(Complex other) {
        return new Complex(re + other.re, im + other.im);
    }

    public Complex sub(Complex other) {
        return new Complex(re - other.re, im - other.im);
    }

    public Complex negate() {
        return new Complex(-re, -im);
    }

    public Complex power(int n) {
        double arg = arg();
        double r = Math.pow(module(), n);

        return new Complex(r * Math.cos(n * arg), r * Math.sin(n * arg));
    }

    public List<Complex> root(int n) {
        double arg = arg();
        double r = Math.pow(module(), 1.0 / n);
        List<Complex> roots = new ArrayList<>();

        for (int k = 0; k < n; k++) {
            double re = Math.cos((arg + 2 * k * Math.PI) / n);
            double im = Math.sin((arg + 2 * k * Math.PI) / n);

            Complex c = new Complex(r * re, r * im);
            roots.add(c);
        }

        return roots;
    }

    private double arg() {
        return Math.atan2(im, re);
    }

    @Override
    public String toString() {
        char sign = im < 0 ? '-' : '+';
        return String.format("%f %c %fi", re, sign, Math.abs(im));
    }
}
