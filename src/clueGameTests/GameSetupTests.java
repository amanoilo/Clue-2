package clueGameTests;

import org.junit.BeforeClass;
import org.junit.Test;

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
		ComputerPlayer human1 = new HumanPlayer();
		ComputerPlayer comp1 = new ComputerPlayer();
		ComputerPlayer comp5 = new ComputerPlayer();

		
		game.createPlayers();
		
		AssertTrue(game.getPlayers(), 6);
		
		
	}
	
	@Test
	public void loadCards()
	{
		
	}
	
	@Test
	public void dealCards()
	{
		
	}
}
