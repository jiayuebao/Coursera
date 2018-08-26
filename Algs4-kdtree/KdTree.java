/**
 * Use a 2d-tree to support efficient range search (find all of the points contained in a query rectangle) 
 * and nearest-neighbor search (find a closest point to a query point).
 *
 * Created by Jiayue Bao on 08/26/2018.
 */

import java.util.TreeSet;
import java.awt.Color;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private Node root;
	private int size;
	private Point2D nearest;

	private static class Node {
		private Point2D point;
		private RectHV rect; // the axis-aligned rectangle corresponding to this node
		private Node lb; // the left/bottom subtree
		private Node rt; // the right/top subtree

		public Node(Point2D p,RectHV r) {
			point = p;
			rect  = r;
		}

	}

	/** construct an empty set of points */
	public KdTree() {
		root = null;
		size = 0;
	}

	/** is the set empty? */
	public boolean isEmpty() {
		return root == null;
	}

	/** number of points in the set */
	public int size() {
		return size;
	}

	/** add the point to the set (if it is not already in the set) */
	public void insert(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		root = insert(root,p,true,new RectHV(0.0,0.0,1.0,1.0));
	}

	private Node insert(Node node, Point2D p, boolean isX, RectHV rect) {
		if (node == null) {
			size++;
			return new Node(p,rect);
		}

		if (node.point.equals(p)) return node; // same point only add once

		double cmp = isX ? (p.x()-node.point.x()):(p.y()-node.point.y());

		if (cmp < 0) node.lb = insert(node.lb,p,!isX,subRect(node,isX,true));
		else node.rt = insert(node.rt,p,!isX,subRect(node,isX,false));

		return node;
	}

	
	/** return a sub-rectangular according to X/Y orientation and lb/rt subtree */
	private RectHV subRect(Node parent, boolean isX, boolean isLB) {
		RectHV parentRect = parent.rect;
		Double currX = parent.point.x();
		Double currY = parent.point.y();
		if (isX && isLB) 
			return new RectHV(parentRect.xmin(),parentRect.ymin(),currX,parentRect.ymax());
		else if (isX && !isLB)
			return new RectHV(currX,parentRect.ymin(),parentRect.xmax(),parentRect.ymax());
		else if (!isX && isLB)
			return new RectHV(parentRect.xmin(),parentRect.ymin(),parentRect.xmax(),currY);
		else
			return new RectHV(parentRect.xmin(),currY,parentRect.xmax(),parentRect.ymax());
	}

	/** does the set contain point p */
	public boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		return contains(p,true,root);
	}

	private boolean contains(Point2D p, boolean isX,Node node) {
		if (node == null) return false;
		if (p.equals(node.point)) return true;

		double cmp = isX ? (p.x()-node.point.x()):(p.y()-node.point.y());
		if (cmp < 0) return contains(p,!isX,node.lb);
		else return contains(p,!isX,node.rt);

	}

	/** draw all points to standard draw */
	public void draw() {
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius();
		new RectHV(0,0,1,1).draw();
		draw(root,true);
	}

	private void draw(Node node,boolean isRed) {
		if (node == null) return;
		StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
		node.point.draw();

		StdDraw.setPenRadius();
		if (isRed) {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.line(node.point.x(),node.rect.ymin(),node.point.x(),node.rect.ymax());
		}
		else {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.line(node.rect.xmin(),node.point.y(),node.rect.xmax(),node.point.y());
		}

		draw(node.lb,!isRed);
		draw(node.rt,!isRed);
	}

	/** all points that are inside the rectangle (or on the boundary) */
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) throw new IllegalArgumentException();
		TreeSet<Point2D> set = new TreeSet<>();
		range(set, rect, root);
		return set;

	}

	private void range(TreeSet<Point2D> set, RectHV rect, Node node) {
		if (node == null) return;
		if (rect.contains(node.point)) set.add(node.point);
		if (node.lb != null && rect.intersects(node.lb.rect)) 
			range(set,rect,node.lb);
		if (node.rt != null && rect.intersects(node.rt.rect)) 
			range(set,rect,node.rt);
		
	}

	/** a nearest neighbor in the set to point p; null if the set is empty */
	public Point2D nearest(Point2D p) {
		if (p == null) throw new IllegalArgumentException();
		nearest(p,root);
		return nearest;
	}

	private void nearest(Point2D p, Node node) {
		if (node == null) return;
		if (nearest == null || node.point.distanceTo(p) < nearest.distanceTo(p)) 
			nearest = node.point;

		if (node.lb != null && node.lb.rect.distanceTo(p) < nearest.distanceTo(p)) 
			nearest(p,node.lb);
		if (node.rt != null && node.rt.rect.distanceTo(p) < nearest.distanceTo(p)) 
			nearest(p,node.rt);

	}

}