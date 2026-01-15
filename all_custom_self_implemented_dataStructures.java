// Contents of all Java files from src/dataStuctures

// --- CircularVector.java ---
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

// --- Dictionary.java ---
package dataStuctures;

/*
 * Dictionary.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of Dictionary ADT using a hash table.
 */

/**
 * Dictionary implementation using a hash table with separate chaining.
 */
public class Dictionary {
	/** Table of lists to store elements. */
	private LinkedList[] table;
	/** Number of elements in the dictionary. */
	private int size;

	/**
	 * Creates an empty dictionary with specified capacity.
	 *
	 * @param capacity initial capacity
	 */
	public Dictionary(int capacity) {
		table = new LinkedList[capacity];
		for (int i = 0; i < capacity; i++) {
			table[i] = new LinkedList();
		}
		size = 0;
	}

	/**
	 * Returns the number of elements in the dictionary.
	 *
	 * @return number of elements
	 */
	public int size() {
		return size;
	}

	/**
	 * Computes the hash code for a given key.
	 *
	 * @param key the key
	 * @return hash code
	 */
	private int hash(Object key) {
		return (key.hashCode() & 0x7FFFFFFF) % table.length;
	}

	/**
	 * Inserts a key-value pair into the dictionary.
	 *
	 * @param key key to insert
	 * @param value value to insert
	 */
	public void insert(Object key, Object value) {
		int index = hash(key);
		table[index].AddLast(new Entry(key, value));
		size++;
	}

	/**
	 * Searches for a value by key.
	 *
	 * @param key the key
	 * @return value associated with the key, or null if not found
	 */
	public Object search(Object key) {
		int index = hash(key);
		LinkedList list = table[index];
		for (Object obj : list) {
			Entry entry = (Entry) obj;
			if (entry.key.equals(key)) {
				return entry.value;
			}
		}
		return null;
	}

	/**
	 * Removes a key-value pair from the dictionary.
	 *
	 * @param key the key
	 */
	public void remove(Object key) {
		int index = hash(key);
		LinkedList list = table[index];
		list.Remove(key);
		size--;
	}

	/**
	 * Entry class for key-value pairs.
	 */
	private class Entry {
		public Object key;
		public Object value;

		public Entry(Object key, Object value) {
			this.key = key;
			this.value = value;
		}
	}
}

// --- DictionaryTree.java ---
package dataStuctures;

/*
 * DictionaryTree.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of Dictionary ADT using a tree.
 */

/**
 * Dictionary implementation using a binary search tree.
 */
public class DictionaryTree {
	/** Root of the tree. */
	private TreeNode root;
	/** Number of elements in the dictionary. */
	private int size;

	/**
	 * Creates an empty dictionary.
	 */
	public DictionaryTree() {
		root = null;
		size = 0;
	}

	/**
	 * Returns the number of elements in the dictionary.
	 *
	 * @return number of elements
	 */
	public int size() {
		return size;
	}

	/**
	 * Searches for a value by key.
	 *
	 * @param key the key
	 * @return value associated with the key, or null if not found
	 */
	public Object search(Object key) {
		TreeNode node = findNode(root, key);
		if (node == null) {
			return null;
		}
		return node.value;
	}

	/**
	 * Inserts a key-value pair into the dictionary.
	 *
	 * @param key key to insert
	 * @param value value to insert
	 */
	public void insert(Object key, Object value) {
		root = insertNode(root, key, value);
		size++;
	}

	/**
	 * Removes a key-value pair from the dictionary.
	 *
	 * @param key the key
	 */
	public void remove(Object key) {
		root = removeNode(root, key);
		size--;
	}

	/**
	 * Finds a node by key.
	 *
	 * @param node current node
	 * @param key key to find
	 * @return node with the key, or null if not found
	 */
	private TreeNode findNode(TreeNode node, Object key) {
		if (node == null) {
			return null;
		}
		int cmp = key.hashCode() - node.key.hashCode();
		if (cmp == 0) {
			return node;
		} else if (cmp < 0) {
			return findNode(node.left, key);
		} else {
			return findNode(node.right, key);
		}
	}

	/**
	 * Inserts a node.
	 *
	 * @param node current node
	 * @param key key to insert
	 * @param value value to insert
	 * @return new node
	 */
	private TreeNode insertNode(TreeNode node, Object key, Object value) {
		if (node == null) {
			return new TreeNode(key, value);
		}
		int cmp = key.hashCode() - node.key.hashCode();
		if (cmp == 0) {
			node.value = value;
		} else if (cmp < 0) {
			node.left = insertNode(node.left, key, value);
		} else {
			node.right = insertNode(node.right, key, value);
		}
		return node;
	}

	/**
	 * Removes a node.
	 *
	 * @param node current node
	 * @param key key to remove
	 * @return new node
	 */
	private TreeNode removeNode(TreeNode node, Object key) {
		if (node == null) {
			return null;
		}
		int cmp = key.hashCode() - node.key.hashCode();
		if (cmp == 0) {
			if (node.left == null) {
				return node.right;
			}
			if (node.right == null) {
				return node.left;
			}
			TreeNode minNode = findMin(node.right);
			node.key = minNode.key;
			node.value = minNode.value;
			node.right = removeNode(node.right, minNode.key);
		} else if (cmp < 0) {
			node.left = removeNode(node.left, key);
		} else {
			node.right = removeNode(node.right, key);
		}
		return node;
	}

