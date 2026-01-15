package dataStuctures;

/*
 * RedBlackTree.java
 * Rishikesh Soni
 * 27/12/2025
 *
 * Red-black tree with insert balancing.
 */

/**
 * Red-black tree with insert balancing.
 * Uses a black sentinel node (nil) instead of null children.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class RedBlackTree {
	/**
	 * Node color.
	 */
	public enum TreeNodeColor {
		Red, Black
	}

	/**
	 * Tree node used by the red-black tree.
	 */
	protected class ColouredTreeNode implements Comparable {
		protected TreeNodeColor color;
		protected Comparable value;
		protected ColouredTreeNode leftNode;
		protected ColouredTreeNode rightNode;
		protected ColouredTreeNode parentNode;

		/**
		 * Creates a node.
		 *
		 * @param value stored value (null for nil node)
		 * @param color node color
		 */
		public ColouredTreeNode(Comparable value, TreeNodeColor color) {
			this.value = value;
			this.color = color;
		}

		/**
		 * Returns a string "value : color".
		 *
		 * @return string form
		 */
		@Override
		public String toString() {
			if (value == null) {
				return "nil : " + color;
			}
			return value.toString() + " : " + color;
		}

		/**
		 * Compares nodes by their stored values.
		 *
		 * @param arg0 other node
		 * @return comparison result
		 */
		@Override
		public int compareTo(Object arg0) {
			ColouredTreeNode node2 = (ColouredTreeNode) arg0;
			return value.compareTo(node2.value);
		}
	}

	/** Root node (nil node means empty). */
	protected ColouredTreeNode root;

	/** Sentinel node used instead of null children. */
	protected ColouredTreeNode nilNode;

	/**
	 * Creates an empty red-black tree.
	 */
	public RedBlackTree() {
		nilNode = new ColouredTreeNode(null, TreeNodeColor.Black);
		root = nilNode;
	}

	/**
	 * Left rotation around node x.
	 *
	 * @param x pivot node
	 */
	private void rotateLeft(ColouredTreeNode x) {
		ColouredTreeNode y = x.rightNode;
		x.rightNode = y.leftNode;
		if (y.leftNode != nilNode) {
			y.leftNode.parentNode = x;
		}

		y.parentNode = x.parentNode;
		if (x.parentNode == nilNode) {
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
	 * Right rotation around node y.
	 *
	 * @param y pivot node
	 */
	private void rotateRight(ColouredTreeNode y) {
		ColouredTreeNode x = y.leftNode;
		y.leftNode = x.rightNode;
		if (x.rightNode != nilNode) {
			x.rightNode.parentNode = y;
		}

		x.parentNode = y.parentNode;
		if (y.parentNode == nilNode) {
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
	 * Inserts a key and restores red-black properties.
	 *
	 * @param element value to insert
	 */
	public void rbInsert(Comparable element) {
		ColouredTreeNode z = new ColouredTreeNode(element, TreeNodeColor.Red);
		ColouredTreeNode y = nilNode;
		ColouredTreeNode x = root;

		while (x != nilNode) {
			y = x;
			if (z.compareTo(x) < 0) {
				x = x.leftNode;
			} else {
				x = x.rightNode;
			}
		}

		z.parentNode = y;
		if (y == nilNode) {
			root = z;
		} else if (z.compareTo(y) < 0) {
			y.leftNode = z;
		} else {
			y.rightNode = z;
		}

		z.leftNode = nilNode;
		z.rightNode = nilNode;
		fixUpInsert(z);
	}

	/**
	 * Fixes colors/rotations after an insert.
	 *
	 * @param z inserted node
	 */
	private void fixUpInsert(ColouredTreeNode z) {
		while (z.parentNode != null && z.parentNode.parentNode != null && z.parentNode.color == TreeNodeColor.Red) {
			if (z.parentNode == z.parentNode.parentNode.leftNode) {
				ColouredTreeNode y = z.parentNode.parentNode.rightNode;
				if (y.color == TreeNodeColor.Red) {
					z.parentNode.color = TreeNodeColor.Black;
					y.color = TreeNodeColor.Black;
					z.parentNode.parentNode.color = TreeNodeColor.Red;
					z = z.parentNode.parentNode;
				} else {
					if (z == z.parentNode.rightNode) {
						z = z.parentNode;
						rotateLeft(z);
					}
					z.parentNode.color = TreeNodeColor.Black;
					z.parentNode.parentNode.color = TreeNodeColor.Red;
					rotateRight(z.parentNode.parentNode);
				}
			} else {
				ColouredTreeNode y = z.parentNode.parentNode.leftNode;
				if (y.color == TreeNodeColor.Red) {
					z.parentNode.color = TreeNodeColor.Black;
					y.color = TreeNodeColor.Black;
					z.parentNode.parentNode.color = TreeNodeColor.Red;
					z = z.parentNode.parentNode;
				} else {
					if (z == z.parentNode.leftNode) {
						z = z.parentNode;
						rotateRight(z);
					}
					z.parentNode.color = TreeNodeColor.Black;
					z.parentNode.parentNode.color = TreeNodeColor.Red;
					rotateLeft(z.parentNode.parentNode);
				}
			}
		}

		if (z == root) {
			root.color = TreeNodeColor.Black;
		}
	}

	/**
	 * Prints the tree in breadth-first order (value + color).
	 */
	public void recPrint() {
		Queue t = new Queue();
		t.push(root);
		while (!t.empty()) {
			ColouredTreeNode n = (ColouredTreeNode) t.pop();
			System.out.println(n);

			if (n.leftNode != nilNode) {
				t.push(n.leftNode);
			}
			if (n.rightNode != nilNode) {
				t.push(n.rightNode);
			}
		}
	}
}
/* 
class Main {
	public static void main(String[] args) {
		RedBlackTree t = new RedBlackTree();
		int[] keys = new int[] { 41, 38, 31, 12, 19, 8 };
		for (int i = 0; i < keys.length; i++) t.rbInsert(keys[i]);
		System.out.println("Red-Black Tree after inserting 41, 38, 31, 12, 19, 8:");
		t.recPrint();

	}
}
*/