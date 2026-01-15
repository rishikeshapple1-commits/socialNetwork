package dataStuctures;

/*
 * Matrix.java
 * Rishikesh Soni
 * 25/12/2025
 *
 * square matrix used by MatrixGraph.
 */

/**
 * Square matrix used by {@link MatrixGraph}.
 *
 * Internally stored as a {@link Vector} of row {@link Vector}s.
 */
@SuppressWarnings({"rawtypes"})
public class Matrix {

	/** Dimension (number of rows/columns). */
	private int n;

	/** Vector of row vectors (each row stores {@link Comparable} values). */
	private Vector data;

	/**
	 * Creates an {@code nrNodes x nrNodes} matrix initialized to 0.0.
	 *
	 * @param nrNodes matrix dimension
	 */
	public Matrix(int nrNodes) {
		n = nrNodes;
		data = new Vector(n);
		for (int r = 0; r < n; r++) {
			Vector row = new Vector(n);
			for (int c = 0; c < n; c++) {
				row.addLast(Double.valueOf(0.0));
			}
			data.addLast(row);
		}
	}

	/**
	 * Sets a matrix cell.
	 *
	 * @param row row index
	 * @param col column index
	 * @param weight value to store
	 */
	public void set(int row, int col, Comparable weight) {
		((Vector) data.get(row)).set(col, weight);
	}

	/**
	 * Returns a matrix cell.
	 *
	 * @param row row index
	 * @param col column index
	 * @return stored value
	 */
	public Comparable get(int row, int col) {
		return (Comparable) ((Vector) data.get(row)).get(col);
	}

	/**
	 * Returns the matrix dimension.
	 *
	 * @return dimension
	 */
	public int size() {
		return n;
	}

	/**
	 * Returns a row-by-row string dump.
	 *
	 * @return string form
	 */
	@Override
	public String toString() {
		String s = "";
		for (int r = 0; r < n; r++) {
			Vector row = (Vector) data.get(r);
			for (int c = 0; c < n; c++) {
				s += row.get(c);
				if (c < n - 1) {
					s += " ";
				}
			}
			s += "\n";
		}
		return s;
	}

}
/* 
class Main {
	public static void main(String[] args) {
		System.out.println("--- Matrix demo ---");
		Matrix m = new Matrix(3);

		System.out.println("Initial 3x3 (expected all 0.0):");
		System.out.println(m);

		m.set(0, 1, Double.valueOf(2.5));
		m.set(2, 0, Double.valueOf(7.0));
		m.set(1, 2, Double.valueOf(1.0));

		System.out.println("After set operations:");
		System.out.println(m);

		System.out.println("get(0,1) expected 2.5: " + m.get(0, 1));
		System.out.println("get(2,0) expected 7.0: " + m.get(2, 0));
		System.out.println("get(1,2) expected 1.0: " + m.get(1, 2));
		System.out.println("size() expected 3: " + m.size());
	}
}
	*/