/**
 * A brute-force implementation for range search (find all of the points contained in a query rectangle)
 * and nearest-neighbor search (find a closest point to a query point). 
 * 
 * Created by Jiayue Bao on 08/25/2018.
 */

import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	private TreeSet<Point2D> set;

	/** construct an empty set of points */
	public PointSET() {
		set = new TreeSet<Point2D>();
	}

	/** is the set empty? */
	public boolean isEmpty() {
		return set.isEmpty();
	}

	/** number of points in the set */
	public int size() {
		return set.size();
	}

	/** add the point to the set (if it is not already in the set) */
	public void insert(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		set.add(p);
	}

	/** does the set contain point p */
	public boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		return set.contains(p);
	}

	/** draw all points to standard draw */
	public void draw() {
		for (Point2D p: set)
			p.draw();
	}

	/** all points that are inside the rectangle (or on the boundary) */
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) throw new IllegalArgumentException();
		TreeSet<Point2D> range = new TreeSet<>();
		for (Point2D p :set) {
			if (rect.contains(p)) range.add(p);
		}
		return range;

	}

	/** a nearest neighbor in the set to point p; null if the set is empty */
	public Point2D nearest(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		if (set.isEmpty()) return null;

		Point2D nearest = set.first();
		for (Point2D q : set) {
			if (q.distanceTo(p) < nearest.distanceTo(p)) {
				nearest = q;
			}
		}
		return nearest;
	}
}