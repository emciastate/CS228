package edu.iastate.cs228.hw4;

import java.util.HashMap;

public abstract class Expression 
{
	protected String postfixExpression; 		
	protected HashMap<Character, Integer> varTable; // hash map to store variables in the 

	
	protected Expression()
	{
		// no implementation needed 
		// removable when you are done
	}
	
	
	/**
	 * Initialization with a provided hash map. 
	 * 
	 * @param varTbl
	 */
	protected Expression(String st, HashMap<Character, Integer> varTbl)
	{
		postfixExpression = removeExtraSpaces(st);
		varTable = new HashMap<Character, Integer>();
		varTable.putAll(varTbl);
	}
	
	
	/**
	 * Initialization with a default hash map.
	 * 
	 * @param st
	 */
	protected Expression(String st) 
	{
		postfixExpression = st;
		varTable = new HashMap<Character, Integer>();
	}

	
	/**
	 * Setter for instance variable varTable.
	 * @param varTbl
	 */
	public void setVarTable(HashMap<Character, Integer> varTbl) 
	{
		varTable = varTbl;
	}
	
	
	/**
	 * Evaluates the infix or postfix expression. 
	 * 
	 * @return value of the expression 
	 * @throws ExpressionFormatException, UnassignedVariableException
	 */
	public abstract int evaluate() throws ExpressionFormatException, UnassignedVariableException;  

	
	
	// --------------------------------------------------------
	// Helper methods for InfixExpression and PostfixExpression 
	// --------------------------------------------------------

	/** 
	 * Checks if a string represents an integer.  You may call the static method 
	 * Integer.parseInt(). 
	 * 
	 * @param s
	 * @return
	 */
	protected static boolean isInt(String s) 
	{
		try 
        { 
            Integer.parseInt(s); //if no exception thrown, given string is an integer
            return true; 
        }  
        catch (NumberFormatException e)  
        { 
            return false; //if exception is thrown, the given string is not an integer
        } 
	}

	
	/**
	 * Checks if a char represents an operator, i.e., one of '~', '+', '-', '*', '/', '%', '^', '(', ')'. 
	 * 
	 * @param c
	 * @return
	 */
	protected static boolean isOperator(char c) 
	{
		String key = "~+-*/%^()";
		for(int i=0; i<key.length();i++)
		{
			if(c==key.charAt(i))
			{
				return true;
			}
		}
		return false;
	}

	
	/** 
	 * Checks if a char is a variable, i.e., a lower case English letter. 
	 * 
	 * @param c
	 * @return
	 */
	protected static boolean isVariable(char c) 
	{
		if(Character.isLowerCase(c)) return true;
		return false;
	}
	
	
	/**
	 * Removes extra blank spaces in a string. 
	 * @param s
	 * @return
	 */
	protected static String removeExtraSpaces(String s) 
	{
		String replace = s.trim().replaceAll(" +", " ");
		return replace;
	}

}
