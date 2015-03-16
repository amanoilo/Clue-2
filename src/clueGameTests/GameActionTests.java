package clueGameTests;

import static org.junit.Assert.*;
import clueGame.*;

import java.awt.Color;
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
		HumanPlayer human1 = new HumanPlayer("Hank Fleck", Color.blue, board.getCellAt(6, 0));
		ComputerPlayer comp1 = new ComputerPlayer("Mary Duworth", Color.red, board.getCellAt(0, 12));
		ComputerPlayer comp5 = new ComputerPlayer("Chet Brown", Color.black, board.getCellAt(0, 15));
		
		Solution solution = new Solution("Bjorn Bjornson", "Pen", "Shandalar");
		Solution accusationH1, accusationC1, accusationC5;
		
		// Tests correct solution
		accusationH1 = human1.makeAccusation("Bjorn Bjornson", "Pen", "Shandalar");
		accusationC1 = comp1.makeAccusation("Bjorn Bjornson", "Pen", "Shandalar");
		accusationC5 = comp5.makeAccusation("Bjorn Bjornson", "Pen", "Shandalar");
		
		assertEquals(solution.compareTo(accusationH1), 1);
		assertEquals(solution.compareTo(accusationC1), 1);
		assertEquals(solution.compareTo(accusationC5), 1);
		
		// Tests solution with wrong person
		accusationH1 = human1.makeAccusation("Chet Brown", "Pen", "Shandalar");
		accusationC1 = comp1.makeAccusation("Chet Brown", "Pen", "Shandalar");
		accusationC5 = comp5.makeAccusation("Chet Brown", "Pen", "Shandalar");
		
		assertEquals(solution.compareTo(accusationH1), 0);
		assertEquals(solution.compareTo(accusationC1), 0);
		assertEquals(solution.compareTo(accusationC5), 0);
		
		// Tests solution with wrong weapon
		accusationH1 = human1.makeAccusation("Bjorn Bjornson", "Heartbreak", "Shandalar");
		accusationC1 = comp1.makeAccusation("Bjorn Bjornson", "Heartbreak", "Shandalar");
		accusationC5 = comp5.makeAccusation("Bjorn Bjornson", "Heartbreak", "Shandalar");
		
		assertEquals(solution.compareTo(accusationH1), 0);
		assertEquals(solution.compareTo(accusationC1), 0);
		assertEquals(solution.compareTo(accusationC5), 0);
		
		// Tests solution with wrong room
		accusationH1 = human1.makeAccusation("Bjorn Bjornson", "Pen", "Innistrad");
		accusationC1 = comp1.makeAccusation("Bjorn Bjornson", "Pen", "Innistrad");
		accusationC5 = comp5.makeAccusation("Bjorn Bjornson", "Pen", "Innistrad");
		
		assertEquals(solution.compareTo(accusationH1), 0);
		assertEquals(solution.compareTo(accusationC1), 0);
		assertEquals(solution.compareTo(accusationC5), 0);
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
