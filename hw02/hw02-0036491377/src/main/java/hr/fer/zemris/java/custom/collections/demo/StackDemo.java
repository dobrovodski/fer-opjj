package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Demo for {@code ObjectStack} class. Evaluated postfix expression provided as
 * a single argument.
 * 
 * @author matej
 *
 */
public class StackDemo {

	static ObjectStack stack;

	public static void main(String[] args) {
		String input = args[0];
		String[] splitInput = input.split("\\s+");
		stack = new ObjectStack();

		int result;
		try {
			result = StackDemo.calculate(splitInput);
		} catch (IllegalArgumentException ex) {
			System.err.format(ex.getMessage());
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
			if (StackDemo.isNumeric(element)) {
				stack.push(Integer.parseInt(element));
			} else {
				int result = StackDemo.evaluate((int) stack.pop(), (int) stack.pop(), element);
				stack.push(result);
			}
		}

		if (stack.size() != 1) {
			throw new IllegalArgumentException(
					"Could not parse as postfix expression. Input was: " + String.join(" ", expression));
		}

		return (int) stack.pop();
	}

	/**
	 * Returns {@code true} if the string is an integer, {@code false} otherwise.
	 * 
	 * @param num string to check
	 * @return {@code true} if the string is an integer, {@code false} otherwise
	 */
	public static boolean isNumeric(String num) {
		try {
			Integer.parseInt(num);
		} catch (NumberFormatException ex) {
			return false;
		}

		return true;
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
			throw new IllegalArgumentException("Operator evaluate undefined for "+ op);
		}
	}

}
