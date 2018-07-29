/*----------------------------------------------------------------
 *  Author:        Jiayue Bao
 *  Last Updated:  7/28/2018
 *
 *  Compilation:   javac-algs4 Deque.java
 *  Execution:     java-algs4 Deque
 *
 * A double-ended queue or deque (pronounced “deck”) is a 
 * generalization of a stack and a queue that supports adding 
 * and removing items from either the front or the back of the 
 * data structure.
 *
 *----------------------------------------------------------------*/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private int n; // size of Deque
	private Node first;
	private Node last;


	//helper linked list class
	private class Node {
		private Item item;
		private Node prev;
		private Node next;	

		public Node(Item item, Node prev, Node next) {
			this.item = item;
			this.prev = prev;
			this.next = next;
		}	
	}

	/** construct an empty deque */
	public Deque() {
		first = null;
		last = null;
		n = 0;
	}

	/** is the deque empty? */
	public boolean isEmpty() {
		return first == null && last == null;
	}

	/** return the number of items on the deque */
	public int size() {
		return n;
	}

	/** add the item to the front */
	public void addFirst(Item item) {
		if (item == null) throw new IllegalArgumentException("calls with a null argument.");
		if (isEmpty()) {
			first = last = new Node(item,null,null);
		}
		else {
			Node oldfirst = first;
		    first = new Node(item,null,oldfirst);
		    oldfirst.prev = first;
		}
		
		n++;
		System.out.println("addFirst: "+ item);
		print();
	}

	/** add the item to the end */
	public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("calls with a null argument.");
        if (isEmpty()) {
			last = first = new Node(item,null,null);
		}
		else {
			Node oldlast = last;
		    last = new Node(item,oldlast,null);
		    oldlast.next = last;
		}
		n++;
		System.out.println("addLast: "+ item);
		print();
	}

	/** remove and return the item from the front */
	public Item removeFirst() {
		if (isEmpty()) throw new NoSuchElementException("The deque is empty.");
		Item item = first.item;
		first = first.next;
		if(first == null) {
			last = null;
		}
		else {
			first.prev = null;
		}
		
		n--;
		System.out.println("removeFirst: "+ item);
		print();
		return item;
		


	}

	/** remove and return the item from the end */
	public Item removeLast() {
		if (isEmpty()) throw new NoSuchElementException("The deque is empty.");
		Item item = last.item;
		last = last.prev;
		if(last == null) {
			first = null;
		}
		else {
			last.next = null;
		}
		
		n--;
		System.out.println("removeLast: "+ item);
		print();
		return item;
	}


	/** return an iterator over items in order from front to end */
	public Iterator<Item> iterator() {
		return new ListIterator();

	}

	/** an iterator */
	private class ListIterator implements Iterator<Item> {
		private Node current;
		public ListIterator() {
			current = first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}


	private void print() {
		Iterator<Item> iterator = iterator();
		while (iterator.hasNext()) {
			System.out.print(iterator.next()+",");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<>();
		deque.addFirst(1);
		deque.addLast(2);
		deque.addFirst(3);
		deque.addFirst(4);
		deque.removeFirst();
		deque.removeFirst();
		deque.removeLast();
		deque.removeLast();
	}
}