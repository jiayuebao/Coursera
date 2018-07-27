//import edu.princeton.cs.algs4.*;
public class PercolationStats {
	private double[] threshold;
	private double mean;
	private double stddev;
	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) throw new IllegalArgumentException("n or trials <=0.");
		threshold = new double[trials];
		for (int i=0; i < trials;i++) {
			Percolation sites = new Percolation(n);
			while(!sites.percolates()) {
				
				int row = (int)(n*Math.random())+1;
				int col = (int)(n*Math.random())+1;
	
				if (!sites.isOpen(row,col)) sites.open(row,col);
			}

			threshold[i] = (double)sites.numberOfOpenSites()/(double)(n*n);
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		double sum = 0;
		for (double x : threshold) {
			sum += x;
		}

		mean = sum/threshold.length;
		return mean;

	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		double sum = 0;
		for (double x : threshold) {
			sum = sum + (x-mean)*(x-mean);
		}

		stddev = Math.sqrt(sum/(threshold.length-1));
		return stddev;

	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean - 1.96*stddev/Math.sqrt(threshold.length);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean + 1.96*stddev/Math.sqrt(threshold.length);
	}

	// test client
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Please enter the size and trials.");
			return;
		}

		int size = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);

		PercolationStats stats = new PercolationStats(size,trials);
		System.out.println("mean                    = "+stats.mean());
		System.out.println("stddev                  = " +stats.stddev());
		System.out.println("95% confidence interval = [" +stats.confidenceLo()+", "+stats.confidenceHi()+"]");

	}
}