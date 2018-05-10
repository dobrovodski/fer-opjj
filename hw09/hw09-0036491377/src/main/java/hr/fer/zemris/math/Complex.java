package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used for representing complex numbers. It provides useful methods for unary and binary operations
 * performed on complex numbers.
 *
 * @author matej
 */
public class Complex {
    /**
     * Real part of the number.
     */
    private double re;
    /**
     * Imaginary part of the number.
     */
    private double im;

    /**
     * Represents the complex number 0 + 0i.
     */
    public static final Complex ZERO = new Complex(0, 0);
    /**
     * Represents the complex number 1 + 0i.
     */
    public static final Complex ONE = new Complex(1, 0);
    /**
     * Represents the complex number -1 + 0i.
     */
    public static final Complex ONE_NEG = new Complex(-1, 0);
    /**
     * Represents the complex number 0 + 1i.
     */
    public static final Complex IM = new Complex(0, 1);
    /**
     * Represents the complex number 0 - 1i.
     */
    public static final Complex IM_NEG = new Complex(0, -1);

    /**
     * Constructor for Complex.
     *
     * @param re real part of the number
     * @param im imaginary part of the number
     */
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Default constructor. Sets both real and imaginary part to 0.
     */
    public Complex() {
        this.re = 0;
        this.im = 0;
    }

    /**
     * Calculates the modulus of this number.
     *
     * @return modulus of this number
     */
    public double module() {
        return modulus();
    }

    /**
     * Calculates the modulus of this number.
     *
     * @return modulus of this number
     */
    private double modulus() {
        return Math.sqrt(re * re + im * im);
    }

    /**
     * Multiplies this complex number with the given one and returns a new one.
     *
     * @param other complex number to multiply with
     *
     * @return this and other multiplied together
     */
    public Complex multiply(Complex other) {
        Objects.requireNonNull(other, "Other vector cannot be null.");
        return new Complex(re * other.re - im * other.im, re * other.im + im * other.re);
    }

    /**
     * Divides this complex number with the given one and returns a new one.
     *
     * @param other complex number to divide with
     *
     * @return this divided by other
     */
    public Complex divide(Complex other) {
        Objects.requireNonNull(other, "Other vector cannot be null.");
        double divisor = other.re * other.re + other.im * other.im;
        return new Complex((re * other.re + im * other.im) / divisor, (im * other.re - re * other.im) / divisor);
    }

    /**
     * Adds this complex number with the given one and returns a new one.
     *
     * @param other complex number to add with
     *
     * @return this and other added together
     */
    public Complex add(Complex other) {
        Objects.requireNonNull(other, "Other vector cannot be null.");
        return new Complex(re + other.re, im + other.im);
    }

    /**
     * Subtracts given complex number from this and returns a new one.
     *
     * @param other complex number to subtract from this
     *
     * @return this subtracted by other
     */
    public Complex sub(Complex other) {
        Objects.requireNonNull(other, "Other vector cannot be null.");
        return new Complex(re - other.re, im - other.im);
    }

    /**
     * Returns the the negated form of this complex number.
     *
     * @return this number negated
     */
    public Complex negate() {
        return new Complex(-re, -im);
    }

    /**
     * Returns this complex number to the n-th power.
     *
     * @param n power to raise this number by
     *
     * @return this number raised to the n-th power
     */
    public Complex power(int n) {
        double arg = arg();
        double r = Math.pow(module(), n);

        return new Complex(r * Math.cos(n * arg), r * Math.sin(n * arg));
    }

    /**
     * Takes the n-th root from the complex number and returns all possible roots in a list.
     *
     * @param n root to take
     *
     * @return list of roots
     *
     * @throws IllegalArgumentException if root is less than 1
     */
    public List<Complex> root(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Root cannot be less than 1.");
        }
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

    /**
     * Returns the argument of the complex number.
     *
     * @return argument of complex number
     */
    private double arg() {
        return Math.atan2(im, re);
    }

    @Override
    public String toString() {
        char sign = im < 0 ? '-' : '+';
        return String.format("%f %c i%f", re, sign, Math.abs(im));
    }

    /**
     * Multiplies this complex number with the given one and returns a new one with the multiplied values.
     *
     * @param str complex number to multiply with
     *
     * @return this and other multiplied together
     */
    public static Complex fromString(String str) {
        Objects.requireNonNull(str, "String cannot be null.");

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

    @Override
    public boolean equals(Object o) {
        final double EPS = 1E-6;
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Complex complex = (Complex) o;
        return Math.abs(complex.re - re) < EPS &&
               Math.abs(complex.im - im) < EPS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }
}
