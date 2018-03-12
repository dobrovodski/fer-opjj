package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * @author matej
 *
 */
public class UniqueNumbers {

	static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}

	/**
	 * Invoked when the program is executed.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		TreeNode head = null;
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Unesite broj > ");
			String input = scanner.nextLine();

			if (input.equals("kraj")) {
				System.out.print("Ispis od najmanjeg: ");
				printAscending(head);
				System.out.print("\nIspis od najvećeg: ");
				printDescending(head);
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

		scanner.close();
	}

	/**
	 * Inserts a node into the given binary search tree .
	 * @param head head node of the binary search tree
	 * @param value value to be inserted in the tree
	 * @return head of the binary search tree
	 */
	private static TreeNode addNode(TreeNode head, int value) {
		if (head == null) {
			head = new TreeNode();
			head.value = value;
		} else if (head.value == value) {
			System.out.println("Broj već postoji. Preskačem.");
		} else if (value < head.value) {
			head.left = addNode(head.left, value);
		} else {
			head.right = addNode(head.right, value);
		}
		return head;
	}

	/**
	 * Calculates the size of the given binary search tree.
	 * @param head head of the tree
	 * @return size of the tree
	 */
	private static int treeSize(TreeNode head) {
		if (head != null) {
			return treeSize(head.left) + 1 + treeSize(head.right);
		} else {
			return 0;
		}
	}

	/**
	 * Returns <code>true</code> if a given value is in the tree.
	 * @param head head of the tree
	 * @param value value to be found
	 * @return <code>true</code> if value is in true, <code>false</code> otherwise
	 */
	private static boolean containsValue(TreeNode head, int value) {
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
	 * Prints binary search tree in ascending order.
	 * @param node
	 */
	private static void printAscending(TreeNode node) {
		if (node == null) {
			return;
		}
		printAscending(node.left);
		System.out.print(node.value + " ");
		printAscending(node.right);
	}
	
	/**
	 * Prints binary search tree in descending order.
	 * @param node
	 */
	private static void printDescending(TreeNode node) {
		if (node == null) {
			return;
		}
		printDescending(node.right);
		System.out.print(node.value + " ");
		printDescending(node.left);
	}

}
