package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComplexRootedPolynomial {
    List<Complex> roots;

    public ComplexRootedPolynomial(Complex ...roots) {
        Objects.requireNonNull(roots, "Roots cannot be null.");
        if (roots.length == 0) {
            throw new IllegalArgumentException("Polynomial needs to have at least one root");
        }

        this.roots = new ArrayList<>();
        for (Complex root : roots) {
            this.roots.add(root);
        }
    }

    public Complex apply(Complex z) {
        Complex result = new Complex(1, 0);
        for (Complex root : roots) {
            result = result.multiply(z.sub(root));
        }

        return result;
    }

    public ComplexPolynomial toComplexPolynom() {
        return toComplexPolynomial();
    }

    private ComplexPolynomial toComplexPolynomial() {
        return null;
    }

    public int indexOfClosestRootFor(Complex z, double threshold) {
        int closest = 0;
        double smallestDistance = 0;

        for (int i = 0; i < roots.size(); i++) {
            Complex c = roots.get(i);
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
            sb.append(String.format("(z - com(%s)) * ", c.toString()));
        }

        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