	/**
	 * Finds the minimum node.
	 *
	 * @param node current node
	 * @return minimum node
	 */
	private TreeNode findMin(TreeNode node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	/**
	 * Tree node class.
	 */
	private class TreeNode {
		public Object key;
		public Object value;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(Object key, Object value) {
			this.key = key;
			this.value = value;
			left = null;
			right = null;
		}
	}
}

// --- DoubleLinkedList.java ---
package dataStuctures;

/*
 * DoubleLinkedList.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a double-linked list.
 */

/**
 * Double-linked list implementation.
 */
public class DoubleLinkedList {
	/** Head of the list. */
	private Node head;
	/** Tail of the list. */
	private Node tail;
	/** Number of elements in the list. */
	private int size;

	/**
	 * Creates an empty list.
	 */
	public DoubleLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Returns the number of elements in the list.
	 *
	 * @return number of elements
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds an element to the front of the list.
	 *
	 * @param element element to add
	 */
	public void AddFirst(Object element) {
		Node newNode = new Node(element);
		if (size == 0) {
			head = newNode;
			tail = newNode;
		} else {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		size++;
	}

	/**
	 * Adds an element to the end of the list.
	 *
	 * @param element element to add
	 */
	public void AddLast(Object element) {
		Node newNode = new Node(element);
		if (size == 0) {
			head = newNode;
			tail = newNode;
		} else {
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
		}
		size++;
	}

	/**
	 * Returns the first element.
	 *
	 * @return first element, or {@code null} if empty
	 */
	public Object GetFirst() {
		if (size == 0) {
			return null;
		}
		return head.element;
	}

	/**
	 * Returns the last element.
	 *
	 * @return last element, or {@code null} if empty
	 */
	public Object GetLast() {
		if (size == 0) {
			return null;
		}
		return tail.element;
	}

	/**
	 * Removes the first element if it exists.
	 */
	public void RemoveFirst() {
		if (size > 0) {
			head = head.next;
			if (head != null) {
				head.prev = null;
			}
			size--;
		}
	}

	/**
	 * Removes the last element if it exists.
	 */
	public void RemoveLast() {
		if (size > 0) {
			tail = tail.prev;
			if (tail != null) {
				tail.next = null;
			}
			size--;
		}
	}

	/**
	 * Returns a string representation of the list.
	 *
	 * @return string form
	 */
	@Override
	public String toString() {
		String s = "[";
		Node current = head;
		while (current != null) {
			s += current.element.toString();
			s += " ";
			current = current.next;
		}
		s += "]";
		return s;
	}

	/**
	 * Node class for double-linked list.
	 */
	private class Node {
		public Object element;
		public Node next;
		public Node prev;

		public Node(Object element) {
			this.element = element;
			next = null;
			prev = null;
		}
	}
}

// --- Graph.java ---
package dataStuctures;

/*
 * Graph.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a graph using an adjacency list.
 */

/**
 * Graph implementation using an adjacency list.
 */
public class Graph {
	/** Adjacency list for the graph. */
	private LinkedList[] adjList;
	/** Number of vertices in the graph. */
	private int numVertices;

	/**
	 * Creates an empty graph with specified number of vertices.
	 *
	 * @param numVertices number of vertices
	 */
	public Graph(int numVertices) {
		this.numVertices = numVertices;
		adjList = new LinkedList[numVertices];
		for (int i = 0; i < numVertices; i++) {
			adjList[i] = new LinkedList();
		}
	}

	/**
	 * Adds an edge to the graph.
	 *
	 * @param source source vertex
	 * @param destination destination vertex
	 */
	public void addEdge(int source, int destination) {
		adjList[source].AddLast(destination);
		adjList[destination].AddLast(source);
	}

	/**
	 * Removes an edge from the graph.
	 *
	 * @param source source vertex
	 * @param destination destination vertex
	 */
	public void removeEdge(int source, int destination) {
		adjList[source].Remove(destination);
		adjList[destination].Remove(source);
	}

	/**
	 * Returns the adjacency list for a vertex.
	 *
	 * @param vertex the vertex
	 * @return adjacency list
	 */
	public LinkedList getAdjacencyList(int vertex) {
		return adjList[vertex];
	}

	/**
	 * Returns the number of vertices in the graph.
	 *
	 * @return number of vertices
	 */
	public int getNumVertices() {
		return numVertices;
	}
}

// --- LinkedList.java ---
package dataStuctures;

/*
 * LinkedList.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a singly-linked list.
 */

/**
 * Singly-linked list implementation.
 */
public class LinkedList {
	/** Head of the list. */
	private Node head;
	/** Number of elements in the list. */
	private int size;

	/**
	 * Creates an empty list.
	 */
	public LinkedList() {
		head = null;
		size = 0;
	}

	/**
	 * Returns the number of elements in the list.
	 *
	 * @return number of elements
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds an element to the front of the list.
	 *
	 * @param element element to add
	 */
	public void AddFirst(Object element) {
		Node newNode = new Node(element);
		newNode.next = head;
		head = newNode;
		size++;
	}

	/**
	 * Adds an element to the end of the list.
	 *
	 * @param element element to add
	 */
	public void AddLast(Object element) {
		Node newNode = new Node(element);
		if (head == null) {
			head = newNode;
		} else {
			Node current = head;
			while (current.next != null) {
				current = current.next;
			}
			current.next = newNode;
		}
		size++;
	}

	/**
	 * Returns the first element.
	 *
	 * @return first element, or {@code null} if empty
	 */
	public Object GetFirst() {
		if (head == null) {
			return null;
		}
		return head.element;
	}

	/**
	 * Returns the last element.
	 *
	 * @return last element, or {@code null} if empty
	 */
	public Object GetLast() {
		if (head == null) {
			return null;
		}
		Node current = head;
		while (current.next != null) {
			current = current.next;
		}
		return current.element;
	}

	/**
	 * Removes the first element if it exists.
	 */
	public void RemoveFirst() {
		if (head != null) {
			head = head.next;
			size--;
		}
	}

