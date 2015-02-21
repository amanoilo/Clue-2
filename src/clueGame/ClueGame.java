package clueGame;

import java.io.*;
import java.util.*;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board board;
	private String Config;
	private String FileID;
	
	
	public void loadConfigFiles() throws BadConfigFormatException{
		FileReader reader = null;
		Scanner fin = null;
		try{
			reader = new FileReader(Config);
			fin = new Scanner(reader);
			
			String currentLine = "";
			int currentChar = 0;
			boolean beforeComma = true;
			String roomName = "";
			String roomChar = "";
			while(fin.hasNextLine()){
				currentLine = fin.nextLine();
				while(currentChar < currentLine.length()){
					if(beforeComma){
						if(currentLine.charAt(currentChar) != ','){
							roomChar += currentLine.charAt(currentChar);
							currentChar++;
						}
						else{
							beforeComma = false;
							currentChar++;
						}
					}
					else{
						roomName += currentLine.charAt(currentChar);
						currentChar++;
					}
				}
				if(roomChar.length() > 1){
					throw new BadConfigFormatException("Your legend file was trying to associate " + roomChar + " with " + roomName +
							". Only one character is permitted to be identified with a room.");
				}
				else{
					rooms.put(roomChar.charAt(0),roomName);
					currentChar = 0;
					beforeComma = true;
					roomName = "";
					roomChar = "";
				}
			}	
			fin.close();
			try{
				reader.close();
			}catch(IOException e){
				System.out.println("Couldn't close the legend file?");
			}

			board = new Board(FileID, rooms);
			board.loadBoardConfig();
		}catch(FileNotFoundException e){
			System.out.println("Couldn't find that legend file!");
		}
	}
	
	public Board getBoard(){
		return board;
	}
	
	public ClueGame(String fileID, String config) {
		rooms = new HashMap<Character, String>();
		Config = config;
		FileID = fileID;
	}

}
