package dataStuctures;

/*
 * Graph.java
 * Rishikesh Soni
 * 2/01/2026
 *
 * Directed weighted graph using adjacency lists.
 */

/**
 * Directed, weighted graph implemented using adjacency lists.
 *
 * Each vertex is represented by a {@link Node} that stores a label and a list of
 * outgoing {@link Edge} objects.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Graph {

	/**
	 * Graph vertex storing a label, outgoing edges, and a visited flag.
	 */
	public class Node implements Comparable {
		private Comparable info;
		private Vector edges;
		private boolean visited;

		/**
		 * Creates a node with the given label.
		 *
		 * @param label label for the node (may be {@code null})
		 */
		public Node(Comparable label) {
			info = label;
			edges = new Vector(10);
			visited = false;
		}

		/**
		 * Adds an outgoing edge from this node.
		 *
		 * @param e edge to add
		 */
		public void addEdge(Edge e) {
			edges.addLast(e);
		}

		/**
		 * Returns the vector of outgoing edges.
		 *
		 * @return outgoing edge list
		 */
		public Vector getEdges() {
			return edges;
		}

		/**
		 * Indicates whether this node has been visited.
		 *
		 * @return {@code true} if visited
		 */
		public boolean isVisited() {
			return visited;
		}

		/**
		 * Sets the visited flag.
		 *
		 * @param v new visited state
		 */
		public void setVisited(boolean v) {
			visited = v;
		}

		/**
		 * Compares nodes based on their labels.
		 *
		 * @param o other node
		 * @return comparison result
		 */
		@Override
		public int compareTo(Object o) {
			Node n = (Node) o;
			return n.info.compareTo(info);
		}

		/**
		 * Returns the label stored in this node.
		 *
		 * @return node label
		 */
		public Comparable getLabel() {
			return info;
		}

		@Override
		public String toString() {
			return info == null ? "null" : info.toString();
		}
	}

	/**
	 * Directed, weighted edge from an implicit source node to a destination node.
	 */
	private class Edge implements Comparable {
		private Node toNode;
		private double weight;

		/**
		 * Creates an edge.
		 *
		 * @param to destination node
		 * @param w edge weight
		 */
		public Edge(Node to, double w) {
			toNode = to;
			weight = w;
		}

		/**
		 * Returns the destination node.
		 *
		 * @return destination node
		 */
		public Node getTo() {
			return toNode;
		}

		/**
		 * Returns the edge weight.
		 *
		 * @return weight
		 */
		public double getWeight() {
			return weight;
		}

		/**
		 * Compares edges based on their destination nodes.
		 *
		 * @param o other edge
		 * @return comparison result
		 */
		@Override
		public int compareTo(Object o) {
			Edge n = (Edge) o;
			return n.toNode.compareTo(toNode);
		}

		@Override
		public String toString() {
			return toNode + "(" + weight + ")";
		}
	}

	private Vector nodes;

	/**
	 * Creates an empty graph.
	 */
	public Graph() {
		nodes = new Vector(10);
	}

	/**
	 * Adds a node with the given label to the graph.
	 *
	 * @param label label for the node (may be {@code null})
	 */
	public void addNode(Comparable label) {
		nodes.addLast(new Node(label));
	}

	/**
	 * Returns the number of nodes in the graph.
	 *
	 * @return node count
	 */
	public int nrNodes() {
		return nodes.size();
	}

	/**
	 * Returns the internal node list.
	 *
	 * @return vector of nodes
	 */
	public Vector getNodes() {
		return nodes;
	}

	/**
	 * Resets {@code visited=false} for all nodes.
*
*	 * Used before path search.
	 */
	public void resetVisited() {
		for (int i = 0; i < nodes.size(); i++) {
			((Node) nodes.get(i)).setVisited(false);
		}
	}

	private Node findNode(Comparable nodeLabel) {
		Node res = null;
		for (int i = 0; i < nodes.size(); i++) {
			Node n = (Node) nodes.get(i);
			Comparable lab = n.getLabel();
			if (lab == null && nodeLabel == null) {
				res = n;
				break;
			}
			if (lab != null && nodeLabel != null && lab.compareTo(nodeLabel) == 0) {
				res = n;
				break;
			}
		}
		return res;
	}

	/**
	 * Adds a directed edge with weight.
	 *
	 * @param nodeLabel1 source label
	 * @param nodeLabel2 destination label
	 */
	public void addEdge(Comparable nodeLabel1, Comparable nodeLabel2) {
		addEdge(nodeLabel1, nodeLabel2, 1.0);
	}

	/**
	 * Adds a directed, weighted edge from nodeLabel1 to nodeLabel2.
	 * If either label does not exist as a node, the call does nothing.
	 *
	 * @param nodeLabel1 source label
	 * @param nodeLabel2 destination label
	 * @param w weight
	 */
	public void addEdge(Comparable nodeLabel1, Comparable nodeLabel2, double w) {
		Node n1 = findNode(nodeLabel1);
		Node n2 = findNode(nodeLabel2);
		if (n1 == null || n2 == null) {
			return;
		}
		n1.addEdge(new Edge(n2, w));
	}

	/**
	 * Returns a String list of nodes and edges.
	 *
	 * @return a String containing the node/edge listing
	 */
	@Override
	public String toString() {
		String s = "nodes: ";
		for (int i = 0; i < nodes.size(); i++) {
			s += ((Node) nodes.get(i));
			if (i < nodes.size() - 1) {
				s += ", ";
			}
		}
		s += "\n";

		for (int i = 0; i < nodes.size(); i++) {
			Node from = (Node) nodes.get(i);
			Vector es = from.getEdges();
			for (int j = 0; j < es.size(); j++) {
				Edge e = (Edge) es.get(j);
				s += from + " to " + e.getTo() + " (" + e.getWeight() + ")\n";
			}
		}
		return s;
	}

	/**
	 * Checks whether a path exists using DFS, but without cycle protection.
     * @param nodeLabel1 start label
	 * @param nodeLabel2 end label
	 * @return {@code true} if a path exists
	 */
	public boolean findPathBoolNoCyclesHandled(Comparable nodeLabel1, Comparable nodeLabel2) {
		Node startState = findNode(nodeLabel1);
		Node endState = findNode(nodeLabel2);
		if (startState == null || endState == null) {
			return false;
		}

		Stack toDoList = new Stack();
		toDoList.push(startState);
		while (!toDoList.empty()) {
			Node current = (Node) toDoList.pop();
			if (current == endState) {
				return true;
			}

			Vector es = current.getEdges();
			for (int i = 0; i < es.size(); i++) {
				Edge e = (Edge) es.get(i);
				toDoList.push(e.getTo());
			}
		}
		return false;
	}

	/**
	 * Finds an actual path using DFS plus visited flags.
*
*	 * Works in graphs with cycles because it marks nodes as visited.
	 *
	 * @param nodeLabel1 start label
	 * @param nodeLabel2 end label
	 * @return a list of node labels (start..end), or {@code null} if no path exists
	 */
	public LinkedList findPath(Comparable nodeLabel1, Comparable nodeLabel2) {
		Node start = findNode(nodeLabel1);
		Node end = findNode(nodeLabel2);
		if (start == null || end == null) {
			return null;
		}

		resetVisited();

		LinkedList parentPairs = new LinkedList();

		Stack toDo = new Stack();
		toDo.push(start);
		start.setVisited(true);

		while (!toDo.empty()) {
			Node cur = (Node) toDo.pop();
			if (cur == end) {
				return buildPathFromParents(start, end, parentPairs);
			}

			Vector es = cur.getEdges();
			for (int i = 0; i < es.size(); i++) {
				Edge e = (Edge) es.get(i);
				Node nxt = e.getTo();
				if (!nxt.isVisited()) {
					nxt.setVisited(true);
					parentPairs.addLast(new ParentPair(nxt, cur));
					toDo.push(nxt);
				}
			}
		}
		return null;
	}

	/**
	 * Child-to-parent mapping used to reconstruct a path after a DFS.
	 */
	private static class ParentPair {
		Object child;
		Object parent;

		/**
		 * Creates a parent pair.
		 *
		 * @param c child node
		 * @param p parent node
		 */
		ParentPair(Object c, Object p) {
			child = c;
			parent = p;
		}
	}

	/**
	 * Builds a start-to-end label path by walking parent pointers.
	 *
	 * @param start start node
	 * @param end end node
	 * @param parentPairs list of parent relations collected during the search
	 * @return path as labels (start..end), or {@code null} if reconstruction fails
	 */
	private LinkedList buildPathFromParents(Node start, Node end, LinkedList parentPairs) {
		LinkedList rev = new LinkedList();
		Node cur = end;
		while (cur != null) {
			rev.addLast(cur.getLabel());
			if (cur == start) {
				break;
			}
			cur = (Node) findParent(cur, parentPairs);
		}

		if (rev.getLast() != start.getLabel()) {
			return null;
		}

		LinkedList path = new LinkedList();
		for (int i = rev.size() - 1; i >= 0; i--) {
			path.addLast(rev.get(i));
		}
		return path;
	}

	/**
	 * Looks up the parent of a child node in the recorded parent pairs.
	 *
	 * @param child child node
	 * @param parentPairs list of parent relations
	 * @return parent node, or {@code null} if none is recorded
	 */
	private Object findParent(Node child, LinkedList parentPairs) {
		for (int i = 0; i < parentPairs.size(); i++) {
			ParentPair pp = (ParentPair) parentPairs.get(i);
			if (pp.child == child) {
				return pp.parent;
			}
		}
		return null;
	}
}
/* 
class Main{
	public static void main(String[] args) {
		Graph g = new Graph();
		g.addNode("A");
		g.addNode("B");
		g.addNode("C");
		g.addNode("D");

		g.addEdge("A", "B", 1);
		g.addEdge("A", "C", 2);
		g.addEdge("B", "D", 3);
		g.addEdge("C", "D", 4);

		System.out.println(g);
		System.out.println("path A to D: " + g.findPath("A", "D"));

		g.addEdge("D", "A", 5);
		System.out.println("cycle, path A to D: " + g.findPath("A", "D"));
	}
}
    */
