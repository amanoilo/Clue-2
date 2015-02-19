package clueGame;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board board;
	
	
	public void loadConfigFiles() throws BadConfigFormatException, FileNotFoundException{
		
	}
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException{
		
	}
	
	public Board getBoard(){
		return new Board(0,0);
	}
	
	public ClueGame(String fileID, String config) {
		rooms = new HashMap<Character, String>();
	}

}
