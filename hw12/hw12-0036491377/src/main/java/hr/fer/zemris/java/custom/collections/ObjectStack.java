package hr.fer.zemris.java.custom.collections;

/**
 * The {@code ObjectStack} class represents an array-backed stack.
 *
 * @author matej
 */
public class ObjectStack {

    ArrayIndexedCollection array;

    /**
     * Default constructor for ObjectStack.
     */
    public ObjectStack() {
        array = new ArrayIndexedCollection();
    }

    /**
     * Returns {@code true} if there are elements on the stack, {@code false} otherwise.
     *
     * @return {@code true} if there are elements on the stack, {@code false} otherwise
     */
    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * Returns the number of elements on the stack.
     *
     * @return number of elements on stack
     */
    public int size() {
        return array.size();
    }

    /**
     * Pushes given value onto the stack.
     *
     * @param value value to be put on top of the stack
     */
    public void push(Object value) {
        array.add(value);
    }

    /**
     * Pops the value on top of the stack and removes it from the stack.
     *
     * @return value on top of the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public Object pop() {
        if (isEmpty()) {
            throw new EmptyStackException("Cannot pop from empty stack");
        }

        Object element = array.get(array.size() - 1);
        array.remove(array.size() - 1);
        return element;
    }

    /**
     * Returns the value on top of the stack without modifying it.
     *
     * @return value on top of the stack
     *
     * @throws EmptyStackException if the stack is empty
     */
    public Object peek() {
        if (isEmpty()) {
            throw new EmptyStackException("Cannot pop from empty stack");
        }

        return array.get(array.size() - 1);
    }

    /**
     * Removes all elements from the stack.
     */
    public void clear() {
        array.clear();
    }

}
