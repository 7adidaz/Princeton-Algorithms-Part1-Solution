import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class PointSET {

  private final SET<Point2D> points = new SET<Point2D>();

  // construct an empty set of points
  public PointSET() {

  }

  public boolean isEmpty() // is the set empty?
  {
    return points.size() == 0;
  }

  public int size() // number of points in the set
  {
    return points.size();
  }

  public void insert(Point2D p) // add the point to the set(if it is not already in the set)
  {
    nullChecker(p);
    points.add(p);
  }

  public boolean contains(Point2D p) // does the set contain point p?
  {

    nullChecker(p);
    return points.contains(p);

  }

  public void draw() // draw all points to standard draw
  {
    for (Point2D point : points) {
      point.draw();
    }
  }

  public Iterable<Point2D> range(RectHV rect)
  // all points that are inside the rectangle (or on the boundary)
  {

    nullChecker(rect);
    SET<Point2D> inRange = new SET<Point2D>();
    for (Point2D point : points) {
      if (rect.contains(point)) {
        inRange.add(point);

      }
    }

    return inRange;
  }

  public Point2D nearest(Point2D p)
  // a nearest neighbor in the set to point p; null if the set is empty
  {

    nullChecker(p);
    double smallest = 10000;
    Point2D near = null;
    double distance;
    for (Point2D point : points) {
      distance = point.distanceSquaredTo(p);
      if (!p.equals(point) && distance < smallest) {
        smallest = distance;
        near = point;
      }
    }
    if (this.size() > 0) {

      return near;
    } else {
      return null;
    }
  }

  private void nullChecker(Object p) {
    if (p == null)
      throw new IllegalArgumentException();

  }

  // public static void main(String[] args) {

  // PointSET ps = new PointSET();

  // double x = StdRandom.uniform();
  // double y = StdRandom.uniform();

  // double x2 = StdRandom.uniform();
  // double y2 = StdRandom.uniform();

  // if (x > x2) {

  // double temp = x;
  // x = x2;
  // x2 = temp;
  // }

  // if (y > y2) {

  // double temp = y;
  // y = y2;
  // y2 = temp;
  // }
  // RectHV rec = new RectHV(x, y, x2, y2);

  // // RectHV rec = new RectHV(1.7, 1, 2.3, 3);
  // StdDraw.enableDoubleBuffering();
  // StdDraw.setXscale(0, 1);
  // StdDraw.setYscale(0, 1);

  // // StdDraw.setXscale(0, 11);
  // // StdDraw.setYscale(0, 11);

  // StdDraw.setPenColor(StdDraw.MAGENTA);
  // StdDraw.setPenRadius(0.0045);
  // rec.draw();

  // StdDraw.setPenColor(StdDraw.RED);
  // StdDraw.setPenRadius(0.0055);

  // for (int i = 0; i < 1000; i++) {
  // x = StdRandom.uniform();

  // y = StdRandom.uniform();
  // Point2D p = new Point2D(x, y);

  // ps.insert(p);
  // }

  // ps.draw();
  // StdDraw.setPenColor(StdDraw.BLUE);
  // for (Point2D p : ps.range(rec)) {
  // p.draw();

  // }

  // StdDraw.setPenColor(StdDraw.GREEN);

  // Point2D middle = null;
  // // Point2D middle = new Point2D(0.5, 0.5);
  // Point2D s = ps.nearest(middle);
  // s.drawTo(middle);

  // StdDraw.setPenColor(StdDraw.BLACK);
  // middle.draw();
  // // System.out.println(ps.points.toString());
  // StdDraw.show();
  // }
}
