package edu.iastate.cs228.hw1;

/**
 *  
 * @author jgmartin
 *
 */

/*
 * A rabbit eats grass and lives no more than three years.
 */
public class Rabbit extends Animal 
{	
	/**
	 * Creates a Rabbit object.
	 * @param p: plain  
	 * @param r: row position 
	 * @param c: column position
	 * @param a: age 
	 */
	public Rabbit (Plain p, int r, int c, int a) 
	{
		plain = p;
		row = r;
		column = c;
		age = a;
	}
		
	// Rabbit occupies the square.
	public State who()
	{
		return State.RABBIT; 
	}
	
	/**
	 * A rabbit dies of old age or hunger. It may also be eaten by a badger or a fox.  
	 * @param pNew     plain of the next cycle 
	 * @return Living  new life form occupying the same square
	 */
	public Living next(Plain pNew)
	{
		Living nextAnimal;
		int[] neighbors = new int[5];
		census(neighbors);
		if(this.age == RABBIT_MAX_AGE) //Empty if the Rabbit’s current age is 3
		{
			nextAnimal = new Empty(pNew,row,column);
		}
		else if(neighbors[3]==0) //Empty if there is no Grass in the neighborhood (the rabbit needs food);
		{
			nextAnimal = new Empty(pNew,row,column);
		}
		else if(neighbors[2]+neighbors[0]>=neighbors[4]&&(neighbors[2]>neighbors[0]))//Fox if in the neighborhood there are at least as many Foxes and Badgers combined
			//as Rabbits, and furthermore, if there are more Foxes than Badgers
		{
			nextAnimal = new Fox(pNew,row,column,0);
		}
		else if(neighbors[0]>neighbors[4])//Badger if there are more Badgers than Rabbits in the neighborhood
		{
			nextAnimal = new Badger(pNew,row,column,0);
		}
		else //otherwise, Rabbit
		{
			this.age++;
			nextAnimal = this;
		}
		return nextAnimal; 
	}
}
