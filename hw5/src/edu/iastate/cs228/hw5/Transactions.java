package edu.iastate.cs228.hw5;


import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 *  
 * @author jakeg
 *
 */

/**
 * 
 * The Transactions class simulates video transactions at a video store. 
 *
 */
public class Transactions 
{
	
	/**
	 * The main method generates a simulation of rental and return activities.  
	 *  
	 * @param args
	 * @throws FileNotFoundException
	 * @throws AllCopiesRentedOutException 
	 * @throws IllegalArgumentException 
	 * @throws FilmNotInInventoryException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException, AllCopiesRentedOutException, FilmNotInInventoryException
	{	
		
			
				VideoStore vStore = new VideoStore("videoList1.txt");
				System.out.print("Transactions at a Video Store\n");
				System.out.print("keys: 1 (rent)     2 (bulk rent)\n");
				System.out.print("      3 (return)   4 (bulk return)\n");
				System.out.print("      5 (summary)  6 (exit)\n\n");
				Scanner scan = new Scanner(System.in);
				String film = "";
				while(true)
				{
					try { // make sure try catch is within a while loop
					System.out.print("Transaction: ");
					int trans = scan.nextInt();
					if(trans == 1) //rent a movie with specified or unspecified number of copies
					{
						System.out.print("Film to rent: ");
						String line = scan.next()+ " ";
						line += scan.nextLine();
						line = line.replaceAll("[()]", "");
						Scanner scan2 = new Scanner(line);
						int copies = 1;
						film = "";
						while(scan2.hasNext())
						{
							if(scan2.hasNextInt())
							{
								copies = scan2.nextInt();
							}
							else
							{
								film+=scan2.next() + " ";
							}
						}
						vStore.videoRent(film, copies);
						System.out.print("\n");
					}
					else if(trans == 2) //bulk rent using a given file
					{
						System.out.print("Video file (rent): ");
						String file = scan.next();
						vStore.bulkRent(file);
						System.out.print("\n");
					}
					else if(trans == 3) //rent a movie with specified or unspecified number of copies
					{
						System.out.print("Film to return: ");
						String line = scan.next()+ " ";
						line += scan.nextLine();
						line = line.replaceAll("[()]", "");
						Scanner scan2 = new Scanner(line);
						int copies = 1;
						film = "";
						while(scan2.hasNext())
						{
							if(scan2.hasNextInt())
							{
								copies = scan2.nextInt();
							}
							else
							{
								film+=scan2.next() + " ";
							}
						}
						vStore.videoReturn(film, copies);
						System.out.print("\n");
					}
					else if(trans == 4)//bulk return using a given file
					{
						System.out.print("Video file (return): ");
						String file = scan.next();
						vStore.bulkReturn(file);
						System.out.print("\n");
					}
					else if(trans == 5) //give the transaction summary of rented films and films left in inventory
					{
						System.out.print(vStore.transactionsSummary());
						System.out.print("\n");
					}
					else if(trans == 6) //exit
					{
						break;
					}
					else//ask to enter one of the numbers listed
					{
						System.out.print("Enter one of the specified transaction numbers\n\n");
					}
				
			}catch(IllegalArgumentException e) {
				System.out.print(e.getMessage());
			}catch(AllCopiesRentedOutException e) {
				System.out.print(e.getMessage());
			}catch(FilmNotInInventoryException e) {
				System.out.print(e.getMessage());
			}
				}
	}
}
