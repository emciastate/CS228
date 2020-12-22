package edu.iastate.cs228.hw2;

import java.util.Comparator;

/**
 *  
 * @author jakeg
 *
 */

public class XComparator implements Comparator<Point>
{
	private Point referencePoint;
	
	public XComparator(Point p)
	{
		referencePoint = p;
	}
	public int compare(Point p1, Point p2) //compare by x, so set the xORy variable to true, then just use the point's compareTo
	{
		Point.xORy = true;
		return p1.compareTo(p2);
	}
}