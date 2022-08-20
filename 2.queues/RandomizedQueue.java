import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private int N = 1;
  private int size = 0;
  private Item[] items;
  // private int[] shuffled;

  public RandomizedQueue() {
    items = (Item[]) new Object[1];
    // shuffled = new int[1];

  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (N == size) {
      resize(N * 2);
      N = N * 2;
    }
    items[size] = item;
    // shuffled[size] = size;
    size++;
  }

  public Item dequeue() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    int randomIndex = StdRandom.uniform(size);

    Item t = items[randomIndex];
    items[randomIndex] = null;

    for (int i = randomIndex; i < size - 1; i++) {
      items[i] = items[i + 1];
    }

    size--;
    if (size == N / 4 && N > 0) {
      N = N / 2;
      resize(N);
    }
    return t;

  }

  public Item sample() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    int randomIndex = StdRandom.uniform(size);
    return items[randomIndex];
  }

  private void resize(int capacity) {
    Item[] copy = (Item[]) new Object[capacity];

    for (int i = 0; i < N; i++)
      copy[i] = items[i];
    items = copy;
  }

  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    private int start = 0;
    private int[] shuffled = arrConstructor(size);

    public boolean hasNext() {
      return start < size;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {

      return items[shuffled[start++]];
      // return shuffled[start++];
    }

  }

  private int[] arrConstructor(int sz) {
    int[] arr = new int[sz];
    for (int i = 0; i < sz; i++) {
      arr[i] = i;

    }
    // for (int i : arr) {
    // System.out.print(i + ", ");

    // }
    StdRandom.shuffle(arr);
    return arr;
  }

  public static void main(String[] args) {

    RandomizedQueue<Integer> rQueue = new RandomizedQueue<Integer>();
    System.out.println("empty: " + rQueue.isEmpty());
    rQueue.enqueue(2);
    rQueue.enqueue(21);
    rQueue.enqueue(68);

    rQueue.enqueue(-11);
    System.out.println("empty: " + rQueue.isEmpty());
    System.out.println("size: " + rQueue.size());
    System.out.println(rQueue.dequeue());
    for (int integer : rQueue) {
      System.out.print(integer + ", ");
    }
    System.out.println(" ");
    System.out.println(rQueue.sample());

    System.out.println("size: " + rQueue.size());
  }

  // private void print() {
  // for (int i = 0; i < size; i++) { // size or N // confirmed that the null
  // shifted;

  // if (items[i] == null) {

  // System.out.print("n, ");
  // } else {
  // System.out.print(items[i] + ", ");
  // }

  // }

  // System.out.println(" \n" + " _____________________");
  // }
  /// testinf
  // public static void main(String[] args) {

  // RandomizedQueue<Integer> R = new RandomizedQueue<Integer>();
  // R.enqueue(1);
  // R.enqueue(2);
  // R.enqueue(3);
  // R.enqueue(4);

  // for (int i = 0; i < 15; i++) {
  // int x = StdRandom.uniform(100);
  // System.out.print(x + ", ");
  // R.enqueue(x);
  // }
  // System.out.println(" \n" + " _____________________");

  // // R.print();
  // for (int i = 0; i < 7; i++) {

  // // R.dequeue();
  // // System.out.println(R.size + " : " + R.N);
  // // R.print();

  // // System.out.println(R.size + " : " + R.N);
  // System.out.print(R.dequeue() + ", ");
  // }

  // for (int integer : R) {

  // System.out.print(integer + ", ");
  // }

  // System.out.println(" \n" + " _____________________");
  // for (int integer : R) {
  // System.out.print(integer + ", ");

  // }
  // // StdRandom.shuffle(R);
  // System.out.println(" \n" + " _____________________");
  // // for (int integer : R) {
  // System.out.print(integer + ", ");

  // }
  // System.out.println(" \n" + " _____________________");
  // for (int i = 0; i < 15; i++) {
  // System.out.print(R.sample() + ", ");

  // }
  // System.out.println(" \n" + " _____________________");

  // R.print();

  // System.out.println(R.dequeue());
  // System.out.println("helloooooo");
  // System.out.println(R.size + " : " + R.N);
  // }
}
