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
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "mergesort"; 
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		int l = 0;
		int r = pts.length-1;
		mergeSort(pts,l,r);
	}
	private void mergeSort(Point[] p, int l, int r)
	{
		if (l < r) 
        { 
            int m = (l+r)/2; // Find the middle index
  
            mergeSort(p, l, m); 
            mergeSort(p , m+1, r);  // Sort left and right sides 
  
            merge(p, l, m, r); // Merge left and right
        } 
	}

	private void merge(Point arr[], int l, int m, int r) 
    { 
        // Find sizes of both subarrays
        int n1 = m - l + 1; 
        int n2 = r - m; 
  
        Point L[] = new Point [n1]; 
        Point R[] = new Point [n2]; 
  
        for (int i=0; i<n1; ++i) 
            L[i] = new Point(arr[l + i].getX(),arr[l + i].getY()); 
        for (int j=0; j<n2; ++j) 
            R[j] = new Point(arr[m + 1+ j].getX(),arr[m + 1+ j].getY()); //copy the elements into the arrays
  
        int i = 0, j = 0; //use for left and right indexes
  
        int k = l; // Initial index of merged subarray
        while (i < n1 && j < n2) 
        { 
        	if (pointComparator.compare(L[i], R[j])<=0) 
            { 
                arr[k] = new Point(L[i].getX(),L[i].getY()); 
                i++; 
            } 
            else
            { 
                arr[k] = new Point(R[j].getX(),R[j].getY()); 
                j++; 
            } 
            k++; 
        } 
  
        while (i < n1) 
        { 
        	arr[k] = new Point(L[i].getX(),L[i].getY()); //copy rest of L
            i++; 
            k++; 
        } 
  
        while (j < n2) 
        { 
        	arr[k] = new Point(R[j].getX(),R[j].getY()); //copy rest of R
            j++; 
            k++; 
        } 
    } 

}
