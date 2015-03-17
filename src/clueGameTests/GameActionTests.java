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
		// set up players for accusation tests
		HumanPlayer human1 = new HumanPlayer("Hank Fleck", Color.blue, board.getCellAt(6, 0));
		ComputerPlayer comp1 = new ComputerPlayer("Mary Duworth", Color.red, board.getCellAt(0, 12));
		ComputerPlayer comp5 = new ComputerPlayer("Chet Brown", Color.black, board.getCellAt(0, 15));

		// create a correct solution
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
		BoardCell start;
		Set<BoardCell> targets;
		ComputerPlayer comp1;

		// Tests when a room is nearby and not visited previously
		comp1 = new ComputerPlayer("Mary Duworth", Color.red, board.getCellAt(6, 0));
		comp1.setLastRoomVisited('k');
		start = board.getCellAt(6, 0);
		targets = board.calcTargets(start, 2);
		comp1.pickLocation(targets);

		assertEquals(((RoomCell)comp1.getLocation()).getDoorDirection(), DoorDirection.UP);

		// Tests when a room is nearby but has already been visited
		comp1 = new ComputerPlayer("Mary Duworth", Color.red, board.getCellAt(15, 3));
		comp1.setLastRoomVisited('d');
		start = board.getCellAt(15, 6);
		targets = board.calcTargets(start, 2);

		for (int i = 0; i < targets.size(); i++)
		{
			comp1.pickLocation(targets);
			assertFalse(comp1.getLocation().isDoorway());
		}

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
		board.calcTargets(0, 12, 2);
		int loc_0_10Tot = 0;
		int loc_2_12Tot = 0;
		int loc_1_11Tot = 0;
		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(0, 10))
				loc_0_10Tot++;
			else if (selected == board.getCellAt(2, 12))
				loc_2_12Tot++;
			else if (selected == board.getCellAt(1, 11))
				loc_1_11Tot++;
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, loc_0_10Tot + loc_2_12Tot + loc_1_11Tot);
		// Ensure each target was selected more than once
		assertTrue(loc_0_10Tot > 10);
		assertTrue(loc_2_12Tot > 10);
		assertTrue(loc_1_11Tot > 10);							
	}

	@Test
	public void disproveSuggestion()
	{
		game.createPlayers();

		// HumanPlayer human1 = new HumanPlayer("Hank Fleck", Color.blue, board.getCellAt(6, 0));
		// ComputerPlayer comp1 = new ComputerPlayer("Mary Duworth", Color.red, board.getCellAt(0, 12));
		// ComputerPlayer comp5 = new ComputerPlayer("Chet Brown", Color.black, board.getCellAt(0, 15));

		Solution suggestion;
		Card c1, c2, c3, result;
		
		// prepare a player's cards to be checked
		c1 = new Card(CardType.PERSON, "Mary Duworth");
		c2 = new Card(CardType.WEAPON, "Heartbreak");;
		c3 = new Card(CardType.ROOM, "Zendikar");;
		game.getPlayer(1).giveCard(c1);
		game.getPlayer(1).giveCard(c2);
		game.getPlayer(1).giveCard(c3);
		

		// Test that suggestion is disproved by a computer with the person card
		suggestion = new Solution("Mary Duworth", "Pen", "Kamigawa");
		result = game.handleSuggestion(suggestion, game.getPlayer(0));
		assertTrue(result.getName() == "Mary Duworth");

		// Test that suggestion is disproved by a computer with the weapon card
		
		suggestion = new Solution("Hank Fleck", "Heartbreak", "Kamigawa");
		result = game.handleSuggestion(suggestion, game.getPlayer(0));
		assertTrue(result.getName() == "Heartbreak");

		// Test that suggestion is disproved by a computer with the room card
		suggestion = new Solution("Hank Fleck", "Pen", "Zendikar");
		result = game.handleSuggestion(suggestion, game.getPlayer(0));
		assertTrue(result.getName() == "Zendikar");

		// Test that suggestion is disproved by a computer with multiple cards
		// Computer will choose first card found that is a match
		suggestion = new Solution("Mary Duworth", "Pen", "Zendikar");
		result = game.handleSuggestion(suggestion, game.getPlayer(0));
		assertTrue(result.getName() == "Mary Duworth" || result.getName() == "Zendikar");

		// Test that suggestion is not disproved
		suggestion = new Solution("Hank Fleck", "Pen", "Kamigawa");
		result = game.handleSuggestion(suggestion, game.getPlayer(0));
		assertTrue(result == null);
	}

	@Test
	public void possibleChoices()
	{
		// Test to ensure all computer players have a private "possible choices"
		// arrayList of all of the cards in play
		
		
	}
	@Test
	public void makeSuggestion()
	{
		

		
	}
}
