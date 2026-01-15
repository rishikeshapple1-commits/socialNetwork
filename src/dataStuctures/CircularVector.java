package dataStuctures;

/*
 * CircularVector.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Ring buffer backed by Vector.
 */

/**
 * Circular buffer (ring buffer) backed by {@link Vector}.
 * This uses a fixed capacity and throws an exception when full.
 */
public class CircularVector {
	/** Underlying storage. */
	private Vector data;
	/** Index of the logical first element. */
	private int first;
	/** Number of stored elements. */
	private int count;

	/**
	 * Creates an empty circular vector with capacity 5.
	 */
	public CircularVector() {
		count = 0;
		first = 0;
		data = new Vector(5);
	}

	/**
	 * Returns the number of stored elements.
	 *
	 * @return size
	 */
	public int size() {
		return count;
	}

	/**
	 * Adds an element to the front of the buffer.
	 *
	 * @param element element to add
	 */
	public void AddFirst(Object element) {
		if (count >= data.capacity()) {
			throw new RuntimeException("CircularVector is full");
		}
		first = (first - 1 + data.capacity()) % data.capacity();
		data.set(first, element);
		count++;
	}

	/**
	 * Adds an element to the end of the buffer.
	 *
	 * @param element element to add
	 */
	public void AddLast(Object element) {
		if (count >= data.capacity()) {
			throw new RuntimeException("CircularVector is full");
		}
		int index = (first + count) % data.capacity();
		data.set(index, element);
		count++;
	}

	/**
	 * Returns the first element.
	 *
	 * @return first element, or {@code null} if empty
	 */
	public Object GetFirst() {
		if (count == 0) {
			return null;
		}
		return data.get(first);
	}

	/**
	 * Returns the last element.
	 *
	 * @return last element, or {@code null} if empty
	 */
	public Object GetLast() {
		if (count == 0) {
			return null;
		}
		int lastIndex = (first + count - 1) % data.capacity();
		return data.get(lastIndex);
	}

	/**
	 * Removes the first element if it exists.
	 */
	public void RemoveFirst() {
		if (count > 0) {
			first = (first + 1) % data.capacity();
			count--;
		}
	}

	/**
	 * Removes the last element if it exists.
	 */
	public void RemoveLast() {
		if (count > 0) {
			count--;
		}
	}

	/**
	 * Returns a string representation of the current buffer contents.
	 *
	 * @return string form
	 */
	@Override
	public String toString() {
		String s = "[";
		for (int i = 0; i < count; i++) {
			int index = (first + i) % data.capacity();
			s += data.get(index).toString();
			s += " ";
		}
		s += "]";
		return s;
	}
}

/*
class Main{
    public static void main(String[] args) {
        CircularVector cv = new CircularVector();
        cv.AddLast(10);
        cv.AddLast(20);
        cv.AddFirst(5);
        System.out.println("Circular Vector: " + cv.toString());
        System.out.println("First Element: " + cv.GetFirst());
        System.out.println("Last Element: " + cv.GetLast());
        cv.RemoveFirst();
        System.out.println("After removing first: " + cv.toString());
        cv.RemoveLast();
        System.out.println("After removing last: " + cv.toString());

        cv.AddLast(30);
        cv.AddLast(40);
        cv.AddLast(50);
        cv.AddLast(60);
        try {
            cv.AddLast(70);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
*/
