package dataStuctures;

/*
 * Queue.java
 * Rishikesh Soni
 * 26/11/2025
 *
 * Simple FIFO queue backed by Vector.
 */

/**
 * Simple FIFO (first-in, first-out) queue backed by {@link Vector}.
 *
 * New elements are pushed at the end, and popping removes the front element.
 */
public class Queue {

	/** Underlying storage for the queue. */
	private Vector data;

	/**
	 * Creates an empty queue.
	 */
	public Queue() {
		data = new Vector(10);
	}

	/**
	 * Adds an element to the back of the queue.
	 *
	 * @param o element to add
	 */
	public void push(Object o) {
		data.addLast(o);
	}

	/**
	 * Removes and returns the front element.
	 *
	 * @return the front element, or {@code null} if the queue is empty
	 */
	public Object pop() {
		if (empty()) {
			return null;
		}
		Object frontElement = data.get(0);
		data.removeFirst();
		return frontElement;
	}

	/**
	 * Returns the front element without removing it.
	 *
	 * @return the front element, or {@code null} if the queue is empty
	 */
	public Object top() {
		if (empty()) {
			return null;
		}
		return data.get(0);
	}

	/**
	 * Returns the number of elements currently in the queue.
	 *
	 * @return queue size
	 */
	public int size() {
		return data.size();
	}

	/**
	 * Indicates whether the queue is empty.
	 *
	 * @return {@code true} if empty
	 */
	public boolean empty() {
		return size() == 0;
	}

	/**
	 * Returns a string representation of the queue contents.
	 *
	 * @return string form of the underlying data
	 */
	@Override
	public String toString() {
		return data.toString();
	}
}
/* 
class Main{
    public static void main(String[] args) {
        Queue queue = new Queue();
        // Test push operation
        queue.push(10);
        queue.push(20);
        queue.push(30);
        System.out.println("Front element: " + queue.top());
        System.out.println("Queue size: " + queue.size()); 

        // Testing pop op
        System.out.println("Popped element: " + queue.pop()); 
        System.out.println("Front element after pop: " + queue.top()); 
        System.out.println("Queue size after pop: " + queue.size()); 

        // Testing empty
        queue.pop();
        queue.pop();
        System.out.println("Is queue empty? " + queue.empty()); 
        //Q1 ends here
        //Q2: Time complexity of push and pop is O(1)
        //Q2 ends here
        //Q4: 
        System.out.println("Time Complexity Comparison:");
        System.out.println("Operation\tVector-Based Queue\tLinked List-Based Queue");
        System.out.println("Push\t\tO(1)\t\t\tO(1)");
        System.out.println("Pop\t\tO(n)\t\t\tO(1)");
        //Q4 ends here
    }
}
*/