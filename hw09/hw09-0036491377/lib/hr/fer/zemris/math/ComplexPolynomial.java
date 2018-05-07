package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComplexPolynomial {
    private Complex[] factors;


    public ComplexPolynomial(Complex... factors) {
        this.factors = factors;
    }

    public short order() {
        return (short) (factors.length - 1);
    }

    public ComplexPolynomial multiply(ComplexPolynomial p) {
        List<Complex> newFactors = new ArrayList<>();

        for (Complex factor : factors) {
            for (Complex other : p.factors) {
                newFactors.add(factor.multiply(other));
            }
        }

        return new ComplexPolynomial(newFactors.toArray(new Complex[0]));
    }

    public ComplexPolynomial derive() {
        return differentiate();
    }

    private ComplexPolynomial differentiate() {
        Complex[] newFactors = new Complex[this.order()];

        for (int i = 1; i < factors.length; i++) {
            newFactors[i - 1] = factors[i].multiply(new Complex(i, 0));
        }

        return new ComplexPolynomial(newFactors);
    }

    public Complex apply(Complex z) {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
