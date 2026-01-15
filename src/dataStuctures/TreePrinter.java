package dataStuctures;

/*
 * TreePrinter.java
 * Rishikesh Soni
 * 28/12/2025
 *
 * Tree action that prints each visited node.
 */

/**
 * {@link Tree.TreeAction} implementation that prints each visited node.
 */
public class TreePrinter extends Tree.TreeAction {

	/**
	 * Prints the visited node.
	 *
	 * @param n visited node
	 */
	@Override
	public void run(Tree.TreeNode n) {
		System.out.println(n);
	}
}
/* 
//Local test code 
class Main {
	public static void main(String[] args) {
		Tree tree = new Tree();
		tree.insert(5);
		tree.insert(3);
		tree.insert(7);
		tree.insert(2);
		tree.insert(4);
		tree.insert(6);
		tree.insert(8);

		System.out.println("In-order traversal:");
		tree.traverseInOrder(new TreePrinter());
	}
}
*/