package dataStuctures;

/*
 * QuadPointTree.java
 * Rishikesh Soni
 * 26/12/2025
 *
 * 2D point tree with up to 4 children per node.
 */

/**
 * 2D point tree where each node can have up to 4 children.
 * Exercises: 10.1 Binary Search Tree, Question 8
 *
 * `minX()` returns the point with the smallest x.
 */
public class QuadPointTree {

	/**
	 * Simple 2D integer point.
	 */
	public static class Point {
		public int x;
		public int y;

		/**
		 * Creates a point.
		 *
		 * @param x x-coordinate
		 * @param y y-coordinate
		 */
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}

	/**
	 * Internal tree node containing a point and up to 4 quadrant children.
	 */
	private static class Node {
		Point p;
		Node leLe; // x<=, y<=
		Node leGt; // x<=, y>
		Node gtLe; // x>,  y<=
		Node gtGt; // x>,  y>

		/**
		 * Creates a node.
		 *
		 * @param p point to store
		 */
		Node(Point p) {
			this.p = p;
		}
	}

	/** Root node (null means empty). */
	private Node root;

	/**
	 * Creates an empty tree.
	 */
	public QuadPointTree() {
		root = null;
	}

	/**
	 * Inserts a point.
	 *
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void insert(int x, int y) {
		insert(new Point(x, y));
	}

	/**
	 * Inserts a point.
	 *
	 * @param p point to insert
	 */
	public void insert(Point p) {
		root = insertAt(root, p);
	}

	/**
	 * Recursive insert helper.
	 *
	 * @param cur current subtree root
	 * @param p point to insert
	 * @return subtree root after insertion
	 */
	private Node insertAt(Node cur, Point p) {
		if (cur == null) return new Node(p);

		if (p.x <= cur.p.x) {
			if (p.y <= cur.p.y) cur.leLe = insertAt(cur.leLe, p);
			else cur.leGt = insertAt(cur.leGt, p);
		} else {
			if (p.y <= cur.p.y) cur.gtLe = insertAt(cur.gtLe, p);
			else cur.gtGt = insertAt(cur.gtGt, p);
		}
		return cur;
	}

	/**
	 * Returns the point with the smallest x.
	 *
	 * @return point with smallest x, or {@code null} if empty
	 */
	public Point minX() {
		if (root == null) return null;
		return minX(root, root.p);
	}

	/**
	 * Recursive min-x helper.
	 *
	 * @param n current node
	 * @param best current best point
	 * @return best point found
	 */
	private Point minX(Node n, Point best) {
		if (n == null) return best;
		if (n.p.x < best.x) best = n.p;
		best = minX(n.leLe, best);
		best = minX(n.leGt, best);
		best = minX(n.gtLe, best);
		best = minX(n.gtGt, best);
		return best;
	}

	/**
	 * Debugging dump of the tree in preorder.
	 *
	 * @return a multi-line string representation
	 */
	@Override
	public String toString() {
		final String[] s = new String[] { "" };
		preorder(root, 0, s);
		return s[0];
	}

	/**
	 * Recursive preorder dump helper.
	 *
	 * @param n current node
	 * @param depth current indentation depth
	 * @param s output accumulator
	 */
	private void preorder(Node n, int depth, String[] s) {
		if (n == null) return;
		for (int i = 0; i < depth; i++) s[0] += "  ";
		s[0] += n.p + "\n";
		preorder(n.leLe, depth + 1, s);
		preorder(n.leGt, depth + 1, s);
		preorder(n.gtLe, depth + 1, s);
		preorder(n.gtGt, depth + 1, s);
	}
}
/* 
class Main {
	public static void main(String[] args) {
		QuadPointTree t = new QuadPointTree();
		// Points from the figure
		t.insert(4, 4);
		t.insert(1, 1);
		t.insert(1, 5);
		t.insert(2, 6);
		t.insert(3, 7);
		t.insert(5, 8);
		t.insert(6, 2);
		t.insert(8, 6);

		System.out.println("Tree (preorder ):");
		System.out.println(t);

		System.out.println("minX = " + t.minX());
	}
}
*/