package clueGame;
import java.io.*;
import java.util.*;
import clueGame.BoardCell.*;
import java.awt.Color;

public class Player {
	
	private String name;
	private String color;
	private ArrayList<Card> playerCards;
	private boolean human;
	private BoardCell location;
	
	Player(String name, String color, boolean human)
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
	
	public String getColor()
	{
		return color;
	}
	
	public BoardCell getLocation()
	{
		return location;
	}
}
