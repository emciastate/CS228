package edu.iastate.cs228.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner; 

/**
 * 
 * @author jakeg 
 *
 */

public class VideoStore 
{
	protected SplayTree<Video> inventory;     // all the videos at the store
	
	// ------------
	// Constructors 
	// ------------
	
    /**
     * Default constructor sets inventory to an empty tree. 
     */
    public VideoStore()
    {
    	// no need to implement. 
    }
    
    
	/**
	 * Constructor accepts a video file to create its inventory.  Refer to Section 3.2 of  
	 * the project description for details regarding the format of a video file. 
	 * 
	 * Calls setUpInventory(). 
	 * 
	 * @param videoFile  no format checking on the file
	 * @throws FileNotFoundException
	 */
    public VideoStore(String videoFile) throws FileNotFoundException  
    {
    	setUpInventory(videoFile);
    }
    
    
   /**
     * Accepts a video file to initialize the splay tree inventory.  To be efficient, 
     * add videos to the inventory by calling the addBST() method, which does not splay. 
     * 
     * Refer to Section 3.2 for the format of video file. 
     * 
     * @param  videoFile  correctly formated if exists
     * @throws FileNotFoundException 
     */
    public void setUpInventory(String videoFile) throws FileNotFoundException
    {
    	inventory = new SplayTree<Video>();
    	File vidFile = new File(videoFile);
    	if(!vidFile.exists()) throw new FileNotFoundException();
    	Scanner scan = new Scanner(vidFile);
    	//format of a line in file
    	//Forrest Gump(-1)---> negatives are initialized to zero copies
    	//Brokeback Mountain ---> no number is initialized to one copy
    	//Slumdog Millionaire (5) --> five copies
    	//save whole line using scan.nextLine();
    	//then take the string and remove parenthesis that are in the entire string
    	while(scan.hasNextLine()) 
    	{
    		
	    	String line = scan.nextLine();
	    	line = line.replaceAll("[()]", ""); // take out parenthesis from string
	    	Scanner scan2 = new Scanner(line);
	    	String videoTitle = "";
	    	int copies = 0;
	    	boolean hasNum = false;
	    	while(scan2.hasNext())
	    	{
	    		if(scan2.hasNextInt())
	    		{
	    			copies = scan2.nextInt();
	    			if(copies<0) copies = 0;
	    			hasNum = true;
	    		}
	    		else
	    		{
	    			videoTitle+=scan2.next() + " "; //include a space
	    		}
	    	}
	    	if(!hasNum) copies = 1;//if a number wasn't given, default copies is 1
	    	videoTitle = videoTitle.trim(); //trim unnecessary space
	    	Video v1 = new Video(videoTitle, copies);
	    	inventory.addBST(v1); //addBST, don't splay
    	}
    }
	
    
    // ------------------
    // Inventory Addition
    // ------------------
    
    /**
     * Find a Video object by film title. 
     * 
     * @param film
     * @return
     */
	public Video findVideo(String film) 
	{
		Video v = new Video(film);
		if(inventory.contains(v))
		{
			return inventory.findElement(v);
		}
		else
		{
			return null;
		}
	}


	/**
	 * Updates the splay tree inventory by adding a number of video copies of the film.  
	 * (Splaying is justified as new videos are more likely to be rented.) 
	 * 
	 * Calls the add() method of SplayTree to add the video object.  
	 * 
	 *     a) If true is returned, the film was not on the inventory before, and has been added.  
	 *     b) If false is returned, the film is already on the inventory. 
	 *     
	 * The root of the splay tree must store the corresponding Video object for the film. Update 
	 * the number of copies for the film.  
	 * 
	 * @param film  title of the film
	 * @param n     number of video copies 
	 */
	public void addVideo(String film, int n)  
	{
		Video v = new Video(film,n);
		boolean addret = inventory.add(v);
		if(!addret) inventory.findElement(v).addNumCopies(n); //if addret is false, then the film is already in the inventory, so just add more copies
	}
	

	/**
	 * Add one video copy of the film. 
	 * 
	 * @param film  title of the film
	 */
	public void addVideo(String film)
	{
		addVideo(film,1); //use the add method above, defaulting to 1 copy
	}
	

