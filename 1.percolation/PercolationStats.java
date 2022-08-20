import edu.princeton.cs.algs4.StdRandom;
import java.lang.Math;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

import edu.princeton.cs.algs4.StdIn;

public class PercolationStats {

  private double mean[];
  private int trials, n;

  // private boolean states;

  public PercolationStats(int n, int trials) {

    if (n < 1 || trials < 1) {
      throw new IllegalArgumentException();
    }

    this.trials = trials;
    int row, col;
    // double i;
    mean = new double[trials];
    this.n = n;
    for (int j = 0; j < this.trials; j++) {
      // System.out.println("j is counting : " + j);
      // i = 0;
      Percolation prc = new Percolation(this.n);
      // states = false;
      while (!prc.percolates()) {
        row = StdRandom.uniform(1, n + 1);
        col = StdRandom.uniform(1, n + 1);
        // System.out.println("R: " + row + ",C: " + col);
        prc.open(row, col);
        // i++;
        // states = this.prc.percolates();

        // System.out.println("percolates : " + states);
        // if (states) {
        // break;
        // }

      }

      // System.out.println(prc.numberOfOpenSites());
      mean[j] = (double) prc.numberOfOpenSites() / (n * n);
      // System.out.println();
      // System.out.println(i + " / " + (n * n) + ": " + mean[j]);
    }
  }

  public double mean() {

    return StdStats.mean(this.mean);
  }

  public double stddev() {

    return StdStats.stddev(this.mean);
  }

  public double confidenceLo() {
    double low = mean() - ((1.96 * stddev()) / Math.sqrt(trials));
    return low;

  }

  public double confidenceHi() {
    double hi = mean() + ((1.96 * stddev()) / Math.sqrt(trials));
    return hi;

  }

  public static void main(String[] args) {

    int n, t;
    // n = StdIn.readInt();

    // t = StdIn.readInt();

    n = Integer.parseInt(args[0]);

    t = Integer.parseInt(args[1]);

    // int d[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    // double mean = StdStats.mean(d);
    // System.out.println(mean);

    PercolationStats ps = new PercolationStats(n, t);
    // StdOut.println("n : " + n + ", t : " + t);
    StdOut.println("mean = " + ps.mean());
    StdOut.println("stddev = " + ps.stddev());
    StdOut.println("95% confidence interval = " + "[" + ps.confidenceLo() + ", "
        + ps.confidenceHi() + "]");

    // StdOut.println("________________________________________");
    // n = 20;
    // t = 10;

    // PercolationStats ps2 = new PercolationStats(n, t);
    // StdOut.println("n : " + n + ", t : " + t);
    // StdOut.println("mean =" + ps2.mean());
    // StdOut.println("stddev =" + ps2.stddev());
    // StdOut.println("[" + ps2.confidenceLo() + ", " + ps2.confidenceHi() + "]");

  }
}
