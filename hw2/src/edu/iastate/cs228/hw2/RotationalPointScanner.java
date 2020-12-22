package edu.iastate.cs228.hw2;
/**
 *  
 * @author jakeg
 *
 */

import java.io.File;

/**
 * 
 * @author 
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array by polar angle with respect to a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class RotationalPointScanner  
{
	private Point[] points; 
	
	public static Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
	protected String outputFileName;   // "select.txt", "insert.txt", "merge.txt", or "quick.txt"
	
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[]. Set outputFileName. 
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public RotationalPointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if(pts == null || pts.length==0) throw new IllegalArgumentException();
		points = new Point[pts.length];
		for(int i = 0; i<pts.length; i++)
		{
			points[i] = new Point(pts[i].getX(),pts[i].getY());
		}
		sortingAlgorithm = algo;
		
		if(algo.equals(Algorithm.SelectionSort)) outputFileName = "select.txt";
		else if(algo.equals(Algorithm.InsertionSort)) outputFileName = "insert.txt";
		else if(algo.equals(Algorithm.MergeSort)) outputFileName = "merge.txt";
		else outputFileName = "quick.txt";
	}

	
	/**
	 * This constructor reads points from a file. Set outputFileName. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected RotationalPointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
			int count = 0;
			sortingAlgorithm = algo;
			
			File f = new File(inputFileName);
			
			if(f.exists()) 
			{
				Scanner checkInt = new Scanner(f);
				while(checkInt.hasNextInt())
				{
					count++;
					checkInt.nextInt();
				}
				checkInt.close();
			
				if(count%2!=0) 
				{
					throw new InputMismatchException();
				}
				else
				{
					Scanner scan = new Scanner(f);
					points = new Point[count/2];
					for(int i = 0; i < (count/2); i++)
					{
						int x1 = scan.nextInt();
						int y1 = scan.nextInt();
						Point newp = new Point(x1,y1);
						points[i] = newp;
					}
					scan.close();
					
					if(algo.equals(Algorithm.SelectionSort)) outputFileName = "select.txt";
					else if(algo.equals(Algorithm.InsertionSort)) outputFileName = "insert.txt";
					else if(algo.equals(Algorithm.MergeSort)) outputFileName = "merge.txt";
					else outputFileName = "quick.txt";
				}
			}
			else
			{
				throw new FileNotFoundException();
			}
		
	}

	
	/**
	 * Carry out three rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates. 
	 *     d) Sort points[] again by the polar angle with respect to medianCoordinatePoint.
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting. Copy the sorting result back onto the array points[] by calling 
	 * the method getPoints() in AbstractSorter. 
	 *      
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		AbstractSorter aSorter; 
		if(sortingAlgorithm.equals(Algorithm.SelectionSort))
		{
			aSorter = new SelectionSorter(points);
		}
		else if(sortingAlgorithm.equals(Algorithm.InsertionSort))
		{
			aSorter = new InsertionSorter(points);
		}
		else if(sortingAlgorithm.equals(Algorithm.QuickSort))
		{
			aSorter = new QuickSorter(points);
		}
		else 
		{
			aSorter = new MergeSorter(points);
		}
		long before = System.nanoTime();
		aSorter.setComparator(0);
		aSorter.sort();
		aSorter.getPoints(points);
		int medx = aSorter.getMedian().getX();
		aSorter.setComparator(1);
		aSorter.sort();
		aSorter.getPoints(points);
		int medy = aSorter.getMedian().getY();
		medianCoordinatePoint = new Point(medx,medy);
		aSorter.setReferencePoint(medianCoordinatePoint);
		aSorter.setComparator(2);
		aSorter.sort();
		aSorter.getPoints(points);
		long after = System.nanoTime();
		scanTime = after - before;
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the three 
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0, 1, or 2. in case it is 2, must have made 
		//        the call setReferencePoint(medianCoordinatePoint) already. 
		//
		//     b) call sort(). 		
		// 
		// sum up the times spent on the three sorting rounds and set the instance variable scanTime. 
		
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		return sortingAlgorithm + " " + points.length + " " + scanTime; 
	}
	
	
	/**
	 * Write points[] after a call to scan().  When printed, the points will appear 
	 * in order of polar angle with respect to medianCoordinatePoint with every point occupying a separate 
	 * line.  The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		String allPts = "";
		for(int i = 0; i<points.length; i++)
		{
			allPts += points[i].toString()+"\n";
		}
		return allPts; 
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writePointsToFile() throws FileNotFoundException
	{
		File out = new File(outputFileName);
		if(out.exists())
		{
			PrintWriter write = new PrintWriter(out);
			write.print(points.toString());
			write.close();
		}
		else
		{
			throw new FileNotFoundException();
		}
	}	

	
	/**
	 * This method is called after each scan for visually check whether the result is correct.  You  
	 * just need to generate a list of points and a list of segments, depending on the value of 
	 * sortByAngle, as detailed in Section 4.1. Then create a Plot object to call the method myFrame().  
	 */
	public void draw()
	{		
		int numSegs = 0;  // number of segments to draw 

		// Based on Section 4.1, generate the line segments to draw for display of the sorting result.
		// Assign their number to numSegs, and store them in segments[] in the order. 
		for(int i = 0; i<points.length-1;i++)
		{
			if(!points[i].equals(points[i+1]))//consecutive points
			{
				numSegs++;
			}
		}
		if(!points[0].equals(points[points.length-1]))
		{
			numSegs++;//first to last point
		}
		numSegs+=points.length; //median to every point
		
		Segment[] segments = new Segment[numSegs]; 
		int k = 0;
		for(int i = 0; i<points.length-1;i++)
		{
			if(!points[i].equals(points[i+1]))//consecutive points
			{
				segments[k] = new Segment(points[i],points[i+1]);
				k++;
			}
		}
		for(int i = 0; i<points.length;i++)
		{
			segments[k] = new Segment(points[i],medianCoordinatePoint); //every point to median
			k++;
		}
		if(!points[0].equals(points[points.length-1])) segments[k] = new Segment(points[0],points[points.length-1]);
		
		String sort = null;
		
		switch(sortingAlgorithm)
		{
		case SelectionSort: 
			sort = "Selection Sort"; 
			break; 
		case InsertionSort: 
			sort = "Insertion Sort"; 
			break; 
		case MergeSort: 
			sort = "Mergesort"; 
			break; 
		case QuickSort: 
			sort = "Quicksort"; 
			break; 
		default: 
			break; 		
		}
		
		// The following statement creates a window to display the sorting result.
		Plot.myFrame(points, segments, sort);
		
	}
		
}
