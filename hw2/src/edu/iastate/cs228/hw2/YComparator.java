package edu.iastate.cs228.hw2;

import java.util.Comparator;

/**
 *  
 * @author jakeg
 *
 */


public class YComparator implements Comparator<Point>
{
	private Point referencePoint;
	
	public YComparator(Point p)
	{
		referencePoint = p;
	}
	public int compare(Point p1, Point p2) //compare by y, so set the xORy variable to false, then just use the point's compareTo
	{
		Point.xORy = false;
		return p1.compareTo(p2);
	}
}
