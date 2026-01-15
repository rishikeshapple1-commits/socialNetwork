package dataStuctures;

/*
 * MatrixGraph.java
 * Rishikesh Soni
 * 25/12/2025
 *
 * Adjacency-matrix based graph.
 */

/**
 * Graph backed by an adjacency matrix.
 */
@SuppressWarnings({"rawtypes"})
public class MatrixGraph {

	/** Adjacency matrix storing edge weights. */
	private Matrix data;

	/**
	 * Creates a graph with {@code nrNodes} nodes and no edges.
	 *
	 * @param nrNodes number of nodes
	 */
	public MatrixGraph(int nrNodes) {
		data = new Matrix(nrNodes);
	}

	/**
	 * Adds/overwrites a directed weighted edge.
	 *
	 * @param from source node index
	 * @param to destination node index
	 * @param w edge weight
	 */
	public void addEdge(int from, int to, double w) {
		data.set(from, to, Double.valueOf(w));
	}

	/**
	 * Returns the weight of the directed edge.
	 *
	 * @param from source node index
	 * @param to destination node index
	 * @return weight, or 0.0 if no edge exists
	 */
	public double getEdge(int from, int to) {
		Comparable v = data.get(from, to);
		if (v == null) {
			return 0.0;
		}
		return ((Double) v).doubleValue();
	}

	/**
	 * Returns the number of nodes.
	 *
	 * @return node count
	 */
	public int size() {
		return data.size();
	}

	/**
	 * Returns a list of all non-zero edges.
	 *
	 * @return string form
	 */
	@Override
	public String toString() {
		String s = "nodes=" + size() + "\n";
		for (int i = 0; i < size(); i++) {
			for (int j = 0; j < size(); j++) {
				double w = getEdge(i, j);
				if (w != 0.0) {
					s += i + " to " + j + " (" + w + ")\n";
				}
			}
		}
		return s;
	}

}
/* 
class Main {
	public static void main(String[] args) {
		MatrixGraph g = new MatrixGraph(4);
		g.addEdge(0, 1, 2);
		g.addEdge(0, 2, 5);
		g.addEdge(2, 3, 1);
		System.out.println(g);
		
	}
}
*/