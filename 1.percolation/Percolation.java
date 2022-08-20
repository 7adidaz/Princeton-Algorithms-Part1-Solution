import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {

  private WeightedQuickUnionUF wq;
  private int n;
  private int top = 0, bottom;
  private int nOfObj;
  private boolean[][] open;

  public Percolation(int n) {
    if (n < 1) {
      throw new IllegalArgumentException();
    }
    this.n = n;
    wq = new WeightedQuickUnionUF((n * n) + 2);
    open = new boolean[n][n];
    this.bottom = n * n + 1;
    this.nOfObj = 0;
  }

  public void open(int row, int col) {

    if (validIndex(row, col)) {
      if (!isOpen(row, col)) {
        this.open[row - 1][col - 1] = true;
        this.nOfObj++;
        if (col - 1 % this.n == 0) {
          // wq.union(place, place + 1);
          // System.out.println("(" + row + ", " + col + ")" + " is connectd to : " + "("
          // + row + ", " + (col + 1) + ")");
          if (isOpen(row, col + 1)) {

            wq.union(index(row, col), index(row, col + 1));
          }

        } else if (col == this.n) {

          if (isOpen(row, col - 1)) {

            wq.union(index(row, col), index(row, col - 1));
          }

          // System.out.println("(" + row + ", " + col + ")" + " is connectd to : " + "("
          // + row + ", " + (col - 1) + ")");
        } else {

          if (isOpen(row, col + 1)) {

            wq.union(index(row, col), index(row, col + 1));
          }

          if (isOpen(row, col - 1)) {

            wq.union(index(row, col), index(row, col - 1));
          }

          // System.out.println("(" + row + ", " + col + ")" + " is connectd to : " + "("
          // + row + ", " + (col + 1) + ")");
          // System.out.println("(" + row + ", " + col + ")" + " is connectd to : " + "("
          // + row + ", " + (col - 1) + ")");
        }

        if (row == 1) {
          if (isOpen(row + 1, col)) {
            wq.union(index(row, col), index(row + 1, col));
          }
          wq.union(index(row, col), this.top);

        } else if (row == this.n) {
          if (isOpen(row - 1, col)) {

            wq.union(index(row, col), index(row - 1, col));
          }

          wq.union(index(row, col), this.bottom);
        } else {
          if (isOpen(row - 1, col)) {

            wq.union(index(row, col), index(row - 1, col));
          }
          if (isOpen(row + 1, col)) {

            wq.union(index(row, col), index(row + 1, col));
          }

        }
      }

    } else
      throw new IllegalArgumentException();
  }

  public boolean isOpen(int row, int col) {
    if (validIndex(row, col)) {
      if (open[row - 1][col - 1]) {
        return true;
      }
      return false;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public int numberOfOpenSites() {
    return this.nOfObj;
  }

  public boolean isFull(int row, int col) {
    if (validIndex(row, col)) {
      return (wq.find(top) == wq.find(index(row, col))) && isOpen(row, col);
    } else {
      throw new IllegalArgumentException();
    }
  }

  public boolean percolates() {
    return wq.find(this.top) == wq.find(this.bottom);
  }

  private int index(int row, int col) {
    return (row - 1) * n + col;
  }

  private boolean validIndex(int row, int col) {
    if (row < 1 && col < 1 && row > n && col > n) {
      // throw new IllegalArgumentException();
      return false;
    }
    return true;
  }

  // public static void main(String[] args) {

  // Percolation p = new Percolation(6);

  // p.open(1, 6);
  // p.open(2, 6);
  // p.open(3, 6);
  // p.open(4, 6);
  // p.open(5, 6);
  // p.open(5, 5);

  // p.open(4, 5);
  // p.open(4, 4);
  // System.out.println(p.isFull(4, 4));

  // // p.open(3, 3);

  // // p.open(2, 2);

  // // p.open(3, 2);
  // // for (int i = 0; i < 20; i++) {
  // // System.out.println(p.isFull(2, 3));
  // // }
  // System.out.println("p: " + p.percolates());
  // System.out.println(p.numberOfOpenSites());
  // // System.out.println(p.index(l, k));
  // // System.out.println(p.validIndex(l, k));

  // }

}
