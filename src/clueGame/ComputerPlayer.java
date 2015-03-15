package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player{
	
	private char lastRoomVisited;
	
	public ComputerPlayer(String name, Color color, BoardCell location)
	{
		super(name, color, location, false);
	}
	
	public ComputerPlayer(String name, Color color)
	{
		super(name, color, false);
	}
	
	public void pickLocation()
	{
		
	}
	
	public void createSuggestion()
	{
		
	}
	
	public void updateSeen()
	{
		
	}
}
