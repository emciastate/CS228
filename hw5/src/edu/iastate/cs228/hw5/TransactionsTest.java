package edu.iastate.cs228.hw5;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class TransactionsTest {

	@Test
	public void testInventorySetup() throws FileNotFoundException {
		// test 
		VideoStore vStore = new VideoStore("videoList1.txt");
		
		assertTrue(vStore.findVideo("The Godfather").getNumAvailableCopies() == 1);
		
		assertTrue(vStore.findVideo("Forrest Gump").getNumAvailableCopies() == 1);

		assertTrue(vStore.findVideo("Brokeback Mountain").getNumAvailableCopies() == 1);
		
		assertTrue(vStore.findVideo("A Streetcar Named Desire").getNumAvailableCopies() == 1);
		
		assertTrue(vStore.findVideo("Slumdog Millionaire").getNumAvailableCopies() == 5);
		
		assertTrue(vStore.findVideo("Taxi Driver").getNumAvailableCopies() == 1);
		
		assertTrue(vStore.findVideo("Psycho").getNumAvailableCopies() == 1);
		
		assertTrue(vStore.findVideo("Singin' in the Rain").getNumAvailableCopies() == 2);
		
	}
	@Test
	public void testRent() throws FileNotFoundException, IllegalArgumentException, FilmNotInInventoryException, AllCopiesRentedOutException {
		VideoStore vStore = new VideoStore("videoList1.txt");
		vStore.bulkRent("videoList2.txt"); //slumdog millionaire should have one copy left
		
		assertTrue(vStore.findVideo("The Godfather").getNumAvailableCopies() == 0);
		
		assertTrue(vStore.findVideo("Forrest Gump").getNumAvailableCopies() == 0);
		
		assertTrue(vStore.findVideo("Slumdog Millionaire").getNumAvailableCopies() == 1);
		
		vStore.videoRent("Slumdog Millionaire", 1); //slumdog millionaire should have no copies left
		
		assertTrue(vStore.findVideo("Slumdog Millionaire").getNumAvailableCopies() == 0);
	}
	@Test
	public void testReturn() throws FileNotFoundException, IllegalArgumentException, FilmNotInInventoryException, AllCopiesRentedOutException {
		VideoStore vStore = new VideoStore("videoList1.txt");
		vStore.bulkRent("videoList2.txt");
		vStore.bulkReturn("videoList2.txt");
		
		assertTrue(vStore.findVideo("The Godfather").getNumAvailableCopies() == 1);
		
		assertTrue(vStore.findVideo("Forrest Gump").getNumAvailableCopies() == 1);
		
		assertTrue(vStore.findVideo("Slumdog Millionaire").getNumAvailableCopies() == 5);
		
	}
	@Test
	public void testAddVideo() throws FileNotFoundException {
		VideoStore vStore = new VideoStore("videoList1.txt");
		vStore.addVideo("Spiderman", 3);
		vStore.addVideo("The Dark Knight");
		vStore.addVideo("Avengers:Endgame", 10);
		
		assertTrue(vStore.findVideo("Spiderman").getNumAvailableCopies() == 3);
		
		assertTrue(vStore.findVideo("The Dark Knight").getNumAvailableCopies() == 1);
		
		assertTrue(vStore.findVideo("Avengers:Endgame").getNumAvailableCopies() == 10);
	}

}
