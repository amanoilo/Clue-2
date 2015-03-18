package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{

	private char lastRoomVisited;
	private ArrayList<Card> seenCards;
	private ArrayList<Card> possibleChoices;

	public ComputerPlayer(String name, Color color, BoardCell location)
	{
		super(name, color, location, false);
		lastRoomVisited = '\0';
		seenCards = new ArrayList<Card>();
	}

	public BoardCell pickLocation(Set<BoardCell> targets)
	{
		// search for a room to visit
		for (BoardCell b : targets)
		{
			// visit that location, if it has not already been visited
			if (b.isDoorway() && ((RoomCell)b).getInitial() != lastRoomVisited) 
			{
				setLocation(b);
				return getLocation();
			}
		}
		
		// if no rooms exist, find a random square from the targets list
		int size = targets.size();
		int item = new Random().nextInt(size);
		int i = 0;
		
		for(BoardCell b : targets)
		{
		    if (i == item && !b.isDoorway())
		    {
		    	setLocation(b);
		    	return getLocation();
		    }
		        
		    i++;
		}
		
		return null;
	}

	public Solution createSuggestion(String roomName)
	{
		for(Card choice : possibleChoices)
		{
			for (Card seenCard : seenCards)
			{
				// if choice
				
			}
			
		}

		
		Solution suggestion = new Solution(roomName, "blah", "blah");		

		return suggestion;
	}

	public void updateSeen(Card card)
	{
		seenCards.add(card);
	}
	
	public void setLastRoomVisited(char lastRoomVisited)
	{
		this.lastRoomVisited = lastRoomVisited;
	}
	
	public char getLastRoomVisited()
	{
		return lastRoomVisited;
	}
	
	public void setPossibleChoices(ArrayList<Card> possibleChoices)
	{
		this.possibleChoices = possibleChoices;
	}
	
	public ArrayList<Card> getPossibleChoices()
	{
		return possibleChoices;
	}
}
