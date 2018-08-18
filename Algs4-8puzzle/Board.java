/*-----------------------------------------------------------------------
 *  Author:        Jiayue Bao
 *  Last Updated:  8/17/2018
 *
 *  Compilation:   javac-algs4 Board.java
 *  Execution:     java-algs4 Board puzzle04.txt
 *  
 *  An immutable data type used for solving 8-puzzle problem.
 *  Receives an n-by-n array containing the n2 integers between 0 and n2 − 1, 
 *  where 0 represents the blank square.
 * 
 *-------------------------------------------------------------------------*/
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In; 
import java.util.List;
import java.util.ArrayList;

public class Board {
	private int[][] curr;
	private int dim;
	private int hamming;
	private int manhattan;
	private int[] blankPos = new int[2]; // the position of zero
	/** construct a board from an n-by-n array of blocks */
	public Board(int[][] blocks) {
		dim = blocks.length;
		curr = new int[dim][dim];

		for (int i = 0; i < dim; i++) 
			for (int j = 0; j < dim; j++) {
				curr[i][j] = blocks[i][j];
				if (curr[i][j] == 0) {
					blankPos[0] = i;
					blankPos[1] = j; 
				}
				else {
					hamming += calHamming(i,j);
					manhattan += calManhattan(i,j);
				}
				
			}


	}

	private Board(int[][] blocks,int hamming,int manhattan, int row, int col) {
		dim = blocks.length;
		curr = new int[dim][dim];
		for (int i = 0; i < dim; i++) 
			for (int j = 0; j < dim; j++) 
				curr[i][j] = blocks[i][j];

		this.hamming = hamming;
		this.manhattan = manhattan;
		blankPos[0] = row;
		blankPos[1] = col;
	}

	/** board dimension n */
	public int dimension() {
		return dim;
	}


	/** number of blocks out of place */
	public int hamming() {
		return hamming;
	}

	
	/** sum of Manhattan distance between blocks and goal */
	public int manhattan() {
		return manhattan;
	}

	/** calculate the manhattan distance of one block */
	private int calManhattan(int i, int j) {
		if (curr[i][j] != i*dim+j+1) 
			return Math.abs((curr[i][j]-1)/dim-i)+Math.abs((curr[i][j]-1)%dim-j);
		else return 0;
	}

	/** calculate the hamming distance of one block */
	private int calHamming(int i, int j) {
		if (curr[i][j] != i*dim+j+1) return 1;
		else return 0;
	}

	/** is this board the goal board? */
	public boolean isGoal() {
		return hamming == 0;

	}

	private void exch(int[][] a, int row1, int col1, int row2, int col2) {
		int tmp = a[row1][col1];
		a[row1][col1] = a[row2][col2];
		a[row2][col2] = tmp;
	}
	/** a board that is obtained by exchanging any pair of blocks */
	public Board twin() {
		int[][] blocks = new int[dim][dim];
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++) {
				blocks[i][j] = curr[i][j];
			} 
				
			
		if (blocks[0][0]!=0 && blocks[0][1]!=0) {
			exch(blocks,0,0,0,1);
		}

		else if (blocks[0][0] == 0) {
			exch(blocks,0,1,1,1);
		}
		else if (blocks[0][1] == 0) {
			exch(blocks,0,0,1,0);
		}

		Board twin = new Board(blocks);
		return twin;

	}

	/** does this board equal y? */
	public boolean equals(Object y) {
		Board that= (Board) y;
		if (that.dim != dim) return false;
		for (int i = 0; i < dim; i++) 
			for (int j = 0; j < dim; j++) {
				if (this.curr[i][j] != that.curr[i][j]) return false;
			}
		return true;

	}

	private Board neighbor(int row, int col) {
		int[][] blocks = new int[dim][dim];
		for (int i = 0; i < dim; i++)
			for (int j = 0; j < dim; j++) {
				blocks[i][j] = curr[i][j];
			} 
		exch(blocks, row, col, blankPos[0],blankPos[1]);
		
		return new Board(blocks);

	}
	/** all neighboring boards */
	public Iterable<Board> neighbors() {
		ArrayList<Board> neighbors = new ArrayList<>();
		// add left
		if (blankPos[1] > 0) {
			Board left = neighbor(blankPos[0],blankPos[1]-1);
			neighbors.add(left);
		}
		// add right
		if (blankPos[1] < dim-1) {
			Board right = neighbor(blankPos[0],blankPos[1]+1);
			neighbors.add(right);
		}
		// add up
		if(blankPos[0] > 0) {
			Board up = neighbor(blankPos[0]-1,blankPos[1]);
			neighbors.add(up);
		}
		// add down
		if(blankPos[0] < dim-1) {
			Board bottom = neighbor(blankPos[0]+1,blankPos[1]);
			neighbors.add(bottom);
		}

		return neighbors;
	}

	/** string representation of this board */
	public String toString() {
		String str = "";
		for (int i = 0; i < dim; i++){
			for (int j = 0; j < dim; j++) {
				str += curr[i][j]+ "  ";
			}
			str += "\n";
		} 
			
		return str;
	}

	/** unit test */
	public static void main(String[] args) {
		In in = new In(args[0]);
    	int n = in.readInt();
    	int[][] blocks = new int[n][n];
    	for (int i = 0; i < n; i++)
        	for (int j = 0; j < n; j++)
            	blocks[i][j] = in.readInt();

        // Test constructor
        StdOut.println("INITIAL:");
    	Board initial = new Board(blocks);
    	StdOut.print(initial.toString());

    	// Test hamming and manhattan distance
    	StdOut.println("hamming: " + initial.hamming());
    	StdOut.println("manhattan: " + initial.manhattan());

    	// Test twin
    	StdOut.println();
    	StdOut.println("TWIN:");
    	Board twin = initial.twin();
    	StdOut.print(twin.toString());
    	StdOut.println("hamming: " + twin.hamming());
    	StdOut.println("manhattan: " + twin.manhattan());

    	// Test neighbor
    	StdOut.println();
    	StdOut.println("NEIGHBORS:");
    	ArrayList<Board> neighbors = (ArrayList<Board>)initial.neighbors();
    	for (Board neighbor:neighbors) {
    		StdOut.print(neighbor.toString());
    		StdOut.println("hamming: " + neighbor.hamming());
    		StdOut.println("manhattan: " + neighbor.manhattan());

    		StdOut.println();
    	}


	}
}