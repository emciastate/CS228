package edu.iastate.cs228.hw1;

/**
 *  
 * @author jgmartin
 *
 */

import java.io.File; 
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner; 
import java.util.Random; 

/**
 * 
 * The plain is represented as a square grid of size width x width. 
 *
 */
public class Plain 
{
	private int width; // grid size: width X width 
	
	public Living[][] grid; 
	
	/**
	 *  Default constructor reads from a file 
	 */
	public Plain(String inputFileName) throws FileNotFoundException
	{		
		Scanner widthReader = new Scanner(inputFileName); //scanner to get width of first line
		width = (widthReader.nextLine().length())/3; // given this idea from Alec Meyer to take first line and find width using the length
		widthReader.close();
		Scanner in = new Scanner(inputFileName); // scanner to actually read entire file
		grid = new Living[width][width];
		for(int i = 0; i<width; i++)
		{
			for(int j = 0; j<width; j++)
			{
				String temp = in.next(); 
				if(temp.charAt(0)=='B')
				{
					int age = Integer.parseInt(temp.charAt(1)+""); //convert the char into a string and then into an int
					Living b = new Badger(this,i,j,age);
					grid[i][j]=b;
				}
				else if(temp.charAt(0)=='F')
				{
					int age = Integer.parseInt(temp.charAt(1)+"");
					Living f = new Fox(this,i,j,age);
					grid[i][j]=f;
				}
				else if(temp.charAt(0)=='R')
				{
					int age = Integer.parseInt(temp.charAt(1)+"");
					Living r = new Rabbit(this,i,j,age);
					grid[i][j]=r;
				}
				else if(temp.charAt(0)=='G')
				{
					Living g = new Grass(this,i,j);
					grid[i][j]=g;
				}
				else if(temp.charAt(0)=='E')
				{
					Living e = new Empty(this,i,j);
					grid[i][j]=e;
				}
			}
		}
		in.close();
	}
	
	/**
	 * Constructor that builds a w x w grid without initializing it. 
	 * @param width  the grid 
	 */
	public Plain(int w)
	{
		width = w;
		grid = new Living[width][width];
	}
	
	
	public int getWidth()
	{ 
		return width;  
	}
	
	/**
	 * Initialize the plain by randomly assigning to every square of the grid  
	 * one of BADGER, FOX, RABBIT, GRASS, or EMPTY.  
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit() // each living object is set to age 0 or with no age
	{
		Random generator = new Random(); 
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < width; j++)
			{
				int rand = generator.nextInt(5); // get the random integer from 0-4
				if(rand == 0) //badger
				{
					Living b = new Badger(this,i,j,0);
					grid[i][j]=b;
				}
				else if(rand == 1) //fox
				{
					Living f = new Fox(this,i,j,0);
					grid[i][j]=f;
				}
				else if(rand == 2) //rabbit
				{
					Living r = new Rabbit(this,i,j,0);
					grid[i][j]=r;
				}
				else if(rand == 3) //grass
				{
					Living g = new Grass(this,i,j);
					grid[i][j]=g;
				}
				else if(rand == 4) //empty
				{
					Living e = new Empty(this,i,j);
					grid[i][j]=e;
				}
			}
		}
	}
	
	
	/**
	 * Output the plain grid. For each square, output the first letter of the living form
	 * occupying the square. If the living form is an animal, then output the age of the animal 
	 * followed by a blank space; otherwise, output two blanks.  
	 */
	public String toString() // checks each point in the grid and adds the corresponding 
							 // letter to the string along with the age if the Living object has an age
	{
		String strReturn = "";
		if(grid[0][0]==null) return "empty plain"; //return "empty" if calling toString() on an empty grid
		for(int i = 0; i<width; i++)
		{
			if(i!=0)
			{
				strReturn += "\n"; //doesn't add a new line until after the first row has been added
			}
			for(int j = 0; j<width; j++)
			{
				if(grid[i][j].who()==State.BADGER)
				{
					strReturn += "B"+((Animal) grid[i][j]).myAge() + " ";
				}
				else if(grid[i][j].who()==State.FOX)
				{
					strReturn += "F"+((Animal) grid[i][j]).myAge() + " ";
				}
				else if(grid[i][j].who()==State.RABBIT)
				{
					strReturn += "R"+((Animal) grid[i][j]).myAge() + " ";
				}
				else if(grid[i][j].who()==State.GRASS)
				{
					strReturn += "G"+ "  ";
				}
				else if(grid[i][j].who()==State.EMPTY)
				{
					strReturn += "E"+ "  ";
				}
			}
		}
		return strReturn; 
	}
	

	/**
	 * Write the plain grid to an output file.  Also useful for saving a randomly 
	 * generated plain for debugging purpose. 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException
	{
		PrintWriter p = new PrintWriter(outputFileName);
		p.print(this.toString()); // toString handles the formatting so no formatting is necessary in this method
								  // just call toString()
		p.close();
	}			
}
