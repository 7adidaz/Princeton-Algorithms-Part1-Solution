import edu.princeton.cs.algs4.StdIn;

import edu.princeton.cs.algs4.StdOut;

public class Permutation {
  public static void main(String[] args) {
    int len = Integer.parseInt(args[0]);

    RandomizedQueue<String> randomQ = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      String s = StdIn.readString();
      randomQ.enqueue(s);
    }

    for (int i = 0; i < len; i++) {
      StdOut.println(randomQ.dequeue());
    }
  }
}
