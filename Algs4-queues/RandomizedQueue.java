/*----------------------------------------------------------------
 *  Author:        Jiayue Bao
 *  Last Updated:  7/29/2018
 *
 *  Compilation:   javac-algs4 RandomizedQueue.java
 *  Execution:     java-algs4 RandomizedQueue (main method not provided here)
 *
 *  A randomized queue is similar to a stack or queue, except that 
 *  the item removed is chosen uniformly at random from items in the 
 *  data structure.
 *
 *----------------------------------------------------------------*/
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int size;
	private Item[] array;
	/** construct an empty randomized queue */
	public RandomizedQueue() {
		size = 0;
		array = (Item[]) new Object[2];

	}

	/** is the randomized queue empty?*/
	public boolean isEmpty() {
		return size==0;
	}

	/** return the number of items on the randomized queue */
	public int size() {
		return size;
	}

	// resize the array
	private void resize(int capacity) {
		Item[] tmp = (Item[]) new Object[capacity];
		for (int i = 0; i < size; i++) {
			tmp[i] = array[i];
		}
		array = tmp;
	}

	/** add the item */
	public void enqueue(Item item) {
		if (item == null) throw new IllegalArgumentException("Calls with a null argument.");
		if (size == array.length) resize(size*2);
		array[size++] = item;
	}

	/** remove and return a random item */
	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException("The RandomizedQueue is empty.");
		int index = StdRandom.uniform(size);
		Item item = array[index];
		
		for (int i = index + 1;i < size; i++) {
			array[i-1] = array[i];
		}

		array[--size] = null;
		if(size > 0 && size == array.length/4) resize(array.length/2);
		return item;
	}

	/** return a random item (but do not remove it) */
	public Item sample() {
		if (isEmpty()) throw new NoSuchElementException("The RandomizedQueue is empty.");
		int index = StdRandom.uniform(size);
		return array[index];
	}

	/** return an independent iterator over items in random order */
	public Iterator<Item> iterator() {
		return new RandomArrayIterator();
	}
	// an iterator
	private class RandomArrayIterator implements Iterator<Item> {
		private int current; // the index of the order 
		private int[] order; // the index of the queue
		public RandomArrayIterator() {
			current = 0;
			order = new int[size];
			for (int i = 0; i < size; i++ ) {
				order[i] = i;
			}
			StdRandom.shuffle(order);
		}
		public boolean hasNext() {
			return current != size;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			return array[order[current++]];
		}
	}

}