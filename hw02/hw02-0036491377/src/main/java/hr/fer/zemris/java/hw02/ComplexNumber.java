package hr.fer.zemris.java.hw02;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.atan2;
import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.PI;

/**
 * The {@code ComplexNumber} class represents complex numbers and provides a way
 * to do mathematical operations with them.
 * 
 * @author matej
 *
 */
public class ComplexNumber {

	private double real;
	private double imaginary;

	private static final double EPS = 1E-8;

	/**
	 * Constructor which creates a {@code ComplexNumber} object from a real and
	 * imaginary part.
	 * 
	 * @param real
	 *            real part of complex number
	 * @param imaginary
	 *            imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Creates an instance of {@code ComplexNumber} from a real part only.
	 * 
	 * @param real
	 *            real part of complex number
	 * @return instance of {@code ComplexNumber}
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Creates an instance of {@code ComplexNumber} from an imaginary part only.
	 * 
	 * @param imaginary
	 *            imaginary part of complex number
	 * @return instance of {@code ComplexNumber}
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates an instance of {@code ComplexNumber} from the magnitude and angle of
	 * number.
	 * 
	 * @param magnitude
	 *            magnitude of complex number
	 * @param angle
	 *            angle of complex number
	 * @return instance of {@code ComplexNumber}
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = cos(angle) * magnitude;
		double imaginary = cos(angle) * magnitude;

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Creates an instance of {@code ComplexNumber} from a string representation of
	 * a complex number.
	 * 
	 * @param s
	 *            string to convert
	 * @return instance of {@code ComplexNumber}
	 * @throws IllegalArgumentException
	 *             if the string cannot be parsed as a complex number
	 */
	public static ComplexNumber parse(String s) {
		String regex = "^(?<imaginaryNoCoef>(-)?i)?$|" // Matches complex numbers "i" or "-i"
				+ "^((?<onlyImaginary>(-)?([0-9]++(\\.[0-9]+)?)?)i)?$|" // Only imaginary, i.e. "3i" or "-2.2i"
				+ "^(?<real>(\\+|-)?[0-9]+(\\.[0-9]+)?)?(\\+)?" // Matches the real part of the number
				+ "((?<imaginary>((\\+|-)?([0-9]++(\\.[0-9]+)?)?))i)?$"; // Matches the imaginary part of number

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);

		double real = 0;
		double imaginary = 0;

		if (!m.find()) {
			throw new IllegalArgumentException("Could not parse string as complex number. Input: " + s);
		}

		String realGroup = m.group("real");
		String imaginaryGroup = m.group("imaginary");
		String noCoefImaginaryGroup = m.group("imaginaryNoCoef");
		String onlyImaginaryGroup = m.group("onlyImaginary");

		if (realGroup != null) {
			real = Double.parseDouble(realGroup);
		}

		if (imaginaryGroup != null) {
			imaginary = Double.parseDouble(imaginaryGroup);
		}

		if (noCoefImaginaryGroup != null) {
			if (noCoefImaginaryGroup.startsWith("-")) {
				imaginary = -1.0;
			} else {
				imaginary = 1.0;
			}
		}

		if (onlyImaginaryGroup != null) {
			imaginary = Double.parseDouble(onlyImaginaryGroup);
		}

