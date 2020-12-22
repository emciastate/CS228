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
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  
	{
		super(pts);
		algorithm = "selection sort";
	}	

	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 */
	@Override 
	public void sort()
	{
		int n = points.length; 
		  
        for (int i = 0; i < n-1; i++) //move through unsorted array
        { 
            int min_index = i; 
            for (int j = i+1; j < n; j++) 
            	if (pointComparator.compare(points[j],points[min_index])<0) //find minimum index and update the value
                    min_index = j; 
  
            Point temp = new Point(points[min_index].getX(),points[min_index].getY()); //swap min element with first
            points[min_index] = new Point(points[i].getX(),points[i].getY()); 
            points[i] = new Point(temp.getX(),temp.getY()); 
        }  
	}	
}
