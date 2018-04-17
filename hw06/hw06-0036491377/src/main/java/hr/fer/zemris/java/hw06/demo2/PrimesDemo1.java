package hr.fer.zemris.java.hw06.demo2;

/**
 * Demo class for {@link PrimesCollection}. Shows basic functionality.
 * @author matej
 */
public class PrimesDemo1 {
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(30); // 5: how many of them
        for(Integer prime : primesCollection) {
            System.out.println("Got prime: "+prime);
        }
    }
}
