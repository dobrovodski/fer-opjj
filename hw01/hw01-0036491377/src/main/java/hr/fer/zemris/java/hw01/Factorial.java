package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * @author matej
 */
public class Factorial {

	/**
	 * Invoked when the program is executed.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Unesite broj > ");
			String input = scanner.nextLine();

			// Loop exit condition
			if (input.equals("kraj")) {
				System.out.println("Doviđenja.");
				break;
			}

			int num;
			try {
				num = Integer.parseInt(input);
			} catch (NumberFormatException ex) {
				System.out.format("'%s' nije cijeli broj.\n", input);
				continue;
			}

			if (num < 1 || num > 20) {
				System.out.format("'%d' nije broj u dozvoljenom rasponu.\n", num);
				continue;
			}

			long factorial = factorial(num);
			System.out.format("%d! = %d\n", num, factorial);
		}

		scanner.close();
	}

	/**
	 * Calculates the factorial of a given number.
	 *
	 * @param num given number
	 * @return factorial of given number
	 * @see <a href="https://en.wikipedia.org/wiki/Factorial">Factorial</a>
	 */
	public static long factorial(int num) {
		if (num < 0) {
			throw new IllegalArgumentException("Faktorijel nije definiran za negativne brojeve");
		}

		if (num == 0 || num == 1) {
			return 1;
		}

		long result = 1;
		for (int i = 2; i <= num; i++) {
			result *= i;
		}

		return result;
	}

}
