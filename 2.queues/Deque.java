
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  // public class Deque<Item> {
  private class Node {
    Item item;
    Node next;
    Node previous;
  }

  private Node first;
  private Node last;

  private int size = 0;

  public Deque() {
    // first.next = null;
    // first.previous = null;

    // last.next = null;
    // last.previous = null;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void addFirst(Item item) {

    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (size == 0) {

      Node new_node = new Node();
      new_node.item = item;
      first = new_node;
      last = new_node;

    } else {

      Node new_node = new Node();
      new_node.item = item;
      first.previous = new_node;
      new_node.next = first;
      first = new_node;

    }

    size++;
  }

  public void addLast(Item item) {

    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (size == 0) {

      Node new_node = new Node();
      new_node.item = item;
      first = new_node;
      last = new_node;

    } else {

      Node new_node = new Node();
      new_node.item = item;
      new_node.previous = last;
      last.next = new_node;
      last = new_node;
    }
    size++;
  }

  public Item removeFirst() {

    if (size == 0) {
      throw new NoSuchElementException();
    }

    Item t = first.item;

    if (size == 1) {
      first = null;
      last = null;
    } else {
      Node next = first.next;
      first.next = null;
      first = next;
      first.previous = null;
    }
    size--;
    return t;
  }

  public Item removeLast() {

    if (size == 0) {
      throw new NoSuchElementException();
    }
    Item t = last.item;

    if (size == 1) {

      first = null;
      last = null;
    } else {
      Node previous = last.previous;
      last.previous = null;
      last = previous;
      last.next = null;
    }

    size--;
    return t;

  }

  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {

    private Node current = first;

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      Item item = current.item;
      current = current.next;
      return item;
    }

  }

  public static void main(String[] args) {

    Deque<Integer> d = new Deque<Integer>();

    System.err.println(d.isEmpty());
    d.addFirst(1);

    d.addLast(100);
    d.addFirst(4);
    d.addFirst(-6);

    d.addLast(35);

    for (int i : d) {
      System.out.print(i + ", ");
    }
    // d.addFirst(null);
    System.out.println(d.removeFirst());
    System.out.println(d.removeLast());
    System.out.println(d.size());

    System.err.println(d.isEmpty());
    // System.out.println(d.removeLast());
    // System.out.println(d.removeLast());

  }
}
