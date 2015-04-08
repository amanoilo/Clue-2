package clueGame;
import java.io.*;
import java.util.*;

import clueGame.BoardCell.*;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Player {

	private String name;
	private Color color;
	private ArrayList<Card> playerCards;
	private boolean human;
	private BoardCell location;
	private boolean canAdvance;
	private boolean canAccuse;


	public Player(String name, Color color, BoardCell location, boolean human)
	{
		playerCards = new ArrayList<Card>();
		this.name = name;
		this.color = color;
		this.location = location;
		this.human = human;
		this.canAdvance = false;
		this.canAccuse = false;
	}

	public abstract Solution createSuggestion(String roomName);
	

	public boolean disprove(String personGuess, String roomGuess, String weaponGuess)
	{
		for (Card c : playerCards)
		{
			if (c.getName() == personGuess || c.getName() == roomGuess || c.getName() == weaponGuess) 
				return true; 
		}
		return false;
	}

	public boolean disprove(Solution s)
	{
		for (Card c : playerCards)
		{
			if (c.getName() == s.getPerson() || c.getName() == s.getRoom() || c.getName() == s.getWeapon()) 
				return true; 
		}
		return false;
	}

	public Solution makeAccusation(String personGuess, String roomGuess, String weaponGuess)
	{
		Solution accusation = new Solution(personGuess, roomGuess, weaponGuess);
		return accusation;
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

	public void setLocation(BoardCell location)
	{
		this.location = location;
	}

	public void giveCard(Card card)
	{
		playerCards.add(card);
	}

	public ArrayList<Card> getCards()
	{
		return playerCards;	
	}

	public boolean isHuman() 
	{ 
		return human; 
	}
	
	public boolean canAdvance(){
		return canAdvance;
	}
	
	public void setCanAdvance(boolean bool){
		this.canAdvance = bool;
	}
	
	public boolean canAccuse(){
		return canAccuse;
	}
	public void setCanAccuse(boolean bool){
		this.canAccuse = bool;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillOval(location.getC() * Board.SCALE_FACTOR, location.getR() * Board.SCALE_FACTOR, Board.SCALE_FACTOR, Board.SCALE_FACTOR);
		g.setColor(Color.BLACK);
		g.drawOval(location.getC() * Board.SCALE_FACTOR, location.getR() * Board.SCALE_FACTOR, Board.SCALE_FACTOR, Board.SCALE_FACTOR);
	}
	
	public void drawConflict(Graphics g, Player p1, Player p2)
	{
		g.setColor(p1.color);
		g.fillArc(p1.location.getC() * Board.SCALE_FACTOR, location.getR() * Board.SCALE_FACTOR, Board.SCALE_FACTOR, Board.SCALE_FACTOR, 0, 180);
		g.setColor(p2.color);
		g.fillArc(p2.location.getC() * Board.SCALE_FACTOR, location.getR() * Board.SCALE_FACTOR, Board.SCALE_FACTOR, Board.SCALE_FACTOR, 180, 180);
		g.setColor(Color.BLACK);
		g.drawOval(location.getC() * Board.SCALE_FACTOR, location.getR() * Board.SCALE_FACTOR, Board.SCALE_FACTOR, Board.SCALE_FACTOR);
	}

}
