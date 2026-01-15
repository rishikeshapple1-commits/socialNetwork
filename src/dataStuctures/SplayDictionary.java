package dataStuctures;

/*
 * SplayDictionary.java
 * Rishikesh Soni
 * 30/12/2025
 *
 * Dictionary backed by a splay tree.
 */

/**
 * Dictionary backed by a {@link SplayTree}.
 *
 * The tree is splayed on every successful lookup and insert.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SplayDictionary {
	/**
	 * Internal key/value pair stored in the {@link SplayTree}.
	 */
	private static class Pair implements Comparable {
		/** Key for dictionary ordering and lookup. */
		Object key;
		/** Value associated with the key. */
		Object value;

		/**
		 * Creates a key/value pair.
		 *
		 * @param key dictionary key
		 * @param value dictionary value
		 */
		Pair(Object key, Object value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Compares pairs by comparing their keys.
		 *
		 * If keys are {@link Comparable}, their natural ordering is used.
		 * Otherwise, comparison falls back to {@code toString()}.
		 *
		 * @param o other pair
		 * @return comparison result
		 */
		@Override
		public int compareTo(Object o) {
			Pair other = (Pair) o;
			if (key == null && other.key == null) {
				return 0;
			}
			if (key == null) {
				return -1;
			}
			if (other.key == null) {
				return 1;
			}
			if (key instanceof Comparable) {
				return ((Comparable) key).compareTo(other.key);
			}
			return key.toString().compareTo(other.key.toString());
		}

		@Override
		public String toString() {
			return "(" + key + "=" + value + ")";
		}
	}

	/** Backing splay tree for storage. */
	private SplayTree tree = new SplayTree();

	/**
	 * Adds or overwrites a key/value pair.
	 *
	 * @param key dictionary key
	 * @param value dictionary value
	 */
	public void add(Object key, Object value) {
		Pair existing = (Pair) tree.find(new Pair(key, null));
		if (existing != null) {
			existing.value = value;
			return;
		}
		tree.insert(new Pair(key, value));
	}

	/**
	 * Returns the value for a key.
	 *
	 * @param key dictionary key
	 * @return value for key, or {@code null} if missing
	 */
	public Object find(Object key) {
		Pair p = (Pair) tree.find(new Pair(key, null));
		return p == null ? null : p.value;
	}

	/**
	 * Returns the key currently at the splay tree root.
	 *
	 * Useful for checking that successful operations splay the accessed key.
	 *
	 * @return root key, or {@code null} if empty
	 */
	public Object rootKey() {
		if (tree == null || tree.root == null) {
			return null;
		}
		Pair p = (Pair) tree.root.value;
		return p == null ? null : p.key;
	}
}

/*
// Local test code
class Main {
	public static void main(String[] args) {
		System.out.println("SplayDictionary: insert + splay");
		SplayDictionary d = new SplayDictionary();
		d.add("c", 3);
		d.add("a", 1);
		d.add("b", 2);

		System.out.println("find(b) = " + d.find("b"));
		System.out.println("find(a) = " + d.find("a"));

		System.out.println("\nSplayDictionary: successful find splays key to root");
		System.out.println("find(b) = " + d.find("b"));
		System.out.println("root after find(b) (will show b): " + d.rootKey());
		System.out.println("find(a) = " + d.find("a"));
		System.out.println("root after find(a) (will show a): " + d.rootKey());

		System.out.println("\nSplayDictionary: unsuccessful find returns null and does not change root");
		Object before = d.rootKey();
		System.out.println("find(x) = " + d.find("x"));
		System.out.println("root before find(x): " + before);
		System.out.println("root after  find(x): " + d.rootKey());

		System.out.println("\nSplayDictionary: overwrite existing key (no duplicates)");
		d.add("a", 111);
		System.out.println("find(a) after overwrite = " + d.find("a"));
		System.out.println("root after overwrite+find(a) (will show a): " + d.rootKey());

		System.out.println("\nSplayDictionary: re-access shows splaying effect");
		d.find("c");
		System.out.println("root after find(c) (will show c): " + d.rootKey());
		d.find("b");
		System.out.println("root after find(b) (will show b): " + d.rootKey());
	} 
}
        */