	/**
	 * Removes the last element if it exists.
	 */
	public void RemoveLast() {
		if (head != null) {
			if (head.next == null) {
				head = null;
			} else {
				Node current = head;
				while (current.next.next != null) {
					current = current.next;
				}
				current.next = null;
			}
			size--;
		}
	}

	/**
	 * Removes an element.
	 *
	 * @param element element to remove
	 */
	public void Remove(Object element) {
		if (head == null) {
			return;
		}
		if (head.element.equals(element)) {
			head = head.next;
			size--;
			return;
		}
		Node current = head;
		while (current.next != null) {
			if (current.next.element.equals(element)) {
				current.next = current.next.next;
				size--;
				return;
			}
			current = current.next;
		}
	}

	/**
	 * Returns a string representation of the list.
	 *
	 * @return string form
	 */
	@Override
	public String toString() {
		String s = "[";
		Node current = head;
		while (current != null) {
			s += current.element.toString();
			s += " ";
			current = current.next;
		}
		s += "]";
		return s;
	}

	/**
	 * Node class for singly-linked list.
	 */
	private class Node {
		public Object element;
		public Node next;

		public Node(Object element) {
			this.element = element;
			next = null;
		}
	}
}

// --- Matrix.java ---
package dataStuctures;

/*
 * Matrix.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a matrix using a two-dimensional array.
 */

/**
 * Matrix implementation using a two-dimensional array.
 */
public class Matrix {
	/** Underlying array for matrix storage. */
	private Object[][] array;
	/** Number of rows in the matrix. */
	private int numRows;
	/** Number of columns in the matrix. */
	private int numCols;

	/**
	 * Creates an empty matrix with specified dimensions.
	 *
	 * @param numRows number of rows
	 * @param numCols number of columns
	 */
	public Matrix(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		array = new Object[numRows][numCols];
	}

	/**
	 * Returns the number of rows in the matrix.
	 *
	 * @return number of rows
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * Returns the number of columns in the matrix.
	 *
	 * @return number of columns
	 */
	public int getNumCols() {
		return numCols;
	}

	/**
	 * Gets an element at the specified position.
	 *
	 * @param row row index
	 * @param col column index
	 * @return element at the position
	 */
	public Object get(int row, int col) {
		return array[row][col];
	}

	/**
	 * Sets an element at the specified position.
	 *
	 * @param row row index
	 * @param col column index
	 * @param value value to set
	 */
	public void set(int row, int col, Object value) {
		array[row][col] = value;
	}
}

// --- MatrixGraph.java ---
package dataStuctures;

/*
 * MatrixGraph.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a graph using a matrix.
 */

/**
 * Graph implementation using a matrix.
 */
public class MatrixGraph {
	/** Adjacency matrix for the graph. */
	private Matrix matrix;
	/** Number of vertices in the graph. */
	private int numVertices;

	/**
	 * Creates an empty graph with specified number of vertices.
	 *
	 * @param numVertices number of vertices
	 */
	public MatrixGraph(int numVertices) {
		this.numVertices = numVertices;
		matrix = new Matrix(numVertices, numVertices);
	}

	/**
	 * Adds an edge to the graph.
	 *
	 * @param source source vertex
	 * @param destination destination vertex
	 */
	public void addEdge(int source, int destination) {
		matrix.set(source, destination, 1);
		matrix.set(destination, source, 1);
	}

	/**
	 * Removes an edge from the graph.
	 *
	 * @param source source vertex
	 * @param destination destination vertex
	 */
	public void removeEdge(int source, int destination) {
		matrix.set(source, destination, 0);
		matrix.set(destination, source, 0);
	}

	/**
	 * Returns the adjacency matrix.
	 *
	 * @return adjacency matrix
	 */
	public Matrix getAdjacencyMatrix() {
		return matrix;
	}

	/**
	 * Returns the number of vertices in the graph.
	 *
	 * @return number of vertices
	 */
	public int getNumVertices() {
		return numVertices;
	}
}

// --- PriorityQueue.java ---
package dataStuctures;

/*
 * PriorityQueue.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a priority queue using a binary heap.
 */

/**
 * Priority queue implementation using a binary heap.
 */
public class PriorityQueue {
	/** Underlying heap array. */
	private Object[] heap;
	/** Number of elements in the queue. */
	private int size;

	/**
	 * Creates an empty priority queue with specified capacity.
	 *
	 * @param capacity initial capacity
	 */
	public PriorityQueue(int capacity) {
		heap = new Object[capacity + 1];
		size = 0;
	}

	/**
	 * Returns the number of elements in the queue.
	 *
	 * @return number of elements
	 */
	public int size() {
		return size;
	}

	/**
	 * Inserts an element into the priority queue.
	 *
	 * @param element element to insert
	 */
	public void insert(Object element) {
		if (size >= heap.length - 1) {
			throw new RuntimeException("PriorityQueue is full");
		}
		heap[++size] = element;
		swim(size);
	}

	/**
	 * Removes and returns the highest priority element.
	 *
	 * @return highest priority element
	 */
	public Object remove() {
		if (size == 0) {
			return null;
		}
		Object max = heap[1];
		heap[1] = heap[size--];
		sink(1);
		return max;
	}

	/**
	 * Restores the heap order by moving an element up.
	 *
	 * @param k index of the element to move up
	 */
	private void swim(int k) {
		while (k > 1 && less(k / 2, k)) {
			swap(k, k / 2);
			k = k / 2;
		}
	}

	/**
	 * Restores the heap order by moving an element down.
	 *
	 * @param k index of the element to move down
	 */
	private void sink(int k) {
		while (2 * k <= size) {
			int j = 2 * k;
			if (j < size && less(j, j + 1)) {
				j++;
			}
			if (!less(k, j)) {
				break;
			}
			swap(k, j);
			k = j;
		}
	}

