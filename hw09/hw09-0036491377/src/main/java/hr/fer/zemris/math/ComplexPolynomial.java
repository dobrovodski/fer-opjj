package hr.fer.zemris.math;

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

    public ComplexPolynomial derive() {
        return differentiate();
    }

    private ComplexPolynomial differentiate() {
        Complex[] newFactors = new Complex[this.order()];

        for (int i = 0; i < factors.length - 1; i++) {
            // zx * z^n ->  n * zx * z^(n-1)
            // factors[i] = zx -> newFactors[i] = n * zx
            // i can stay the same, what matters is that the order is preserved
            // factors.length - 1 because the last one must be a constant (there cannot be gaps between factors)
            newFactors[i] = factors[i].multiply(new Complex(order() - i, 0));
        }

        return new ComplexPolynomial(newFactors);
    }

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
