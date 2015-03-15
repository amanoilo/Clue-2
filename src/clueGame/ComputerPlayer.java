package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class ComputerPlayer extends Player{
	
	private char lastRoomVisited;
	private ArrayList<String> seenCards;
	
	public ComputerPlayer(String name, Color color, BoardCell location)
	{
		super(name, color, location, false);
	}
	
//	public ComputerPlayer(String name, Color color)
//	{
//		super(name, color, false);
//	}
	
	public void pickLocation()
	{
		
	}
	
	public Solution createSuggestion()
	{
		String roomName = ""; 

		// if (getLocation().isRoom()) room
		
		Solution suggestion = new Solution(roomName, "blah", "blah");		
		
		return suggestion;
	}
	
	public void updateSeen()
	{
		
	}
}
