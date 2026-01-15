package dataStuctures;

/*
 * SplayTree.java
 * Rishikesh Soni
 * 28/12/2025
 *
 * Self-adjusting binary search tree (splay tree).
 */

/**
 * Self-adjusting binary search tree (splay tree) based on {@link Tree}.
 *
 * After accesses (and inserts), a node is splayed to the root using rotations.
 * This implementation relies on {@link Tree} parent pointers.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SplayTree extends Tree {

	/**
	 * Performs a left rotation at node {@code x}.
	 *
	 * @param x rotation pivot (becomes left child of its right child)
	 */
	private void rotateLeft(TreeNode x) {
		if (x == null) {
			return;
		}
		TreeNode y = x.rightNode;
		if (y == null) {
			return;
		}

		x.rightNode = y.leftNode;
		if (y.leftNode != null) {
			y.leftNode.parentNode = x;
		}

		y.parentNode = x.parentNode;
		if (x.parentNode == null) {
			root = y;
		} else if (x == x.parentNode.leftNode) {
			x.parentNode.leftNode = y;
		} else {
			x.parentNode.rightNode = y;
		}

		y.leftNode = x;
		x.parentNode = y;
	}

	/**
	 * Performs a right rotation at node {@code y}.
	 *
	 * @param y rotation pivot (becomes right child of its left child)
	 */
	private void rotateRight(TreeNode y) {
		if (y == null) {
			return;
		}
		TreeNode x = y.leftNode;
		if (x == null) {
			return;
		}

		y.leftNode = x.rightNode;
		if (x.rightNode != null) {
			x.rightNode.parentNode = y;
		}

		x.parentNode = y.parentNode;
		if (y.parentNode == null) {
			root = x;
		} else if (y == y.parentNode.rightNode) {
			y.parentNode.rightNode = x;
		} else {
			y.parentNode.leftNode = x;
		}

		x.rightNode = y;
		y.parentNode = x;
	}

	/**
	 * Moves a node up to the root using zig / zig-zig / zig-zag steps.
	 *
	 * @param splayNode node to splay (if {@code null}, does nothing)
	 */
	public void splay(TreeNode splayNode) {
		if (splayNode == null) {
			return;
		}

		TreeNode parent;
		TreeNode grandParent;
		while ((parent = splayNode.parentNode) != null) {
			grandParent = parent.parentNode;
			if (grandParent == null) {
				// Zig
				if (parent.leftNode == splayNode) {
					rotateRight(parent);
				} else {
					rotateLeft(parent);
				}
			} else {
				if (grandParent.leftNode == parent) {
					if (parent.leftNode == splayNode) {
						// Zig-Zig
						rotateRight(grandParent);
						rotateRight(parent);
					} else {
						// Zig-Zag
						rotateLeft(parent);
						rotateRight(grandParent);
					}
				} else {
					if (parent.rightNode == splayNode) {
						// Zig-Zig
						rotateLeft(grandParent);
						rotateLeft(parent);
					} else {
						// Zig-Zag
						rotateRight(parent);
						rotateLeft(grandParent);
					}
				}
			}
		}
	}

	/**
	 * Searches for a value.
	 *
	 * If found, splays the matching node to the root.
	 *
	 * @param element value to find
	 * @return stored value, or {@code null} if not found
	 */
	@Override
	public Comparable find(Comparable element) {
		TreeNode cur = root;
		while (cur != null) {
			int cmp = element.compareTo(cur.value);
			if (cmp == 0) {
				splay(cur);
				return cur.value;
			}
			cur = (cmp < 0) ? cur.leftNode : cur.rightNode;
		}
		return null;
	}

	/**
	 * Inserts a value.
	 *
	 * If the value already exists, the existing node is splayed to the root.
	 * Otherwise, the newly inserted node is splayed.
	 *
	 * @param element value to insert
	 */
	@Override
	public void insert(Comparable element) {
		if (root == null) {
			root = new TreeNode(element);
			root.parentNode = null;
			return;
		}

		TreeNode cur = root;
		TreeNode parent = null;
		int lastCmp = 0;
		while (cur != null) {
			parent = cur;
			lastCmp = element.compareTo(cur.value);
			if (lastCmp == 0) {
				splay(cur);
				return;
			}
			cur = (lastCmp < 0) ? cur.leftNode : cur.rightNode;
		}

		TreeNode n = new TreeNode(element);
		n.parentNode = parent;
		if (lastCmp < 0) {
			parent.leftNode = n;
		} else {
			parent.rightNode = n;
		}
		splay(n);
	}

}

/*
class Main {

	public static void main(String[] args) {
		System.out.println("\n--- SplayTree: insert + splay on find ---");
		SplayTree t = new SplayTree();
		for (int i = 1; i <= 10; i++) t.insert(i);
		System.out.println("root after inserts " + t.root);

		System.out.println("\n Successful find splays to root");
		t.find(3);
		System.out.println("root after find(3) " + t.root);

		System.out.println("\n Unsuccessful find does not change root");
		Object before = t.root == null ? null : t.root.value;
		t.find(999);
		Object after = t.root == null ? null : t.root.value;
		System.out.println("root before find(999): " + before);
		System.out.println("root after  find(999): " + after);

		System.out.println("\nDuplicate insert splays existing node");
		t.insert(7);
		System.out.println("root after insert(7) agaain " + t.root);

		System.out.println("\nRotation cases ");
		SplayTree cases = new SplayTree();
		// Build a fixed shape so we can force different splay patterns.
		// Inserting 100, 50, 150, 25, 75, 60 makes 60 a left-right grandchild.
		cases.insert(100);
		cases.insert(50);
		cases.insert(150);
		cases.insert(25);
		cases.insert(75);
		cases.insert(60);
		System.out.println("case tree root after inserts " + cases.root);

		cases.find(50);
		System.out.println("after find(50) (zig , root 50): " + cases.root);

		// Zig-zig: go down the same side twice.
		cases.find(25);
		System.out.println("after find(25) (zig-zig , root 25): " + cases.root);

		// Zig-zag: opposite sides.
		cases.find(75);
		System.out.println("after find(75) (zig-zag, root 75): " + cases.root);

		System.out.println("\nSplayDictionary (tree splays on access)");
		SplayDictionary d = new SplayDictionary();
		d.add("c", 3);
		d.add("a", 1);
		d.add("b", 2);
		System.out.println("find(b)  " + d.find("b"));
		System.out.println("dictionary root after find(b) should be b: " + d.rootKey());
		System.out.println("find(a)  " + d.find("a"));
		System.out.println("dictionary root after find(a) should be a: " + d.rootKey());
	}
}
*/