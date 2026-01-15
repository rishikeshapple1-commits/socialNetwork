package dataStuctures;

/*
 * Dictionary.java
 * Rishikesh Soni
 * 15/12/2025
 *
 * Dictionary that maps keys to values.
 */

/**
 * Small dictionary backed by a {@link Vector} of (key,value) pairs.
 */
public class Dictionary {
	/**
	 * Internal key/value pair stored in the backing vector.
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private class DictionaryPair implements Comparable {
		private Object key;
		private Object value;

		/**
		 * Creates a pair.
		 *
		 * @param key key
		 * @param value value
		 */
		public DictionaryPair(Object key, Object value) {
			this.key = key;
			this.value = value;
		}

		/** @return key */
		public Object getKey() {
			return key;
		}

		/** @return value */
		public Object getValue() {
			return value;
		}

		/**
		 * Updates the stored value.
		 *
		 * @param value new value
		 */
		public void setValue(Object value) {
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
	}

	/** Backing storage for pairs. */
	private Vector data;

	/**
	 * Creates an empty dictionary.
	 */
	public Dictionary() {
		data = new Vector(10);
	}

	/**
	 * Adds a key/value pair.
*
*	 * If the key already exists, its value is overwritten.
	 *
	 * @param key key to add
	 * @param value value to associate
	 */
	public void add(Object key, Object value) {
		int pos = findPosition(key);
		if (pos >= 0) {
			DictionaryPair existing = (DictionaryPair) data.get(pos);
			existing.setValue(value);
			return;
		}
		data.addLast(new DictionaryPair(key, value));
	}

	/**
	 * Returns the index for a key.
	 *
	 * @param key key to search for
	 * @return index for key, or -1 if not present
	 */
	public int findPosition(Object key) {
		for (int i = 0; i < data.size(); i++) {
			DictionaryPair p = (DictionaryPair) data.get(i);
			if (key == null) {
				if (p.getKey() == null) return i;
			} else {
				if (key.equals(p.getKey())) return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the value for a key.
	 *
	 * @param key key to search for
	 * @return value, or {@code null} if missing
	 */
	public Object find(Object key) {
		int pos = findPosition(key);
		if (pos < 0) return null;
		return ((DictionaryPair) data.get(pos)).getValue();
	}

	/**
	 * Removes a key from the dictionary.
	 *
	 * @param key key to remove
	 */
	public void removeKey(Object key) {
		int pos = findPosition(key);
		if (pos < 0) return;

		// Remove by shifting left, since Vector has no remove(index).
		for (int i = pos; i < data.size() - 1; i++) {
			data.set(i, data.get(i + 1));
		}
		data.removeLast();
	}

	/**
	 * Returns the number of stored pairs.
	 *
	 * @return size
	 */
	public int size() {
		return data.size();
	}

	/**
	 * Returns a string representation of the dictionary.
	 *
	 * @return string form
	 */
	@Override
	public String toString() {
		String s = "(";
		for (int i = 0; i < data.size(); i++) {
			DictionaryPair p = (DictionaryPair) data.get(i);
			s += "(" + p.getKey() + "," + p.getValue() + ")";
			if (i < data.size() - 1) {
				s += " ";
			}
		}
		s += ")";
		return s;
	}
}
/* 
class Main { 

	public static void main(String[] args) {
		Dictionary dict = new Dictionary();

		System.out.println("Size (expected 0): " + dict.size());
		System.out.println("Find missing key (expected null): " + dict.find("missing"));

		dict.add("Rishi", "111-111");
		dict.add("Hemant", "222-222");
		dict.add("Salman", "333-333");

		System.out.println("Dictionary: " + dict);
		System.out.println("Size (expected 3): " + dict.size());
		System.out.println("Hemant = " + dict.find("Hemant"));

		dict.add("Hemant", "999-999");
		System.out.println("Hemant after overwrite = " + dict.find("Hemant"));
		System.out.println("Size (expected still 3): " + dict.size());

		dict.removeKey("Rishi");
		System.out.println("After removeKey(Rishi): " + dict);
		System.out.println("Rishi after remove (expected null): " + dict.find("Rishi"));
		System.out.println("Size (expected 2): " + dict.size());
	}
}
	*/
