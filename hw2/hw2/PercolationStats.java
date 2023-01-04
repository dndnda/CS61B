package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;


public class PercolationStats {
    // perform T independent experiments on an N-by-N grid

    private double[] thresholds;
    private double sqrtT;
    private int N;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        thresholds = new double[N];
        this.N = N;
        sqrtT = Math.sqrt(T);

        /* Monte Carlo Simulation */
        for (int t = 0; t < T; t++) {
            Percolation p = pf.make(N);
            thresholds[t] = simulate(p);
        }
    }

    private double simulate(Percolation p) {
        int[] openOrder = StdRandom.permutation(N * N);
        for (int i = 0; !p.percolates(); i++) {
            int x = coordinate(openOrder[i])[0];
            int y = coordinate(openOrder[i])[1];
            p.open(x, y);
        }
        return ((double) p.numberOfOpenSites()) / (N * N);
    }

    private int[] coordinate(int n) {
        return new int[]{n / N, n % N};
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / sqrtT;
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / sqrtT;
    }
}
