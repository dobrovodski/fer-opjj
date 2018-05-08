package hr.fer.zemris.math;

import java.util.Objects;

public class ComplexRootedPolynomial {
    private Complex[] roots;

    public ComplexRootedPolynomial(Complex... roots) {
        Objects.requireNonNull(roots, "Roots cannot be null.");
        if (roots.length == 0) {
            throw new IllegalArgumentException("Polynomial needs to have at least one root");
        }

        this.roots = roots;
    }

    public Complex apply(Complex z) {
        Complex result = Complex.ONE;
        for (Complex root : roots) {
            result = result.multiply(z.sub(root));
        }

        return result;
    }

    public ComplexPolynomial toComplexPolynom() {
        return toComplexPolynomial();
    }

    private ComplexPolynomial toComplexPolynomial() {
        ComplexPolynomial cp = new ComplexPolynomial(Complex.ONE);
        for (Complex root : roots) {
            cp = cp.multiply(new ComplexPolynomial(Complex.ONE, root.negate()));
        }

        return cp;
    }

    public int indexOfClosestRootFor(Complex z, double threshold) {
        int closest = -1;
        double smallestDistance = -1;

        for (int i = 0; i < roots.length; i++) {
            Complex c = roots[i];
            double dist = c.sub(z).module();
            if (dist < smallestDistance || smallestDistance == -1) {
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
