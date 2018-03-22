package hr.fer.zemris.java.custom.collections;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.atan2;
import static java.lang.Math.pow;
import static java.lang.Math.PI;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * JUnit tests for ComplexNumber.
 * 
 * @see <a href=
 *      "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html">
 *      Naming standards for unit tests </a>
 * @author matej
 *
 */
public class ComplexNumberTest {

	private static final double EPS = 1E-8;

	@Test
	public void FromReal_ValueProvided_ZeroImaginaryPart() {
		double real = 7.3;
		ComplexNumber c = ComplexNumber.fromReal(real);

		Assert.assertEquals(0, c.getImaginary(), EPS);
	}

	@Test
	public void FromReal_ValueProvided_GetRealReturnsValue() {
		double real = 7.3;
		ComplexNumber c = ComplexNumber.fromReal(real);

		Assert.assertEquals(real, c.getReal(), EPS);
	}

	@Test
	public void FromImaginary_ValueProvided_ZeroImaginaryPart() {
		double imaginary = -5.377;
		ComplexNumber c = ComplexNumber.fromImaginary(imaginary);

		Assert.assertEquals(0, c.getReal(), EPS);
	}

	@Test
	public void FromImaginary_ValueProvided_GetImaginaryReturnsValue() {
		double imaginary = -5.377;
		ComplexNumber c = ComplexNumber.fromImaginary(imaginary);

		Assert.assertEquals(imaginary, c.getImaginary(), EPS);
	}

	@Test
	public void FromMagnitudeAndAngle_SimpleValues_CorrectlyConverted() {
		double magnitude = 5;
		double angle = PI / 4;
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);