	/**
     * Update the splay trees inventory by adding videos.  Perform binary search additions by 
     * calling addBST() without splaying. 
     * 
     * The videoFile format is given in Section 3.2 of the project description. 
     * 
     * @param videoFile  correctly formated if exists 
     * @throws FileNotFoundException
     */
    public void bulkImport(String videoFile) throws FileNotFoundException 
    {
    	File vidFile = new File(videoFile);
    	if(!vidFile.exists()) throw new FileNotFoundException();
    	Scanner scan = new Scanner(vidFile);
    	//format of a line in file
    	//Forrest Gump(-1)---> negatives are initialized to zero copies
    	//Brokeback Mountain ---> no number is initialized to one copy
    	//Slumdog Millionaire (5) --> five copies
    	//save whole line using scan.nextLine();
    	//then take the string and remove parenthesis that are in the entire string
    	while(scan.hasNextLine()) 
    	{
    		
	    	String line = scan.nextLine();
	    	line = line.replaceAll("[()]", ""); // take out parenthesis from string
	    	Scanner scan2 = new Scanner(line);
	    	String videoTitle = "";
	    	int copies = 0;
	    	boolean hasNum = false;
	    	while(scan2.hasNext())
	    	{
	    		if(scan2.hasNextInt())
	    		{
	    			copies = scan2.nextInt();
	    			if(copies<0) copies = 0; //0 copies will still throw exception
	    			hasNum = true;
	    		}
	    		else
	    		{
	    			videoTitle+=scan2.next() + " ";
	    		}
	    	}
	    	if(!hasNum) copies = 1;
	    	videoTitle = videoTitle.trim();
	    	if(!videoTitle.isEmpty()) 
	    	{
	    		Video v1 = new Video(videoTitle, copies);
	    		if(inventory.contains(v1)) 
	    		{
	    			inventory.findElement(v1).addNumCopies(copies);
	    		}
	    		else
	    		{
	    			inventory.addBST(v1);
	    		}
	    	}
    	}
    }

    
    // ----------------------------
    // Video Query, Rental & Return 
    // ----------------------------
    
	/**
	 * Search the splay tree inventory to determine if a video is available. 
	 * 
	 * @param  film
	 * @return true if available
	 */
	public boolean available(String film)
	{
		Video v = new Video(film);
		if(inventory.contains(v))
		{
			if(inventory.findElement(v).getNumAvailableCopies()>0)
			{
				return true;
			}
		}
		return false;
	}

	
	
