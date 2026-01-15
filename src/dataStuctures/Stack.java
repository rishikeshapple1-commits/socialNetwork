package dataStuctures;

/*
 * Stack.java
 * Rishikesh Soni
 * 25/11/2025
 *
 * Simple LIFO stack backed by Vector.
 */

/**
 * Simple LIFO (last-in, first-out) stack backed by {@link Vector}.
 *
 * New elements are pushed at the end, and popping removes the most recently
 * pushed element.
 */
public class Stack {

	/** Underlying storage for the stack. */
	private Vector data;

	/**
	 * Creates an empty stack.
	 */
	public Stack() {
		data = new Vector(10);
	}

	/**
	 * Pushes an element onto the top of the stack.
	 *
	 * @param o element to push
	 */
	public void push(Object o) {
		data.addLast(o);
	}

	/**
	 * Removes and returns the top element.
	 *
	 * @return top element, or {@code null} if the stack is empty
	 */
	public Object pop() {
		if (empty()) {
			return null;
		}
		Object topElement = data.get(data.size() - 1);
		data.set(data.size() - 1, null);
		return topElement;
	}

	/**
	 * Returns the top element without removing it.
	 *
	 * @return top element, or {@code null} if the stack is empty
	 */
	public Object top() {
		if (empty()) {
			return null;
		}
		return data.get(data.size() - 1);
	}

	/**
	 * Returns the number of elements currently on the stack.
	 *
	 * @return stack size
	 */
	public int size() {
		return data.size();
	}

	/**
	 * Indicates whether the stack is empty.
	 *
	 * @return {@code true} if empty
	 */
	public boolean empty() {
		return size() == 0;
	}

	/**
	 * Returns a string representation of the stack contents.
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
        Stack stack = new Stack();
        // Test push operation
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("Top element: " + stack.top());
        System.out.println("Stack size: " + stack.size()); 

        // Testing pop op
        System.out.println("Popped element: " + stack.pop()); 
        System.out.println("Top element after pop: " + stack.top()); 
        System.out.println("Stack size after pop: " + stack.size()); 

        // Testing empty
        stack.pop();
        stack.pop();
        System.out.println("Is stack empty? " + stack.empty()); 
        //Q1 ends here
        //Q2: Time complexity of push and pop is O(1)
        //Q2 ends here
        
    }
}
*/