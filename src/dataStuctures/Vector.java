package dataStuctures;

/*
 * Vector.java
 * Rishikesh Soni
 * Last Update 25/11/2025
 *
 * Dynamic array.
 */

/**
 * Simple dynamic array that stores {@code Object} references.
 */
public class Vector {

	/** Backing array. Only indices {@code 0..count-1} are valid elements. */
	private Object[] data;

	/** Number of elements currently stored in the vector. */
	private int count;

	/**
	 * Creates a vector with an initial capacity.
	 *
	 * @param capacity initial capacity of the backing array
	 */
	public Vector(int capacity) {
		data = new Object[capacity];
		count = 0;
	}

	/**
	 * Returns the number of elements currently stored.
	 *
	 * @return number of elements
	 */
	public int size() {
		return count;
	}

	/**
	 * Indicates whether this vector is empty.
	 *
	 * @return {@code true} if size is 0
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the element at a given index.
	 *
	 * @param index index to read 
	 * @return element at index
	 */
	public Object get(int index) {
		return data[index];
	}

	/**
	 * Replaces the element at a given index.
	 *
	 * @param index index to write
	 * @param obj new value
	 */
	public void set(int index, Object obj) {
		data[index] = obj;
	}

	/**
	 * Checks whether the vector contains the given object by reference equality.
	 *
	 * @param obj object to find
	 * @return {@code true} if the same reference exists in the vector
	 */
	public boolean contains(Object obj) {
		for (int i = 0; i < count; i++) {
			if (data[i] == obj) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Inserts an element at the front of the vector.
	 *
	 * @param item element to insert
	 */
	public void addFirst(Object item) {
		extendCapacity();
		for (int i = count; i > 0; i--) {
			data[i] = data[i - 1];
		}
		count++;
		data[0] = item;
	}

	/**
	 * Returns the current capacity of the  array.
	 *
	 * @return array length
	 */
	public int capacity() {
		return data.length;
	}

	/**
	 * Appends an element to the end of the vector.
	 *
	 * @param o element to append
	 */
	public void addLast(Object o) {
		extendCapacity();
		data[count++] = o;
	}
	
	/*
	public boolean binarySearch(Object key)
	{
	int start = 0;
	int end = count - 1;
	while(start <= end)
	{
		int middle = (start + end + 1) / 2;
		if(key < data[middle]) end = middle -1;
		else if(key > data[middle]) start = middle + 1;
		else return true;
	}
	return false;
	}
	*/

	public Object getFirst() {
		// add your code
		return data[0];
	}

	/**
	 * Returns the last element.
	 *
	 * @return last element (no empty check)
	 */
	public Object getLast() {
		return data[count - 1];
	}

	/**
	 * Removes the last element.
*
*	 * If empty, prints an error message.
	 */
	public void removeLast() {
		if (count > 0) {
			count--;
		} else {
			System.out.println("Vector is empty");
		}
	}

	/**
	 * Removes the first element by shifting elements to the left.
*
*	 * If empty, prints an error message.
	 */
	public void removeFirst() {
		if (count > 0) {
			for (int i = 0; i < count - 1; i++) {
				data[i] = data[i + 1];
			}
			count--;
		} else {
			System.out.println("Vector is empty");
		}
	}

	/**
	 * Reverses the order of elements in-place.
	 */
	public void reverse() {
		for (int i = 0; i < count / 2; i++) {
			Object temp = data[i];
			data[i] = data[count - 1 - i];
			data[count - 1 - i] = temp;
		}
	}
	// extendCapacity is private because it is a helper method that is only intended to be used within the Vector class itself.

	/**
	 * Ensures the backing array has room for at least one more element.
	 */
	private void extendCapacity() {
		if (count >= data.length) {
			Object[] Data2 = new Object[data.length * 2];
			for (int i = 0; i < count; i++) {
				Data2[i] = data[i];
			}
			data = Data2;
		}
	}

	/**
	 * Returns a string representation of the vector contents.
	 *
	 * @return string form
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (int i = 0; i < count; i++) {
			sb.append(data[i]);
			if (i < count - 1) {
				sb.append(" ");
			}
		}
		sb.append(" ]");
		return sb.toString();
	}

	/**
	 * Returns a new vector where each element from this vector appears twice,
	 * in the same order.
	 *
	 * @return repeated vector
	 */
	public Vector repeat() {
		Vector newVector = new Vector(this.size() * 2);
		for (int i = 0; i < this.size(); i++) {
			Object element = this.get(i);
			newVector.addLast(element);
			newVector.addLast(element);
		}
		return newVector;
	}

	/**
	 * Returns a new vector that interleaves this vector with another.
*
*	 * The new vector alternates elements from each vector until one runs out,
	 * then appends the remaining tail of the longer vector.
	 *
	 * @param v2 other vector
	 * @return interleaved vector
	 */
	public Vector interleave(Vector v2) {
		int newSize = this.size() + v2.size();
		System.out.println("Size of first vector: " + this.size());
		System.out.println("Size of second vector: " + v2.size());
		Vector interleavedVector = new Vector(newSize);
		int minSize = Math.min(this.size(), v2.size());
		for (int i = 0; i < minSize; i++) {
			interleavedVector.addLast(this.get(i));
			interleavedVector.addLast(v2.get(i));
		}
		// If one vector is longer, add the remaining elements
		if (this.size() > minSize) {
			for (int i = minSize; i < this.size(); i++) {
				interleavedVector.addLast(this.get(i));
			}
		} else if (v2.size() > minSize) {
			for (int i = minSize; i < v2.size(); i++) {
				interleavedVector.addLast(v2.get(i));
			}
		}

		return interleavedVector;
	}
}

/* 
	class Main {
		public static void main(String[] args) {
			Vector vec = new Vector(101);
			//for testing purpose
			vec.addLast(1);	
			vec.addLast(2);
			vec.addLast(3);	
			vec.addFirst(0);
			System.out.println("First Element: " + vec.getFirst());	
			System.out.println("Last Element: " + vec.getLast());
			vec.reverse();
			System.out.println("After Reversing:");	
			for(int i=0; i<vec.size(); i++) {
				System.out.println(vec.get(i));
			}
			//make the vecotor empty
		while(!vec.isEmpty()) {
				vec.removeLast();
			}
				
// Q1 adding the elements 1 to 100 to it, getting the size of the vector, verify whether the vector contains the elements 6 and 101 
			for(int i=1; i<=100; i++) {
				vec.addLast(i);
			}
			System.out.println("Size of vector: " + vec.size());
			System.out.println("Vector contains 6: " + vec.contains(6));
			System.out.println("Vector contains 101: " + vec.contains(101));
			//Q1 ends here
			//Q2 is already implemented
			// Q3
			System.out.println("Vector: " + vec.toString());
			//Q3 ends here
			//Q4, Q5,Q6 is also implemented already
  		    //Q7 repeat function
			Vector repeatedVec = vec.repeat();
			System.out.println("Repeated Vector: " + repeatedVec.toString());
			// time complexity of "repeat" method is O(n), where n is the size of the original vector because the method iterates through each element of the original vector once to add it twice to the new vector.
			//Q7 ends here
			//Q8 interleave function
			Vector v1 = new Vector(4);
			v1.addLast(1);
			v1.addLast(2);
			v1.addLast(3);
			v1.addLast(7);

			Vector v2 = new Vector(3);
			v2.addLast(4);
			v2.addLast(5);
			v2.addLast(6);
			Vector interleavedVec = v1.interleave(v2);
			System.out.println("Interleaved Vector: " + interleavedVec.toString());
			// time complexity of "interleave" method is O(m + n), where m and n are the sizes of the two vectors being interleaved because the method iterates through each element of both vectors once to add them to the new vector.
			//Q8 ends here
            //Q9 is already implemented in extendCapacity method.
      		// checking extendCapacity method
			Vector smallVec = new Vector(2);
			smallVec.addLast(10);
			smallVec.addLast(20);
			System.out.println("Before adding more elements: " + smallVec.toString());
			smallVec.addLast(30); // triggering capacity extension
			smallVec.addLast(40);
			System.out.println("After adding more elements: " + smallVec.toString());
			//Q9 ends here
		}
	
	}
*/
