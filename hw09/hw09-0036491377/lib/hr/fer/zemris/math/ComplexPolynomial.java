package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Objects;

public class ComplexPolynomial {
    private Complex[] factors;


    public ComplexPolynomial(Complex... factors) {
        Objects.requireNonNull(factors, "Factors cannot be null.");
        this.factors = factors;
    }

    public short order() {
        return (short) (factors.length - 1);
    }

    public ComplexPolynomial multiply(ComplexPolynomial p) {
        Objects.requireNonNull(p, "Cannot multiply with null.");
        Complex[] newFactors = new Complex[factors.length];

        for (int i = 0; i < factors.length; i++) {
            for (int j = 0; j < p.factors.length; j++) {
                int index = i * j;
                Complex mul = factors[i].multiply(p.factors[j]);
                newFactors[index] = newFactors[index] == null ? mul : newFactors[index].add(mul);
            }
        }
        return new ComplexPolynomial(newFactors);
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
        Objects.requireNonNull(z, "Cannot apply polynomial to null.");
        Complex sum = new Complex(0, 0);
        for (Complex factor : factors) {
            sum = sum.add(z.power(0).multiply(factor));
        }

        return sum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < factors.length; i++) {
            Complex c = factors[i];
            String exp = i > 0 ? "z^" + i : "";
            sb.append(String.format("(%s)%s", c.toString(), exp));
        }

        return sb.toString();
    }
}
