package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{

	private char lastRoomVisited;
	private ArrayList<Card> seenCards;

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

	public Solution createSuggestion()
	{
		String roomName = ""; 

		// if (getLocation().isRoom()) room

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
}
