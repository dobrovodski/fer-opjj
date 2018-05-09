package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class represents a complex polynomial of the form zn*z^n + . . . + z1 * z + z0. The coefficients are provided
 * via the constructor.
 *
 * @author matej
 */
public class ComplexPolynomial {
    /**
     * Factors of the polynomial.
     */
    private Complex[] factors;


    /**
     * Constructor for the polynomial.
     *
     * @param factors factors of the polynomial.
     */
    public ComplexPolynomial(Complex... factors) {
        Objects.requireNonNull(factors, "Factors cannot be null.");
        this.factors = factors;
    }

    /**
     * Returns the order of the polynomial.
     *
     * @return order of the polynomial
     */
    public short order() {
        return (short) (factors.length - 1);
    }

    /**
     * Multiplies this complex polynomial with the given one and returns the result.
     *
     * @param p other complex polynomial
     *
     * @return result of multiplying this with the given polynomial
     */
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        Objects.requireNonNull(p, "Cannot multiply with null.");
        Complex[] newFactors = new Complex[order() + p.order() + 1];

        for (int i = 0; i < factors.length; i++) {
            for (int j = 0; j < p.factors.length; j++) {
                int index = i + j;
                Complex mul = factors[i].multiply(p.factors[j]);
                newFactors[index] = newFactors[index] == null ? mul : newFactors[index].add(mul);
            }
        }

        return new ComplexPolynomial(newFactors);
    }

    /**
     * Differentiates the polynomial with respect to z and returns the result
     *
     * @return the derivative of the polynomial
     */
    public ComplexPolynomial derive() {
        return differentiate();
    }

    /**
     * Differentiates the polynomial with respect to z and returns the result
     *
     * @return the derivative of the polynomial
     */
    private ComplexPolynomial differentiate() {
        Complex[] newFactors = new Complex[this.order()];

        for (int i = 0; i < factors.length - 1; i++) {
            // zx * z^n ->  n * zx * z^(n-1)
            // factors[i] = zx -> newFactors[i] = n * zx
            // i can stay the same, what matters is that the order is preserved
            newFactors[i] = factors[i].multiply(new Complex(order() - i, 0));
        }

        return new ComplexPolynomial(newFactors);
    }

    /**
     * Returns the result of applying the polynomial to the given number z.
     *
     * @param z complex number z
     *
     * @return the result of calculating the polynomial with the given number z
     */
    public Complex apply(Complex z) {
        Objects.requireNonNull(z, "Cannot apply polynomial to null.");
        Complex sum = new Complex(0, 0);

        for (int i = 0; i < factors.length; i++) {
            // i = 0 -> highest power
            // pow = order - i;
            // (za, zb, zc) -> za * z^2 + zb * z + zc
            Complex applied = z.power(order() - i).multiply(factors[i]);
            sum = sum.add(applied);
        }

        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < factors.length; i++) {
            Complex c = factors[i];
            String exp = i < factors.length - 1 ? "*z^" + (factors.length - 1 - i) : "";
            sb.append(String.format("(%s)%s + ", c.toString(), exp));
        }

        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}
