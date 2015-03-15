package clueGame;
import java.io.*;
import java.util.*;
import clueGame.BoardCell.*;
public class Player {
	
	private String name;
	private String color;
	private ArrayList<Card> playerCards;
	private boolean human;
	private BoardCell location;
	
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
