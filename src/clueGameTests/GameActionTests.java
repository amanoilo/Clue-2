package clueGameTests;

import static org.junit.Assert.*;
import clueGame.*;
import clueGame.RoomCell.DoorDirection;

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
		
		// Tests solution with everything wrong
		accusationH1 = human1.makeAccusation("Chet Brown", "Heartbreak", "Innistrad");
		accusationC1 = comp1.makeAccusation("Chet Brown", "Heartbreak", "Innistrad");
		accusationC5 = comp5.makeAccusation("Chet Brown", "Heartbreak", "Innistrad");
		
		assertEquals(solution.compareTo(accusationH1), 0);
		assertEquals(solution.compareTo(accusationC1), 0);
		assertEquals(solution.compareTo(accusationC5), 0);
	}
	
	@Test
	public void selectLocation()
	{
		ClueGame game = new ClueGame("map/Clue Map.txt", "map/legend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
		BoardCell start, end;
		Set<BoardCell> targets;
		ComputerPlayer comp1;
		
		// Tests when a room is nearby and not visited previously
		comp1 = new ComputerPlayer("Mary Duworth", Color.red, board.getCellAt(6, 0));
		comp1.setLastRoomVisited('k');
		start = board.getCellAt(6, 0);
		end = board.getCellAt(8, 0);
		targets = board.calcTargets(start, 2);
		comp1.pickLocation(targets);
		((RoomCell)end).getDoorDirection();
		
		assertEquals(((RoomCell)end).getDoorDirection(), DoorDirection.UP);
		
		// Tests when a room is nearby but has already been visited
		comp1 = new ComputerPlayer("Mary Duworth", Color.red, board.getCellAt(15, 3));
		comp1.setLastRoomVisited('d');
		start = board.getCellAt(15, 3);
		targets = board.calcTargets(start, 5);
		comp1.pickLocation(targets);
		
		// Tests when no room is nearby
		comp1 = new ComputerPlayer("Mary Duworth", Color.red, board.getCellAt(15, 3));
		comp1.setLastRoomVisited('d');
		start = board.getCellAt(10, 8);
		targets = board.calcTargets(start, 2);
		comp1.pickLocation(targets);
		
	}
	
	@Test
	public void testTargetRandomSelection() {
	ComputerPlayer player = new ComputerPlayer("Mary Duworth", Color.red, board.getCellAt(6, 0));
	// Pick a location with no rooms in target, just three targets
	board.calcTargets(10, 8, 2);
	int loc_12_0Tot = 0;
	int loc_14_2Tot = 0;
	int loc_15_1Tot = 0;
	// Run the test 100 times
	for (int i=0; i<100; i++) {
		BoardCell selected = player.pickLocation(board.getTargets());
		if (selected == board.getCellAt(12, 0))
			loc_12_0Tot++;
		else if (selected == board.getCellAt(14, 2))
			loc_14_2Tot++;
		else if (selected == board.getCellAt(15, 1))
			loc_15_1Tot++;
		else
			fail("Invalid target selected");
	}
	// Ensure we have 100 total selections (fail should also ensure)
	assertEquals(100, loc_12_0Tot + loc_14_2Tot + loc_15_1Tot);
	// Ensure each target was selected more than once
	assertTrue(loc_12_0Tot > 10);
	assertTrue(loc_14_2Tot > 10);
	assertTrue(loc_15_1Tot > 10);							
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
