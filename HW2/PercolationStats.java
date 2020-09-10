/* *****************************************************************************
 * File Name: PercolationStats.java
 * Name: Frendy Lio
 * NetID: frendy@hawaii.edu
 *
 * More information:
   * N = size of each side of a grid
   * T = Number of Experiments/ Trials
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  /* ===================================================================
    Variables
  =================================================================== */
  private static final double CONF95 = 1.96;
  private double[] PercolationStats;
  private int T;
  /* ===================================================================
    Perform T independent experiments on an N-by-N grid
    =================================================================== */
  public PercolationStats(int N, int T){
    if (N <= 0 || T <= 0)
      throw new IllegalArgumentException("n or trials must > 0");
    
    // Variables
    Percolation percolation;
    int row;
    int col;
    int totalOpen;
    double threshold;
    PercolationStats = new double[T];

    this.T = T; // Use for lOw and Hi
    // Do T trials
    for (int i = 0; i < T;  i++){
      percolation = new Percolation(N);
    
      // Keep opening sites till it percolates
      while(percolation.percolates() == false){
        row = (int) (StdRandom.random() * (N)) +1; // Random return [0, 1)
        col = (int) (StdRandom.random() * (N)) +1; // Random return [0, 1)
        percolation.open(row,col); // Open site
      }
      
      totalOpen = percolation.numberOfOpenSites();
      threshold = (double) (totalOpen)/ (double)(N*N) ;
      PercolationStats[i] = threshold;
    } 
  }

  /* ===================================================================
   sample mean of percolation threshold
   =================================================================== */
  public double mean(){
    return StdStats.mean(this.PercolationStats);
  }

  /* ===================================================================
   sample standard deviation of percolation threshold
   =================================================================== */

  public double stddev(){
    return StdStats.stddev(this.PercolationStats);
  }

  /* ===================================================================
   low  endpoint of 95% confidence interval
   =================================================================== */
  public double confidenceLo(){
    return mean() - ((CONF95 * stddev()) / Math.sqrt(this.T));
  }

  /* ===================================================================
   high endpoint of 95% confidence interval
   =================================================================== */
  public double confidenceHi(){
    return mean() + ((CONF95 * stddev()) / Math.sqrt(this.T));
  }

  /* ===================================================================
  // test client
  =================================================================== */
  public static void main(String[] args) {
    Stopwatch Stopwatch = new Stopwatch();

    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    if (n <= 0 || trials <= 0)
      throw new IllegalArgumentException("n or trials must > 0");
    //your code here
    PercolationStats myPS = new PercolationStats(n, trials);

    StdOut.println("mean = " + myPS.mean());
    StdOut.println("stddev = " + myPS.stddev());
    String conf = "95% confidence interval = [" + myPS.confidenceLo();
    StdOut.println(conf + ", " + myPS.confidenceHi() + "]");
    
    double timePass = Stopwatch.elapsedTime();
    StdOut.println("Time Passed:" + timePass);

  }
}