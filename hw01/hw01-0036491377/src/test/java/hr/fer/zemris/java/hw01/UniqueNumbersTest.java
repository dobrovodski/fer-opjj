package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

/**
 * JUnit tests for UniqueNumbers.
 * 
 * @see <a href=
 *      "http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html">
 *      Naming standards for unit tests </a>
 * @author matej
 *
 */
public class UniqueNumbersTest {
	
	@Test
	public void AddNode_OneValueAdded_Added() {
		TreeNode head = null;

		head = UniqueNumbers.addNode(head, 42);

		Assert.assertEquals(42, head.value);
	}

	@Test
	public void AddNode_SimpleValues_Added() {
		TreeNode head = null;

		head = UniqueNumbers.addNode(head, 42);
		head = UniqueNumbers.addNode(head, 76);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 35);
		head = UniqueNumbers.addNode(head, 69);
		head = UniqueNumbers.addNode(head, 1);
		head = UniqueNumbers.addNode(head, 3);

		Assert.assertEquals(76, head.right.value);
		Assert.assertEquals(69, head.right.left.value);
		Assert.assertEquals(1, head.left.left.value);
		Assert.assertEquals(35, head.left.right.value);
	}

	@Test
	public void ContainsValue_ExistentValue_ValueFound() {
		TreeNode head = null;

		head = UniqueNumbers.addNode(head, 105);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 8);
		head = UniqueNumbers.addNode(head, 6766);
		head = UniqueNumbers.addNode(head, 56);

		Assert.assertEquals(true, UniqueNumbers.containsValue(head, 6766));
	}

	@Test
	public void ContainsValue_NonexistentValue_ValueNotFound() {
		TreeNode head = null;

		head = UniqueNumbers.addNode(head, 105);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 8);
		head = UniqueNumbers.addNode(head, 6766);
		head = UniqueNumbers.addNode(head, 56);

		Assert.assertEquals(false, UniqueNumbers.containsValue(head, 55));
	}
	
	@Test
	public void TreeSize_NoNodesAdded_Zero() {
		TreeNode head = null;

		Assert.assertEquals(0, UniqueNumbers.treeSize(head));
	}
	
	@Test
	public void TreeSize_FiveNodesAdded_Five() {
		TreeNode head = null;

		head = UniqueNumbers.addNode(head, 105);
		head = UniqueNumbers.addNode(head, 21);
		head = UniqueNumbers.addNode(head, 8);
		head = UniqueNumbers.addNode(head, 6766);
		head = UniqueNumbers.addNode(head, 56);

		Assert.assertEquals(5, UniqueNumbers.treeSize(head));
	}

}
