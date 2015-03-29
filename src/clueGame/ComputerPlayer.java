package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
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
		possibleChoices = new ArrayList<Card>();
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

	@Override
	public Solution createSuggestion(String roomName)
	{
		// suggestions will be populated with all  
		// valid person and weapon suggestions
		ArrayList<Card> suggestions = new ArrayList<Card>();
		String personName = "";
		String weaponName = "";
		
		for(Card choice : possibleChoices)
		{
			boolean seen = false;
			for (Card seenCard : seenCards)
			{
				if (choice.getName() == seenCard.getName()) 
				{
					seen = true;
					break;
				}
			}
			
			if (!seen) suggestions.add(choice);
		}
		
		// shuffles the suggestions
		Collections.shuffle(suggestions);
		
		// find the first person and weapon in the suggestions list
		for (Card c : suggestions)
		{
			if (c.getType() == Card.CardType.PERSON) personName = c.getName();
			if (c.getType() == Card.CardType.WEAPON) weaponName = c.getName();
			
		}
		return new Solution(personName, weaponName, roomName);	
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
