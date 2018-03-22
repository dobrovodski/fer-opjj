package hr.fer.zemris.java.custom.collections.demo;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Demo for {@code ObjectStack} class. Evaluates postfix expression provided as
 * a single argument.
 * 
 * @author matej
 *
 */
public class StackDemo {

	static ObjectStack stack;
	static final String OPS[] = { "+", "-", "%", "*", "/" };

	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.err.println("This program only accepts a single argument.");
			return;
		}
		
		String input = args[0];
		String[] splitInput = input.split("\\s+");
		stack = new ObjectStack();

		int result;
		try {
			result = StackDemo.calculate(splitInput);
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			return;
		}

		System.out.format("Expression evaluates to %d.", result);
	}

	/**
	 * Calculates the result of a postfix expression.
	 * 
	 * @param expression string representation of postfix expression
	 * @return integer result of evaluated expression
	 * @throws IllegalArgumentException if unable to evaluate expression
	 */
	public static int calculate(String[] expression) {
		for (String element : expression) {
			if (!StackDemo.isOperator(element)) {
				try {
					int num = Integer.parseInt(element);
					stack.push(num);
				} catch (NumberFormatException ex) {
					// Rethrow exception
					throw new IllegalArgumentException(
							"Could not parse symbol. Failed at: " + element);
				}
			} else {
				try {
					int result = StackDemo.evaluate((int) stack.pop(), (int) stack.pop(), element);
					stack.push(result);
				} catch (EmptyStackException ex) {
					// Rethrow exception
					throw new IllegalArgumentException(
							"Could not parse expression because of operator/number mismatch. Input was: " + String.join(" ", expression));
				}
			}
		}

		if (stack.size() != 1) {
			throw new IllegalArgumentException(
					"Could not parse expression. Input was: " + String.join(" ", expression));
		}

		return (int) stack.pop();
	}

	/**
	 * Returns {@code true} if the string represents a valid operator, {@code false} otherwise.
	 * 
	 * @param op string to check
	 * @return {@code true} if the string represents a valid operator, {@code false} otherwise
	 */
	public static boolean isOperator(String op) {
		return Arrays.asList(OPS).contains(op);
	}

	/**
	 * Returns the result of the mathematical operation defined by {@code op} with
	 * parameters {@code a} and {@code b}.
	 * 
	 * @param a first parameter
	 * @param b second parameter
	 * @param op mathematical operator to perform
	 * @return integer value of the mathematical operation
	 * @throws IllegalArgumentException if the operation isn't defined
	 */
	public static int evaluate(int a, int b, String op) {
		switch (op) {
		case "+":
			return b + a;
		case "-":
			return b - a;
		case "/":
			return b / a;
		case "*":
			return b * a;
		case "%":
			return b % a;
		default:
			throw new IllegalArgumentException("Operator evaluation undefined for "+ op);
		}
	}

}
