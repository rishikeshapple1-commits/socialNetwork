package dataStuctures;

/*
 * PriorityQueueLinkedList.java
 * Rishikesh Soni
 * 3/12/2025
 *
 * Priority queue backed by a sorted linked list.
 */

/**
 * Priority queue backed by a sorted {@link LinkedList}.
 *
 * Stores (element, priority) pairs and returns/removes the item with the
 * smallest priority value first.
 */
@SuppressWarnings({"rawtypes"})
public class PriorityQueueLinkedList {

	/**
	 * Internal (element, priority) pair stored in the backing {@link LinkedList}.
	 */
	private class PriorityPair implements Comparable {
		private Object element;
		private int priority;

		/**
		 * Creates a new pair.
		 *
		 * @param element element to store
		 * @param priority priority value (smaller pops first)
		 */
		public PriorityPair(Object element, int priority) {
			this.element = element;
			this.priority = priority;
		}

		/**
		 * Compares pairs by priority only.
		 *
		 * @param o other pair
		 * @return negative/zero/positive based on priority ordering
		 */
		public int compareTo(Object o) {
			PriorityPair p2 = (PriorityPair) o;
			return Integer.compare(this.priority, p2.priority);
		}

		@Override
		public String toString() {
			return "(" + element + "," + priority + ")";
		}
	}

	private LinkedList data;

	/**
	 * Creates an empty priority queue.
	 */
	public PriorityQueueLinkedList() {
		data = new LinkedList();
	}

	/**
	 * Inserts an element with the given priority.
	 *
	 * @param o element to add
	 * @param priority priority value (smaller values are returned first)
	 */
	public void push(Object o, int priority) {
		PriorityPair pair = new PriorityPair(o, priority);
		data.addSorted(pair);
	}

	/**
	 * Removes and returns the element with the smallest priority.
	 *
	 * @return the element with the smallest priority, or {@code null} if empty
	 */
	public Object pop() {
		if (data.size() == 0) {
			return null;
		}
		PriorityPair highestPriorityPair = (PriorityPair) data.getFirst();
		data.removeFirst();
		return highestPriorityPair.element;
	}

	/**
	 * Returns the element with the smallest priority without removing it.
	 *
	 * @return the top element, or {@code null} if empty
	 */
	public Object top() {
		if (data.size() == 0) {
			return null;
		}
		PriorityPair highestPriorityPair = (PriorityPair) data.getFirst();
		return highestPriorityPair.element;
	}

}
/* 
class Main {
	public static void main(String[] args) {
		PriorityQueueLinkedList pq = new PriorityQueueLinkedList();

		pq.push("Task1", 2);
		pq.push("Task2", 1);
		pq.push("Task3", 3);

		System.out.println("Top element: " + pq.top());
		System.out.println("Popped element: " + pq.pop());
		System.out.println("Top element after pop: " + pq.top());
	}
}               
*/