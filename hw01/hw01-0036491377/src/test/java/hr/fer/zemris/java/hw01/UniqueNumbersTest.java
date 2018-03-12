package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbersTest {

	@Test
	public void treeSizeNoDuplicates() {
		TreeNode head = null;

		head = UniqueNumbers.addNode(head, 42);
		head = UniqueNumbers.addNode(head, 76);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 35);
		head = UniqueNumbers.addNode(head, 69);
		head = UniqueNumbers.addNode(head, 1);
		head = UniqueNumbers.addNode(head, 3);

		Assert.assertEquals(7, UniqueNumbers.treeSize(head));
	}

	@Test
	public void treeSizeWithDuplicates() {
		TreeNode head = null;

		head = UniqueNumbers.addNode(head, 42);
		head = UniqueNumbers.addNode(head, 76);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 35);

		head = UniqueNumbers.addNode(head, 42);
		head = UniqueNumbers.addNode(head, 76);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 35);

		Assert.assertEquals(4, UniqueNumbers.treeSize(head));
	}

	@Test
	public void containsExistingValue() {
		TreeNode head = null;

		head = UniqueNumbers.addNode(head, 105);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 8);
		head = UniqueNumbers.addNode(head, 6766);
		head = UniqueNumbers.addNode(head, 56);
		
		Assert.assertEquals(true, UniqueNumbers.containsValue(head, 6766));
	}

	@Test
	public void containsNonExistingValue() {
		TreeNode head = null;

		head = UniqueNumbers.addNode(head, 105);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 8);
		head = UniqueNumbers.addNode(head, 6766);
		head = UniqueNumbers.addNode(head, 56);
		
		Assert.assertEquals(false, UniqueNumbers.containsValue(head, 55));
	}

	@Test
	public void checkSortedAscending() {
		TreeNode head = null;

		head = UniqueNumbers.addNode(head, 105);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 8);
		head = UniqueNumbers.addNode(head, 6766);
		head = UniqueNumbers.addNode(head, 56);
		
		String sorted = "8 21 56 105 6766";
		
		Assert.assertEquals(sorted, UniqueNumbers.sortAscending(head, "").trim());
	}

	@Test
	public void checkSortedDescending() {
		TreeNode head = null;

		head = UniqueNumbers.addNode(head, 105);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 8);
		head = UniqueNumbers.addNode(head, 6766);
		head = UniqueNumbers.addNode(head, 56);
		
		String sorted = "6766 105 56 21 8";
		
		Assert.assertEquals(sorted, UniqueNumbers.sortDescending(head, "").trim());
	}

}
