package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * @author matej
 */
public class Rectangle {

	/**
	 * Invoked when the program is executed.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if (!(args.length == 0 || args.length == 2)) {
			System.out.println("Program zahtijeva 0 ili 2 argumenta.");
			return;
		}

		if (args.length == 2) {
			double width;
			double height;

			try {
				width = Double.parseDouble(args[0]);
				height = Double.parseDouble(args[1]);
			} catch (NumberFormatException ex) {
				System.out.println("Program zahtijeva decimalne brojeve");
				return;
			}

			if (width < 0 || height < 0) {
				System.out.println("Unijeli ste negativnu vrijednost.");
				return;
			}

			printRectangle(width, height);
		}

		if (args.length == 0) {
			Scanner scanner = new Scanner(System.in);
			double width = queryInput(scanner, "Unesite širinu > ");
			double height = queryInput(scanner, "Unesite visinu > ");

			scanner.close();
			printRectangle(width, height);
		}

	}

	/**
	 * Repeatedly asks the user for input until a non-negative number is given.
	 *
	 * @param scanner scanner used to scan user input
	 * @param prompt  prompt provided to the user
	 * @return non-negative number provided by the user
	 */
	private static double queryInput(Scanner scanner, String prompt) {
		double num;

		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine();

			try {
				num = Double.parseDouble(input);
			} catch (NumberFormatException ex) {
				System.out.format("'%s' se ne može protumačiti kao broj.\n", input);
				continue;
			}

			if (num < 0) {
				System.out.println("Unijeli ste negativnu vrijednost.");
				continue;
			}

			break;
		}

		return num;
	}

	/**
	 * Calculates the area of a rectangle using its width and height.
	 *
	 * @param width  width of rectangle
	 * @param height height of rectangle
	 * @return area of rectangle
	 */
	public static double rectangleArea(double width, double height) {
		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("Pravokutnik mora imati pozitivne stranice");
		}

		return width * height;
	}

	/**
	 * Calculates the perimeter of a rectangle using its width and height.
	 *
	 * @param width  width of rectangle
	 * @param height height of rectangle
	 * @return perimeter of rectangle
	 */
	public static double rectanglePerimeter(double width, double height) {
		if (width < 0 || height < 0) {
			throw new IllegalArgumentException("Pravokutnik mora imati pozitivne stranice");
		}

		return 2 * (width + height);
	}

	/**
	 * Prints information about a rectangle to the standard output.
	 *
	 * @param width  width of rectangle
	 * @param height height of rectangle
	 */
	private static void printRectangle(double width, double height) {
		double area = rectangleArea(width, height);
		double perimeter = rectanglePerimeter(width, height);

		System.out.format("Pravokutnik širine %.2f i visine %.2f ima površinu %.2f te opseg %.2f.", width, height, area,
				perimeter);
	}

}
