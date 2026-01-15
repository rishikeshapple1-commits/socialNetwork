package dataStuctures;

/*
 * Tree.java
 * Rishikesh Soni
 * 25/12/2025
 *
 * Basic binary search tree.
 */

/**
 * Basic binary search tree (BST) implementation.
 *
 * Values are stored as {@link Comparable} objects. Duplicate inserts are ignored.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Tree {
	/**
	 * Single node in the tree.
	 */
	public class TreeNode implements Comparable {
		/** Stored value for this node. */
		protected Comparable value;
		/** Left child node (values smaller than this node). */
		protected TreeNode leftNode;
		/** Right child node (values larger than this node). */
		protected TreeNode rightNode;
		/** Parent node, or {@code null} if this node is the root. */
		protected TreeNode parentNode;

		/**
		 * Creates a node with a value.
		 *
		 * @param v value to store
		 */
		public TreeNode(Comparable v) {
			this(v, null, null, null);
		}

		/**
		 * Creates a node with a value and child references.
		 *
		 * @param v value to store
		 * @param left left child
		 * @param right right child
		 */
		public TreeNode(Comparable v, TreeNode left, TreeNode right) {
			this(v, left, right, null);
		}

		/**
		 * Internal constructor that can also set the parent.
		 *
		 * @param v value to store
		 * @param left left child
		 * @param right right child
		 * @param parent parent node
		 */
		private TreeNode(Comparable v, TreeNode left, TreeNode right, TreeNode parent) {
			value = v;
			leftNode = left;
			rightNode = right;
			parentNode = parent;
			if (leftNode != null) {
				leftNode.parentNode = this;
			}
			if (rightNode != null) {
				rightNode.parentNode = this;
			}
		}

		/**
		 * Returns the left child.
		 *
		 * @return left child or {@code null}
		 */
		public TreeNode getLeftTree() {
			return leftNode;
		}

		/**
		 * Returns the right child.
		 *
		 * @return right child or {@code null}
		 */
		public TreeNode getRightTree() {
			return rightNode;
		}

		/**
		 * Returns the parent node.
		 *
		 * @return parent node or {@code null} if root
		 */
		public TreeNode getParentTree() {
			return parentNode;
		}

		/**
		 * Returns the stored value.
		 *
		 * @return stored value
		 */
		public Comparable getValue() {
			return value;
		}

		/**
		 * Compares nodes by comparing their stored values.
		 *
		 * @param o other node
		 * @return comparison result
		 */
		@Override
		public int compareTo(Object o) {
			return value.compareTo(((TreeNode) o).value);
		}

		/**
		 * Returns the stored value as a string.
		 *
		 * @return string form
		 */
		@Override
		public String toString() {
			return value == null ? "null" : value.toString();
		}
	}

	/**
	 * Action callback used by traversals.
	 */
	public static abstract class TreeAction {
		/**
		 * Runs once per visited node.
		 *
		 * @param n current visited node
		 */
		public abstract void run(Tree.TreeNode n);
	}

	/** Root node (null means empty tree). */
	protected TreeNode root;

	/**
	 * Creates an empty tree.
	 */
	public Tree() {
		root = null;
	}

	/**
	 * Breadth-first traversal (level order).
	 *
	 * @param action action to apply to each visited node
	 */
	public void traverse(TreeAction action) {
		if (root == null) {
			return;
		}

		Queue t = new Queue();
		t.push(root);
		while (!t.empty()) {
			TreeNode n = (TreeNode) t.pop();
			action.run(n);
			if (n.getLeftTree() != null) {
				t.push(n.getLeftTree());
			}
			if (n.getRightTree() != null) {
				t.push(n.getRightTree());
			}
		}
	}

	/**
	 * Recursive in-order traversal helper.
	 *
	 * @param n current node
	 * @param action action to apply to each visited node
	 */
	private void traverseNode(TreeNode n, TreeAction action) {
		if (n == null) {
			return;
		}

		if (n.getLeftTree() != null) {
			traverseNode(n.getLeftTree(), action);
		}
		action.run(n);
		if (n.getRightTree() != null) {
			traverseNode(n.getRightTree(), action);
		}
	}

	/**
	 * In-order traversal (left, node, right).
	 *
	 * @param action action to run on each visited node
	 */
	public void traverseInOrder(TreeAction action) {
		traverseNode(root, action);
	}

	/**
	 * Inserts a value into the tree.
	 *
	 * Duplicate values are ignored.
	 *
	 * @param element value to insert
	 */
	public void insert(Comparable element) {
		insertAtNode(element, root, null);
	}

	/**
	 * Inserts an element by traversing the tree.
	 *
	 * @param element element to insert
	 * @param current current node in the search
	 * @param parent parent node of current
	 */
	private void insertAtNode(Comparable element, TreeNode current, TreeNode parent) {
		if (current == null) {
			TreeNode newNode = new TreeNode(element);
			newNode.parentNode = parent;

			if (parent != null) {
				if (element.compareTo(parent.value) < 0) {
					parent.leftNode = newNode;
				} else {
					parent.rightNode = newNode;
				}
			} else {
				root = newNode;
				newNode.parentNode = null;
			}
			return;
		}

		if (element.compareTo(current.value) == 0) {
			// Duplicate: ignore.
			return;
		}

		if (element.compareTo(current.value) < 0) {
			insertAtNode(element, current.getLeftTree(), current);
		} else {
			insertAtNode(element, current.getRightTree(), current);
		}
	}

	/**
	 * Returns the stored value that compares equal to {@code element}.
	 *
	 * @param element value to search for
	 * @return stored value, or {@code null} if not found
	 */
	public Comparable find(Comparable element) {
		return findNode(element, root);
	}

	/**
	 * Recursive search helper.
	 *
	 * @param element value to search for
	 * @param current current node
	 * @return stored value, or {@code null}
	 */
	private Comparable findNode(Comparable element, TreeNode current) {
		if (current == null) {
			return null;
		}

		if (element.compareTo(current.value) == 0) {
			return current.value;
		}

		if (element.compareTo(current.value) < 0) {
			return findNode(element, current.getLeftTree());
		}

		return findNode(element, current.getRightTree());
	}

	/**
	 * Indicates whether an element exists in the tree.
	 *
	 * @param element value to search for
	 * @return {@code true} if found
	 */
	public boolean findBool(Comparable element) {
		return find(element) != null;
	}

	/**
	 * Returns the height of the tree.
	 *
	 * @return height (empty tree = 0)
	 */
	public int depth() {
		return depthOf(root);
	}

	/**
	 * Recursive depth helper.
	 *
	 * @param n current node
	 * @return depth of subtree
	 */
	private int depthOf(TreeNode n) {
		if (n == null) {
			return 0;
		}
		int l = depthOf(n.getLeftTree());
		int r = depthOf(n.getRightTree());
		return 1 + (l > r ? l : r);
	}

	/**
	 * Returns the maximum value in the tree.
	 *
	 * @return maximum, or {@code null} if empty
	 */
	public Comparable max() {
		if (root == null) {
			return null;
		}
		TreeNode cur = root;
		while (cur.getRightTree() != null) {
			cur = cur.getRightTree();
		}
		return cur.getValue();
	}

	/**
	 * Returns the minimum value in the tree.
	 *
	 * @return minimum, or {@code null} if empty
	 */
	public Comparable min() {
		if (root == null) {
			return null;
		}
		TreeNode cur = root;
		while (cur.getLeftTree() != null) {
			cur = cur.getLeftTree();
		}
		return cur.getValue();
	}

	/**
	 * Removes and returns the minimum value.
	 *
	 * @return removed minimum, or {@code null} if empty
	 */
	public Comparable removeMin() {
		if (root == null) {
			return null;
		}
		TreeNode[] res = removeMinNode(root);
		root = res[0];
		TreeNode removed = res[1];
		return removed == null ? null : removed.getValue();
	}

	/**
	 * Removes the minimum node from a subtree.
	 *
	 * @param node subtree root
	 * @return array {@code [newSubtreeRoot, removedNode]}
	 */
	private TreeNode[] removeMinNode(TreeNode node) {
		if (node.getLeftTree() == null) {
			// node is the minimum; replace it by its right subtree
			TreeNode newRoot = node.getRightTree();
			if (newRoot != null) {
				newRoot.parentNode = node.parentNode;
			}
			return new TreeNode[] { newRoot, node };
		}

		TreeNode[] leftRes = removeMinNode(node.getLeftTree());
		node.leftNode = leftRes[0];
		if (node.leftNode != null) {
			node.leftNode.parentNode = node;
		}
		return new TreeNode[] { node, leftRes[1] };
	}

	/**
	 * Returns the number of nodes in the tree.
	 *
	 * @return node count
	 */
	public int size() {
		return sizeOf(root);
	}

	/**
	 * Recursive size helper.
	 *
	 * @param n current node
	 * @return subtree size
	 */
	private int sizeOf(TreeNode n) {
		if (n == null) {
			return 0;
		}
		return 1 + sizeOf(n.getLeftTree()) + sizeOf(n.getRightTree());
	}

	/**
	 * Returns the sum of numeric values in the tree.
	 *
	 * Non-{@link Number} values count as 0.
	 *
	 * @return sum
	 */
	public double sumNumbers() {
		return sumNumbers(root);
	}

	/**
	 * Recursive numeric sum helper.
	 *
	 * @param n current node
	 * @return sum of numeric values in subtree
	 */
	private double sumNumbers(TreeNode n) {
		if (n == null) {
			return 0.0;
		}
		double self = 0.0;
		if (n.getValue() instanceof Number) {
			self = ((Number) n.getValue()).doubleValue();
		}
		return self + sumNumbers(n.getLeftTree()) + sumNumbers(n.getRightTree());
	}

	/**
	 * Returns the average of numeric values in the tree.
	 *
	 * @return average, or 0.0 if empty
	 */
	public double averageNumbers() {
		int s = size();
		if (s == 0) {
			return 0.0;
		}
		return sumNumbers() / s;
	}

	/**
	 * Returns the median value in sorted (in-order) order.
	 *
	 * @return median, or {@code null} if empty
	 */
	public Comparable median() {
		int n = size();
		if (n == 0) {
			return null;
		}
		int k = (n - 1) / 2; // 0-based middle
		return kthInOrder(root, new int[] { 0 }, k);
	}

	/**
	 * Returns the k-th value in in-order sequence.
	 *
	 * @param node current node
	 * @param idx current index 
	 * @param k target index
	 * @return k-th value, or {@code null}
	 */
	private Comparable kthInOrder(TreeNode node, int[] idx, int k) {
		if (node == null) {
			return null;
		}
		Comparable left = kthInOrder(node.getLeftTree(), idx, k);
		if (left != null) {
			return left;
		}
		if (idx[0] == k) {
			return node.getValue();
		}
		idx[0]++;
		return kthInOrder(node.getRightTree(), idx, k);
	}

	/**
	 * Swaps left/right children everywhere in the tree.
	 */
	public void swapTree() {
		swapNode(root);
	}

	/**
	 * Recursive swap helper.
	 *
	 * @param n current node
	 */
	private void swapNode(TreeNode n) {
		if (n == null) {
			return;
		}
		TreeNode tmp = n.leftNode;
		n.leftNode = n.rightNode;
		n.rightNode = tmp;
		swapNode(n.leftNode);
		swapNode(n.rightNode);
	}

	/**
	 * Returns a BFS listing of the tree values, one per line.
	 *
	 * @return BFS string output
	 */
	@Override
	public String toString() {
		final String[] s = new String[] { "" };
		traverse(new TreeAction() {
			public void run(TreeNode n) {
				s[0] += n.toString() + "\n";
			}
		});
		return s[0];
	}
}
/* 
// Local test code 
class Main {

	public static void main(String[] args) {
		Tree t = new Tree();
		for (int i = 1; i <= 6; i++) {
			t.insert(i);
		}

		System.out.println("BFS toString (queue) after inserting 1 to 6:");
		System.out.println(t);

		System.out.println("find(4)=" + t.findBool(4));
		System.out.println("find(10)=" + t.findBool(10));
		System.out.println("depth=" + t.depth());
		System.out.println("max=" + t.max());
		System.out.println("min=" + t.min());
		System.out.println("average=" + t.averageNumbers());
		System.out.println("median=" + t.median());

		System.out.println("BFS traversal using TreePrinter:");
		t.traverse(new TreePrinter());

		System.out.println("In-order traversal:");
		t.traverseInOrder(new TreeAction() {
			public void run(Tree.TreeNode n) {
				System.out.print(n + " ");
			}
		});
		System.out.println();

		t.swapTree();
		System.out.println("After swapTree(), BFS toString:");
		System.out.println(t);
	}
}
*/
