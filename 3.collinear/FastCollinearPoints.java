
import java.util.Arrays;
// import java.util.LinkedList;
import edu.princeton.cs.algs4.StdIn;
import java.io.File;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

import edu.princeton.cs.algs4.In;

// import java.awt.Point;

public class FastCollinearPoints {

  private Point[][] finalSetOfLines;

  private int nOfLines = 0;

  public FastCollinearPoints(Point[] points) {
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

    int pointsLength = points.length;

    // list of relative slopes for each point
    double relativeSlopes[] = new double[pointsLength];
    double sortedRelativeSlopes[] = new double[pointsLength];

    // alist of Line Point for doublicates;
    finalSetOfLines = new Point[pointsLength + 1][2];
    // first we loop over the points to get the slopes
    for (int i = 0; i < pointsLength; i++) {

      // System.out.println(" POINT : " + points[i]);
      // registuring the slopes
      for (int j = 0; j < pointsLength; j++) {
        relativeSlopes[j] = points[i].slopeTo(points[j]);
        sortedRelativeSlopes[j] = points[i].slopeTo(points[j]);
        // System.out.print(relativeSlopes[j] + ", ");
      }
      // sorting the slope
      Arrays.sort(sortedRelativeSlopes);

      // System.out.println(" ");
      // System.out.println("SORTED: ");
      // for (int k = 0; k < sortedRelativeSlopes.length; k++) {

      // System.out.print(sortedRelativeSlopes[k] + ", ");
      // }

      // System.out.println("---------------------- ");
      int doublicateCounter = 1;
      // find dublicate slopes

      for (int j = 0; j < pointsLength - 1; j++) {

        if (sortedRelativeSlopes[j] == sortedRelativeSlopes[j + 1]) {

          // System.out.println("is " +
          // sortedRelativeSlopes[j] + " : " + sortedRelativeSlopes[j + 1] +

          // (sortedRelativeSlopes[j] == sortedRelativeSlopes[j + 1]));
          doublicateCounter++;

        } else {

          // check if the dublicatecounter is larger than 3;
          if (doublicateCounter >= 3) {
            Point[] tempList = pointsOfSegments(sortedRelativeSlopes[j], relativeSlopes, points);

            in_FinalListOfPoints(tempList[0], tempList[1]);

            // }
          }

          doublicateCounter = 1;
        }
        if (sortedRelativeSlopes[pointsLength - 2] == sortedRelativeSlopes[pointsLength - 1]
            && doublicateCounter >= 3) {

          Point[] tempList = pointsOfSegments(sortedRelativeSlopes[j], relativeSlopes, points);

          in_FinalListOfPoints(tempList[0], tempList[1]);
        }
      }

      // System.out.println(" ");
      // System.out.println("_________________________________");

      // System.out.println(" ");
    }
  }

  private boolean in_FinalListOfPoints(Point small, Point large) {

    // checking wether the line's points already registerd

    for (int i = 0; i < nOfLines; i++) {
      if (small == finalSetOfLines[i][0] && large == finalSetOfLines[i][1]) {
        // System.out.println(small + " found : " + large + " : found.");
        return false;
      }
    }
    finalSetOfLines[nOfLines][0] = small;
    finalSetOfLines[nOfLines][1] = large;
    nOfLines++;

    // System.out.println(small + " : " + large);
    return true;

  }

  private Point[] pointsOfSegments(double doublicatedSlope, double[] listOfSlopes, Point[] points) {

    // finding the original slopes with points, then compairing the points to find
    // the largest and the smallest point
    // System.out.println(" ");
    // System.out.println("called with : " + doublicatedSlope);

    // System.out.println(" ");
    Point largestPoint = new Point(0, 0);
    Point smallestPoint = new Point(32767, 32767);
    // returing the largest and the smallest point in the list
    Point[] returnList = new Point[2];

    for (int i = 0; i < listOfSlopes.length; i++) {
      // if the i_th slope is equal to the required slope
      if (listOfSlopes[i] == doublicatedSlope || listOfSlopes[i] == Double.NEGATIVE_INFINITY) {

        // if the point with the targeted slope is less than the smalles point ( finding
        // smallest point on the line)
        if (points[i].compareTo(smallestPoint) < 0) {

          // System.out.println(points[i] + " compareTo " + smallestPoint + " is
          // smaller");
          smallestPoint = points[i];

        }
        // same as before ( finding the largest point on the segment)
        if (points[i].compareTo(largestPoint) > 0) {
          // System.out.println(points[i] + " compareTo " + smallestPoint + " is
          // larger!");

          largestPoint = points[i];
        }

      }

    }
    returnList[0] = smallestPoint;
    returnList[1] = largestPoint;
    return returnList;
  }

  public int numberOfSegments() {
    return nOfLines;
  }

  public LineSegment[] segments() {

    LineSegment[] lineSegments = new LineSegment[nOfLines];

    LineSegment tempLine;
    for (int i = 0; i < nOfLines; i++) {
      lineSegments[i] = new LineSegment(finalSetOfLines[i][0], finalSetOfLines[i][1]);

    }
    return lineSegments;

  }

  public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    // System.out.println(" SEG : " + collinear.numberOfSegments());
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }

  // public static void main(String[] args) {

  // }
}
