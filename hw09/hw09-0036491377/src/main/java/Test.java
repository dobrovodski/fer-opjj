import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.Vector3;

public class Test {
    public static void main(String[] args) {
        Vector3 i = new Vector3(1,0,0);
        Vector3 j = new Vector3(0,1,0);
        Vector3 k = i.cross(j);
        Vector3 l = k.add(j).scale(5);
        Vector3 m = l.normalized();
        System.out.println(i);
        System.out.println(j);
        System.out.println(k);
        System.out.println(l);
        System.out.println(l.norm());
        System.out.println(m);
        System.out.println(l.dot(j));
        System.out.println(i.add(new Vector3(0,1,0)).cosAngle(l));

        Complex c1 = new Complex(0, 1);
        c1.root(3);
        Complex c2 = new Complex(69.69, -69.69);
        c2.root(12);

        ComplexPolynomial cp = new ComplexPolynomial(
                new Complex(1, 0),
                new Complex(5, 0),
                new Complex(2, 0),
                new Complex(7, 2)
        );
        cp.derive();
    }
}
