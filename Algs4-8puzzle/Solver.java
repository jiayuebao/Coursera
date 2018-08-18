/*-----------------------------------------------------------------------
 *  Author:        Jiayue Bao
 *  Last Updated:  8/18/2018
 *
 *  Compilation:   javac-algs4 Solver.java
 *  Execution:     java-algs4 Solver puzzle04.txt
 *  Dependencies:  Board.java
 *  
 *  Implement the A* algorithm using MinPQ from algs4.jar for the priority queue.
 * 
 *-------------------------------------------------------------------------*/
import java.util.Comparator;
import java.util.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In; 

public class Solver {
	private Node goal = null;

	private class Node {
		Board board;
		int moves;
		Node predecessor;
		public Node(Board board, Node predecessor,int moves) {
			this.board = board;
			this.predecessor = predecessor;
			this.moves = moves;
		}

	}

	private class ManhattanPriority implements Comparator<Node>{
		public int compare(Node n1, Node n2) {
			int p1 = n1.board.manhattan() + n1.moves;
			int p2 = n2.board.manhattan() + n2.moves;
			if (p1 < p2) return -1;
			else if (p1 > p2) return 1;
			else return 0;
		}

	}

	/** find a solution to the initial board (using the A* algorithm) */
	public Solver(Board initial) {
		if (initial == null) throw new IllegalArgumentException();
		Comparator<Node> MANHATTAN_PRIORITY= new ManhattanPriority();
		MinPQ<Node> pq = new MinPQ<Node>(MANHATTAN_PRIORITY);
		pq.insert(new Node(initial, null, 0));
		pq.insert(new Node(initial.twin(),null,0));
		
		while(!pq.isEmpty()) {
			Node curr = pq.delMin(); // deque the minimum node
			if (curr.board.isGoal()) {// reach the goal board
				// find the first node of the track
				Node first = curr;
				while(first.predecessor!=null) {
					first = first.predecessor;
				}
				if(first.board.equals(initial)) {
					goal = curr;
				}
				break;
			}
			for (Board neighbor:curr.board.neighbors()) { // enque the neighbors
				if (curr.predecessor==null || !neighbor.equals(curr.predecessor.board)) {
					pq.insert(new Node(neighbor,curr,curr.moves+1));
				}
			}
		}

	}

	/** is the initial board solvable? */
	public boolean isSolvable() {
		return goal != null;
	}

	/** min number of moves to solve initial board; -1 if unsolvable */
	public int moves() {
		if (!isSolvable()) return -1;
		return goal.moves;
	}

	/** sequence of boards in a shortest solution; null if unsolvable */
	public Iterable<Board> solution() {
		if (!isSolvable()) return null;
		Stack<Board> stack = new Stack<>();
		Node node = goal;
		while(node!=null) {
			stack.push(node.board);
			node = node.predecessor;
		}
		Stack<Board> solution = new Stack<>();
		while(!stack.isEmpty()) {
			solution.push(stack.pop());
		}
		return solution;
		
	}

	/** solve a slider puzzle */
	public static void main(String[] args) {
		 // create initial board from file
    	In in = new In(args[0]);
    	int n = in.readInt();
    	int[][] blocks = new int[n][n];
    	for (int i = 0; i < n; i++)
        	for (int j = 0; j < n; j++)
            	blocks[i][j] = in.readInt();
    	Board initial = new Board(blocks);

    	// solve the puzzle
    	Solver solver = new Solver(initial);

    	// print solution to standard output
    	if (!solver.isSolvable())
        StdOut.println("No solution possible");
    	else {
        	StdOut.println("Minimum number of moves = " + solver.moves());
        	for (Board board : solver.solution())
            	StdOut.println(board);
    	}
	}
} 