	/**
	 * Compares two elements.
	 *
	 * @param i index of the first element
	 * @param j index of the second element
	 * @return true if the first element is less than the second
	 */
	private boolean less(int i, int j) {
		return ((Comparable) heap[i]).compareTo(heap[j]) < 0;
	}

	/**
	 * Swaps two elements in the heap.
	 *
	 * @param i index of the first element
	 * @param j index of the second element
	 */
	private void swap(int i, int j) {
		Object temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
}

// --- PriorityQueueLinkedList.java ---
package dataStuctures;

/*
 * PriorityQueueLinkedList.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a priority queue using a linked list.
 */

/**
 * Priority queue implementation using a linked list.
 */
public class PriorityQueue
	/** Underlying linked list. */
	private LinkedList list;

	/**
	 * Creates an empty priority queue.
	 */
	public PriorityQueueLinkedList() {
		list = new LinkedList();
	}

	/**
	 * Returns the number of elements in the queue.
	 *
	 * @return number of elements
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Inserts an element into the priority queue.
	 *
	 * @param element element to insert
	 */
	public void insert(Object element) {
		if (list.size() == 0) {
			list.AddFirst(element);
		} else {
			Node current = list.head;
			while (current != null && ((Comparable) current.element).compareTo(element) < 0) {
				current = current.next;
			}
			if (current == null) {
				list.AddLast(element);
			} else if (current == list.head) {
				list.AddFirst(element);
			} else {
				Node newNode = new Node(element);
				newNode.next = current;
				Node prev = list.head;
				while (prev.next != current) {
					prev = prev.next;
				}
				prev.next = newNode;
				newNode.prev = prev;
			}
		}
	}

	/**
	 * Removes and returns the highest priority element.
	 *
	 * @return highest priority element
	 */
	public Object remove() {
		if (list.size() == 0) {
			return null;
		}
		Object max = list.head.element;
		list.RemoveFirst();
		return max;
	}
}

// --- QuadPointTree.java ---
package dataStuctures;

/*
 * QuadPointTree.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a 2D point quad tree.
 */

/**
 * Quad tree implementation for 2D points.
 */
public class QuadPointTree {
	/** Root of the quad tree. */
	private QuadTreeNode root;

	/**
	 * Creates an empty quad tree.
	 */
	public QuadPointTree() {
		root = null;
	}

	/**
	 * Inserts a point into the quad tree.
	 *
	 * @param point the point to insert
	 */
	public void insert(Point point) {
		root = insert(root, point, null);
	}

	/**
	 * Searches for a point in the quad tree.
	 *
	 * @param point the point to search for
	 * @return true if the point is found, false otherwise
	 */
	public boolean search(Point point) {
		return search(root, point);
	}

	/**
	 * Removes a point from the quad tree.
	 *
	 * @param point the point to remove
	 */
	public void remove(Point point) {
		root = remove(root, point);
	}

	/**
	 * Inserts a point into the quad tree node.
	 *
	 * @param node the quad tree node
	 * @param point the point to insert
	 * @param rect the bounding rectangle of the node
	 * @return the updated quad tree node
	 */
	private QuadTreeNode insert(QuadTreeNode node, Point point, Rectangle rect) {
		if (node == null) {
			return new QuadTreeNode(point, rect);
		}
		if (node.point.equals(point)) {
			return node;
		}
		if (rect == null) {
			rect = new Rectangle(point.x, point.y, point.x, point.y);
		} else {
			rect = rect.union(new Rectangle(point.x, point.y, point.x, point.y));
		}
		if (node.nw == null && node.ne == null && node.sw == null && node.se == null) {
			node.nw = new QuadTreeNode(null, null);
			node.ne = new QuadTreeNode(null, null);
			node.sw = new QuadTreeNode(null, null);
			node.se = new QuadTreeNode(null, null);
		}
		if (point.x < node.point.x) {
			if (point.y < node.point.y) {
				insert(node.nw, point, rect);
			} else {
				insert(node.sw, point, rect);
			}
		} else {
			if (point.y < node.point.y) {
				insert(node.ne, point, rect);
			} else {
				insert(node.se, point, rect);
			}
		}
		return node;
	}

	/**
	 * Searches for a point in the quad tree node.
	 *
	 * @param node the quad tree node
	 * @param point the point to search for
	 * @return true if the point is found, false otherwise
	 */
	private boolean search(QuadTreeNode node, Point point) {
		if (node == null) {
			return false;
		}
		if (node.point.equals(point)) {
			return true;
		}
		if (point.x < node.point.x) {
			if (point.y < node.point.y) {
				return search(node.nw, point);
			} else {
				return search(node.sw, point);
			}
		} else {
			if (point.y < node.point.y) {
				return search(node.ne, point);
			} else {
				return search(node.se, point);
			}
		}
	}

	/**
	 * Removes a point from the quad tree node.
	 *
	 * @param node the quad tree node
	 * @param point the point to remove
	 * @return the updated quad tree node
	 */
	private QuadTreeNode remove(QuadTreeNode node, Point point) {
		if (node == null) {
			return null;
		}
		if (node.point.equals(point)) {
			if (node.nw == null && node.ne == null && node.sw == null && node.se == null) {
				return null;
			}
			if (node.nw != null) {
				node.nw = remove(node.nw, point);
			}
			if (node.ne != null) {
				node.ne = remove(node.ne, point);
			}
			if (node.sw != null) {
				node.sw = remove(node.sw, point);
			}
			if (node.se != null) {
				node.se = remove(node.se, point);
			}
			return node;
		}
		if (point.x < node.point.x) {
			if (point.y < node.point.y) {
				node.nw = remove(node.nw, point);
			} else {
				node.sw = remove(node.sw, point);
			}
		} else {
			if (point.y < node.point.y) {
				node.ne = remove(node.ne, point);
			} else {
				node.se = remove(node.se, point);
			}
		}
		return node;
	}

