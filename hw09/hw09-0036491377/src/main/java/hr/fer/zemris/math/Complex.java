package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        return String.format("%f %c i%f", re, sign, Math.abs(im));
    }

    public static Complex fromString(String str) {
        if (str == null) {
            throw new NullPointerException("String cannot be null.");
        }

        if (str.isEmpty()) {
            throw new IllegalArgumentException("Cannot parse empty string as complex number");
        }

        // Good luck
        String regex = "^(?<imaginaryNoCoef>(-)?i)?$|" // Matches complex numbers "i" or "-i"
                       + "^(?<onlyImaginary>(-)?i([0-9]++(\\.[0-9]+)?)?)?$|" // Only imaginary, i.e. "i3" or "-i2.2"
                       + "^(?<real>([+\\-])?[0-9]+(\\.[0-9]+)?)?(\\+)?" // Matches the real part of the number
                       + "(?<imaginary>(([+\\-])?i([0-9]*(\\.[0-9]+)?)?))?$"; // Matches the imaginary part of number

        Pattern p = Pattern.compile(regex);
        // We remove all whitespace here for the sake of simplicity
        // That means that stuff like 1 . 3 + i 1 0 will work
        str = str.replaceAll("\\s+", "");
        Matcher m = p.matcher(str);

        double real = 0;
        double imaginary = 0;

        if (!m.find()) {
            throw new IllegalArgumentException("Could not parse string as complex number. Input: " + str);
        }

        String realGroup = m.group("real");
        String imaginaryGroup = m.group("imaginary");
        String noCoefImaginaryGroup = m.group("imaginaryNoCoef");
        String onlyImaginaryGroup = m.group("onlyImaginary");

        if (realGroup != null) {
            real = Double.parseDouble(realGroup);
        }

        if (imaginaryGroup != null) {
            imaginaryGroup = imaginaryGroup.replace("i", "").trim();
            if (imaginaryGroup.equals("") || imaginaryGroup.equals("-")) {
                imaginaryGroup += "1";
            }

            imaginary = Double.parseDouble(imaginaryGroup);
        }

        if (noCoefImaginaryGroup != null) {
            noCoefImaginaryGroup = noCoefImaginaryGroup.replace("i", "");

            if (noCoefImaginaryGroup.startsWith("-")) {
                imaginary = -1.0;
            } else {
                imaginary = 1.0;
            }
        }

        if (onlyImaginaryGroup != null) {
            onlyImaginaryGroup = onlyImaginaryGroup.replace("i", "").trim();
            imaginary = Double.parseDouble(onlyImaginaryGroup);
        }

        return new Complex(real, imaginary);
    }
}
