package clueGameTests;

import static org.junit.Assert.*;
import clueGame.*;

import java.io.FileNotFoundException;
import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;


public class GameActionTests {
	private static Board board;

	@BeforeClass
	public static void setUp()
	{
		ClueGame game = new ClueGame("map/Clue Map.txt", "map/legend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}
	
	@Test
	public void checkAccusation()
	{
		
	}
	
	@Test
	public void selectLocation()
	{
		
	}
	
	@Test
	public void disproveSugggestion()
	{
		
	}
	
	@Test
	public void makeSuggestion()
	{
		
	}
}
