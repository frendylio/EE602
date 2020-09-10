/* *****************************************************************************
 * File Name: Percolation.java
 * Name: Frendy Lio
 * NetID: frendy@hawaii.edu
 *
 * More information:
   * N = size of each side of a grid
   * Row = 1 to N NOT 0 to N - 1... In PercolationVisualizer
 ******************************************************************************/


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  /* ===================================================================
    Variables
  =================================================================== */
  private int totalOpen; // remember total num of open sites
  private int N = 0;

  private boolean[][] Grid; // storages open and blocked sites

  // WeightedQuickUnionUF
  private WeightedQuickUnionUF WQUGrid; // percolation with constant time
  private WeightedQuickUnionUF WQUFull; // so it doesnt get backWash, remove virtual sink
  private int source = 0;
  private int sink =0;
  /*================
    Helper function
  ==================*/
  public boolean OutOfBounds(int row, int col){
    return (row <= 0 || row > this.N || col <= 0 || col > this.N);
  }

  public int FindGridPosition(int row, int col){
    return (row-1)*this.N + (col-1) + 1;
  }

  /* ===================================================================
    create N-by-N grid, with all sites blocked
  =================================================================== */
  public Percolation(int N){
    if (N <= 0) throw new IllegalArgumentException("N too small");

    // Create a Map to check if it is blocked or open
    // By default is False thus, we have a Map that is all Blocked
    Grid = new boolean[N][N];
    
    // Initialize variables
    this.totalOpen = 0;
    this.N = N;

    // WeightedQuickUnionUF Variables
    // 0 = source
    // N = sink
    this.WQUGrid = new WeightedQuickUnionUF(N*N + 2); // To make a virtual source and sink
    this.WQUFull = new WeightedQuickUnionUF(N*N + 1); // only virtual source
    this.sink = N*N + 1;
    return;
  }

  /* ===================================================================
   is the site (row, col) open?
  =================================================================== */
  public boolean isOpen(int row, int col) {
    if (this.OutOfBounds(row, col)) {
      throw new IndexOutOfBoundsException();
    }

    return Grid[row - 1][col - 1];
  }

   /* ===================================================================
    open site (row i, column j) if it is not open already
  =================================================================== */
   public void open(int row, int col) {
    if (this.OutOfBounds(row, col)) {     
      throw new IndexOutOfBoundsException();
    }

    // Base case if It is open already do nothing
    if (this.isOpen(row, col)){
      return;
    }

    // If it is close, we need to open it
    Grid[row - 1][col - 1] = true;

    // Increase counter
    this.totalOpen = this.totalOpen + 1;

    // Connect Open Sites
    int gridPosition = this.FindGridPosition(row, col);

    // if Top Row, connect to source
    if (row == 1){
      WQUGrid.union(this.source,gridPosition);
      WQUFull.union(this.source,gridPosition);
    }

    // If Bottom row, connect to sink
    if (row == this.N){
      WQUGrid.union(this.sink,gridPosition);
    }

    // Connect to Top if open
    if(!this.OutOfBounds(row - 1, col) && this.isOpen(row - 1, col)){
      WQUGrid.union(gridPosition,this.FindGridPosition(row - 1, col));
      WQUFull.union(gridPosition,this.FindGridPosition(row - 1, col));
    }

    // Connect to Bottom if open
    if(!this.OutOfBounds(row + 1, col) && this.isOpen(row + 1, col)){
      WQUGrid.union(gridPosition,this.FindGridPosition(row + 1, col));
      WQUFull.union(gridPosition,this.FindGridPosition(row + 1, col));
    }
    // Connect to LEFT if open
    if(!this.OutOfBounds(row, col - 1) && this.isOpen(row, col - 1)){
      WQUGrid.union(gridPosition,this.FindGridPosition(row, col - 1));
      WQUFull.union(gridPosition,this.FindGridPosition(row, col - 1));
    }
    // Connect to RIGHT if open
    if(!this.OutOfBounds(row, col + 1) && this.isOpen(row, col + 1)){
      WQUGrid.union(gridPosition,this.FindGridPosition(row, col + 1));
      WQUFull.union(gridPosition,this.FindGridPosition(row, col + 1));
    }

    return;
  }


  /* ===================================================================
    is site (row i, column j) full?
  =================================================================== */
  public boolean isFull(int row, int col) {
    if (this.OutOfBounds(row, col)) {
      throw new IndexOutOfBoundsException();
    }
    // use union to find if full
    if(this.WQUFull.connected(this.source, this.FindGridPosition(row, col)))
      return true;

    return false;
  }

  /* ===================================================================
    returns the number of open sites
    =================================================================== */
  public int numberOfOpenSites() {
    return this.totalOpen;
  }

  /* ===================================================================
    does the system percolate?
  =================================================================== */
  public boolean percolates(){
    return this.WQUGrid.connected(this.source, this.sink);
  }

  /* ===================================================================
  // test client
  =================================================================== */
  public static void main(String[] args) {
    // Test Group (1): opening random sites with the following n's until it percolates
    int[] nRandom = { 3, 5, 10, 20, 50, 250, 500, 1000, 2000 };
    for (int myi = 0; myi < nRandom.length; myi++) {
      int counter = 0;
      Percolation myPerRandomSites = new Percolation(nRandom[myi]);
      while (!myPerRandomSites.percolates()) {
        // generate a random site (row, col) to open
        int row = (int) (Math.random() * nRandom[myi]) + 1;
        int col = (int) (Math.random() * nRandom[myi]) + 1;
        if (!myPerRandomSites.isOpen(row, col)) {
          myPerRandomSites.open(row, col);
          counter++;
        }
      }
      StdOut.println("n = " + nRandom[myi] + " : Percolated after opening " + counter
          + " random sites");
    }

    // Test Group (2): catch exceptions
    Percolation myPerException = new Percolation(10);
    int[][] invalidSites = { { -1, 5 }, { 11, 5 }, { 0, 5 }, { 5, -1 },
        { -2147483648, -2147483648 }, { 2147483647, 2147483647 }
    };
    for (int myi = 0; myi < invalidSites.length; myi++) {
      try {
        myPerException.open(invalidSites[myi][0], invalidSites[myi][1]);
      }
      catch (IndexOutOfBoundsException indexOutOfBoundsException) {
        System.out.println(invalidSites[myi][0] + "," + invalidSites[myi][1]);
        System.out.println("open() causes an exception: IndexOutOfBoundsException.");
      }
    }


    // Test Group (3): invalid argument
    int[] myN = { -10, -1, 0 };
    
    for (int myi = 0; myi < myN.length; myi++) {
      try {
        Percolation TestPercolation = new Percolation(myN[myi]);
      }
      catch (IllegalArgumentException IllegalArgumentException){
        System.out.println("Constructor invalid argument:" + myN[myi]);
      }
    }
  }

}