	/**
	 * Quad tree node class.
	 */
	private class QuadTreeNode {
		public Point point;
		public Rectangle rect;
		public QuadTreeNode nw;
		public QuadTreeNode ne;
		public QuadTreeNode sw;
		public QuadTreeNode se;

		public QuadTreeNode(Point point, Rectangle rect) {
			this.point = point;
			this.rect = rect;
			nw = null;
			ne = null;
			sw = null;
			se = null;
		}
	}

	/**
	 * 2D point class.
	 */
	public class Point {
		public int x;
		public int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			Point other = (Point) obj;
			return x == other.x && y == other.y;
		}

		@Override
		public int hashCode() {
			return 31 * x + y;
		}
	}

	/**
	 * Rectangle class for bounding boxes.
	 */
	private class Rectangle {
		public int x1;
		public int y1;
		public int x2;
		public int y2;

		public Rectangle(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}

		/**
		 * Computes the union of two rectangles.
		 *
		 * @param r1 first rectangle
		 * @param r2 second rectangle
		 * @return union rectangle
		 */
		public Rectangle union(Rectangle r1, Rectangle r2) {
			int x1 = Math.min(r1.x1, r2.x1);
			int y1 = Math.min(r1.y1, r2.y1);
			int x2 = Math.max(r1.x2, r2.x2);
			int y2 = Math.max(r1.y2, r2.y2);
			return new Rectangle(x1, y1, x2, y2);
		}
	}
}

// --- Queue.java ---
package dataStuctures;

/*
 * Queue.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a queue using a linked list.
 */

/**
 * Queue implementation using a linked list.
 */
public class Queue {
	/** Underlying linked list. */
	private LinkedList list;

	/**
	 * Creates an empty queue.
	 */
	public Queue() {
		list = new LinkedList();
	}

	/**
	 * Returns the number of elements in the queue.
	 *
	 * @return number of elements
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Adds an element to the end of the queue.
	 *
	 * @param element element to add
	 */
	public void enqueue(Object element) {
		list.AddLast(element);
	}

	/**
	 * Removes and returns the element from the front of the queue.
	 *
	 * @return element from the front
	 */
	public Object dequeue() {
		if (list.size() == 0) {
			return null;
		}
		Object element = list.GetFirst();
		list.RemoveFirst();
		return element;
	}

	/**
	 * Returns the element at the front of the queue without removing it.
	 *
	 * @return element from the front, or {@code null} if empty
	 */
	public Object peek() {
		return list.GetFirst();
	}
}

// --- RedBlackTree.java ---
package dataStuctures;

/*
 * RedBlackTree.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a red-black tree.
 */

/**
 * Red-black tree implementation.
 */
public class RedBlackTree {
	/** Root of the tree. */
	private Node root;

	/**
	 * Creates an empty red-black tree.
	 */
	public RedBlackTree() {
		root = null;
	}

	/**
	 * Inserts a value into the red-black tree.
	 *
	 * @param value value to insert
	 */
	public void insert(Object value) {
		root = insert(root, value);
		root.color = Black;
	}

	/**
	 * Removes a value from the red-black tree.
	 *
	 * @param value value to remove
	 */
	public void remove(Object value) {
		root = remove(root, value);
		if (root != null) {
			root.color = Black;
		}
	}

	/**
	 * Searches for a value in the red-black tree.
	 *
	 * @param value value to search for
	 * @return true if the value is found, false otherwise
	 */
	public boolean search(Object value) {
		return search(root, value);
	}

	/**
	 * Rotates the tree to the left.
	 *
	 * @param node node to rotate
	 * @return new root of the rotated subtree
	 */
	private Node rotateLeft(Node node) {
		Node x = node.right;
		node.right = x.left;
		x.left = node;
		x.color = node.color;
		node.color = Red;
		return x;
	}

	/**
	 * Rotates the tree to the right.
	 *
	 * @param node node to rotate
	 * @return new root of the rotated subtree
	 */
	private Node rotateRight(Node node) {
		Node x = node.left;
		node.left = x.right;
		x.right = node;
		x.color = node.color;
		node.color = Red;
		return x;
	}

	/**
	 * Flips the colors of a node and its children.
	 *
	 * @param node node to flip
	 */
	private void flipColors(Node node) {
		node.color = Red;
		node.left.color = Black;
		node.right.color = Black;
	}

	/**
	 * Inserts a value into the red-black tree.
	 *
	 * @param node current node
	 * @param value value to insert
	 * @return new node
	 */
	private Node insert(Node node, Object value) {
		if (node == null) {
			return new Node(value, Black);
		}
		int cmp = ((Comparable) value).compareTo(node.value);
		if (cmp < 0) {
			node.left = insert(node.left, value);
		} else if (cmp > 0) {
			node.right = insert(node.right, value);
		}
		if (node.right != null && node.right.color == Red && (node.left == null || node.left.color == Black)) {
			node = rotateLeft(node);
		}
		if (node.left != null && node.left.color == Red && node.left.left != null && node.left.left.color == Red) {
			node = rotateRight(node);
		}
		if (node.left != null && node.left.color == Red && node.right != null && node.right.color == Red) {
			flipColors(node);
		}
		return node;
	}

	/**
	 * Removes a value from the red-black tree.
	 *
	 * @param node current node
	 * @param value value to remove
	 * @return new node
	 */
	private Node remove(Node node, Object value) {
		if (node == null) {
			return null;
		}
		int cmp = ((Comparable) value).compareTo(node.value);
		if (cmp < 0) {
			if (node.left != null && node.left.color == Black) {
				node = moveRedLeft(node);
			}
			node.left = remove(node.left, value);
		} else {
			if (node.left != null && node.left.color == Red) {
				node = rotateRight(node);
			}
			if (cmp == 0 && node.right == null) {
				return null;
			}
			if (node.right != null && node.right.color == Black) {
				node = moveRedRight(node);
			}
			if (cmp == 0) {
				Node minNode = findMin(node.right);
				node.value = minNode.value;
				node.right = removeMin(node.right);
			} else {
				node.right = remove(node.right, value);
			}
		}
		return fixUp(node);
	}

