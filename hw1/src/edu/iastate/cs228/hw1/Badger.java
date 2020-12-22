package edu.iastate.cs228.hw1;

/**
 *  
 * @author jgmartin
 *
 */

/**
 * A badger eats a rabbit and competes against a fox. 
 */
public class Badger extends Animal
{
	/**
	 * Constructor 
	 * @param p: plain
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Badger (Plain p, int r, int c, int a) 
	{
		plain = p;
		row = r;
		column = c;
		age = a;
	}
	
	/**
	 * A badger occupies the square. 	 
	 */
	public State who()
	{
		return State.BADGER; 
	}
	
	/**
	 * A badger dies of old age or hunger, or from isolation and attack by a group of foxes. 
	 * @param pNew     plain of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Plain pNew) //employ survival rules
	{
		// 
				// See Living.java for an outline of the function. 
				// See the project description for the survival rules for a badger. 
				// For each class (life form), carry out the following: 
				// 
				// 1. Obtains counts of life forms in the 3x3 neighborhood of the class object. 

				// 2. Applies the survival rules for the life form to determine the life form  
				//    (on the same square) in the next cycle.  These rules are given in the  
				//    project description. 
				// 
				// 3. Generate this new life form at the same location in the plain pNew.
		Living nextAnimal;
		int[] neighbors = new int[5];
		census(neighbors);
		if(this.age == BADGER_MAX_AGE) //Empty if the Badger is currently at age 4
		{
			nextAnimal = new Empty(pNew,row,column);
		}
		else if(neighbors[0]==1&&neighbors[2]>1) //Fox, if there is only one Badger but more than one Fox in the neighborhood
		{
			nextAnimal = new Fox(pNew,row,column,0);
		}
		else if(neighbors[0]+neighbors[2]>neighbors[4]) //Empty, if Badgers and Foxes together outnumber Rabbits in the neighborhood
		{
			nextAnimal= new Empty(pNew,row,column);
		}
		else //otherwise, Badger
		{
			this.age++;
			nextAnimal = this;
		}
		return nextAnimal; 
	}
}
