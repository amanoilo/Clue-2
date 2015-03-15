package clueGameTests;

import static org.junit.Assert.*;
import clueGame.*;

import java.io.FileNotFoundException;
import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;


public class GameActionTests {
	private static Board board;
	private static ClueGame game;
	private static Solution solution;
	@BeforeClass
	public static void setUp()
	{
		game = new ClueGame("map/Clue Map.txt", "map/legend.txt");
		game.loadConfigFiles();
		solution = new Solution("Nicol Bolas", "Endless Breadsticks", "Shalandar");
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