	/**
	 * Moves a red node from the right to the current node.
	 *
	 * @param node current node
	 * @return new node
	 */
	private Node moveRedRight(Node node) {
		flipColors(node);
		if (node.left != null && node.left.color == Red) {
			node = rotateRight(node);
		}
		return node;
	}

	/**
	 * Moves a red node from the left to the current node.
	 *
	 * @param node current node
	 * @return new node
	 */
	private Node moveRedLeft(Node node) {
		flipColors(node);
		if (node.right != null && node.right.color == Red) {
			node = rotateLeft(node);
		}
		return node;
	}

	/**
	 * Fixes up the tree after a removal.
	 *
	 * @param node current node
	 * @return new node
	 */
	private Node fixUp(Node node) {
		if (node.right != null && node.right.color == Red) {
			node = rotateLeft(node);
		}
		if (node.left != null && node.left.color == Red && node.left.left != null && node.left.left.color == Red) {
			node = rotateRight(node);
		}
		if (node.left != null && node.left.color == Red && node.right != null && node.right.color == Red) {
			flipColors(node);
		}
		return node;
	}

	/**
	 * Finds the minimum node in a subtree.
	 *
	 * @param node root of the subtree
	 * @return minimum node
	 */
	private Node findMin(Node node) {
		if (node.left == null) {
			return node;
		}
		return findMin(node.left);
	}

	/**
	 * Removes the minimum node from a subtree.
	 *
	 * @param node root of the subtree
	 * @return new root of the subtree
	 */
	private Node removeMin(Node node) {
		if (node == null) {
			return null;
		}
		if (node.left == null) {
			return null;
		}
		if (node.left.color == Black) {
			node = moveRedLeft(node);
		}
		node.left = removeMin(node.left);
		return fixUp(node);
	}

	/**
	 * Node class for red-black tree.
	 */
	private class Node {
		public Object value;
		public Node left;
		public Node right;
		public int color;

		public Node(Object value, int color) {
			this.value = value;
			this.color = color;
			left = null;
			right = null;
		}
	}

	/** Color constants for red-black tree. */
	private static final int Red = 0;
	private static final int Black = 1;
}

// --- SplayDictionary.java ---
package dataStuctures;

/*
 * SplayDictionary.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a splay tree as a dictionary.
 */

/**
 * Splay tree implementation as a dictionary.
 */
public class SplayDictionary {
	/** Root of the splay tree. */
	private SplayNode root;

	/**
	 * Creates an empty splay dictionary.
	 */
	public SplayDictionary() {
		root = null;
	}

	/**
	 * Searches for a value by key.
	 *
	 * @param key the key
	 * @return value associated with the key, or null if not found
	 */
	public Object search(Object key) {
		root = splay(root, key);
		if (root == null) {
			return null;
		}
		return root.value;
	}

	/**
	 * Inserts a key-value pair into the dictionary.
	 *
	 * @param key key to insert
	 * @param value value to insert
	 */
	public void insert(Object key, Object value) {
		if (root == null) {
			root = new SplayNode(key, value);
			return;
		}
		root = splay(root, key);
		int cmp = key.hashCode() - root.key.hashCode();
		if (cmp == 0) {
			root.value = value;
			return;
		}
		SplayNode newNode = new SplayNode(key, value);
		if (cmp < 0) {
			newNode.left = root.left;
			newNode.right = root;
			root.left = null;
		} else {
			newNode.left = root;
			newNode.right = root.right;
			root.right = null;
		}
		root = newNode;
	}

	/**
	 * Removes a key-value pair from the dictionary.
	 *
	 * @param key the key
	 */
	public void remove(Object key) {
		if (root == null) {
			return;
		}
		root = splay(root, key);
		if (key.equals(root.key)) {
			if (root.left == null) {
				root = root.right;
			} else {
				SplayNode maxNode = findMax(root.left);
				maxNode.right = root.right;
				root = root.left;
			}
		}
	}

	/**
	 * Splays the tree at the given key.
	 *
	 * @param node the root of the subtree
	 * @param key the key to splay at
	 * @return new root of the splayed subtree
	 */
	private SplayNode splay(SplayNode node, Object key) {
		if (node == null) {
			return null;
		}
		int cmp1 = key.hashCode() - node.key.hashCode();
		if (cmp1 < 0) {
			if (node.left == null) {
				return node;
			}
			int cmp2 = key.hashCode() - node.left.key.hashCode();
			if (cmp2 < 0) {
				node.left.left = splay(node.left.left, key);
				node = rotateRight(node);
			} else if (cmp2 > 0) {
				node.left.right = splay(node.left.right, key);
				if (node.left.right != null) {
					node.left = rotateLeft(node.left);
				}
			}
			return node.left == null ? node : rotateRight(node);
		} else if (cmp1 > 0) {
			if (node.right == null) {
				return node;
			}
			int cmp2 = key.hashCode() - node.right.key.hashCode();
			if (cmp2 < 0) {
				node.right.left = splay(node.right.left, key);
				if (node.right.left != null) {
					node.right = rotateRight(node.right);
				}
			} else if (cmp2 > 0) {
				node.right.right = splay(node.right.right, key);
				node = rotateLeft(node);
			}
			return node.right == null ? node : rotateLeft(node);
		}
		return node;
	}

