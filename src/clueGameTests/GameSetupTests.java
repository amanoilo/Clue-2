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
		HumanPlayer human1 = new HumanPlayer();
		ComputerPlayer comp1 = new ComputerPlayer();
		ComputerPlayer comp5 = new ComputerPlayer();

		
		game.createPlayers();
		
		assertEquals(game.getPlayers(), 6);
		
		
		//Test Names
		assertEquals(human1.getName(), "Jon");
		assertEquals(comp1.getName(), "Mary");
		assertEquals(comp5.getName(), "Chet");
		
		//Test Colors
		assertEquals(human1.getColor(), "Blue");
		assertEquals(Comp1.getColor(), "Red");
		assertEquals(Comp5.getColor(), "Black");
		
		//Test Start Locations
		assertEquals(human1.getLocation(), board.getCellAt(6, 0));
		assertEquals(comp1.getLocation(), board.getCellAt(0, 12));
		assertEquals(comp5.getLocation(), board.getCellAt(22, 15));
	}
	
	@Test
	public void loadCards()
	{
		//check amount of cards
		assertTrue(game.getDeckSize() == 21);
		
		//check amount of each CardType
		assertTrue(game.getWeapons() == 6);
		assertTrue(game.getPlayers() == 6);
		assertTrue(game.getRooms() == 9);
		
		
	}
	
	@Test
	public void dealCards()
	{
		
	}
}
