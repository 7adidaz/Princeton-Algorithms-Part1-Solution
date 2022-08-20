import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.LinkedList;
import java.util.List;

public class KdTree {
  private twoDNode root = null;

  public KdTree() {
  }

  public boolean isEmpty() {
    return size(root) == 0;
  }

  public int size() {
    return size(root);
  }

  public void insert(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();

    root = put(root, p, true, null);
  }

  private twoDNode put(twoDNode node, Point2D point, boolean isVertical, twoDNode previous) {
    if (node == null) {
      return new twoDNode(point, isVertical, previous, 1);
    }

    if (node.point.compareTo(point) == 0)
      return node;

    if (node.compareTo(point) > 0) {
      node.left = put(node.left, point, !isVertical, node);
    } else {
      node.right = put(node.right, point, !isVertical, node);
    }
    node.size = 1 + size(node.left) + size(node.right);
    return node;
  }

  private int size(twoDNode x) {
    if (x == null)
      return 0;
    else
      return x.size;
  }

  public boolean contains(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();

    return get(root, p) != null;
  }

  private twoDNode get(twoDNode node, Point2D point) {
    if (node == null)
      return null;

    if (node.point.equals(point))
      return node;

    if (node.compareTo(point) > 0) {
      return get(node.left, point);
    } else {
      return get(node.right, point);
    }
  }

  public void draw() {
    if (root != null) {
      draw(root);
    }
  }

  private void draw(twoDNode node) {
    StdDraw.setPenRadius(0.005);

    Point2D point = node.point;
    RectHV rect = node.rect;

    if (node.isVertical) {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(point.x(), rect.ymin(), point.x(), rect.ymax());
    } else {
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(rect.xmin(), point.y(), rect.xmax(), point.y());
    }

    StdDraw.setPenRadius(0.03);
    StdDraw.setPenColor(StdDraw.BLACK);
    node.point.draw();

    if (node.left != null) {
      draw(node.left);
    }

    if (node.right != null) {
      draw(node.right);
    }
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null)
      throw new IllegalArgumentException();

    return range(root, rect, new LinkedList<Point2D>());
  }

  private List<Point2D> range(twoDNode node, RectHV rect, List<Point2D> points) {
    if (node == null)
      return points;

    if (node.rect.intersects(rect)) {
      if (rect.contains(node.point)) {
        points.add(node.point);
      }

      range(node.left, rect, points);
      range(node.right, rect, points);
    }

    return points;
  }

  private Point2D nearestPoint = null;

  public Point2D nearest(Point2D p) {
    if (p == null)
      throw new IllegalArgumentException();

    if (this.isEmpty())
      return null;

    nearestPoint = root.point;
    nearestPoint(root, p);

    return nearestPoint;
  }

  private void nearestPoint(twoDNode node, Point2D point) {
    if (node == null)
      return;

    if (node.rect.distanceTo(point) < nearestPoint.distanceTo(point)) {
      updateTheNearest(point, node.point);

      if (node.compareTo(point) > 0) {
        nearestPoint(node.left, point);
        nearestPoint(node.right, point);
      } else {
        nearestPoint(node.right, point);
        nearestPoint(node.left, point);
      }
    }
  }

  private void updateTheNearest(Point2D point, Point2D newPoint) {
    if (nearestPoint == null) {
      nearestPoint = newPoint;
      return;
    }

    double distance1 = point.distanceTo(nearestPoint);
    double distance2 = point.distanceTo(newPoint);

    if (distance2 < distance1) {
      nearestPoint = newPoint;
      return;
    }
    return;
  }

  private class twoDNode {
    private final Point2D point;
    private twoDNode left;
    private twoDNode right;
    private final boolean isVertical;
    private final RectHV rect;
    private int size;

    public twoDNode(Point2D p, boolean Vertical, twoDNode parent, int size) {
      point = p;
      isVertical = Vertical;
      this.size = size;

      if (parent == null) {
        this.rect = new RectHV(0, 0, 1, 1);
      } else {
        double minX = parent.rect.xmin();
        double minY = parent.rect.ymin();
        double maxX = parent.rect.xmax();
        double maxY = parent.rect.ymax();

        int result = parent.compareTo(point);

        if (isVertical) {
          if (result > 0) {
            maxY = parent.point.y();
          } else {
            minY = parent.point.y();
          }
        } else {
          if (result > 0) {
            maxX = parent.point.x();
          } else {
            minX = parent.point.x();
          }
        }

        this.rect = new RectHV(minX, minY, maxX, maxY);
      }
    }

    public int compareTo(Point2D that) {
      if (isVertical) {
        return Double.compare(this.point.x(), that.x());
      } else {
        return Double.compare(this.point.y(), that.y());
      }
    }

  }

  public static void main(String[] args) {

    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 1);
    StdDraw.setYscale(0, 1);

    // StdDraw.setPenColor(StdDraw.MAGENTA);
    StdDraw.setPenRadius(0.0045);
    // testing the insertion, Search and levelOrder;

    KdTree dT = new KdTree();
    Point2D p = new Point2D(.7, .2);
    Point2D p2 = new Point2D(.5, .4);

    // dT.insert(p);
    // dT.insert(p2);
    // dT.insert(new Point2D(.2, .3));
    // dT.insert(new Point2D(.4, .7));
    // dT.insert(new Point2D(.9, .6));

    double x, y;
    for (int i = 0; i < 30; i++) {

      x = StdRandom.uniform();
      y = StdRandom.uniform();

      dT.insert(new Point2D(x, y));
    }
    dT.draw();
    RectHV rect = new RectHV(0, 0, 0.8, .5);

    // RectHV rect = new RectHV(0, 0, 1, 1);
    // RectHV rect = new RectHV(0, 0, 0.5, .5);

    // rect.draw();
    for (int i = 0; i < 5; i++) {

      x = StdRandom.uniform();
      y = StdRandom.uniform();

      Point2D tst = new Point2D(x, y);

      StdDraw.setPenColor(StdDraw.ORANGE);
      tst.draw();
      // System.out.println(dT.nearest(tst));
      Point2D temp = dT.nearest(tst);

      StdDraw.setPenColor(StdDraw.BOOK_RED);
      StdDraw.setPenRadius(0.0015);

      temp.drawTo(tst);

      StdDraw.setPenColor(StdDraw.BOOK_BLUE);
      StdDraw.setPenRadius(0.0045);
      temp.draw();
    }

    // for (Point2D pnt : dT.range(rect)) {
    // System.out.println(pnt.toString());
    // pnt.draw();
    // }

    StdDraw.show();
  }

}
