package clueGame;

import java.util.HashMap;
import java.util.Map;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board board;
	
	
	public void loadConfigFiles(){
		
	}
	public void loadRoomConfig(){
		
	}
	
	public Board getBoard(){
		return new Board(0,0);
	}
	
	public ClueGame(String fileID, String config) {
		rooms = new HashMap<Character, String>();
	}

}
