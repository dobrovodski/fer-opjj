package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class represents a complex polynomial of the form (z-z1)*(z-z2)*...*(z-zn). The roots are provided via the
 * constructor.
 *
 * @author matej
 */
public class ComplexRootedPolynomial {
    /**
     * Roots of the polynomial.
     */
    private Complex[] roots;

    /**
     * Constructor for the polynomial.
     *
     * @param roots roots of the polynomial
     */
    public ComplexRootedPolynomial(Complex... roots) {
        Objects.requireNonNull(roots, "Roots cannot be null.");
        if (roots.length == 0) {
            throw new IllegalArgumentException("Polynomial needs to have at least one root");
        }

        this.roots = roots;
    }

    /**
     * Returns the result of applying the polynomial to the given number z.
     *
     * @param z complex number z
     *
     * @return the result of calculating the polynomial with the given number z
     */
    public Complex apply(Complex z) {
        /*Complex result = Complex.ONE;
        for (Complex root : roots) {
            result = result.multiply(z.sub(root));
        }*/
        return this.toComplexPolynomial().apply(z);
    }

    /**
     * Converts this {@link ComplexRootedPolynomial} to a {@link ComplexPolynomial}.
     *
     * @return this converted to a ComplexPolynomial
     */
    public ComplexPolynomial toComplexPolynom() {
        return toComplexPolynomial();
    }

    /**
     * Converts this {@link ComplexRootedPolynomial} to a {@link ComplexPolynomial}.
     *
     * @return this converted to a ComplexPolynomial
     */
    private ComplexPolynomial toComplexPolynomial() {
        ComplexPolynomial cp = new ComplexPolynomial(Complex.ONE);
        for (Complex root : roots) {
            ComplexPolynomial rootComplexPolynomial = new ComplexPolynomial(Complex.ONE, root.negate());
            cp = cp.multiply(rootComplexPolynomial);
        }

        return cp;
    }

    /**
     * Finds the index of the closest root for the given complex number such that it is under the given threshold. If
     * there is no such index, returns -1.
     *
     * @param z given complex number
     * @param threshold threshold
     *
     * @return index of closest root for given complex number
     */
    public int indexOfClosestRootFor(Complex z, double threshold) {
        int closest = -1;
        double smallestDistance = threshold;

        for (int i = 0; i < roots.length; i++) {
            Complex c = roots[i];
            double dist = c.sub(z).module();
            if (dist < smallestDistance) {
                closest = i;
                smallestDistance = dist;
            }
        }

        return closest;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Complex c : roots) {
            sb.append(String.format("(z - (%s)) * ", c.toString()));
        }

        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
