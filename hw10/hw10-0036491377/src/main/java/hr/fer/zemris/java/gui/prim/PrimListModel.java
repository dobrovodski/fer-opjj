package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a model for prime number generation. It provides methods for notifying listeners and it does so whenever the
 * next prime number is generated.
 *
 * @author matej
 */
public class PrimListModel implements ListModel<Integer> {
    /**
     * List of currently stored primes-
     */
    private List<Integer> primes;
    /**
     * Listeners.
     */
    private List<ListDataListener> listeners;
    /**
     * Current / latest prime.
     */
    private int current;

    /**
     * Constructor.
     */
    public PrimListModel() {
        super();
        primes = new ArrayList<>();
        listeners = new ArrayList<>();
        current = 1;
        primes.add(current);
    }

    @Override
    public int getSize() {
        return primes.size();
    }

    @Override
    public Integer getElementAt(int index) {
        return primes.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    /**
     * Calculates the next prime number and notifies the subscribed observers.
     */
    public void next() {
        while (true) {
            if (current == 1 || current % 2 == 0) {
                current += 1;
            } else {
                current += 2;
            }

            if (isPrime(current)) {
                break;
            }
        }

        int pos = primes.size();
        primes.add(current);

        ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
        for (ListDataListener l : listeners) {
            l.intervalAdded(event);
        }
    }

    /**
     * Returns true if the given number is prime, false otherwise.
     *
     * @param n number to check
     *
     * @return true if the given number is prime, false otherwise
     */
    private boolean isPrime(int n) {
        if (n <= 0) {
            return false;
        }

        if (n == 2) {
            return true;
        }

        if (n % 2 == 0) {
            return false;
        }

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}
