package edu.iastate.cs228.hw1;

/**
 *  
 * @author jgmartin
 *
 */

/** 
 * Empty squares are competed by various forms of life.
 */
public class Empty extends Living 
{
	public Empty (Plain p, int r, int c) 
	{
		plain = p;
		row = r;
		column = c;
	}
	
	public State who()
	{
		return State.EMPTY; 
	}
	
	/**
	 * An empty square will be occupied by a neighboring Badger, Fox, Rabbit, or Grass, or remain empty. 
	 * @param pNew     plain of the next life cycle.
	 * @return Living  life form in the next cycle.   
	 */
	public Living next(Plain pNew)
	{
		Living nextAnimal;
		int[] neighbors = new int[5];
		census(neighbors);
		if(neighbors[4]>1)//Rabbit, if more than one neighboring Rabbit
		{
			nextAnimal = new Rabbit(pNew,row,column,0);
		}
		else if(neighbors[2]>1)//Fox, if more than one neighboring Fox
		{
			nextAnimal = new Fox(pNew,row,column,0);
		}
		else if(neighbors[0]>1)//Badger, if more than one neighboring Badger
		{
			nextAnimal = new Badger(pNew,row,column,0);
		}
		else if(neighbors[3]>=1)//, Grass, if at least one neighboring Grass
		{
			nextAnimal = new Grass(pNew,row,column);
		}
		else //otherwise, Empty
		{
			nextAnimal = this;
		}
		return nextAnimal; 
	}
}