	/**
     * Update inventory. 
     * 
     * Search if the film is in inventory by calling findElement(new Video(film, 1)). 
     * 
     * If the film is not in inventory, prints the message "Film <film> is not 
     * in inventory", where <film> shall be replaced with the string that is the value 
     * of the parameter film.  If the film is in inventory with no copy left, prints
     * the message "Film <film> has been rented out".
     * 
     * If there is at least one available copy but n is greater than the number of 
     * such copies, rent all available copies. In this case, no AllCopiesRentedOutException
     * is thrown.  
     * 
     * @param film   
     * @param n 
     * @throws IllegalArgumentException      if n <= 0 or film == null or film.isEmpty()
	 * @throws FilmNotInInventoryException   if film is not in the inventory
	 * @throws AllCopiesRentedOutException   if there is zero available copy for the film.
	 */
	public void videoRent(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException,  
									     			 AllCopiesRentedOutException 
	{
		if(n<=0 || film==null ||film.isEmpty()) throw new IllegalArgumentException();
		film = film.trim();
		Video v = new Video(film);
		if(inventory.contains(v))
		{
			if(inventory.findElement(v).getNumAvailableCopies()<1) throw new AllCopiesRentedOutException("Film "+ film +" has been rented out\n\n");
			if(n>inventory.findElement(v).getNumAvailableCopies())
			{
				inventory.findElement(v).rentCopies(inventory.findElement(v).getNumAvailableCopies());
			}
			else
			{
				inventory.findElement(v).rentCopies(n);
			}
		}
		else
		{
			throw new FilmNotInInventoryException("Film "+film + " is not in inventory\n\n");
		}
	}

	
	/**
	 * Update inventory.
	 * 
	 *    1. Calls videoRent() repeatedly for every video listed in the file.  
	 *    2. For each requested video, do the following: 
	 *       a) If it is not in inventory or is rented out, an exception will be 
	 *          thrown from videoRent().  Based on the exception, prints out the following 
	 *          message: "Film <film> is not in inventory" or "Film <film> 
	 *          has been rented out." In the message, <film> shall be replaced with 
	 *          the name of the video. 
	 *       b) Otherwise, update the video record in the inventory.
	 * 
	 * For details on handling of multiple exceptions and message printing, please read Section 3.4 
	 * of the project description. 
	 *       
	 * @param videoFile  correctly formatted if exists
	 * @throws FileNotFoundException
     * @throws IllegalArgumentException     if the number of copies of any film is <= 0
	 * @throws FilmNotInInventoryException  if any film from the videoFile is not in the inventory 
	 * @throws AllCopiesRentedOutException  if there is zero available copy for some film in videoFile
	 */
	public void bulkRent(String videoFile) throws FileNotFoundException, IllegalArgumentException, 
												  FilmNotInInventoryException, AllCopiesRentedOutException 
	{
		String videoTitle ="";
		String exceptionString = "";
		int fnfe = 0; //file not found exception
		int iae = 0; //illegal argument exception
		int fniie = 0; //film not in inventory exception
		int acroe = 0; //all copies rented out exception
			try {
					File vidFile = new File(videoFile);
					Scanner scan = new Scanner(vidFile);
					while(scan.hasNextLine()) 
				    {	try {
				    		
					    	String line = scan.nextLine();
					    	line = line.replaceAll("[()]", ""); // take out parenthesis from string
					    	Scanner scan2 = new Scanner(line);
					    	int copies = 1; //copies will be 1 if no int is given
							videoTitle = "";
					    	while(scan2.hasNext())
					    	{
					    		if(scan2.hasNextInt())
					    		{
					    			copies = scan2.nextInt();
					    		}
					    		else
					    		{
					    			videoTitle+=scan2.next() + " ";
					    		}
					    	}
					    	videoTitle = videoTitle.trim();
					    	if(!videoTitle.isEmpty()) videoRent(videoTitle,copies);
				    	} catch(IllegalArgumentException e) {
							exceptionString += "Film " + videoTitle + " has an invalid request\n\n ";
							iae = 3;
						} catch(FilmNotInInventoryException e) {
							exceptionString += "Film " + videoTitle + " is not in inventory\n\n ";
							fniie = 2;
						} catch(AllCopiesRentedOutException e) {
							exceptionString += "Film " + videoTitle + " has been rented out \n\n ";
							acroe = 1;
						}
			    	
			    	}
				}catch(FileNotFoundException e) {
					exceptionString += "File " + videoFile +" not found\n ";
					fnfe = 4;}
		int ret = Math.max(fnfe,iae);
			ret = Math.max(ret, fniie);
			ret = Math.max(ret, acroe);
		if(ret == 1)
		{
			throw new AllCopiesRentedOutException(exceptionString);
		}
		if(ret == 2)
		{
			throw new FilmNotInInventoryException(exceptionString);
		}
		if(ret == 3)
		{
			throw new IllegalArgumentException(exceptionString);
		}
		if(ret == 4)
		{
			throw new FileNotFoundException(exceptionString);
		}
	}

	
	/**
	 * Update inventory.
	 * 
	 * If n exceeds the number of rented video copies, accepts up to that number of rented copies
	 * while ignoring the extra copies. 
	 * 
	 * @param film
	 * @param n
	 * @throws IllegalArgumentException     if n <= 0 or film == null or film.isEmpty()
	 * @throws FilmNotInInventoryException  if film is not in the inventory
	 */
	public void videoReturn(String film, int n) throws IllegalArgumentException, FilmNotInInventoryException 
	{
		if(n<=0||film==null||film.isEmpty()) throw new IllegalArgumentException();
		film = film.trim();
		Video v1 = new Video(film);
		if(inventory.contains(v1))
		{
			if(n>inventory.findElement(v1).getNumRentedCopies())
			{
				inventory.findElement(v1).returnCopies(inventory.findElement(v1).getNumRentedCopies());
			}
			else
			{
				inventory.findElement(v1).returnCopies(n);
			}
		}
		else
		{
			throw new FilmNotInInventoryException("Film "+film + " is not in inventory\n\n");
		}
	}
	
	
	/**
	 * Update inventory. 
	 * 
	 * Handles excessive returned copies of a film in the same way as videoReturn() does.  See Section 
	 * 3.4 of the project description on how to handle multiple exceptions. 
	 * 
	 * @param videoFile
	 * @throws FileNotFoundException
	 * @throws IllegalArgumentException    if the number of return copies of any film is <= 0
	 * @throws FilmNotInInventoryException if a film from videoFile is not in inventory
	 */
	public void bulkReturn(String videoFile) throws FileNotFoundException, IllegalArgumentException,
													FilmNotInInventoryException												
	{
		String videoTitle ="";
		String exceptionString = "";
		int fnfe = 0; //file not found exception
		int iae = 0; //illegal argument exception
		int fniie = 0; //film not in inventory exception
			try {
			File vidFile = new File(videoFile);
			Scanner scan = new Scanner(vidFile);
			while(scan.hasNextLine()) 
	    	{
				try {
		    	String line = scan.nextLine();
		    	line = line.replaceAll("[()]", ""); // take out parenthesis from string
		    	Scanner scan2 = new Scanner(line);
		    	int copies = 1; //copies will be 1 if no int is given(works same as previous methods technically, because I had also done setting copies to zero if copies 
		    	//given is less than 1 but either way an exception is thrown so I'll let n be less than 0 if entered that way
				videoTitle = "";
		    	while(scan2.hasNext())
		    	{
		    		if(scan2.hasNextInt())
		    		{
		    			copies = scan2.nextInt();
		    		}
		    		else
		    		{
		    			videoTitle+=scan2.next() + " ";
		    		}
		    	}
		    	videoTitle = videoTitle.trim();
		    	if(!videoTitle.isEmpty()) videoReturn(videoTitle,copies);
				} catch(IllegalArgumentException e) {
					exceptionString += "Film " + videoTitle + " has an invalid request\n\n ";
					iae = 3;
				} catch(FilmNotInInventoryException e) {
					exceptionString += "Film " + videoTitle + " is not in inventory\n\n ";
					fniie = 2;
				} 
	    	}
		} catch(FileNotFoundException e) {
			exceptionString += "File " + videoFile +" not found\n\n ";
			fnfe = 4;
		}
		int ret = Math.max(fnfe,iae);
		ret = Math.max(ret, fniie);
		if(ret == 2)
		{
			throw new FilmNotInInventoryException(exceptionString);
		}
		if(ret == 3)
		{
			throw new IllegalArgumentException(exceptionString);
		}
		if(ret == 4)
		{
			throw new FileNotFoundException(exceptionString);
		}
	}
		
	

	// ------------------------
	// Methods without Splaying
	// ------------------------
		
	/**
	 * Performs inorder traversal on the splay tree inventory to list all the videos by film 
	 * title, whether rented or not.  Below is a sample string if printed out: 
	 * 
	 * 
	 * Films in inventory: 
	 * 
	 * A Streetcar Named Desire (1) 
	 * Brokeback Mountain (1) 
	 * Forrest Gump (1)
	 * Psycho (1) 
	 * Singin' in the Rain (2)
	 * Slumdog Millionaire (5) 
	 * Taxi Driver (1) 
	 * The Godfather (1) 
	 * 
	 * 
	 * @return
	 */
	public String inventoryList()
	{
		String ret = "Films in inventory:\n\n";
		Iterator<Video> iter = inventory.iterator();
		while(iter.hasNext())
		{
			Video v1 = iter.next();
			ret+= v1.getFilm() + " (" + v1.getNumCopies() + ")\n ";
		}
		return ret; 
	}

	
	/**
	 * Calls rentedVideosList() and unrentedVideosList() sequentially.  For the string format, 
	 * see Transaction 5 in the sample simulation in Section 4 of the project description. 
	 *   
	 * @return 
	 */
	public String transactionsSummary()
	{
		return rentedVideosList() + "\n" + unrentedVideosList(); 
	}	
	
	/**
	 * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * Rented films: 
	 * 
	 * Brokeback Mountain (1)
	 * Forrest Gump (1) 
	 * Singin' in the Rain (2)
	 * The Godfather (1)
	 * 
	 * 
	 * @return
	 */
	private String rentedVideosList()
	{
		String ret = "Rented films:\n\n";
		Iterator<Video> iter = inventory.iterator();
		while(iter.hasNext())
		{
			Video v1 = iter.next();
			if(v1.getNumRentedCopies()>0) ret+= v1.getFilm() + " (" + v1.getNumRentedCopies() + ")\n ";
		}
		return ret;
	}

	
	/**
	 * Performs inorder traversal on the splay tree inventory.  Use a splay tree iterator.
	 * Prints only the films that have unrented copies. 
	 * 
	 * Below is a sample return string when printed out:
	 * 
	 * 
	 * Films remaining in inventory:
	 * 
	 * A Streetcar Named Desire (1) 
	 * Forrest Gump (1)
	 * Psycho (1) 
	 * Slumdog Millionaire (4) 
	 * Taxi Driver (1) 
	 * 
	 * 
	 * @return
	 */
	private String unrentedVideosList()
	{
		String ret = "Films remaining in inventory:\n\n";
		Iterator<Video> iter = inventory.iterator();
		while(iter.hasNext())
		{
			Video v1 = iter.next();
			if(v1.getNumAvailableCopies()>0) ret+= v1.getFilm() + " (" + v1.getNumAvailableCopies() + ")\n ";
		}
		return ret; 
	}	

	
	/**
	 * Parse the film name from an input line. 
	 * 
	 * @param line
	 * @return
	 */
	public static String parseFilmName(String line) 
	{
		String ret = "";
		Scanner scan = new Scanner(line);
		while(scan.hasNext())
		{
			ret+=scan.next();
			ret = ret.replaceAll("[0-9]", "");//only accept letters
			ret = ret.replaceAll("[()]", ""); //removes parentheses
		}
		return ret; 
	}
	
	
	/**
	 * Parse the number of copies from an input line. 
	 * 
	 * @param line
	 * @return
	 */
	public static int parseNumCopies(String line) 
	{
		int copies = 1;//return 1 if line has no copies
		Scanner scan = new Scanner(line);
		while(scan.hasNextInt())
		{
			copies = scan.nextInt();
		}
		return copies; 
	}
}
