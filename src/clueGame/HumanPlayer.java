package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player{

	
	public HumanPlayer(String name, Color color, BoardCell location)
	{
		super(name, color, location, true);
	}
	
	@Override
	public Solution createSuggestion(String roomName){
		return new Solution("","","");
	}
}
