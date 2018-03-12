package hr.fer.zemris.java.hw01;

import java.util.Scanner;

public class UniqueNumbers {

	static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}

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

	private static TreeNode addNode(TreeNode node, int value) {
		if (node == null) {
			node = new TreeNode();
			node.value = value;
		} else if (node.value == value) {
			System.out.println("Broj već postoji. Preskačem.");
		} else if (value < node.value) {
			node.left = addNode(node.left, value);
		} else {
			node.right = addNode(node.right, value);
		}
		return node;
	}

	private static int treeSize(TreeNode head) {
		if (head != null) {
			return treeSize(head.left) + 1 + treeSize(head.right);
		} else {
			return 0;
		}
	}

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

	private static void printAscending(TreeNode node) {
		if (node == null) {
			return;
		}
		printAscending(node.left);
		System.out.print(node.value + " ");
		printAscending(node.right);
	}

	private static void printDescending(TreeNode node) {
		if (node == null) {
			return;
		}
		printDescending(node.right);
		System.out.print(node.value + " ");
		printDescending(node.left);
	}

}
