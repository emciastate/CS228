package edu.iastate.cs228.hw1;

/**
 *  
 * @author jgmartin
 *
 */

/**
 * Grass remains if more than rabbits in the neighborhood; otherwise, it is eaten. 
 *
 */
public class Grass extends Living 
{
	public Grass (Plain p, int r, int c) 
	{
		plain = p;
		row = r;
		column = c;
	}
	
	public State who()
	{
		// TODO  
		return State.GRASS; 
	}
	
	/**
	 * Grass can be eaten out by too many rabbits. Rabbits may also multiply fast enough to take over Grass.
	 */
	public Living next(Plain pNew)
	{
		Living nextAnimal;
		int[] neighbors = new int[5];
		census(neighbors);
		if(neighbors[4]>=neighbors[3]*3)//Empty if at least three times as many Rabbits as Grasses in the neighborhood
		{
			nextAnimal = new Empty(pNew,row,column);
		}
		else if(neighbors[4]>=3)//Empty if at least three times as many Rabbits as Grasses in the neighborhood
		{
			nextAnimal = new Rabbit(pNew,row,column,0);
		}
		else //otherwise, Grass
		{
			nextAnimal = this;
		}
		return nextAnimal; 
	}
}