		Assert.assertEquals(cos(angle) * magnitude, c.getReal(), EPS);
		Assert.assertEquals(sin(angle) * magnitude, c.getImaginary(), EPS);
	}

	@Test
	public void GetMagnitude_SimpleValues_Calculated() {
		double real = 3.6;
		double imaginary = -8.3;
		double magnitude = sqrt(real * real + imaginary * imaginary);
		ComplexNumber c = new ComplexNumber(real, imaginary);

		Assert.assertEquals(magnitude, c.getMagnitude(), EPS);
	}

	@Test
	public void GetAngle_BothPartsPositive_Calculated() {
		double real = 3.6;
		double imaginary = 5;
		double angle = atan2(imaginary, real);
		ComplexNumber c = new ComplexNumber(real, imaginary);

		Assert.assertEquals(angle, c.getAngle(), EPS);

	}

	@Test
	public void GetAngle_RealPartNegative_Calculated() {
		double real = -3.6;
		double imaginary = 5;
		double angle = atan2(imaginary, real);
		ComplexNumber c = new ComplexNumber(real, imaginary);

		Assert.assertEquals(angle, c.getAngle(), EPS);
	}

	@Test(expected = ArithmeticException.class)
	public void GetAngle_BothPartsZero_ExceptionThrown() {
		double real = 0;
		double imaginary = 0;
		ComplexNumber c = new ComplexNumber(real, imaginary);

		c.getAngle();
	}

	@Test
	public void Add_SimpleValues_Calculated() {
		double real1 = -3.6;
		double imaginary1 = 5;
		ComplexNumber c1 = new ComplexNumber(real1, imaginary1);

		double real2 = 2.17;
		double imaginary2 = 9;
		ComplexNumber c2 = new ComplexNumber(real2, imaginary2);

		ComplexNumber c = c1.add(c2);

		Assert.assertEquals(new ComplexNumber(real1 + real2, imaginary1 + imaginary2), c);
	}

	@Test
	public void Sub_SimpleValues_Calculated() {
		double real1 = -3.6;
		double imaginary1 = 5;
		ComplexNumber c1 = new ComplexNumber(real1, imaginary1);

		double real2 = 2.17;
		double imaginary2 = 9;
		ComplexNumber c2 = new ComplexNumber(real2, imaginary2);

		ComplexNumber c = c1.sub(c2);

		Assert.assertEquals(new ComplexNumber(real1 - real2, imaginary1 - imaginary2), c);
	}

	@Test
	public void Mul_SimpleValues_Calculated() {
		double real1 = -3.6;
		double imaginary1 = 5;
		ComplexNumber c1 = new ComplexNumber(real1, imaginary1);

		double real2 = 2.17;
		double imaginary2 = 9;
		ComplexNumber c2 = new ComplexNumber(real2, imaginary2);

		double mulReal = real1 * real2 - imaginary1 * imaginary2;
		double mulImaginary = real1 * imaginary2 + imaginary1 * real2;

		Assert.assertEquals(new ComplexNumber(mulReal, mulImaginary), c1.mul(c2));
	}

	@Test
	public void Div_SimpleValues_Calculated() {
		double real1 = -3.6;
		double imaginary1 = 5;
		ComplexNumber c1 = new ComplexNumber(real1, imaginary1);

		double real2 = 2.17;
		double imaginary2 = 9;
		ComplexNumber c2 = new ComplexNumber(real2, imaginary2);

		double denominator = real2 * real2 + imaginary2 * imaginary2;
		double divReal = (real1 * real2 + imaginary1 * imaginary2) / denominator;
		double divImaginary = (imaginary1 * real2 - real1 * imaginary2) / denominator;

		Assert.assertEquals(new ComplexNumber(divReal, divImaginary), c1.div(c2));
	}

	@Test
	public void Power_SimpleValues_Calculated() {
		double real1 = 3;
		double imaginary1 = 3;
		int pow = 5;
		ComplexNumber c1 = new ComplexNumber(real1, imaginary1);

		double magnitude = c1.getMagnitude();
		double angle = c1.getAngle();

		double powerReal = pow(magnitude, pow) * cos(pow * angle);
		double powerImaginary = pow(magnitude, pow) * sin(pow * angle);

		Assert.assertEquals(new ComplexNumber(powerReal, powerImaginary), c1.power(pow));
	}

	@Test
	public void Power_PowerZero_One() {
		double real1 = 3;
		double imaginary1 = 3;
		int pow = 0;
		ComplexNumber c1 = new ComplexNumber(real1, imaginary1);

		Assert.assertEquals(new ComplexNumber(1, 0), c1.power(pow));
	}

	@Test(expected = IllegalArgumentException.class)
	public void Power_PowerNegative_ExceptionThrown() {
		double real1 = 3;
		double imaginary1 = 3;
		int pow = -3;
		ComplexNumber c1 = new ComplexNumber(real1, imaginary1);

		c1.power(pow);
	}

	@Test
	public void Root_SimpleValues_Calculated() {
		double real1 = 385.2;
		double imaginary1 = -1.1;
		int root = 5;
		ComplexNumber c1 = new ComplexNumber(real1, imaginary1);

		// Check if there are n solutions first
		Assert.assertEquals(root, c1.root(root).length);

		ComplexNumber[] roots = new ComplexNumber[root];
		for (int k = 0; k < root; k++) {
			double r = pow(c1.getMagnitude(), 1.0 / root);
			double angle = c1.getAngle();
			double real = r * cos(angle / root + 2 * k * PI / root);
			double imaginary = r * sin(angle / root + 2 * k * PI / root);

			roots[k] = new ComplexNumber(real, imaginary);
		}

		ComplexNumber[] calculatedRoots = c1.root(root);

		for (int k = 0; k < root; k++) {
			Assert.assertEquals(roots[k], calculatedRoots[k]);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void Root_Zero_ExceptionThrown() {
		double real1 = 385.2;
		double imaginary1 = -1.1;
		int root = 0;
		ComplexNumber c1 = new ComplexNumber(real1, imaginary1);

		c1.root(root);
	}

	@Test(expected = IllegalArgumentException.class)
	public void Root_Negative_ExceptionThrown() {
		double real1 = 385.2;
		double imaginary1 = -1.1;
		int root = -3;
		ComplexNumber c1 = new ComplexNumber(real1, imaginary1);

		c1.root(root);
	}

	@Test
	public void Parse_OnlyRealPartPositive_CorrectlyParsed() {
		double real = 3.51;
		double imaginary = 0;
		String numString = String.format("%f", real);
		ComplexNumber parsed = ComplexNumber.parse(numString);

		Assert.assertEquals(new ComplexNumber(real, imaginary), parsed);
	}

	@Test
	public void Parse_OnlyRealPartNegative_CorrectlyParsed() {
		double real = -3.17;
		double imaginary = 0;
		String numString = String.format("%f", real);
		ComplexNumber parsed = ComplexNumber.parse(numString);

		Assert.assertEquals(new ComplexNumber(real, imaginary), parsed);
	}

	@Test
	public void Parse_OnlyImaginaryPartPositive_CorrectlyParsed() {
		double real = 0;
		double imaginary = 4;
		String numString = String.format("%fi", imaginary);
		ComplexNumber parsed = ComplexNumber.parse(numString);

		Assert.assertEquals(new ComplexNumber(real, imaginary), parsed);
	}

	@Test
	public void Parse_OnlyImaginaryPartNegative_CorrectlyParsed() {
		double real = 0;
		double imaginary = -2.71;
		String numString = String.format("%fi", imaginary);
		ComplexNumber parsed = ComplexNumber.parse(numString);

		Assert.assertEquals(new ComplexNumber(real, imaginary), parsed);
	}

	@Test
	public void Parse_OnlyImaginaryPartNoCoefficientPositive_CorrectlyParsed() {
		String numString = "i";
		ComplexNumber parsed = ComplexNumber.parse(numString);

		Assert.assertEquals(new ComplexNumber(0, 1), parsed);
	}

	@Test
	public void Parse_OnlyImaginaryPartNoCoefficientNegative_CorrectlyParsed() {
		String numString = "-i";
		ComplexNumber parsed = ComplexNumber.parse(numString);

		Assert.assertEquals(new ComplexNumber(0, -1), parsed);
	}

	@Test
	public void Parse_BothPartsPositive_CorrectlyParsed() {
		double real = 3;
		double imaginary = 3;
		String numString = String.format("%f+%fi", real, imaginary);
		ComplexNumber parsed = ComplexNumber.parse(numString);

		Assert.assertEquals(new ComplexNumber(real, imaginary), parsed);
	}

	@Test
	public void Parse_BothPartsNegative_CorrectlyParsed() {
		double real = -2.71;
		double imaginary = -3.15;
		String numString = String.format("%f%fi", real, imaginary);
		ComplexNumber parsed = ComplexNumber.parse(numString);

		Assert.assertEquals(new ComplexNumber(real, imaginary), parsed);
	}

	@Test
	public void Parse_RealNegativeImaginaryPositive_CorrectlyParsed() {
		double real = -333;
		double imaginary = 3;
		String numString = String.format("%f+%fi", real, imaginary);
		ComplexNumber parsed = ComplexNumber.parse(numString);

		Assert.assertEquals(new ComplexNumber(real, imaginary), parsed);
	}

	@Test
	public void Parse_RealPositiveImaginaryNegative_CorrectlyParsed() {
		double real = 333;
		double imaginary = -3;
		String numString = String.format("%f%fi", real, imaginary);
		ComplexNumber parsed = ComplexNumber.parse(numString);

		Assert.assertEquals(new ComplexNumber(real, imaginary), parsed);
	}

	@Test
	public void Parse_IllegalString_ExceptionThrown() {
		String[] definitelyNotComplexNumbers = { "hello3+3xi", "zzzi", "3a+3ia", "2.2a", "3i3", "5ii", "3+ii", "", null, "^i" };
		int count = 0;

		for (String garbage : definitelyNotComplexNumbers) {
			try {
				ComplexNumber.parse(garbage);
			} catch (Exception ex) {
				// Catch all exceptions, only need to know how many there were
				count++;
			}
		}

		if (count != definitelyNotComplexNumbers.length) {
			Assert.fail();
		}
	}
}
