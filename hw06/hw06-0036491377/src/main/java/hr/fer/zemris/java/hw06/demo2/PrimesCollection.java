package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class used to generate prime numbers.
 *
 * @author matej
 */
public class PrimesCollection implements Iterable<Integer> {
    /**
     * Maximum number of primes to generate
     */
    private final int numberOfPrimes;

    /**
     * Default constructor.
     *
     * @param numberOfPrimes number of primes the collection is able to generate
     *
     * @throws IllegalArgumentException if numberOfPrimes is negative
     */
    public PrimesCollection(int numberOfPrimes) {
        if (numberOfPrimes < 1) {
            throw new IllegalArgumentException("Number of primes to generate has to be more than 1");
        }
        this.numberOfPrimes = numberOfPrimes;
    }

    /**
     * Returns {@code true} if the number is prime, {@code false} otherwise.
     *
     * @param n number to rest
     *
     * @return {@code true} if the number is prime, {@code false} otherwise
     */
    private boolean isPrime(int n) {
        if (n == 2) {
            return true;
        }

        if (n % 2 == 0 || n <= 0) {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Iterator class used to iterate through primes.
     */
    private class IteratorImpl implements Iterator<Integer> {
        /**
         * Keeps track of primes generated
         */
        private int count;
        /**
         * Keeps track of last prime generated
         */
        private int lastPrime = 2;

        @Override
        public boolean hasNext() {
            return count < numberOfPrimes;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in collection");
            }

            int nextPrime = lastPrime;
            int currentNumber = lastPrime;

            while (true) {
                if (currentNumber == 2) {
                    currentNumber = 3;
                } else {
                    currentNumber += 2;
                }

                if (isPrime(currentNumber)) {
                    count++;
                    lastPrime = currentNumber;
                    break;
                }
            }

            return nextPrime;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new IteratorImpl();
    }
}
