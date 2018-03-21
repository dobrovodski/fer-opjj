package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

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
			throw new IllegalArgumentException("Could not parse as postfix expression. Input was: " + String.join(" ", expression));
		}

		return (int) stack.pop();
	}

	public static boolean isNumeric(String num) {
		try {
			Integer.parseInt(num);
		} catch (NumberFormatException ex) {
			return false;
		}

		return true;
	}

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
			return 0;
		}
	}

}
