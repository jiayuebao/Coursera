/*-----------------------------------------------------------------------------------------
 *  Author:        Jiayue Bao
 *  Last Updated:  8/03/2018
 *
 *  Compilation:   javac-algs4 BruteCollinear.java
 *  Execution:     java-algs4 BruteCollinear input8.txt
 *  Dependencies:  Point.java LineSegment.java
 *
 *  Given a set of n distinct points(x,y), find every line segment made up of
 *  4 different points.
 *
 *  This brute force method examines 4 points at a time and checks whether 
 *  they all lie on the same line segment. 
 *
 *  Input that has 5 or more collinear points are not supported here.
 *  We compare this method with FastCollinearPoints.java to learn the importance of sorting.  
 *
 *-------------------------------------------------------------------------------------------*/
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
	private List<LineSegment> linesList;

	/** finds all line segments containing 4 points */
	public BruteCollinearPoints(Point[] points) {
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
		for(int i = 0; i < points.length-3;i++)
			for(int j = i+1; j < points.length-2;j++)
				for(int k = j+1; k < points.length-1;k++)
					for(int l = k+1; l < points.length;l++) {
						
						double slope1 = points[i].slopeTo(points[j]);
						double slope2 = points[i].slopeTo(points[k]);
						double slope3 = points[i].slopeTo(points[l]);
						if(slope1 == slope2 && slope2 == slope3){
							Point[] target = new Point[4];
							target[0] = points[i]; 
							target[1] = points[j];
							target[2] = points[k]; 
							target[3] = points[l];
							Arrays.sort(target);
							linesList.add(new LineSegment(target[0],target[3]));
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
    	BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    	for (LineSegment segment : collinear.segments()) {
        	StdOut.println(segment);
        	segment.draw();
    	}
    	StdDraw.show();
	}
}