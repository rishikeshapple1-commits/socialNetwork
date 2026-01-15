package dataStuctures;

/*
 * DoubleLinkedList.java
 * Rishikesh Soni
 * 29/11/2025
 *
 * Doubly linked list.
 */

/**
 * Doubly linked list implementation.
 *
 * Supports adding/removing at both ends and reverse traversal via links.
 */
public class DoubleLinkedList {

	/**
	 * One element in the doubly linked list.
	 */
	private class DoubleLinkedListElement {
		private Object data;
		private DoubleLinkedListElement nextElement;
		private DoubleLinkedListElement previousElement;

		/**
		 * Creates an element and links it into neighbors.
		 *
		 * @param v value to store
		 * @param next next element
		 * @param previous previous element
		 */
		public DoubleLinkedListElement(Object v, DoubleLinkedListElement next, DoubleLinkedListElement previous) {
			data = v;
			nextElement = next;
			previousElement = previous;
			if (nextElement != null) {
				nextElement.previousElement = this;
			}
			if (previousElement != null) {
				previousElement.nextElement = this;
			}
		}

		/**
		 * Creates an isolated element.
		 *
		 * @param v value to store
		 */
		@SuppressWarnings("unused")
		public DoubleLinkedListElement(Object v) {
			this(v, null, null);
		}

		/** @return previous element */
		public DoubleLinkedListElement previous() {
			return previousElement;
		}

		/** @return stored value */
		public Object value() {
			return data;
		}

		/** @return next element */
		public DoubleLinkedListElement next() {
			return nextElement;
		}

		/**
		 * Updates next pointer.
		 *
		 * @param value new next element
		 */
		public void setNext(DoubleLinkedListElement value) {
			nextElement = value;
		}

		/**
		 * Updates previous pointer.
		 *
		 * @param value new previous element
		 */
		public void setPrevious(DoubleLinkedListElement value) {
			previousElement = value;
		}
	}

	/** Number of elements in the list. */
	private int count;

	/** Head element (null if empty). */
	private DoubleLinkedListElement head;

	/** Tail element (null if empty). */
	private DoubleLinkedListElement tail;

	/**
	 * Creates an empty doubly linked list.
	 */
	public DoubleLinkedList() {
		head = null;
		tail = null;
		count = 0;
	}

	/**
	 * Returns number of elements.
	 *
	 * @return list size
	 */
	public int size() {
		return count;
	}

	/**
	 * Returns the first element.
	 *
	 * @return first element, or {@code null} if empty
	 */
	public Object getFirst() {
		if (head == null) {
			return null;
		}
		return head.value();
	}

	/**
	 * Returns the last element.
	 *
	 * @return last element, or {@code null} if empty
	 */
	public Object getLast() {
		if (tail == null) {
			return null;
		}
		return tail.value();
	}

	/**
	 * Adds a value to the front.
	 *
	 * @param value value to add
	 */
	public void addFirst(Object value) {
		head = new DoubleLinkedListElement(value, head, null);
		if (tail == null) {
			tail = head;
		}
		count++;
	}

	/**
	 * Adds a value to the end.
	 *
	 * @param value value to add
	 */
	public void addLast(Object value) {
		tail = new DoubleLinkedListElement(value, null, tail);
		if (head == null) {
			head = tail;
		}
		count++;
	}

	/**
	 * Removes the first element if it exists.
	 */
	public void removeFirst() {
		if (head == null) {
			return;
		}

		head = head.next();
		if (head == null) {
			tail = null;
		} else {
			head.setPrevious(null);
		}
		count--;
	}

	/**
	 * Removes the last element if it exists.
	 */
	public void removeLast() {
		if (tail == null) {
			return;
		}

		tail = tail.previous();
		if (tail == null) {
			head = null;
		} else {
			tail.setNext(null);
		}
		count--;
	}

	/**
	 * Reverses the list in-place by swapping next/previous pointers.
	 */
	public void reverse() {
		DoubleLinkedListElement current = head;
		DoubleLinkedListElement temp = null;
		tail = head;
		while (current != null) {
			temp = current.previous();
			current.setPrevious(current.next());
			current.setNext(temp);
			current = current.previous();
		}
		if (temp != null) {
			head = temp.previous();
		}
	}

	/**
	 * Returns a string representation from head to tail.
	 *
	 * @return string form
	 */
	@Override
	public String toString() {
		String s = "(";
		DoubleLinkedListElement d = head;
		while (d != null) {
			s += d.value() + " ";
			d = d.next();
		}
		s += ")";
		return s;
	}

	/**
	 * Prints the list from tail to head.
	 */
	public void printReverse() {
		System.out.print("(");
		DoubleLinkedListElement d = tail;
		while (d != null) {
			System.out.print(d.value() + " ");
			d = d.previous();
		}
		System.out.println(")");
	}

	/**
	 * Simple manual test/demo.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {
		DoubleLinkedList dll = new DoubleLinkedList();

		dll.addLast(1);
		dll.addLast(2);
		dll.addLast(3);
		dll.addLast(4);
		System.out.println("DoubleLinkedList: " + dll);

		System.out.print("Reverse print: ");
		dll.printReverse();

		dll = new DoubleLinkedList();
		dll.addLast(1);
		System.out.println("Before removeFirst: " + dll);
		dll.removeFirst();
		System.out.println("After removeFirst: " + dll);

		dll.addLast(1);
		dll.addLast(2);
		dll.addLast(3);
		System.out.println("Before removeLast: " + dll);
		dll.removeLast();
		System.out.println("After removeLast: " + dll);

		dll = new DoubleLinkedList();
		dll.addLast(1);
		dll.addLast(2);
		dll.addLast(3);
		dll.addLast(4);
		System.out.println("Before reverse: " + dll);
		dll.reverse();
		System.out.println("After reverse: " + dll);
	}
}