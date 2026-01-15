package dataStuctures;

/*
 * PriorityQueue.java
 * Rishikesh Soni
 * 04/12/2025
 *
 * Priority queue backed by a binary search tree. Question 10 of exercises 10
 */

/**
 * Priority queue backed by a {@link Tree}.
 * Stores (element, priority) pairs and returns/removes the item with the
 * smallest priority value first.
 */
@SuppressWarnings({"rawtypes"})
public class PriorityQueue {

	/**
	 * Internal (element, priority) pair stored in the backing {@link Tree}.
	 */
	private class PriorityPair implements Comparable {
		/** Element stored in the queue. */
		private Object element;
		/** Priority assigned to the element (smaller values pop first). */
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
		 * Compares by priority only.
		 *
		 * @param o other pair
		 * @return negative/zero/positive based on priority ordering
		 */
		public int compareTo(Object o) {
			PriorityPair p2 = (PriorityPair) o;
			return Integer.compare(this.priority, p2.priority);
		}

		/**
		 * Returns a string representation "(element,priority)".
		 *
		 * @return string
		 */
		public String toString() {
			return "(" + element + "," + priority + ")";
		}
	}

	/**
	 * Backing data structure holding {@link PriorityPair} objects.
	 */
	private Tree data;

	/**
	 * Creates an empty priority queue.
	 */
	public PriorityQueue() {
		data = new Tree();
	}

	/**
	 * Inserts an element with the given priority.
	 *
	 * @param o element to add
	 * @param priority priority value (smaller values are returned first)
	 */
	public void push(Object o, int priority) {
		data.insert(new PriorityPair(o, priority));
	}

	/**
	 * Removes and returns the element with the smallest priority.
	 *
	 * @return the element with the smallest priority, or {@code null} if empty
	 */
	public Object pop() {
		Object min = data.removeMin();
		if (min == null) {
			return null;
		}
		return ((PriorityPair) min).element;
	}

	/**
	 * Returns the element with the smallest priority without removing it.
	 *
	 * @return the top element, or {@code null} if empty
	 */
	public Object top() {
		Comparable min = data.min();
		if (min == null) {
			return null;
		}
		return ((PriorityPair) min).element;
	}
}
/*
class Main{
    public static void main(String[] args) {
        PriorityQueue pq = new PriorityQueue();

        // Test push operation
        pq.push("Task1", 2);
        pq.push("Task2", 1);
        pq.push("Task3", 3);

        System.out.println("Top element: " + pq.top()); 
        System.out.println("Popped element: " + pq.pop()); 
        System.out.println("Top element after pop: " + pq.top());     
    }
}   */
