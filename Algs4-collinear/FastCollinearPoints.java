/*-----------------------------------------------------------------------
 *  Author:        Jiayue Bao
 *  Last Updated:  8/03/2018
 *
 *  Compilation:   javac-algs4 BruteCollinear.java
 *  Execution:     java-algs4 BruteCollinear input8.txt
 *  Dependencies:  Point.java LineSegment.java
 *
 *  Given a set of n distinct points(x,y), find every line segment made up of
 *  4 or more different points.
 *  
 *  A faster, sorting-based way to solve this pattern recognition problem.
 *  	step1: Think of p as the origin.
 *  	step2: Sort the points according to y coordinate first, 
 *  	       then the slopes they makes with p.
 *  	step3: Check if any 3 (or more) adjacent points in the sorted order 
 *  	       have equal slopes with respect to p. 
 *  	step4: Check if p is the lowest point of the line. 
 *  	       (if not, this line has been recorded.)
 *  	step5: Apply the process to each point.
 * 
 *-------------------------------------------------------------------------*/
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
	private List<LineSegment> linesList;

	/** finds all line segments containing 4 or more points */
	public FastCollinearPoints(Point[] points) {
		// check null argument
		if (points == null) throw new IllegalArgumentException("Null argument.");
		// check null in points array
		for (int i = 0; i < points.length;i++) {
			if (points[i] == null) throw new IllegalArgumentException("Null reference in points array.");
		}
		// check repeated points in points array
		for (int i = 0; i < points.length-1;i++) {	
			for (int j = i+1; j < points.length; j++) {
				if (points[i].compareTo(points[j]) == 0)throw new IllegalArgumentException("Repeated point in points array.");	
			} 	
		}

		linesList = new ArrayList<>();
		Point[] aux = Arrays.copyOf(points,points.length);
		for (int i = 0; i < points.length; i++) {
			Point origin = points[i]; 
			Arrays.sort(aux);
			Arrays.sort(aux,origin.slopeOrder()); 
			
			int start = 1;
			int end = 1;
			while (start < aux.length) {
				// count points with equal slope
				while (end < aux.length && origin.slopeTo(aux[start]) == origin.slopeTo(aux[end])) {
					end++;
				}
				//[start,end) has the same slope
				if(end - start >=3) {
					if(origin.compareTo(aux[start]) < 0) // the origin point lies the lowest
						linesList.add(new LineSegment(origin,aux[end-1]));
				}
				start = end;
			}			
		}
	}

	/** the number of line segments */
	public int numberOfSegments() {
		return linesList.size();
	}

	/** the line segments */
	public LineSegment[] segments() {
		LineSegment[] lines= new LineSegment[linesList.size()];
		return linesList.toArray(lines);
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