package se.hig.dvg329.algomaze.control.solvers;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FibonacciHeap} is a priority queue. The {@code FibonacciHeap} should be used
 * over other types of heaps when the number of {@link FibonacciHeap#decreaseKey(Node, double)}
 * operations outnumber the {@link FibonacciHeap#dequeueMin()} operations.
 * All credit for this implementation goes to Keith Schwarz. You can find his implementation
 * here: http://keithschwarz.com/interesting/code/?dir=fibonacci-heap.
 * @author Keith Schwarz (htiek@cs.stanford.edu), modified by Thomas Lundgren
 * @version 1.0.0
 * @since 1.0.0
 * @param <E> The type of objects that this {@code FibonacciHeap} can contain.
 */
public class FibonacciHeap<E> {
	
	private Node<E> min;
	private int size;
	
	/**
	 * Constructs an empty {@code FibonacciHeap}.
	 */
	public FibonacciHeap() {}
	
	/**
	 * Inserts an element into this {@code FibonacciHeap}.
	 * @param element The element to insert.
	 * @param priority The priority of the element.
	 * @return The {@link Node} used in the {@code FibonacciHeap}.
	 */
	public Node<E> enqueue(E element, double priority) {
		checkPriority(priority);
		Node<E> newNode = new Node<E>(element, priority);
		
		min = mergeLists(min, newNode);
		
		size++;
		return newNode;
	}
	
	/**
	 * Returns the lowest priority element in this {@code FibonacciHeap}.
	 * @return the {@link Node} of the lowest priority element in this {@code FibonacciHeap}.
	 * @throws HeapEmptyException if an application tries to return an element from an empty {@code FibonacciHeap}.
	 */
	public Node<E> getMin() throws HeapEmptyException {
		if (isEmpty()) {
			throw new HeapEmptyException("Cannot return minimum of an empty heap.");
		}
		return min;
	}
	
	/**
	 * Dequeues the lowest priority element from the {@code FibonacciHeap}.
	 * @return the lowest priority element in the {@code FibonacciHeap}
	 * @throws HeapEmptyException if an application tries to dequeue an element
	 * from an empty {@code FibonacciHeap}.
	 */
	public Node<E> dequeueMin() throws HeapEmptyException {
		if (this.isEmpty()) {
			throw new HeapEmptyException("Cannot remove minimum of an empty heap.");
		}
		Node<E> minElement = min;
		size--;
		
		if (min.next == min) {
			min = null;
		}
		else {
			min.previous.next = min.next;
			min.next.previous = min.previous;
			min = min.next;
		}
		
		if (minElement.child != null) {
			Node<E> curr = minElement.child;
			
			do {
				curr.parent = null;
				curr = curr.next;
			} while (curr != minElement.child);
		}
		min = mergeLists(min, minElement.child);
		
		if (min == null) {
			return minElement;
		}
		
		List<Node<E>> treeTable = new ArrayList<>(47);
		List<Node<E>> toVisit = new ArrayList<>();
		
		for (Node<E> curr = min; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.next) {
			toVisit.add(curr);
		}
		for (Node<E> curr : toVisit) {
			while (true) {
				while (curr.degree >= treeTable.size()) {
					treeTable.add(null);
				}
				if (treeTable.get(curr.degree) == null) {
					treeTable.set(curr.degree, curr);
					break;
				}
				Node<E> other = treeTable.get(curr.degree);
				treeTable.set(curr.degree, null);
				
				Node<E> smallest = (other.priority < curr.priority ? other : curr);
				Node<E> largest = (other.priority < curr.priority ? curr : other);
				
				largest.next.previous = largest.previous;
				largest.previous.next = largest.next;
				
				largest.next = largest.previous = largest;
				smallest.child = mergeLists(smallest.child, largest);
				
				largest.parent = smallest;
				largest.isMarked = false;
				smallest.degree++;
				curr = smallest;
			}
			if (curr.priority <= min.priority) {
				min = curr;
			}
		}
		return minElement;
	}
	
	/**
	 * Merges two {@code FibonacciHeap}s into a single {@code FibonacciHeap}.
	 * @param first the first {@code FibonacciHeap} to merge.
	 * @param second the second {@code FibonacciHeap} to merge.
	 * @param <E> the type of the {@code FibonacciHeap}s to merge and of the returned {@code FibonacciHeap}.
	 * @return the merged {@code FibonacciHeap}.
	 */
	public static <E> FibonacciHeap<E> merge(FibonacciHeap<E> first, FibonacciHeap<E> second) {
		FibonacciHeap<E> mergedFibHeap = new FibonacciHeap<E>();
		
		mergedFibHeap.min = mergeLists(first.min, second.min);
		mergedFibHeap.size = first.size + second.size;
		
		first.min = null;
		second.min = null;
		
		return mergedFibHeap;
	}
	
	/**
	 * Decreases the key (priority) of a specified {@code Node}.
	 * The passed in {@code Node} is assumed to exists within the {@code FibonacciHeap} for
	 * efficiency reasons.
	 * @param node the {@code Node} which priority should be decreased.
	 * @param newPriority the {@code Node}s new priority.
	 * @throws HeapEmptyException if an application tries to decrease the priority of an element
	 * in an empty {@code FibonacciHeap}.
	 * @throws IllegalArgumentException if an application supplies a priority of a {@code Node} in
	 * the {@code FibonacciHeap} that is higher than its current priority.
	 */
	public void decreaseKey(Node<E> node, double newPriority) throws HeapEmptyException, IllegalArgumentException {
		checkPriority(newPriority);
		if(this.isEmpty()) {
			throw new HeapEmptyException("Cannot decrease the key of a Node in an empty heap.");
		}
		if (newPriority > node.priority) {
			throw new IllegalArgumentException("Cannot decrease key to a greater value.");
		}
		node.priority = newPriority;
		
		if (node.parent != null && node.priority <= node.parent.priority) {
			cutNode(node);
		}
		if (node.priority <= min.priority) {
			min = node;
		}
	}
	
	/**
	 * Deletes a {@code Node} from this {@code FibonacciHeap}. The {@code Node}
	 * is assumed to exist in this {@code FibonacciHeap} for efficiency reasons.
	 * No checks are performed to make sure of this.
	 * @param node The {@code Node} which to delete.
	 */
	public void delete(Node<E> node) {
		decreaseKey(node, Double.NEGATIVE_INFINITY);
		dequeueMin();
	}
	
	/**
	 * Returns whether this {@code FibonacciHeap} is empty.
	 * @return true if this {@code FibonacciHeap} is empty, otherwise false.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Returns the size of this {@code FibonacciHeap}.
	 * @return the size of this {@code FibonacciHeap}.
	 */
	public int size() {
		return size;
	}
	
	private void cutNode(Node<E> cutNode) {
		cutNode.isMarked = false;
		
		if (cutNode.parent == null) {
			return;
		}
		if (cutNode.next != cutNode) {
			cutNode.next.previous = cutNode.previous;
			cutNode.previous.next = cutNode.next;
		}
		if (cutNode.parent.child == cutNode) {
			if (cutNode.next != cutNode) {
				cutNode.parent.child = cutNode.next;
			}
			else {
				cutNode.parent.child = null;
			}
		}
		cutNode.parent.degree--;
		cutNode.previous = cutNode.next = cutNode;
		min = mergeLists(min, cutNode);
		
		if (cutNode.parent.isMarked) {
			cutNode(cutNode.parent);
		}
		else {
			cutNode.parent.isMarked = true;
		}
		cutNode.parent = null;
	}
	
	private static <E> Node<E> mergeLists(Node<E> first, Node<E> second) {
		if (first == null && second == null) {
			return null;
		}
		else if (first != null && second == null) {
			return first;
		}
		else if (first == null && second != null) {
			return second;
		}
		else {
			Node<E> firstNext = first.next;
			first.next = second.next;
			first.next.previous = first;
			second.next = firstNext;
			second.next.previous = second;
			
			return first.priority < second.priority ? first : second;
		}
	}
	
	private void checkPriority(double priority) {
		if (Double.isNaN(priority)) {
			throw new IllegalArgumentException("Invalid priority! The passed-in value is considered to be NaN.");
		}
	}
	
	/**
	 * The {@code Node} class is only used by the {@code FibonacciHeap} class to store
	 * the element supplied with the {@link FibonacciHeap#enqueue(Object, double)} method.
	 * @author Thomas Lundgren
	 *
	 * @param <E> the type of Objects stored in the {@link FibonacciHeap}.
	 */
	public static class Node<E> {
		private int degree;
		private double priority;
		private E value;
		private boolean isMarked = false;
		private Node<E> parent, child, next, previous;
		
		private Node(E value, double priority) {
			this.priority = priority;
			this.value = value;
			previous = next = this;
		}
		
		public void setValue(E value) {
			this.value = value;
		}
		
		public E getValue() {
			return value;
		}
		
		public double getPriority() {
			return priority;
		}
	}
}
