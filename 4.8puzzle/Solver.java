
import java.util.ArrayList;
// import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

// import javax.crypto.SealedObject;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {

  private MinPQ<SearchNode> pQ = new MinPQ<SearchNode>(new ManhattanComparable());
  private MinPQ<SearchNode> pQ_twin = new MinPQ<SearchNode>(new ManhattanComparable());

  // // find a solution to the initial board (using the A* algorithm)
  // private SearchNode last;
  private int moves = 0;
  private boolean solvable = false;

  private final ArrayList<Board> iterableList = new ArrayList<Board>();

  public Solver(Board initial) {

    if (initial == null) {
      throw new IllegalArgumentException();
    }

    SearchNode start = new SearchNode(initial, null, 0);
    SearchNode start_twin = new SearchNode(initial.twin(), null, 0);

    pQ.insert(start);
    pQ_twin.insert(start_twin);

    int counter = 0;

    SearchNode deleted = null;
    SearchNode deleted_twin = null;

    while (!pQ.min().current.isGoal() && !pQ_twin.min().current.isGoal()) {

      deleted = pQ.delMin();
      for (Board b : deleted.current.neighbors()) {
        if (!checker(deleted, b)) {
          SearchNode temp = new SearchNode(b, deleted, deleted.moves + 1);
          // System.out.println(" MOVES : " + deleted.moves);
          // System.out.println( " P : " +deleted.priority);

          // System.out.println( deleted.current.toString());
          // System.out.println( " ");

          pQ.insert(temp);
        }
      }

      deleted_twin = pQ_twin.delMin();
      for (Board bt : deleted_twin.current.neighbors()) {
        if (!checker(deleted_twin, bt)) {
          SearchNode temp_twin = new SearchNode(bt, deleted_twin, deleted_twin.moves + 1);

          pQ_twin.insert(temp_twin);
          // System.out.println(temp.current.toString());
        }
      }
    }

    // ArrayList<Board> finalList = new ArrayList<Board>();
    ArrayList<Board> finalList = new ArrayList<Board>();
    SearchNode temp = pQ.delMin();

    if (temp.current.isGoal()) {
      solvable = true;
    }

    finalList.add(temp.current);

    while (temp.previous != null) {

      // System.out.println( " D : " + debugger);

      finalList.add(temp.previous.current);
      temp = temp.previous;
    }

    moves = finalList.size() - 1;
    for (int i = finalList.size() - 1; i >= 0; i--) {

      Board board = finalList.get(i);
      iterableList.add(board);
    }

  }

  private boolean checker(SearchNode node, Board b) {

    // System.out.println(" ");
    SearchNode temp = node;
    while ((temp = temp.previous) != null) {
      // System.out.println(" IS : " + temp.current.toString());
      // System.out.println(b.toString() + " END");
      // System.out.println(" ");

      if (temp.current.equals(b))
        return true;
    }
    return false;
  }

  public boolean isSolvable() {

    return solvable;
  }

  // / min number of moves to solve
  public int moves() {
    if (solvable)
      return this.moves;
    else
      return -1;
  }

  // // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (solvable) {

      return iterableList;
    } else {
      return null;
    }
  }

  private static class ManhattanComparable implements Comparator<SearchNode> {
    public int compare(SearchNode first, SearchNode second) {
      int diff = first.priority - second.priority;
      if (diff > 0)
        return 1;
      else if (diff < 0)
        return -1;
      else
        return 0;
    }
  }

  private static class SearchNode {

    private final int moves;
    private final SearchNode previous;
    private final Board current;
    private final int manhattan;
    private final int priority;

    private SearchNode(Board board, SearchNode prev, int move) {
      moves = move;
      previous = prev;
      current = board;
      manhattan = current.manhattan();
      priority = manhattan + moves;
    }
  }

  // public static void main(String[] args) {

  // // create initial board from file
  // In in = new In(args[0]);
  // int n = in.readInt();
  // int[][] tiles = new int[n][n];
  // for (int i = 0; i < n; i++)
  // for (int j = 0; j < n; j++)
  // tiles[i][j] = in.readInt();
  // Board initial = new Board(tiles);

  // // solve the puzzle
  // Solver solver = new Solver(initial);

  // // print solution to standard output
  // if (!solver.isSolvable())
  // StdOut.println("No solution possible");
  // else {
  // StdOut.println("Minimum number of moves = " + solver.moves());
  // for (Board board : solver.solution())
  // StdOut.println(board);
  // }
  // }

  public static void main(String[] args) {

    // int tiles2[][] = {
    // { 0, 1, 3 },
    // { 4, 2, 5 },
    // { 7, 8, 6 }
    // };

    // int tiles[][] = {
    // { 1, 2, 3 },
    // { 4, 0, 5 },
    // { 7, 8, 6 }
    // };

    // int tilesUnsol[][] = {
    // { 1, 2, 3 },
    // { 4, 5, 6 },
    // { 8, 7, 0 }
    // };

    int tiles_twin_sol[][] = {
        { 2, 1, 3 },
        { 4, 5, 6 },
        { 8, 7, 0 }
    };
    // int tiles3[][] = {
    // { 5, 3, 1, 4 },
    // { 10, 2, 8, 7 },
    // { 14, 13, 0, 11 },
    // { 6, 9, 15, 12 }
    // };

    Board board = new Board(tiles_twin_sol);

    // Board board = new Board(tiles_twin_sol);
    // // Board board = new Board(tiles);
    Solver s = new Solver(board);

    System.err.println("STEPS: " + s.moves());
    System.out.println("SOLVABLE : " + s.isSolvable());
    for (Board boardd : s.solution()) {
      System.out.println(boardd.toString());
    }

    // for (SearchNode sn : s.pQ) {

    // System.out.println(sn.current.toString());

    // }

  }

  // esting SearchNode class;
  // public static void main(String[] args) {

  // { 8, 1, 3 },
  ///

  // nt tiles2[][] = {

  // 4, 0, 5 }
  // 6, 7, 8 }
  // ;
  // // Board bb =

  // earchNode s2 = ne

  // / ArrayList

  // //
  // / Arr.add(s2)

  // // for (SearchNode ssNode : Arr) {
  // / System.out.println(ssNode.current.toString())

  // /

}
