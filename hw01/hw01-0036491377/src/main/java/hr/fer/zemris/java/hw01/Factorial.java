package hr.fer.zemris.java.hw01;

import java.util.Scanner;

public class Factorial {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Unesite broj > ");
			String input = scanner.next();

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

	private static long factorial(int num) {
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
