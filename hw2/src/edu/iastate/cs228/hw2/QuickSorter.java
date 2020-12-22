package edu.iastate.cs228.hw2;
/**
 *  
 * @author jakeg
 *
 */

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author
 *
 */

/**
 * 
 * This class implements the version of the quicksort algorithm presented in the lecture.   
 *
 */

public class QuickSorter extends AbstractSorter
{
	
	// Other private instance variables if you need ... 
		
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *   
	 * @param pts   input array of integers
	 */
	public QuickSorter(Point[] pts)
	{
		super(pts);
		algorithm = "quicksort";
	}
		

	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.  
	 * 
	 */
	@Override 
	public void sort()
	{
		quickSortRec(0,points.length-1);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last)
	{
		if (first < last) 
        { 
            int pi = partition(first, last); //pi is the partition index
   
            quickSortRec(first, pi-1); //recursively sort before partition
            quickSortRec(pi+1, last);  //recursively sort after partition
        } 
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last)
	{
		Point pivot = new Point(points[last].getX(),points[last].getY());  
        int i = (first-1); // index of smaller element 
        for (int j=first; j<last; j++) 
        { 
        	 if (pointComparator.compare(points[j], pivot)<0) //if "smaller" than pivot
            { 
                i++; 
  
                // swap
                Point temp = points[i]; 
                points[i] = points[j]; 
                points[j] = temp; 
            } 
        } 
  
        Point temp = new Point(points[i+1].getX(),points[i+1].getY()); 
        points[i+1] = points[last]; 
        points[last] = temp; 
  
        return i+1;
	}	
	
}
