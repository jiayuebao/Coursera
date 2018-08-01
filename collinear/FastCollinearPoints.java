/*-----------------------------------------------------------------------
 *  Author:        Jiayue Bao
 *  Last Updated:  8/01/2018
 *
 *  Compilation:   javac-algs4 BruteCollinear.java
 *  Execution:     java-algs4 BruteCollinear input8.txt
 *  Dependencies:  Point.java LineSegment.java
 *
 *  A faster, sorting-based way to solve a pattern recognition problem.
 *  
 *  Given a set of n distinct points in the plane, find every (maximal) 
 *  line segment that connects a subset of 4. 
 *    
 *  This brute force method examines 4 points at a time 
 *  and checks whether they all lie on the same line segment, 
 *  returning all such line segments. 
 *
 *  Input that has 5 or more collinear points are not supported here.
 *  We compare this method to a faster, sorting-based solution.  
 *
 *-------------------------------------------------------------------------*/
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
	private List<LineSegment> lines;
	private LineSegment[] lineSegments;
	private int count = 0;

	/** finds all line segments containing 4 or more points */
	public FastCollinearPoints(Point[] points) {
		// validate the points array
		if (points == null) throw new IllegalArgumentException("Null argument.");
		for (int i = 0; i < points.length;i++) {
			if (points[i] == null) 
				throw new IllegalArgumentException("Null reference in points array.");
		}
		Arrays.sort(points);
		for (int i = 0; i < points.length-1;i++) {	 
			if (points[i].compareTo(points[i+1]) == 0)
				throw new IllegalArgumentException("Repeated point in points array.");			
		}

		lines = new ArrayList<>();
		double visited = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < points.length-3; i++) {
			// sort the array according to its y coordinate
			Arrays.sort(points);
			// the origin point
			Point origin = points[i]; 
			// sort the points according to slope
			Arrays.sort(points,i+1,points.length,origin.slopeOrder()); 
			// initialize the previous slope
			double prev = origin.slopeTo(points[i+1]); 
			// hold the points that are on the same line with the origin point
			List<Point> targetpoints = new ArrayList<>(); 
			targetpoints.add(points[i+1]);
			
			for (int j = i+2; j < points.length; j++) {
				double curr = origin.slopeTo(points[j]);
				
				if(curr != prev) {
                  	// find a line segment
				  	if (targetpoints.size() >= 3) {
				  		Point end = targetpoints.get(targetpoints.size()-1);
						if(i == 0 || Math.abs(origin.slopeTo(end))!=Math.abs(points[i-1].slopeTo(origin))) {
							lines.add(new LineSegment(origin, end));
							count++;
						}
						
					}
					targetpoints.clear(); // empty the targetpoints
				}
				targetpoints.add(points[j]);
				prev = curr;
			}

			// find a line segment
			if (targetpoints.size() >= 3) {
				Point end = targetpoints.get(targetpoints.size()-1);
				if(i == 0 || Math.abs(origin.slopeTo(end))!=Math.abs(points[i-1].slopeTo(origin))) {
							lines.add(new LineSegment(origin, end));
							count++;
				}
			}
			
		}


	}


	/** the number of line segments */
	public int numberOfSegments() {
		return count;
	}

	/** the line segments */
	public LineSegment[] segments() {
		lineSegments= new LineSegment[count];
		for (int i = 0; i < count; i++) {
			lineSegments[i] = lines.get(i);
		}
		return lineSegments;
	}

	/** test client */
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
    	for (LineSegment segment : collinear.segments()) {

        	StdOut.println(segment);
        	segment.draw();
    	}
    	StdDraw.show();
	}
}