	/**
	 * Rotates the subtree rooted at the given node to the right.
	 *
	 * @param node the root of the subtree
	 * @return new root of the subtree
	 */
	private SplayNode rotateRight(SplayNode node) {
		SplayNode x = node.left;
		node.left = x.right;
		x.right = node;
		return x;
	}

	/**
	 * Rotates the subtree rooted at the given node to the left.
	 *
	 * @param node the root of the subtree
	 * @return new root of the subtree
	 */
	private SplayNode rotateLeft(SplayNode node) {
		SplayNode x = node.right;
		node.right = x.left;
		x.left = node;
		return x;
	}

	/**
	 * Finds the maximum node in the subtree.
	 *
	 * @param node the root of the subtree
	 * @return maximum node
	 */
	private SplayNode findMax(SplayNode node) {
		while (node.right != null) {
			node = node.right;
		}
		return node;
	}

	/**
	 * Splay tree node class.
	 */
	private class SplayNode {
		public Object key;
		public Object value;
		public SplayNode left;
		public SplayNode right;

		public SplayNode(Object key, Object value) {
			this.key = key;
			this.value = value;
			left = null;
			right = null;
		}
	}
}

// --- SplayTree.java ---
package dataStuctures;

/*
 * SplayTree.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a splay tree.
 */

/**
 * Splay tree implementation.
 */
public class SplayTree {
	/** Root of the splay tree. */
	private SplayNode root;

	/**
	 * Creates an empty splay tree.
	 */
	public SplayTree() {
		root = null;
	}

	/**
	 * Inserts a value into the splay tree.
	 *
	 * @param value value to insert
	 */
	public void insert(Object value) {
		root = insert(root, value);
		root.color = Black;
	}

	/**
	 * Removes a value from the splay tree.
	 *
	 * @param value value to remove
	 */
	public void remove(Object value) {
		root = remove(root, value);
		if (root != null) {
			root.color = Black;
		}
	}

	/**
	 * Searches for a value in the splay tree.
	 *
	 * @param value value to search for
	 * @return true if the value is found, false otherwise
	 */
	public boolean search(Object value) {
		return search(root, value);
	}

	/**
	 * Rotates the tree to the left.
	 *
	 * @param node node to rotate
	 * @return new root of the rotated subtree
	 */
	private SplayNode rotateLeft(SplayNode node) {
		SplayNode x = node.right;
		node.right = x.left;
		x.left = node;
		x.color = node.color;
		node.color = Red;
		return x;
	}

	/**
	 * Rotates the tree to the right.
	 *
	 * @param node node to rotate
	 * @return new root of the rotated subtree
	 */
	private SplayNode rotateRight(SplayNode node) {
		SplayNode x = node.left;
		node.left = x.right;
		x.right = node;
		x.color = node.color;
		node.color = Red;
		return x;
	}

	/**
	 * Flips the colors of a node and its children.
	 *
	 * @param node node to flip
	 */
	private void flipColors(SplayNode node) {
		node.color = Red;
		node.left.color = Black;
		node.right.color = Black;
	}

	/**
	 * Inserts a value into the splay tree.
	 *
	 * @param node current node
	 * @param value value to insert
	 * @return new node
	 */
	private SplayNode insert(SplayNode node, Object value) {
		if (node == null) {
			return new SplayNode(value, Black);
		}
		int cmp = ((Comparable) value).compareTo(node.value);
		if (cmp < 0) {
			node.left = insert(node.left, value);
		} else if (cmp > 0) {
			node.right = insert(node.right, value);
		}
		if (node.right != null && node.right.color == Red && (node.left == null || node.left.color == Black)) {
			node = rotateLeft(node);
		}
		if (node.left != null && node.left.color == Red && node.left.left != null && node.left.left.color == Red) {
			node = rotateRight(node);
		}
		if (node.left != null && node.left.color == Red && node.right != null && node.right.color == Red) {
			flipColors(node);
		}
		return node;
	}

	/**
	 * Removes a value from the splay tree.
	 *
	 * @param node current node
	 * @param value value to remove
	 * @return new node
	 */
	private SplayNode remove(SplayNode node, Object value) {
		if (node == null) {
			return null;
		}
		int cmp = ((Comparable) value).compareTo(node.value);
		if (cmp < 0) {
			if (node.left != null && node.left.color == Black) {
				node = moveRedLeft(node);
			}
			node.left = remove(node.left, value);
		} else {
			if (node.left != null && node.left.color == Red) {
				node = rotateRight(node);
			}
			if (cmp == 0 && node.right == null) {
				return null;
			}
			if (node.right != null && node.right.color == Black) {
				node = moveRedRight(node);
			}
			if (cmp == 0) {
				SplayNode minNode = findMin(node.right);
				node.value = minNode.value;
				node.right = removeMin(node.right);
			} else {
				node.right = remove(node.right, value);
			}
		}
		return fixUp(node);
	}

	/**
	 * Moves a red node from the right to the current node.
	 *
	 * @param node current node
	 * @return new node
	 */
	private SplayNode moveRedRight(SplayNode node) {
		flipColors(node);
		if (node.left != null && node.left.color == Red) {
			node = rotateRight(node);
		}
		return node;
	}

	/**
	 * Moves a red node from the left to the current node.
	 *
	 * @param node current node
	 * @return new node
	 */
	private SplayNode moveRedLeft(SplayNode node) {
		flipColors(node);
		if (node.right != null && node.right.color == Red) {
			node = rotateLeft(node);
		}
		return node;
	}

	/**
	 * Fixes up the tree after a removal.
	 *
	 * @param node current node
	 * @return new node
	 */
	private SplayNode fixUp(SplayNode node) {
		if (node.right != null && node.right.color == Red) {
			node = rotateLeft(node);
		}
		if (node.left != null && node.left.color == Red && node.left.left != null && node.left.left.color == Red) {
			node = rotateRight(node);
		}
		if (node.left != null && node.left.color == Red && node.right != null && node.right.color == Red) {
			flipColors(node);
		}
		return node;
	}

