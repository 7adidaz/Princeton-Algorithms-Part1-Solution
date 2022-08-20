import java.util.ArrayList;

public class Board {
  private final int[][] tiles;
  private final int n;

  // list of out of place tiles
  // private int[] outOfPlace = new int[1000];
  // private int[][] outOfPlace_coord = new int[1000][2];

  private ArrayList<Board> arrNeighors = new ArrayList<Board>();

  public Board(int[][] tiles) {

    this.tiles = tiles;
    n = tiles.length;
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(n + "\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        s.append(String.format("%2d ", tiles[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }
  // public String toString() {
  // String out = "";
  // out += (n + "\n");
  // for (int i = 0; i < n; i++) {
  // for (int j = 0; j < n; j++) {
  // out += tiles[i][j];
  // out += " ";
  // }

  // out += "\n";

  // }
  // return out;
  // }

  public int dimension() {
    return tiles.length;
  }

  public int hamming() {
    int counter = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        // a mapping from 2d to 1d (zero indexed but i added one to eb 1 indexed)
        int toArr = i * n + j + 1;
        // skipping the empty square and counting out of place numbers;
        if (tiles[i][j] != toArr && tiles[i][j] != 0) {
          counter++;
        }
      }
    }
    return counter;
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {

    int value = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int realValue = (i * n + (j + 1));
        if (tiles[i][j] != realValue && tiles[i][j] != 0) {
          int toGetValue = tiles[i][j] - 1;
          int newi = toGetValue / n;
          int newj = toGetValue % n;
          value += Math.abs(newi - i) + Math.abs(newj - j);
        }
      }
    }
    return value;
    // int oldx, oldy;
    // int newi, newj;

    // int sum = 0;
    // for (int i = 0; i < this.hamming(); i++) {

    // // System.out.println("Tile : " + outOfPlace[i]);
    // oldx = outOfPlace_coord[i][0];
    // oldy = outOfPlace_coord[i][1];
    // // System.out.println((oldx + 1) + " : " + (oldy + 1));

    // newj = outOfPlace[i] % n;

    // newj--;

    // newi = (outOfPlace[i] - newj) / n;
    // if (newj < 0) {
    // newj = n - 1;
    // newi = (outOfPlace[i] - newj) / n;
    // }
    // // newi++;

