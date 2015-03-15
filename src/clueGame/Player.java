package clueGame;
import java.io.*;
import java.util.*;
import clueGame.BoardCell.*;
import java.awt.Color;

public class Player {
	
	private String name;
	private Color color;
	private ArrayList<Card> playerCards;
	private boolean human;
	private BoardCell location;
	
	public Player(String name, Color color, BoardCell location, boolean human)
	{
		playerCards = new ArrayList<Card>();
		this.name = name;
		this.color = color;
		this.location = location;
		this.human = human;
	}
	
	public Player(String name, Color color, boolean human)
	{
		this.name = name;
		this.color = color;
		this.human = human;
	}
	
	public Card disprove(String personGuess, String roomGuess, String weaponGuess)
	{
		return playerCards.get(0);
	}
	
	public String getName()
	{
		return name;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public BoardCell getLocation()
	{
		return location;
	}
	
	public void giveCard(Card card)
	{
		playerCards.add(card);
	}
	
	public ArrayList<Card> getCards()
	{
		return playerCards;	
	}
}
