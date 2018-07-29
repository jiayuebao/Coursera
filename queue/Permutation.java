/*----------------------------------------------------------------
 *  Author:        Jiayue Bao
 *  Last Updated:  7/29/2018
 *
 *  Compilation:   javac-algs4 Permutation.java
 *  Execution:     java-algs4 Permutation 3 < distinct.txt
 *
 *  A client program that takes an integer k as a command-line argument; 
 *  reads in a sequence of strings from standard input using StdIn.readString();
 *  and prints exactly k of them, uniformly at random. 
 *  Print each item from the sequence at most once.
 *
 *----------------------------------------------------------------*/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	public static void main(String[] args) {
		if (args.length == 0) {
			StdOut.println("Integer k and input file missing.");
			return;
		}
		
		int k = Integer.parseInt(args[0]);
		if (k < 0) return;

		RandomizedQueue<String> rq = new RandomizedQueue<>();
		while (!StdIn.isEmpty()) {
			String str = StdIn.readString();
			rq.enqueue(str);
		}

		if (k > rq.size()) return;
		for(int i = 0; i < k; i++) {
			String str = rq.dequeue();
			StdOut.println(str);
		}
	}
}