    // // System.out.println("original : ");
    // // System.out.println(newi + " : " + (newj));
    // // System.out.println(' ');
    // sum += (Math.abs(oldx - newi) + Math.abs(oldy - newj));
    // }
    // return sum;
  }

  public boolean isGoal() {

    int toTwoD;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        toTwoD = i * n + j + 1;
        if (tiles[i][j] != toTwoD && tiles[i][j] != 0) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean equals(Object y) {

    // String w = this.toString();
    // String w2 = y.toString();
    // return w.equals(w2);

    if (y == null || this.getClass() != y.getClass()) {
      return false;
    }

    Board other = (Board) y;
    if (this.dimension() != other.dimension()) {
      return false;
    }
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (this.tiles[i][j] != other.tiles[i][j]) {
          return false;
        }
      }
    }
    return true;

  }

  public Iterable<Board> neighbors() {

    this.getNeighbors();
    return arrNeighors;
  }

  private void getNeighbors() {
    // we get the position of the empty tile first;
    // arrNeighors

    int emptyI = 0;
    int emptyJ = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (tiles[i][j] == 0) {

          emptyI = i;
          emptyJ = j;

        }
      }
    }
    // System.out.println("ZERO AT : " + emptyI + " : " + emptyJ);
    int[] tox = { 0, 1, 0, -1 };
    int[] toy = { -1, 0, 1, 0 };

    int newx, newy;
    for (int i = 0; i < 4; i++) {
      newx = emptyI + tox[i];
      newy = emptyJ + toy[i];
      if (validate(newx, newy)) {

        int[][] tempArray = arrCopy(tiles);
        // System.out.println("switching : " + newx + " : " + newy);

        int temp = tempArray[emptyI][emptyJ];
        tempArray[emptyI][emptyJ] = tempArray[newx][newy];
        tempArray[newx][newy] = temp;

        Board tempB = new Board(tempArray);

        arrNeighors.add(tempB);
      }
    }

  }

  private int[][] arrCopy(int[][] original) {

    int size = original.length;
    int[][] clone = new int[size][size];

    for (int i = 0; i < size; i++) {

      for (int j = 0; j < size; j++) {

        clone[i][j] = original[i][j];
      }
    }
    return clone;
  }

  private boolean validate(int x, int y) {
    return x < n && x >= 0 && y < n && y >= 0;
  }

  public Board twin() {
    int[][] tempArray = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        tempArray[i][j] = tiles[i][j];

      }
    }

    if (tempArray[0][0] == 0) {
      swap(tempArray, 0, 1, 1, 1);
    } else {
      if (tempArray[0][1] == 0) {
        swap(tempArray, 0, 0, 1, 0);
      } else {

        swap(tempArray, 0, 0, 0, 1);
      }
    }

    // int temp = tempArray[ith][jth];
    // tempArray[ith][jth] = tempArray[ith2][jth2];
    // tempArray[ith2][jth2] = temp;
    Board tempBoard = new Board(tempArray);
    return tempBoard;

  }

  private void swap(int[][] arr, int i, int j, int i2, int j2) {

    int temp = arr[i][j];
    arr[i][j] = arr[i2][j2];
    arr[i2][j2] = temp;

  }

  public static void main(String[] args) {

    // testing board and toString and hamming and manhattan
    // and is goal

    // int[][] tiles = {
    // { 0, 3 }, { 1, 2 } };

    // manhattan = 10;
    int[][] tiles = {
        { 8, 1, 3 },
        { 4, 0, 2 },
        { 7, 6, 5 }
    };

    int[][] tilesCopy = {
        { 8, 1, 3 },
        { 4, 0, 2 },
        { 7, 6, 5 }
    };

    // manhattan = 4;
    int[][] tiles2 = {
        { 0, 1, 3 },
        { 4, 2, 5 },
        { 7, 8, 6 }
    };

    // manhattan = 4;
    // int[][] tiles = {
    // { 0, 2, 3 },
    // { 4, 5, 6 },
    // { 7, 8, 1 }
    // };

    // goal
    // int[][] tiles = {
    // { 1, 2, 3 },
    // { 4, 5, 6 },
    // { 7, 8, 0 }
    // };

    Board b = new Board(tiles);

    Board b2 = new Board(tilesCopy);
    // Board b2 = new Board(tiles2);
    System.out.println(b.toString());
    System.out.println("N : " + b.dimension());
    System.out.println("hamming : " + b.hamming());
    System.out.println("MANHATTAN : " + b.manhattan());
    System.out.println(b.equals(b2));

    // System.out.println(b.equals(b3));

    // System.out.println("Goal : " + b.isGoal());
    // --passed

    // testing equals, neighbors is working, twin;

    // goal
    // int[][] tiles = {
    // { 1, 2, 3 },
    // { 4, 0, 6 },
    // { 7, 8, 5 }
    // };
    // int[][] tiles2 = {
    // { 1, 2, 3 },
    // { 4, 5, 6 },
    // { 7, 8, 0 }
    // };

    // int[][] tiles3 = {

    // { 5, 3, 1, 4 },
    // { 10, 2, 8, 7 },
    // { 14, 13, 0, 11 },
    // { 6, 9, 15, 12 }

    // };

    // Board b = new Board(tiles3);
    // System.out.println(b.manhattan());
    // // // Board b2 = new Board(tiles2);
    // // System.out.println(b.toString());
    // // System.out.println("________________________");
    // // b.getNeighbors();
    // // System.out.println(b2.toString());

    // // System.out.println("________________________");
    // // b.getNeighbors();
    // // b.printer();
    // // for (Board board : b.neighors()) {
    // System.out.println("original : ");
    // System.out.println(b.toString());
    // System.out.println("________________________");
    // // }

    // System.out.println(b.twin().toString());
    // System.out.println();
    // System.out.println("equal : " + b.equals(b2));
    // -----passed

    // ArrayList<Integer> a = new ArrayList<Integer>();
    // a.add(3);
    // a.add(2);
    // a.add(0);
    // for (int i = 0; i < a.size(); i++) {

    // System.out.println(a);
    // }
    // for (int i : a) {
    // System.out.println(i);

    // }

  }
}
