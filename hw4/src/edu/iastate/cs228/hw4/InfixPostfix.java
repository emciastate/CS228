package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;

/**
 *  
 * @author JakeM
 *
 */

/**
 * 
 * This class evaluates input infix and postfix expressions. 
 *
 */

import java.util.HashMap;
import java.util.Scanner;

public class InfixPostfix 
{

	/**
	 * Repeatedly evaluates input infix and postfix expressions.  See the project description
	 * for the input description. It constructs a HashMap object for each expression and passes it 
	 * to the created InfixExpression or PostfixExpression object. 
	 *  
	 * @param args
	 **/
	public static void main(String[] args) throws  UnassignedVariableException, ExpressionFormatException,FileNotFoundException
	{
		int currenttri = 1; // holds value for current trial, updates every trial
		Scanner scan = new Scanner(System.in); 
		System.out.println("Evaluation of Infix and Postfix Expressions");
		System.out.println("keys: 1 (standard input) 2 (file input) 3 (exit)");
		System.out.println("(Enter \"I\" before an infix expression, \"P\" before a postfix expression)\r\n" + "");
		while(true) 
		{
			System.out.println("Trial "+currenttri+": ");
			int trial = scan.nextInt();
			if(trial == 1) //standard input
			{
				char[] variables = new char[10]; // char array for storing variables given by user
				int[] values = new int[10]; // integer array for storing values given by user
				//the above arrays will be used for output and also creating a hashmap
				int var = 0;
				String ret = "";
				System.out.println("Expression: ");
				String expression = scan.next()+ " ";
				expression += scan.nextLine();
				Scanner scanString = new Scanner(expression);
				while(scanString.hasNext())
				{
					String iORp = scanString.next() + " "; //check if given string is I or P
					if(iORp.charAt(0)=='P')
					{
						while(scanString.hasNext())
						{
							if(scanString.hasNextInt()) // if the next token in string is an int then add it to string
							{
								int integerval = scanString.nextInt();
								ret+= integerval + " ";
							}
							else
							{
								String s = scanString.next();
								ret += s + " ";
								if(Expression.isVariable(s.charAt(0))) //add variables to variable array and update how many variables are in the array
								{
									variables[var] = s.charAt(0);
									var++;
								}
							}
						}
						if(variables[0]=='\u0000')// \u0000 is null
						{
							PostfixExpression p = new PostfixExpression(ret);//if no variables given
							System.out.println("Postfix form: "+ p.toString());
							System.out.println("Expression value: "+ p.evaluate());
						}
						else
						{
							PostfixExpression p = new PostfixExpression(ret);
							System.out.println("Postfix form: "+ p.toString());
							System.out.println("Where:");
							for(int i = 0; i<var; i++)
							{
								System.out.println(variables[i] + " = "); // print out for each value and variable
								values[i] = scan.nextInt();
							}
							HashMap<Character, Integer> map = new HashMap<Character, Integer>();
							for(int j = 0; j<var; j++)
							{
								map.put(variables[j], values[j]); //put the given variables and values into a hashmap
							}
							PostfixExpression p2 = new PostfixExpression(ret,map);
							System.out.println("Expression value: "+ p2.evaluate());
						}
						
					}
					else if(iORp.charAt(0)=='I')
					{
						while(scanString.hasNext())
						{
							char c = scanString.next().charAt(0);
							ret += c + " ";
							if(Expression.isVariable(c))
							{
								variables[var] = c;
								var++;
							}
						}
						if(variables[0]=='\u0000')// \u0000 is null
						{
							InfixExpression p = new InfixExpression(ret);//if no variables given
							System.out.println("Infix form: "+ p.toString());
							p.postfix();
							System.out.println("Postfix form: "+ p.postfixString());
							System.out.println("Expression value: "+ p.evaluate());
						}
						else
						{
							InfixExpression p = new InfixExpression(ret);
							System.out.println("Infix form: "+ p.toString());
							p.postfix();
							System.out.println("Postfix form: "+ p.postfixString());
							System.out.println("Where:");
							for(int i = 0; i<var; i++)
							{
								System.out.println(variables[i] + " = ");// print out for each value and variable
								values[i] = scan.nextInt();
							}
							HashMap<Character, Integer> map = new HashMap<Character, Integer>();
							for(int j = 0; j<var; j++)
							{
								map.put(variables[j], values[j]);//put the given variables and values into a hashmap
							}
							InfixExpression p2 = new InfixExpression(ret,map);
							System.out.println("Expression value: "+ p2.evaluate());
						}
					}
				}
			}
			else if(trial == 2) //file input
			{
				char[] variables = new char[10]; // char array for storing variables given by user
				int[] values = new int[10]; // integer array for storing values given by user
				//the above arrays will be used for output and also creating a hashmap
				int var = 0;
				String ret = "";
				System.out.println("Input from a file");
				System.out.print("Enter file name:");
				String file = scan.next();
				File file1 = new File(file);
				Scanner filescan = new Scanner(file1);
				while(filescan.hasNextLine())
				{
					String line = filescan.nextLine();
					Scanner linescan = new Scanner(line);
					if(linescan.next().charAt(0)=='I')
					{
						while(linescan.hasNext())
						{
							char c = linescan.next().charAt(0);
							ret += c + " ";
							if(Expression.isVariable(c))
							{
								variables[var] = c;
								var++;
							}
						}
						if(variables[0]=='\u0000')// \u0000 is null
						{
							InfixExpression p = new InfixExpression(ret);//if no variables given
							System.out.println("Infix form: "+ p.toString());
							p.postfix();
							System.out.println("Postfix form: "+ p.postfixString());
							System.out.println("Expression value: "+ p.evaluate());
						}
						else // there are variables
						{
							InfixExpression p = new InfixExpression(ret);
							System.out.println("Infix form: "+ p.toString());
							p.postfix();
							System.out.println("Postfix form: "+ p.postfixString());
							for(int i = 0; i<var;i++)
							{
								values[i] = linescan.nextInt();
							}
							System.out.println("where");
							for(int i = 0; i<var;i++)
							{
								System.out.println(variables[i] + " = " + values[i]);
							}
							HashMap<Character, Integer> map = new HashMap<Character, Integer>();
							for(int j = 0; j<var; j++)
							{
								map.put(variables[j], values[j]);
							}
							PostfixExpression p2 = new PostfixExpression(ret,map);
							System.out.println("Expression value: "+ p2.evaluate());
						}
					}
					else if(linescan.next().charAt(0)=='P')
					{
						while(linescan.hasNext())
						{
							char c = linescan.next().charAt(0);
							ret += c + " ";
							if(Expression.isVariable(c))
							{
								variables[var] = c;
								var++;
							}
						}
						if(variables[0]=='\u0000')// \u0000 is null
						{
							PostfixExpression p = new PostfixExpression(ret);//if no variables given
							System.out.println("Postfix form: "+ p.toString());
							System.out.println("Expression value: "+ p.evaluate());
						}
						else // there are variables
						{
							PostfixExpression p = new PostfixExpression(ret);
							System.out.println("Postfix form: "+ p.toString());
							for(int i = 0; i<var;i++)
							{
								values[i] = linescan.nextInt();
							}
							System.out.println("where");
							for(int i = 0; i<var;i++)
							{
								System.out.println(variables[i] + " = " + values[i]);
							}
							HashMap<Character, Integer> map = new HashMap<Character, Integer>();
							for(int j = 0; j<var; j++)
							{
								map.put(variables[j], values[j]);
							}
							PostfixExpression p2 = new PostfixExpression(ret,map);
							System.out.println("Expression value: "+ p2.evaluate());
						}
					}
				}
			}
			else //exit
			{
				break;
			}
			currenttri++;
		}
	}
	
	// helper methods if needed
	// probably should've used helper methods... sorry for messy main()
}
