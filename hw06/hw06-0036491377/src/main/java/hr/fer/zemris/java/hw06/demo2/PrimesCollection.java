package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.function.Consumer;

public class PrimesCollection implements Iterable<Integer> {
    private int numberOfPrimes;

    public PrimesCollection(int numberOfPrimes) {
        this.numberOfPrimes = numberOfPrimes;
    }

    private class IteratorImpl implements Iterator<Integer> {
        private int count;
        private int lastPrime;
        private int maxCount;

        IteratorImpl() {
            maxCount = PrimesCollection.this.numberOfPrimes;
            lastPrime = 2;
        }

        @Override
        public boolean hasNext() {
            return count < maxCount;
        }

        @Override
        public Integer next() {
            int nextPrime = lastPrime;
            int currentNumber = lastPrime;

            //TODO: make this code prettier lol
            while (true) {
                if (currentNumber == 2) {
                    currentNumber++;
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
    }

    @Override
    public Iterator<Integer> iterator() {
        return new IteratorImpl();
    }
}
