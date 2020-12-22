package edu.iastate.cs228.hw2;

/**
 *  
 * @author jakeg
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		int choose = 0;
		int numPts = 0;
		int trial = 1;
		String inputfile = "";
		RotationalPointScanner[] scanners = new RotationalPointScanner[4]; 
		Scanner scan = new Scanner(System.in);
		System.out.print("Performances of Four Sorting Algorithms in Point Scanning\nkeys: 1 (random integers) 2 (file input) 3 (exit)\n");
		while(true) 
		{
			System.out.print("Trial " + trial + ":");
			choose = scan.nextInt();
			
			if(choose == 1) //if user input 1, do random point generation
			{
				Random rand = new Random();
				System.out.print("Enter number of random points: ");
				numPts = scan.nextInt();
				Point[] random = generateRandomPoints(numPts,rand);
				System.out.print("algorithm size time (ns)\n");
				scanners[0] = new RotationalPointScanner(random,Algorithm.SelectionSort);
				scanners[1] = new RotationalPointScanner(random,Algorithm.InsertionSort);
				scanners[2] = new RotationalPointScanner(random,Algorithm.MergeSort);
				scanners[3] = new RotationalPointScanner(random,Algorithm.QuickSort);
				
				for(int i = 0; i<scanners.length;i++)
				{
					scanners[i].scan();
					scanners[i].draw();
				}
				System.out.println("----------------------------------");
				System.out.print(scanners[0].stats()+"\n"+scanners[1].stats()+"\n"+scanners[2].stats()+"\n"+scanners[3].stats()+"\n");
				System.out.println("----------------------------------");
				trial++;
			}
			else if(choose == 2) //if user input 2, read from user file
			{
				System.out.print("Points from a file\n");
				System.out.print("File name: ");
				inputfile = scan.next();// read file name
				System.out.print("algorithm size time (ns)\n");
				scanners[0] = new RotationalPointScanner(inputfile,Algorithm.SelectionSort);
				scanners[1] = new RotationalPointScanner(inputfile,Algorithm.InsertionSort);
				scanners[2] = new RotationalPointScanner(inputfile,Algorithm.MergeSort);
				scanners[3] = new RotationalPointScanner(inputfile,Algorithm.QuickSort);
				
				for(int i = 0; i<scanners.length;i++)
				{
					scanners[i].scan();
					scanners[i].draw();
				}
				System.out.println("----------------------------------");
				System.out.print(scanners[0].stats()+"\n"+scanners[1].stats()+"\n"+scanners[2].stats()+"\n"+scanners[3].stats()+"\n");
				System.out.println("----------------------------------");
				trial++;
			}
			else // if user input is not 1 or 2, break out of loop to end user input
			{
				break;
			}
		}
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		Random generator = new Random();
		Point[] pArr = new Point[numPts];
		for(int i = 0; i<numPts; i++)
		{
			int xcoord = (generator.nextInt(101)-50); // make number between -50 and 50
			int ycoord = (generator.nextInt(101)-50);
			pArr[i] = new Point(xcoord,ycoord);
		}
		return pArr; 
	}
	
}
