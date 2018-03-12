package hr.fer.zemris.java.hw01;

import java.util.Scanner;

public class Rectangle {

	public static void main(String[] args) {
		if (!(args.length == 0 || args.length == 2)) {
			System.out.println("Program zahtijeva 0 ili 2 argumenta.");
			return;
		}

		if (args.length == 2) {
			double width, height;

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
			double width = parseInput(scanner, "Unesite širinu");
			double height = parseInput(scanner, "Unesite visinu");

			scanner.close();
			printRectangle(width, height);
		}

	}

	private static double parseInput(Scanner scanner, String prompt) {
		double num;

		while (true) {
			System.out.format("%s > ", prompt);
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

	private static double rectangleArea(double width, double height) {
		return width * height;
	}

	private static double rectanglePerimeter(double width, double height) {
		return 2 * (width + height);
	}

	private static void printRectangle(double width, double height) {
		double area = rectangleArea(width, height);
		double perimeter = rectanglePerimeter(width, height);

		System.out.format("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.", width, height, area,
				perimeter);
	}

}
