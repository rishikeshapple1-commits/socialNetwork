package dataStuctures;

/*
 * DictionaryTree.java
 * Rishikesh Soni
 * 29/12/2025
 *
 * Dictionary stored in a BST, ordered by key.
 */

/**
 * Dictionary stored in a {@link Tree} (BST), ordered by key.
 */
public class DictionaryTree {

	/**
	 * Internal key/value pair stored in the backing {@link Tree}.
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private static class DictionaryPair implements Comparable {
		private Object key;
		private Object value;

		/**
		 * Creates a pair.
		 *
		 * @param key dictionary key
		 * @param value dictionary value
		 */
		DictionaryPair(Object key, Object value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Returns the stored value.
		 *
		 * @return value
		 */
		Object getValue() {
			return value;
		}

		/**
		 * Updates the value.
		 *
		 * @param value new value
		 */
		void setValue(Object value) {
			this.value = value;
		}

		/**
		 * Compares pairs by key.
		 *
		 * @param o other pair
		 * @return comparison result
		 */
		public int compareTo(Object o) {
			DictionaryPair other = (DictionaryPair) o;
			if (this.key == null && other.key == null) return 0;
			if (this.key == null) return -1;
			if (other.key == null) return 1;
			if (this.key instanceof Comparable) {
				return ((Comparable) this.key).compareTo(other.key);
			}
			return this.key.equals(other.key) ? 0 : this.key.toString().compareTo(other.key.toString());
		}

		@Override
		public String toString() {
			return "(" + key + "=" + value + ")";
		}
	}

	/** Backing BST storing the pairs. */
	private Tree data;

	/**
	 * Creates an empty dictionary tree.
	 */
	public DictionaryTree() {
		data = new Tree();
	}

	/**
	 * Adds a key/value pair.
*
*	 * If the key already exists, its value is overwritten.
	 *
	 * @param key dictionary key
	 * @param value dictionary value
	 */
	public void add(Object key, Object value) {
		DictionaryPair existing = (DictionaryPair) findPair(key);
		if (existing != null) {
			existing.setValue(value);
			return;
		}
		data.insert(new DictionaryPair(key, value));
	}

	/**
	 * Returns the value for a key.
	 *
	 * @param key dictionary key
	 * @return value, or {@code null} if missing
	 */
	public Object findKey(Object key) {
		DictionaryPair p = (DictionaryPair) findPair(key);
		return p == null ? null : p.getValue();
	}

	/**
	 * Finds the stored pair for a key.
	 *
	 * @param key dictionary key
	 * @return pair stored in the tree, or {@code null}
	 */
	private Object findPair(Object key) {
		// Tree.find expects a Comparable element, but we search by key.
		// So we create a "search pair" with the key and a dummy value.
		return data.find(new DictionaryPair(key, null));
	}
}
/* 
class Main {
	public static void main(String[] args) {
		DictionaryTree dict = new DictionaryTree();
		dict.add("a", 1);
		dict.add("b", 2);
		dict.add("a", 3); // overwrite

		System.out.println("Find a (expected 3): " + dict.findKey("a"));
		System.out.println("Find b (expected 2): " + dict.findKey("b"));
		System.out.println("Find c (expected null): " + dict.findKey("c"));
	}
}
*/