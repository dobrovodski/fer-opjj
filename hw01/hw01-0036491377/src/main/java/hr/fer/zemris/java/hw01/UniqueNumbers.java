package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * @author matej
 *
 */
public class UniqueNumbers {

	/**
	 * Representation of a node in a binary search tree
	 */
	static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}

	/**
	 * Invoked when the program is executed.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		TreeNode head = null;
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Unesite broj > ");
			String input = scanner.nextLine();

			// Loop exit condition
			if (input.equals("kraj")) {
				break;
			}

			int value;
			try {
				value = Integer.parseInt(input);
			} catch (NumberFormatException ex) {
				System.out.format("'%s' nije cijeli broj.\n", input);
				continue;
			}

			if (!containsValue(head, value)) {
				head = addNode(head, value);
				System.out.println("Dodano.");
			} else {
				System.out.println("Broj već postoji. Preskačem.");
			}

		}

		printAscending(head);
		printDescending(head);

		scanner.close();
	}

	/**
	 * Inserts a node into the given binary search tree .
	 * 
	 * @param head head node of the binary search tree
	 * @param value value to be inserted in the tree
	 * @return head of the binary search tree
	 */
	public static TreeNode addNode(TreeNode head, int value) {
		if (head == null) {
			head = new TreeNode();
			head.value = value;
		} else if (value < head.value) {
			head.left = addNode(head.left, value);
		} else if (value > head.value) {
			head.right = addNode(head.right, value);
		}

		return head;
	}

	/**
	 * Calculates the size of the given binary search tree.
	 * 
	 * @param head head of the tree
	 * @return size of the tree
	 */
	public static int treeSize(TreeNode head) {
		if (head != null) {
			return treeSize(head.left) + 1 + treeSize(head.right);
		} else {
			return 0;
		}
	}

	/**
	 * Returns <code>true</code> if a given value is in the tree.
	 * 
	 * @param head head of the tree
	 * @param value value to be found
	 * @return <code>true</code> if value is in tree, <code>false</code> otherwise
	 */
	public static boolean containsValue(TreeNode head, int value) {
		if (head == null) {
			return false;
		} else if (head.value == value) {
			return true;
		} else if (value < head.value && head.left != null && containsValue(head.left, value)) {
			return true;
		} else if (value > head.value && head.right != null && containsValue(head.right, value)) {
			return true;
		}

		return false;
	}

	/**
	 * Prints binary search tree in an ascending order.
	 * 
	 * @param head head of the tree to be printed
	 */
	private static void printAscending(TreeNode head) {
		String sorted = sortAscending(head, "");
		System.out.println("Ispis od najmanjeg: " + sorted);
	}

	/**
	 * Returns a string representation of a binary search tree in an ascending
	 * order.
	 * 
	 * @param head head of the binary search tree
	 * @param string starting string to which the values will be appended to
	 * @return string representation of the tree
	 */
	public static String sortAscending(TreeNode head, String string) {
		if (head == null) {
			return string;
		}
		
		string = sortAscending(head.left, string);
		string += head.value + " ";
		string = sortAscending(head.right, string);
		
		return string;

	}

	/**
	 * Prints binary search tree in a descending order.
	 * 
	 * @param head head of the tree to be printed
	 */
	private static void printDescending(TreeNode head) {
		String sorted = sortDescending(head, "");
		System.out.println("Ispis od najvećeg: " + sorted);
	}

	/**
	 * Returns a string representation of a binary search tree in a descending
	 * order.
	 * 
	 * @param head head of the binary search tree
	 * @param string starting string to which the values will be appended to
	 * @return string representation of the tree
	 */
	public static String sortDescending(TreeNode head, String string) {
		if (head == null) {
			return string;
		}
		
		string = sortDescending(head.right, string);
		string += head.value + " ";
		string = sortDescending(head.left, string);
		
		return string;
	}
}
