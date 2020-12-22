package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 *  
 * @author jgmartin
 *
 */

/**
 * 
 * The Wildlife class performs a simulation of a grid plain with
 * squares inhabited by badgers, foxes, rabbits, grass, or none. 
 *
 */
public class Wildlife 
{
	/**
	 * Update the new plain from the old plain in one cycle. 
	 * @param pOld  old plain
	 * @param pNew  new plain 
	 */
	public static void updatePlain(Plain pOld, Plain pNew)
	{ 
		for(int i = 0; i<pOld.getWidth();i++) 
		{
			for(int j =0; j<pOld.getWidth();j++)
			{
				pNew.grid[i][j]=pOld.grid[i][j].next(pOld);
			}
		}
	}
	
	/**
	 * Repeatedly generates plains either randomly or from reading files. 
	 * Over each plain, carries out an input number of cycles of evolution. 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{	
		Plain even;   				 // the plain after an even number of cycles 
		Plain odd;                   // the plain after an odd number of cycles
		
		Scanner scan = new Scanner(System.in); //use scanner for user input
		int cycles = 0;
		while(true) //will repeatedly ask for new values until a number 3 or a number that's not 1 or 2 is entered.
		{
			System.out.print("Enter a number, 1, 2, or 3\n");
			int input = scan.nextInt();
			if(input == 1) 
			{
				System.out.print("Enter width\n");
				even = new Plain(scan.nextInt()); // instantiate even plain by using given width
				odd = new Plain(even.getWidth()); // give odd plain same width as even plain
				System.out.print("Enter number of cycles\n");
				cycles = scan.nextInt(); 
				even.randomInit(); // fill even plain randomly
				System.out.print(even.toString()+"\n\n"); //output the first plain
				for(int i = 0; i<cycles;i++) // update plain based on number of cycles
				{
					if(i%2==0||i==0) 
					{
						updatePlain(even,odd); //generate odd plain from even plain
					}
					else
					{
						updatePlain(odd,even); // generate even plain from odd plain
					}
				}
				System.out.print(odd.toString()+"\n\n"); //output the final plain
			}
			else if(input == 2)
			{
				System.out.print("Enter filename\n");
				String filename = scan.next();
				even = new Plain(filename); // instantiate even plain by using the given file
				odd = new Plain(even.getWidth()); // give odd plain same width as even plain
				System.out.print("Enter number of cycles\n");
				cycles = scan.nextInt();
				System.out.print(even.toString()+"\n\n"); //output the first plain
				for(int i = 0; i<cycles;i++) // update plain based on number of cycles
				{
					if(i%2==0||i==0) 
					{
						updatePlain(even,odd); //generate odd plain from even plain
					}
					else
					{
						updatePlain(odd,even); //generate even plain from odd plain
					}
				}
				System.out.print(odd.toString()+"\n\n"); //output the final plain
			}
			else 
			{
				break; //break out of loop if 3 or anything else
			}
		}
		
	}
}
