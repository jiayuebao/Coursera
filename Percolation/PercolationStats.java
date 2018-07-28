/*----------------------------------------------------------------
 *  Author:        Jiayue Bao
 *  Last Updated:  7/28/2018
 *
 *  Compilation:   javac-algs4 PercolationStats.java
 *  Execution:     java-algs4 PercolationStats
 *  
 *  Estimate the percolation threshold using Monte Carlo simulation.
 *  1. Initialize all sites to be blocked.
 *  2. Repeat the following until the system percolates:
 *       a. Choose a site uniformly at random among all blocked sites.
 *       b. Open the site.
 *  3. The fraction of sites that are opened when the system percolates 
 *     provides an estimate of the percolation threshold.
 *  
 *----------------------------------------------------------------*/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] threshold; // arrays recoding thresholds in different trials
	
	/* perform trials independent experiments on an n-by-n grid */
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) throw new IllegalArgumentException("n or trials <=0.");
		threshold = new double[trials];
		for (int i=0; i < trials;i++) {
			Percolation sites = new Percolation(n);
			while(!sites.percolates()) {
				// Returns a random integer uniformly in [0, n+1).
				int row = StdRandom.uniform(1,n+1);
				int col = StdRandom.uniform(1,n+1);
	
				if (!sites.isOpen(row,col)) sites.open(row,col);
			}

			threshold[i] = (double)sites.numberOfOpenSites()/(double)(n*n);
		}
	}

	/* sample mean of percolation threshold */
	public double mean() {
		return StdStats.mean(threshold);

	}

	/* sample standard deviation of percolation threshold */
	public double stddev() {
		return StdStats.stddev(threshold);
	}

	/* low endpoint of 95% confidence interval */
	public double confidenceLo() {
		return mean() - 1.96*stddev()/Math.sqrt(threshold.length);
	}

	/* high endpoint of 95% confidence interval */
	public double confidenceHi() {
		return mean() + 1.96*stddev()/Math.sqrt(threshold.length);
	}

	/* test client */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Please enter the size and trials.");
			return;
		}

		int size   = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);

		PercolationStats stats = new PercolationStats(size,trials);
		System.out.println("mean                    = "+stats.mean());
		System.out.println("stddev                  = " +stats.stddev());
		System.out.println("95% confidence interval = [" +stats.confidenceLo()+", "+stats.confidenceHi()+"]");

	}
}