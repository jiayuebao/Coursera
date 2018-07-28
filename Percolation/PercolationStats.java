
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    
    private double mean; // sample mean of percolation threshold
    private double stddev; // sample standard deviation of percolation threshold
    private double confidenceLo; // low endpoint of 95% confidence interval
    private double confidenceHi; // high endpoint of 95% confidence interval
    private double[] est; // est[i] = estimate of percolation threshold in perc[i]
    
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException("invalid input");
        est = new double[T];
        for (int k = 0; k < T; ++k) {
            Percolation perc = new Percolation(N);
            double count = 0;
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, N+1);
                int j = StdRandom.uniform(1, N+1);
                if (perc.isOpen(i, j)) continue;
                perc.open(i, j);
                ++count;
            }
            est[k] = count / (N*N);
        }
        mean = StdStats.mean(est);
        stddev = StdStats.stddev(est);
        confidenceLo = mean - (1.96*stddev) / Math.sqrt(T);
        confidenceHi = mean + (1.96*stddev) / Math.sqrt(T);
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }
    
    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }
    
    // test client
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]), T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLo() 
                           + ", " + stats.confidenceHi());
    }
}