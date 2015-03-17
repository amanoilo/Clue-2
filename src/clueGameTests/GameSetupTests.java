package clueGameTests;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
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
		// set up for constructor tests
		HumanPlayer human1 = new HumanPlayer("Hank Fleck", Color.blue, board.getCellAt(6, 0));
		ComputerPlayer comp1 = new ComputerPlayer("Mary Duworth", Color.red, board.getCellAt(0, 12));
		ComputerPlayer comp5 = new ComputerPlayer("Chet Brown", Color.black, board.getCellAt(0, 15));

		// set up for creatPlayers() tests
		game.createPlayers();
		assertEquals(game.getPlayerQuantity(), 6);


		//Test Names for Constructors
		assertEquals(human1.getName(), "Hank Fleck");
		assertEquals(comp1.getName(), "Mary Duworth");
		assertEquals(comp5.getName(), "Chet Brown");

		//Test Names for createPlayers()
		assertEquals(game.getPlayer(0).getName(), "Hank Fleck");
		assertEquals(game.getPlayer(1).getName(), "Mary Duworth");
		assertEquals(game.getPlayer(5).getName(), "Chet Brown");

		//Test Colors for Constructors
		assertEquals(human1.getColor(), Color.blue);
		assertEquals(comp1.getColor(), Color.red);
		assertEquals(comp5.getColor(), Color.black);

		//Test Colors for createPlayers()
		assertEquals(game.getPlayer(0).getColor(), Color.blue);
		assertEquals(game.getPlayer(1).getColor(), Color.red);
		assertEquals(game.getPlayer(5).getColor(), Color.black);

		//Test Start Locations for Constructors
		assertEquals(human1.getLocation(), board.getCellAt(6, 0));
		assertEquals(comp1.getLocation(), board.getCellAt(0, 12));
		assertEquals(comp5.getLocation(), board.getCellAt(0, 15));

		//Test Start Locations for createPlayers()
		assertEquals(game.getPlayer(0).getLocation(), board.getCellAt(6, 0));
		assertEquals(game.getPlayer(1).getLocation(),  board.getCellAt(0, 12));
		assertEquals(game.getPlayer(5).getLocation(), board.getCellAt(0, 15));
	}

	@Test
	public void loadCards_distributeCards()
	{
		// create the deck
		game.createDeck();
		
		//check amount of cards
		assertEquals(21, game.getDeckSize());

		//check amount of each CardType
		assertEquals(6, game.getWeaponQuantity());
		assertEquals(6, game.getPlayerQuantity());
		assertEquals(9, game.getRoomQuantity());

		//check that the deck has these cards
		assertTrue(game.deckContains("Hank Fleck"));
		assertTrue(game.deckContains("Endless Breadsticks"));
		assertTrue(game.deckContains("Shandalar"));

		//dealing tests
		game.distributeCards();

		// check that all players have roughly the same number of cards
		// should be a total of 18 (21 - 3 cards taken for solution)
		assertEquals(game.getPlayer(0).getCards().size(), 3);
		assertEquals(game.getPlayer(1).getCards().size(), 3);
		assertEquals(game.getPlayer(2).getCards().size(), 3);
		assertEquals(game.getPlayer(3).getCards().size(), 3);
		assertEquals(game.getPlayer(4).getCards().size(), 3);
		assertEquals(game.getPlayer(5).getCards().size(), 3);

		// test to see if two players received the same cards
		// only checks adjacent players because a more comprehensive
		// test would be overkill
		ArrayList<Card> a1;
		ArrayList<Card> a2;
		
		for (int i = 0; i < game.getPlayerQuantity() - 1; i++)
		{
			a1 = game.getPlayer(i).getCards();
			a2 = game.getPlayer(i + 1).getCards();
			
			for (Card c1 : a1)
			{
				for (Card c2 : a2)
				{
					assertTrue(c1.getName() != c2.getName());
				}
			}
		}
		
		// tests the methods themselves
		HumanPlayer human1 = new HumanPlayer("Hank Fleck", Color.blue, board.getCellAt(6, 0));
		ComputerPlayer comp1 = new ComputerPlayer("Mary Duworth", Color.red, board.getCellAt(0, 12));
		ComputerPlayer comp5 = new ComputerPlayer("Chet Brown", Color.black, board.getCellAt(0, 15));
		
		Card c1 = new Card(CardType.PERSON, "Mary Duworth");
		Card c2 = new Card(CardType.WEAPON, "Heartbreak");;
		Card c3 = new Card(CardType.ROOM, "Zendikar");;
		
		// check giveCard method to ensure cards are received by player objects
		human1.giveCard(c1);
		comp1.giveCard(c2);
		comp5.giveCard(c3);
		
		assertTrue(human1.getCards().contains(c1));
		assertTrue(comp1.getCards().contains(c2));
		assertTrue(comp5.getCards().contains(c3));
	}
}
