package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author jakeg
 *
 */

/**
 * 
 * This class implements insertion sort.   
 *
 */

public class InsertionSorter extends AbstractSorter 
{
	// Other private instance variables if you need ... 
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 * 
	 * @param pts  
	 */
	public InsertionSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "insertion sort";
	}	

	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 */
	@Override 
	public void sort()
	{
		int i, j;  
		Point key;
	    for (i = 1; i < points.length; i++) 
	    {  
	        key = new Point(points[i].getX(),points[i].getY());  
	        j = i - 1;  
	  
	        while (j >= 0 && pointComparator.compare(points[j], key)>0) 
	        {  
	            points[j + 1] = new Point(points[j].getX(),points[j].getY());  //if element in point is greater than key, move ahead of current position
	            j = j - 1;  
	        }  
	        points[j + 1] = new Point(key.getX(),key.getY());  
	    }  
	}		
}
