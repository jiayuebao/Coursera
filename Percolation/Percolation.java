/****************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:  java Percolation input20.txt
 *  Dependencies: In.java StdOut.java WeightedQuickUnionUF.java
 *
 *  The Percolation System Model.
 *
 ****************************************************************************/

/**
 *  The <tt>Percolation</tt> class represents a percolation system data structure.
 *  It supports the <em>open</em> and <em>percolates</em> operations, along with
 *  methods for determinig whether a specified site is open or full.
 * 
 *  @author YunHsiao Wu
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private int N; // number of columns plus 1
    private WeightedQuickUnionUF uf; // union-find data structure
    // open[i][j] = the condition of site at row i, column j:
    // 0 - Blocked site   1 - Open site    2 - Open site connected to the bottom
    private byte[][] open;
    private int count;
    
    /**
     * Create N-by-N grid, with all sites blocked
     * @throws java.lang.IllegalArgumentException if N <= 0
     * @param N number of row and columns
     */ 
    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException("invalid input");
        this.N = N+1;
        uf = new WeightedQuickUnionUF((N+1)*(N+1));
        open = new byte[N+1][N+1];
        count = 0;
    }
    
    // validate that i and j are valid indices
    private void validate(int i, int j) {
        if (i <= 0 || i >= N) 
            throw new IllegalArgumentException("row index i out of bounds");
        if (j <= 0 || j >= N) 
            throw new IllegalArgumentException("column index j out of bounds");
    }
    
    // union operation for both UF instances
    private void union(int i, int j) {
        uf.union(i, j);
    }
    
    /**
     * Open site (row <tt>i</tt>, column <tt>j</tt>) if it is not open already
     * @param i the integer representing the row index
     * @param j the integer representing the column index
     * @throws java.lang.IllegalArgumentException 
     *     unless both 0 < i <= N and 0 < j <= N
     */ 
    public void open(int i, int j) {
        validate(i, j);
        if (isOpen(i, j)) return;
        
        open[i][j] = 1;
        count++;
        // since they won't be used as open sites, 
        // we make 0 represent the virtual-top, 1 represent the virtual-bottom.
        if (i == N - 1) open[i][j] = 2;
        if (i == 1) { 
            uf.union(0, i * N + j);
            if (open[i][j] == 2) open[0][0] = 2; // 1-by-1 grid corner case
        }

        if (j + 1 < N && isOpen(i, j + 1)) {
            int q = uf.find(i * N + j);
            int p = uf.find(i * N + j + 1);
            union(i * N + j, i * N + j + 1);
            if (open[p / N][p % N] == 2 || open[q / N][q % N] == 2) {
                int t = uf.find(i * N + j);
                open[t / N][t % N] = 2;
            }
        }
        if (j - 1 > 0 && isOpen(i, j - 1)) {
            int q = uf.find(i * N + j);
            int p = uf.find(i * N + j - 1);
            union(i * N + j, i * N + j - 1);
            if (open[p / N][p % N] == 2 || open[q / N][q % N] == 2) {
                int t = uf.find(i * N + j);
                open[t / N][t % N] = 2;
            }
        }
        if (i - 1 > 0 && isOpen(i - 1, j)) {
            int q = uf.find(i * N + j);
            int p = uf.find((i-1) * N + j);
            union(i * N + j, (i-1) * N + j);
            if (open[p / N][p % N] == 2 || open[q / N][q % N] == 2) {
                int t = uf.find(i * N + j);
                open[t / N][t % N] = 2;
            }
        }
        if (i + 1 < N && isOpen(i+1, j)) {
            int q = uf.find(i * N + j);
            int p = uf.find((i + 1) * N + j);
            union(i * N + j, (i+1) * N + j);
            if (open[p / N][p % N] == 2 || open[q / N][q % N] == 2) {
                int t = uf.find(i * N + j);
                open[t/N][t % N] = 2;
            }
        }
    }
    
    /**
     * Is site (row <tt>i</tt>, column <tt>j</tt>) open?
     * @param i the integer representing the row index
     * @param j the integer representing the column index
     * @return <tt>true</tt> if the site at row <tt>i</tt>, column <tt>j</tt>
     *     is open and <tt>false</tt> otherwise
     * @throws java.lang.IllegalArgumentException 
     *     unless both 0 < i <= N and 0 < j <= N
     */
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return open[i][j] > 0;
    }
    
    /**
     * Is site (row <tt>i</tt>, column <tt>j</tt>) full?
     * @param i the integer representing the row index
     * @param j the integer representing the column index
     * @return <tt>true</tt> if the site at row <tt>i</tt>, column <tt>j</tt>
     *     is full and <tt>false</tt> otherwise
     * @throws java.lang.IllegalArgumentException 
     *     unless both 0 < i <= N and 0 < j <= N
     */ 
    public boolean isFull(int i, int j) {
        validate(i, j);
        return open[i][j] > 0 && uf.connected(0, i*N+j);
    }
    
    /**
     * Does the system percolate?
     * @return <tt>true</tt> if the system pocolates and <tt>false</tt> otherwise
     */ 
    public boolean percolates() {
        int root = uf.find(0);
        return open[root/N][root % N] == 2;
    }

    /**
     * number of open sites
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return count;
    }
    
    /**
     * Reads in a sequence of pairs of integers (between 0 and N-1) from input file,
     * where each two integer represents a open site position;
     * open the sites until the system percolates or meet the end of file, 
     * print open sites count and its percolation condition.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int N = in.readInt();         // N-by-N percolation system
        Percolation perc = new Percolation(N);
        boolean percolates = false;
        int count = 0;
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            if (!perc.isOpen(i, j))
                ++count;
            perc.open(i, j);
            percolates = perc.percolates();
            if (percolates) break;
        }
        StdOut.println(count + " open sites");
        if (percolates) StdOut.println("percolates");
        else StdOut.println("does not percolate");
    }
}