import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
	private WeightedQuickUnionUF grid;
	private int n;
	private int openSite;
	private boolean[] open;


	// create n-by-n grid, with all sites blocked
	public Percolation(int N) {
		if (N <= 0) throw new IllegalArgumentException("Grid size <= 0.");
		
		n = N;
		grid = new WeightedQuickUnionUF(n*n + 2);
		open = new boolean[n*n+1]; // mark whether the site is open
		openSite = 0; // number of open sites

		// connect the virtual top and bottom
		for (int i = 1; i <= n; i++) {
			grid.union(0,i); //virtual top
			grid.union(n*n+1,n*n+1-i); //virtual bottom
		} 
		
		
	}

	// open site(row, col) if it is not open already
	public void open(int row, int col) {
		if (row >= 1 && row <= n && col >= 1 && col <= n) {
			if (isOpen(row,col)) return;
			int index = col + (row-1)*n;
			open[index] = true;
			openSite = openSite + 1;

			if (row >= 2 && isOpen(row-1,col)) grid.union(index,index-n);//up
			if (row <= n-1 && isOpen(row+1,col)) grid.union(index,index+n);//down
			if (col >= 2 && isOpen(row,col-1)) grid.union(index,index-1);//left
			if (col <= n-1 && isOpen(row,col+1)) grid.union(index,index+1);//right
			
		} 
		else {
			throw new IllegalArgumentException("Outside the range.");
		}
		
		
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (row >= 1 && row <= n && col >= 1 && col <= n) {
			return open[col + (row-1)*n] == true;
		}
		else {
			throw new IllegalArgumentException("Outside the range.");
		}
	}

	// is site (row, col) full?(connected to the top)
	public boolean isFull (int row, int col) {
		if (row >= 1 && row <= n && col >= 1 && col <= n) {
			return grid.connected(0,col+(row-1)*n);
		}
		else {
			throw new IllegalArgumentException("Outside the range.");
		}
	}

	// number of open sites
	public int numberOfOpenSites() {
		return openSite;
	}

	// does the system percolate?
	public boolean percolates() {
		return grid.connected(0,n*n+1);
	}

}