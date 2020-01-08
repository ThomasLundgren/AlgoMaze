package se.hig.dvg329.algomaze.control.solvers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.hig.dvg329.algomaze.control.solvers.FibonacciHeap;
import se.hig.dvg329.algomaze.control.solvers.HeapEmptyException;

class FibonacciHeapTest {

	FibonacciHeap<Integer> testHeap;
	Integer[] fixture = {5, 3, 1, 2, 4};
	
	@BeforeEach
	void setUp() throws Exception {
		testHeap = new FibonacciHeap<>();
	}

	@AfterEach
	void tearDown() throws Exception {
		testHeap = null;
	}

	@Test
	void newFibHeap_isEmpty_returnsTrue() {
		assertTrue(testHeap.isEmpty(), "A newly created FibHeap is not empty!");
	}
	
	@Test
	void size_onEmptyHeap_returnsZero() {
		assertEquals(0, testHeap.size(), "The size of an empty FibHeap is not zero!");
	}
	
	@Test
	void enqueue_onEmptyFibHeap_isNotEmpty() {
		testHeap.enqueue(fixture[0], 1);
		assertFalse(testHeap.isEmpty(), "FibHeap with one element is empty!");
	}
	
	@Test
	void enqueue_withNaNPriority_throwsIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, () -> {
			testHeap.enqueue(fixture[0], new Double(0.0/0.0));
		});
	}
	
	@Test
	void size_afterEnqueueOneElement_returnsOne() {
		testHeap.enqueue(fixture[0], 1);
		assertEquals(1, testHeap.size(), "size() doesn't return 1 when there is 1 element in the FibHeap!");
	}
	
	@Test
	void getMin_onEmptyFibHeap_throwsException() {
		assertThrows(HeapEmptyException.class, () -> {
			testHeap.getMin();
		});
	}
	
	@Test
	void enqueue_onEmptyHeap_returnsTheEnqueuedElement() {
		FibonacciHeap.Node<Integer> enqueuedNode = testHeap.enqueue(fixture[0], 1);
		assertEquals(enqueuedNode, testHeap.getMin());
	}
	
	@Test
	void getMin_onPopulatedFibHeap_returnsLowestPriorityElement() {
		FibonacciHeap.Node<Integer> lowestPrioNode = testHeap.enqueue(fixture[0], -1.0);
		testHeap.enqueue(fixture[1], 1.0);
		assertEquals(lowestPrioNode, testHeap.getMin());
	}
	
	@Test
	void dequeueMin_onEmptyHeap_throwsHeapEmptyException() {
		assertThrows(HeapEmptyException.class, () -> {
			testHeap.dequeueMin();
		});
	}
	
	@Test
	void dequeueMin_onPopulatedHeap_returnsLowestPrioElement() {
		testHeap.enqueue(fixture[0], 1.0);
		FibonacciHeap.Node<Integer> lowestPrioNode = testHeap.enqueue(fixture[1], 1.0);
		assertEquals(lowestPrioNode, testHeap.dequeueMin());
	}
	
	@Test
	void merge_twoHeaps_dequeueMinInRightOrder() {
		FibonacciHeap<Integer> secondHeap = new FibonacciHeap<>();
		
		FibonacciHeap.Node<Integer> fourth = testHeap.enqueue(fixture[3], 2.0);
		FibonacciHeap.Node<Integer> first = testHeap.enqueue(fixture[0], 0.0);
		FibonacciHeap.Node<Integer> second = testHeap.enqueue(fixture[1], 1.0);
		FibonacciHeap.Node<Integer> third = testHeap.enqueue(fixture[2], 1.5);
		
		FibonacciHeap<Integer> mergedHeap = FibonacciHeap.merge(testHeap, secondHeap);
		
		assertEquals(first, mergedHeap.dequeueMin());
		assertEquals(second, mergedHeap.dequeueMin());
		assertEquals(third, mergedHeap.dequeueMin());
		assertEquals(fourth, mergedHeap.dequeueMin());
	}
	
	@Test
	void decreaseKey_onEmptyHeap_throwsIllegalArgumentException() {
		FibonacciHeap.Node<Integer> aNode = testHeap.enqueue(fixture[0], 1000);
		testHeap.dequeueMin();
		
		assertTrue(testHeap.isEmpty());
		
		assertThrows(HeapEmptyException.class, () -> {
			testHeap.decreaseKey(aNode, 999);
		});
	}
	
	@Test
	void decreaseKey_withHigherKeyAsArgument_throwsIllegalArgumentException() {
		FibonacciHeap.Node<Integer> aNode = testHeap.enqueue(fixture[0], 1000);
		
		assertThrows(IllegalArgumentException.class, () -> {
			testHeap.decreaseKey(aNode, 1001);
		});
	}
	
	@Test
	void decreaseKey_toBeTheLowestPrioElement_returnsThatElement() {
		testHeap.enqueue(fixture[0], 1.0);
		testHeap.enqueue(fixture[1], 2.0);
		FibonacciHeap.Node<Integer> decreasedNode = testHeap.enqueue(fixture[2], 3.0);
		
		testHeap.decreaseKey(decreasedNode, -1.0);
		
		assertEquals(decreasedNode, testHeap.getMin());
	}
	
	@Test
	void delete_onPopulatedHeap_deletesFromHeap() {
		testHeap.enqueue(fixture[0], 1.0);
		testHeap.enqueue(fixture[1], 2.0);
		FibonacciHeap.Node<Integer> deletedNode = testHeap.enqueue(fixture[2], 3.0);
		
		testHeap.delete(deletedNode);
		
		assertNotEquals(deletedNode, testHeap.dequeueMin());
		assertNotEquals(deletedNode, testHeap.dequeueMin());
		assertTrue(testHeap.isEmpty());
	}
	
}
