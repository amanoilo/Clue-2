package clueGame;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board board;
	private String Config;
	private String FileID;
	
	
	public void loadConfigFiles() throws BadConfigFormatException{
		board = new Board(FileID);
		board.loadBoardConfig();
	}
	
	public Board getBoard(){
		return board;
	}
	
	public ClueGame(String fileID, String config) {
		rooms = new HashMap<Character, String>();
		
	}

}