		return new ComplexNumber(real, imaginary);

	}

	/**
	 * Returns the real part of the {@code ComplexNumber}.
	 * 
	 * @return real part of {@code ComplexNumber}
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns the imaginary part of the {@code ComplexNumber}.
	 * 
	 * @return imaginary part of {@code ComplexNumber}
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns the magnitude of the {@code ComplexNumber}.
	 * 
	 * @return magnitude of {@code ComplexNumber}
	 */
	public double getMagnitude() {
		return sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Returns the angle of the {@code ComplexNumber}.
	 * 
	 * @return angle of {@code ComplexNumber}
	 * @throws ArithmeticException
	 *             if both parts of the complex number are zero, the angle is
	 *             indeterminate
	 * @see <a href=https://en.wikipedia.org/wiki/Complex_number#Polar_form.>Polar
	 *      form - Absolute value and argument</a>
	 */
	public double getAngle() {
		if (real == 0 && imaginary == 0) {
			throw new ArithmeticException("Real and imaginary part are both 0 so the angle is indeterminate");
		}

		double angle = atan2(imaginary, real);

		if (angle < 0) {
			angle += 2 * PI;
		}

		return angle;
	}

	/**
	 * Adds two complex numbers together and returns the result as a new
	 * {@code ComplexNumber} object.
	 * 
	 * @param c
	 *            complex number to add
	 * @return a complex number that represents the addition of this number with the
	 *         provided number
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.real, imaginary + c.imaginary);
	}

	/**
	 * Subtracts two complex numbers together and returns the result as a new
	 * {@code ComplexNumber} object.
	 * 
	 * @param c
	 *            complex number to subtract
	 * @return a complex number that represents the subtraction of this number with
	 *         the provided number
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.real, imaginary - c.imaginary);
	}

	/**
	 * Multiplies two complex numbers together and returns the result as a new
	 * {@code ComplexNumber} object.
	 * 
	 * @param c
	 *            complex number to multiply
	 * @return a complex number that represents the multiplication of this number
	 *         with the provided number
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double newReal = real * c.real - imaginary * c.imaginary;
		double newImaginary = real * c.imaginary + imaginary * c.real;

		return new ComplexNumber(newReal, newImaginary);

	}

	/**
	 * Divides two complex numbers together and returns the result as a new
	 * {@code ComplexNumber} object.
	 * 
	 * @param c
	 *            complex number to divide with
	 * @return a complex number that represents the division of this number with the
	 *         provided number
	 */
	public ComplexNumber div(ComplexNumber c) {
		double denominator = c.real * c.real + c.imaginary * c.imaginary;
		double newReal = (real * c.real + imaginary * c.imaginary) / denominator;
		double newImaginary = (imaginary * c.real - real * c.imaginary) / denominator;

		return new ComplexNumber(newReal, newImaginary);
	}

	/**
	 * Raises the complex number to the n-th power and returns the result.
	 * 
	 * @param n
	 *            power to raise to
	 * @return complex number that represents the n-th power of the number
	 * @throws IllegalArgumentException
	 *             if power is negative
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power cannot be negative. Was: " + n);
		}

		double r = this.getMagnitude();
		double angle = this.getAngle();

		return new ComplexNumber(pow(r, n) * cos(n * angle), pow(r, n) * sin(n * angle));
	}

	/**
	 * Takes the n-th root of the complex number and returns an array of n resulting
	 * complex numbers.
	 * 
	 * @param n
	 *            root to take
	 * @return array of n resulting complex numbers
	 * @throws IllegalArgumentException
	 *             if power is negative
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Root can only be positive. Was: " + n);
		}
		ComplexNumber[] solutions = new ComplexNumber[n];
		double r = Math.pow(this.getMagnitude(), 1.0 / n);
		double angle = this.getAngle();

		for (int k = 0; k < n; k++) {
			solutions[k] = new ComplexNumber(r * cos(angle / n + 2 * PI * k / n), r * sin(angle / n + 2 * PI * k / n));
		}

		return solutions;
	}

	@Override
	public String toString() {
		// comparison with zero is okay here because we don't care about precision
		if (imaginary == 0)
			return String.format("%f", real);
		if (imaginary < 0)
			return String.format("%f - %fi", real, -imaginary);
		if (real == 0)
			return String.format("%fi", imaginary);

		return String.format("%f + %fi", real, imaginary);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other instanceof ComplexNumber) {
			ComplexNumber c = (ComplexNumber) other;
			return (abs(real - c.real) < EPS && abs(imaginary - c.imaginary) < EPS);
		}
		return false;
	}

}
