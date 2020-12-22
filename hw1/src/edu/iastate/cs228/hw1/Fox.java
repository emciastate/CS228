package edu.iastate.cs228.hw1;

/**
 *  
 * @author jgmartin
 *
 */

/**
 * A fox eats rabbits and competes against a badger. 
 */
public class Fox extends Animal 
{
	/**
	 * Constructor 
	 * @param p: plain
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Fox (Plain p, int r, int c, int a) 
	{
		plain = p;
		row = r;
		column = c;
		age = a;
	}
		
	/**
	 * A fox occupies the square. 	 
	 */
	public State who()
	{
		return State.FOX; 
	}
	
	/**
	 * A fox dies of old age or hunger, or from attack by numerically superior badgers. 
	 * @param pNew     plain of the next cycle
	 * @return Living  life form occupying the square in the next cycle. 
	 */
	public Living next(Plain pNew) //employ survival rules
	{
		Living nextAnimal;
		int[] neighbors = new int[5];
		census(neighbors);
		if(this.age == FOX_MAX_AGE) //Empty if the Fox is currently at age 6
		{
			nextAnimal = new Empty(pNew,row,column);
		}
		else if(neighbors[0]>neighbors[2]) //Badger, if there are more Badgers than Foxes in the neighborhood
		{
			nextAnimal = new Badger(pNew,row,column,0);
		}
		else if(neighbors[0]+neighbors[2]>neighbors[4]) //Empty, if Badgers and Foxes together outnumber Rabbits in the neighborhood
		{
			nextAnimal = new Empty(pNew,row,column);
		}
		else // otherwise, Fox
		{
			this.age++;
			nextAnimal = this;
		}
		return nextAnimal; 
	}
}
