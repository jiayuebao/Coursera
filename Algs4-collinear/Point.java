/*----------------------------------------------------------------
 *  Compilation:  javac-algs4 Point.java
 *  Execution:    java-algs4 Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 *----------------------------------------------------------------*/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     */
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY; // equal
        else if (this.x == that.x) return Double.POSITIVE_INFINITY; // vertical 
        else if (this.y == that.y) return +0.0; // horizontal
 
        return ((double)that.y - this.y)/((double)that.x-this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     */
    public int compareTo(Point that) {
        if (this.y == that.y) {
            return this.x - that.x;
        }
        else {
            return this.y - that.y;
        }

    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeComparator();
    }

    private class SlopeComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double k1 = slopeTo(p1);
            double k2 = slopeTo(p2);

            if (k1 < k2) return -1;
            else if (k1 == k2) return 0;
            else return +1;
        }
    }
    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p0 = new Point(0,0);
        Point[] p = new Point[10];
        for (int i = 0; i < 10; ++i) {
            int x = StdRandom.uniform(1,100);
            int y = StdRandom.uniform(1,100);
            p[i] = new Point(x,y);
            p0.drawTo(p[i]);
        }
        for (int i = 0; i < 10; ++i) {
            StdOut.println(p[i]);
            StdOut.println(p[i].slopeTo(p0));
        }
        
        StdOut.println();
        Arrays.sort(p);
        for (int i = 0; i < 10; ++i)
            StdOut.println(p[i]);

        StdOut.println();
        Arrays.sort(p, p0.slopeOrder());
        for (int i = 0; i < 10; ++i)
            StdOut.println(p[i]);
    }
}