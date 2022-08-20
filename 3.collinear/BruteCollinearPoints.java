
import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;
import java.io.File;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {

  private int nOfsegments = 0;

  private LineSegment[] lineList;
  private Point[] smallAndLarg;
  private LineSegment lineSeg;

  private Point[][] finalSet;

  public BruteCollinearPoints(Point[] points) {
    if (points == null)
      throw new IllegalArgumentException();
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) {
        throw new IllegalArgumentException();
      }
      for (int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0) {

          throw new IllegalArgumentException();
        }
      }
    }

    finalSet = new Point[points.length][2];

    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        for (int j2 = j + 1; j2 < points.length; j2++) {
          for (int k = j2 + 1; k < points.length; k++) {

            if (isCollinear(
                points[i],
                points[j],
                points[j2],
                points[k])) {

              // System.out.println(i + " : " + j + " : " + j2 + " : " + k);
              // nOfsegments++;
              smallAndLarg = smallest(points, i, j, j2, k);
              adder(smallAndLarg[0], smallAndLarg[1]);
              // lineSeg = new LineSegment(smallAndLarg[0], smallAndLarg[1]);
              // ll.push(lineSeg);
              // i++;

            }
          }
        }
      }
    }
  }

  private void adder(Point small, Point large) {
    for (int i = 0; i < nOfsegments; i++) {
      if (small == finalSet[i][0] && large == finalSet[i][1]) {
        return;
      }
    }
    finalSet[nOfsegments][0] = small;

    finalSet[nOfsegments][1] = large;
    nOfsegments++;
    return;
  }

  private boolean isCollinear(Point p, Point q, Point r, Point s) {

    double slope1 = p.slopeTo(q);
    double slope2 = p.slopeTo(r);
    double slope3 = p.slopeTo(s);

    // // TOdO: COMMENT THIS:
    // System.out.println("slope 1 : " + slope1);
    // System.out.println("slope 2 : " + slope2);
    // System.out.println("slope 3 : " + slope3);

    if (slope1 == slope2 && slope2 == slope3) {
      return true;
    }
    return false;
  }

  public int numberOfSegments() {

    return nOfsegments;
  }

  public LineSegment[] segments() {
    lineList = new LineSegment[nOfsegments];

    LineSegment s;
    for (int i = 0; i < nOfsegments; i++) {

      lineList[i] = new LineSegment(finalSet[i][0], finalSet[i][1]);
      // s = ll.pop();
      // // System.out.println(s);
      // lineList[i] = s;
    }
    return lineList;

  }

  private Point[] smallest(Point[] points, int p, int q, int r, int s) {

    Point[] p2 = new Point[4];
    Point[] returnArr = new Point[2];
    p2[0] = points[p];
    p2[1] = points[q];
    p2[2] = points[r];
    p2[3] = points[s];
    Arrays.sort(p2);
    // for (int i = 0; i < p2.length; i++) {
    // System.out.println(p2[i]);
    // }
    returnArr[0] = p2[0];
    returnArr[1] = p2[3];
    return returnArr;

  }

  public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    // System.out.println("N : " + n);
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();

      points[i] = new Point(x, y);
      // System.out.println(points[i]);
      // points[i].draw();
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    // System.out.println(collinear.numberOfSegments());

    // LineSegment[] s = collinear.segments();
    // StdOut.println(s[1].p);

    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
    // Point[] p = new Point[7];

    // p[0] = new Point(3, 7);
    // p[1] = new Point(5, 1);
    // // TODO: fix the order of the points when collinear;
    // p[2] = new Point(4, 2);

    // p[3] = new Point(4, 4);

    // p[4] = new Point(0, 0);
    // p[5] = new Point(1, 1);
    // p[6] = new Point(3, 3);
    // // for (int i = 0; i < p.length; i++) {
    // // p[i].draw();
    // // }

    // BruteCollinearPoints BCP = new BruteCollinearPoints(p);

    // System.out.println(
    // BCP.numberOfSegments());

    // LineSegment[] LS = BCP.segments();
    // // System.out.println(BCP.ll.size());
    // // System.out.println(LS.length);
    // for (int i = 0; i < LS.length; i++) {

    // LS[i].draw();
    // System.out.println(
    // LS[i].toString());
    // }

  }
}
