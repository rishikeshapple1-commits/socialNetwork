package dataStuctures;

/*
 * LinkedList.java
 * Rishikesh Soni
 * 25/12/2025
 *
 * Singly linked list.
 */

/**
 * Simple singly linked list.
 *
 * Supports adding/removing at ends, indexed access, membership checks, and a few
 * exercise helper operations such as {@link #fropple()} and {@link #addSorted(Comparable)}.
 */
public class LinkedList {

	/**
	 * Singly linked list node.
	 */
	private class ListElement {
		private Object el1;
		private ListElement el2;

		/**
		 * Creates a node.
		 *
		 * @param el element value
		 * @param nextElement next node
		 */
		public ListElement(Object el, ListElement nextElement) {
			el1 = el;
			el2 = nextElement;
		}

		/**
		 * Creates a node with no next pointer.
		 *
		 * @param el element value
		 */
		public ListElement(Object el) {
			this(el, null);
		}

		/**
		 * Returns the stored value.
		 *
		 * @return value
		 */
		public Object first() {
			return el1;
		}

		/**
		 * Returns the next node.
		 *
		 * @return next node
		 */
		public ListElement rest() {
			return el2;
		}

		/**
		 * Replaces the stored value.
		 *
		 * @param value new value
		 */
		public void setFirst(Object value) {
			el1 = value;
		}

		/**
		 * Replaces the next pointer.
		 *
		 * @param value new next node
		 */
		public void setRest(ListElement value) {
			el2 = value;
		}
	}

	/** Head node (null means empty list). */
	private ListElement head;

	/**
	 * Creates an empty list.
	 */
	public LinkedList() {
		head = null;
	}

	/**
	 * Returns the number of elements in the list.
	 *
	 * @return list size
	 */
	public int size() {
		int count = 0;
		ListElement current = head;
		while (current != null) {
			count++;
			current = current.rest();
		}
		return count;
	}

	/**
	 * Adds a value at the front of the list.
	 *
	 * @param o value to add
	 */
	public void addFirst(Object o) {
		head = new ListElement(o, head);
	}

	/**
	 * Adds a value at the end of the list.
	 *
	 * @param o value to add
	 */
	public void addLast(Object o) {
		if (head == null) {
			head = new ListElement(o);
			return;
		}

		ListElement current = head;
		while (current.rest() != null) {
			current = current.rest();
		}
		current.setRest(new ListElement(o));
	}

	/**
	 * Returns the first element.
	 *
	 * @return first element (no empty check)
	 */
	public Object getFirst() {
		return head.first();
	}

	/**
	 * Returns the last element.
	 *
	 * @return last element, or {@code null} if empty
	 */
	public Object getLast() {
		if (head == null) {
			return null;
		}

		ListElement current = head;
		while (current.rest() != null) {
			current = current.rest();
		}
		return current.first();
	}

	/**
	 * Returns the element at index {@code n}.
	 *
	 * @param n 0-based index (no bounds checking)
	 * @return element at index
	 */
	public Object get(int n) {
		ListElement d = head;
		while (n > 0) {
			d = d.rest();
			n--;
		}
		return d.first();
	}

	/**
	 * Sets the element at index {@code n} to {@code o}.
	 *
	 * @param n 0-based index (no bounds checking)
	 * @param o new value
	 */
	public void set(int n, Object o) {
		ListElement d = head;
		while (n > 0) {
			d = d.rest();
			n--;
		}
		d.setFirst(o);
	}

	/**
	 * Checks whether the list contains a value (using {@code equals}).
	 *
	 * @param o value to search for
	 * @return {@code true} if found
	 */
	public boolean contains(Object o) {
		ListElement current = head;
		while (current != null) {
			if (current.first().equals(o)) {
				return true;
			}
			current = current.rest();
		}
		return false;
	}

	/**
	 * Swaps adjacent elements pairwise.
	 *
	 * Example: (a b c d e) becomes (b a d c e).
	 */
	public void fropple() {
		ListElement current = head;
		while (current != null && current.rest() != null) {
			Object temp = current.first();
			current.setFirst(current.rest().first());
			current.rest().setFirst(temp);
			current = current.rest().rest();
		}
	}

	/**
	 * Inserts an element into the list while maintaining sorted order.
	 *
	 * @param o element to insert
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addSorted(Comparable o) {
		if (head == null || ((Comparable) head.first()).compareTo(o) > 0) {
			addFirst(o);
			return;
		}

		ListElement current = head;
		while (current.rest() != null && ((Comparable) current.rest().first()).compareTo(o) <= 0) {
			current = current.rest();
		}
		current.setRest(new ListElement(o, current.rest()));
	}

	/**
	 * Appends the contents of {@code list2} to the end of this list.
	 *
	 * This operation links the underlying nodes; it does not copy them.
	 *
	 * @param list2 list to append
	 */
	public void append(LinkedList list2) {
		if (head == null) {
			head = list2.head;
			return;
		}

		ListElement current = head;
		while (current.rest() != null) {
			current = current.rest();
		}
		current.setRest(list2.head);
	}

	/**
	 * Removes the first element if it exists.
	 */
	public void removeFirst() {
		if (head != null) {
			head = head.rest();
		}
	}

	/**
	 * Returns a string representation of the list.
	 *
	 * @return string form
	 */
	@Override
	public String toString() {
		String s = "(";
		ListElement d = head;
		while (d != null) {
			s += d.first().toString();
			s += " ";
			d = d.rest();
		}
		s += ")";
		return s;
	}

}

/*
class Main {
	public static void main(String[] args) {
		LinkedList list = new LinkedList();

		// Demo data for testing
		for (int i = 4; i > 0; i--) {
			list.addFirst(i);
		}
		System.out.println("LinkedList: " + list);

		list.addLast(5);
		System.out.println("After addLast(5): " + list);

		System.out.println("First: " + list.getFirst());
		System.out.println("Last: " + list.getLast());

		list.set(2, 10);
		System.out.println("After set(2, 10): " + list);

		System.out.println("Contains 3: " + list.contains(3));
		System.out.println("Contains 6: " + list.contains(6));

		list.fropple();
		System.out.println("After fropple: " + list);

		LinkedList list2 = new LinkedList();
		for (int i = 4; i <= 6; i++) {
			list2.addLast(i);
		}
		System.out.println("Second list: " + list2);

		list.append(list2);
		System.out.println("After append: " + list);

		LinkedList sortedList = new LinkedList();
		sortedList.addSorted(5);
		sortedList.addSorted(2);
		sortedList.addSorted(8);
		sortedList.addSorted(1);
		sortedList.addSorted(3);
		System.out.println("Sorted list: " + sortedList);
	}
}
*/

