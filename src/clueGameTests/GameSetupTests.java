package clueGameTests;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import clueGame.Board;
import clueGame.ClueGame;
import clueGame.*;


public class GameSetupTests {
	private static Board board;
	private static ClueGame game;

	@BeforeClass
	public static void setUp()
	{
		game = new ClueGame("map/Clue Map.txt", "map/legend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}
	
	@Test
	public void loadPeople()
	{
		HumanPlayer human1 = new HumanPlayer("Nicol Bolas", "Blue");
		ComputerPlayer comp1 = new ComputerPlayer("Ugin", "Red");
		ComputerPlayer comp5 = new ComputerPlayer("Karn", "Black");

		
		game.createPlayers();
		
		assertEquals(game.getPlayers(), 6);
		
		
		//Test Names
		assertEquals(human1.getName(), "Nicol Bolas");
		assertEquals(comp1.getName(), "Ugin");
		assertEquals(comp5.getName(), "Karn");
		
		//Test Colors
		assertEquals(human1.getColor(), "Blue");
		assertEquals(comp1.getColor(), "Red");
		assertEquals(comp5.getColor(), "Black");
		
		//Test Start Locations
		assertEquals(human1.getLocation(), board.getCellAt(6, 0));
		assertEquals(comp1.getLocation(), board.getCellAt(0, 12));
		assertEquals(comp5.getLocation(), board.getCellAt(22, 15));
	}
	
	@Test
	public void loadCards_distributeCards()
	{
		game.createDeck();
		//check amount of cards
		assertTrue(game.getDeckSize() == 21);
		
		//check amount of each CardType
		assertTrue(game.getWeapons() == 6);
		assertTrue(game.getPlayers() == 6);
		assertTrue(game.getRooms() == 9);
		
		//check that the deck has these cards
		assertTrue(game.deckContains("Nicol Bolas"));
		assertTrue(game.deckContains("Endless Breadsticks"));
		assertTrue(game.deckContains("Shandalar"));
		
		//dealing tests
		game.distributeCards();
		assertTrue(game.getDeckSize() == 0);
		/*fill in here
		 * 
		 * 
		 * 
		 * 
		 */
		
		
		
		
	}

}