	/**
	 * Finds the minimum node in a subtree.
	 *
	 * @param node root of the subtree
	 * @return minimum node
	 */
	private SplayNode findMin(SplayNode node) {
		if (node.left == null) {
			return node;
		}
		return findMin(node.left);
	}

	/**
	 * Removes the minimum node from a subtree.
	 *
	 * @param node root of the subtree
	 * @return new root of the subtree
	 */
	private SplayNode removeMin(SplayNode node) {
		if (node == null) {
			return null;
		}
		if (node.left == null) {
			return null;
		}
		if (node.left.color == Black) {
			node = moveRedLeft(node);
		}
		node.left = removeMin(node.left);
		return fixUp(node);
	}

	/**
	 * Splay tree node class.
	 */
	private class SplayNode {
		public Object value;
		public SplayNode left;
		public SplayNode right;
		public int color;

		public SplayNode(Object value, int color) {
			this.value = value;
			this.color = color;
			left = null;
			right = null;
		}
	}

	/** Color constants for splay tree. */
	private static final int Red = 0;
	private static final int Black = 1;
}

// --- Stack.java ---
package dataStuctures;

/*
 * Stack.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a stack using a linked list.
 */

/**
 * Stack implementation using a linked list.
 */
public class Stack {
	/** Underlying linked list. */
	private LinkedList list;

	/**
	 * Creates an empty stack.
	 */
	public Stack() {
		list = new LinkedList();
	}

	/**
	 * Returns the number of elements in the stack.
	 *
	 * @return number of elements
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Pushes an element onto the stack.
	 *
	 * @param element element to push
	 */
	public void push(Object element) {
		list.AddLast(element);
	}

	/**
	 * Pops an element from the stack.
	 *
	 * @return popped element, or {@code null} if empty
	 */
	public Object pop() {
		if (list.size() == 0) {
			return null;
		}
		Object element = list.GetLast();
		list.RemoveLast();
		return element;
	}

	/**
	 * Peeks at the top element of the stack without removing it.
	 *
	 * @return top element, or {@code null} if empty
	 */
	public Object peek() {
		return list.GetLast();
	}
}

// --- Tree.java ---
package dataStuctures;

/*
 * Tree.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a generic tree.
 */

/**
 * Generic tree implementation.
 */
public class Tree {
	/** Root of the tree. */
	private TreeNode root;

	/**
	 * Creates an empty tree.
	 */
	public Tree() {
		root = null;
	}

	/**
	 * Adds a child to a parent node.
	 *
	 * @param parent parent node
	 * @param child child node to add
	 */
	public void addChild(TreeNode parent, TreeNode child) {
		if (parent == null) {
			root = child;
		} else {
			parent.addChild(child);
		}
	}

	/**
	 * Returns the root of the tree.
	 *
	 * @return root node
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * Tree node class.
	 */
	public class TreeNode {
		public Object element;
		public List<TreeNode> children;

		public TreeNode(Object element) {
			this.element = element;
			children = new ArrayList<>();
		}

		/**
		 * Adds a child to this node.
		 *
		 * @param child child node to add
		 */
		public void addChild(TreeNode child) {
			children.add(child);
		}

		/**
		 * Returns the children of this node.
		 *
		 * @return list of children
		 */
		public List<TreeNode> getChildren() {
			return children;
		}
	}
}

// --- TreePrinter.java ---
package dataStuctures;

/*
 * TreePrinter.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Utility class to print trees.
 */

/**
 * Utility class to print trees.
 */
public class TreePrinter {
	/**
	 * Prints a tree.
	 *
	 * @param tree the tree
	 */
	public static void print(Tree tree) {
		printNode(tree.getRoot(), 0);
	}

	/**
	 * Recursively prints a tree node.
	 *
	 * @param node the node
	 * @param depth the depth
	 */
	private static void printNode(Tree.TreeNode node, int depth) {
		if (node == null) {
			return;
		}
		for (int i = 0; i < depth; i++) {
			System.out.print("  ");
		}
		System.out.println(node.element);
		for (Tree.TreeNode child : node.getChildren()) {
			printNode(child, depth + 1);
		}
	}
}

// --- Vector.java ---
package dataStuctures;

/*
 * Vector.java
 * Rishikesh Soni
 * Updated on 6/12/2025
 *
 * Implementation of a vector using an array.
 */

/**
 * Vector implementation using an array.
 */
public class Vector {
	/** Underlying array for vector storage. */
	private Object[] array;
	/** Number of elements in the vector. */
	private int size;

	/**
	 * Creates an empty vector with initial capacity.
	 */
	public Vector() {
		array = new Object[10];
		size = 0;
	}

	/**
	 * Returns the number of elements in the vector.
	 *
	 * @return number of elements
	 */
	public int size() {
		return size;
	}

	/**
	 * Gets an element at the specified index.
	 *
	 * @param index index of the element
	 * @return element at the index
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return array[index];
	}

	/**
	 * Sets an element at the specified index.
	 *
	 * @param index index of the element
	 * @param value value to set
	 */
	public void set(int index, Object value) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		array[index] = value;
	}

	/**
	 * Adds an element to the end of the vector.
	 *
	 * @param element element to add
	 */
	public void add(Object element) {
		if (size == array.length) {
			resize();
		}
		array[size++] = element;
	}

	/**
	 * Resizes the underlying array to double its current size.
	 */
	private void resize() {
		Object[] newArray = new Object[array.length * 2];
		System.arraycopy(array, 0, newArray, 0, array.length);
		array = newArray;
	}
}
