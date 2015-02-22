package clueGame;

import java.io.*;
import java.util.*;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board board;
	private String Config;
	private String FileID;
	//Change these based on particular game titles - These determine necessary game values
	private static final String WALKWAY_NAME = "Blind Eternities"; //Walkway
	private static final String CLOSET_NAME = "Maelstrom"; //Closet

	private String WalkwayInitial;
	private String ClosetInitial;
	
	
	public void loadConfigFiles(){
		FileReader reader = null;
		Scanner fin = null;
		try{
			reader = new FileReader(Config);
			fin = new Scanner(reader);
			
			String currentLine = "";
			boolean beforeComma = true;
			String roomName = "";
			String roomChar = "";
			while(fin.hasNextLine()){
				int currentChar = 0;
				currentLine = fin.nextLine();
				while(currentChar < currentLine.length()){
					if(beforeComma){
						if(currentLine.charAt(currentChar) == ',' && currentLine.charAt(currentChar+1) == ' '){
							beforeComma = false;
							currentChar += 2;
						}
						else if(currentLine.charAt(currentChar) != ','){
							roomChar += currentLine.charAt(currentChar);
							currentChar++;
						}
						else{
							beforeComma = false;
							currentChar++;
						}
					}
					else if(!beforeComma && currentLine.charAt(currentChar) == ','){
						fin.close();
						throw new BadConfigFormatException("Encountered two declarations on the same line: " + currentLine);
					}
					else{
						roomName += currentLine.charAt(currentChar);
						currentChar++;
					}
				}
				if(roomChar.length() > 1){
					fin.close();
					throw new BadConfigFormatException("Your legend file was trying to associate " + roomChar + " with " + roomName +
							". Only one character is permitted to be identified with a room.");
				}
				else{
					rooms.put(roomChar.charAt(0),roomName);
					if(roomName.equals(WALKWAY_NAME)){
						WalkwayInitial = roomChar;
					}
					else if(roomName.equals(CLOSET_NAME)){
						ClosetInitial = roomChar;
					}
					beforeComma = true;
					roomName = "";
					roomChar = "";
				}
			}	
			if(WalkwayInitial == null || ClosetInitial == null){
				fin.close();
				throw new BadConfigFormatException("The legend file did not include any of the indicated walkway and closet names");
			}
			fin.close();
			try{
				reader.close();
			}catch(IOException e){
				System.out.println("Couldn't close the legend file?");
			}

			board = new Board(FileID, rooms, WalkwayInitial, ClosetInitial);
		}catch(FileNotFoundException e){
			System.out.println("Couldn't find that legend file!");
		}catch(BadConfigFormatException e){
			System.out.println(e);
		}
	}
	
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException{
		FileReader reader = null;
		Scanner fin = null;
			reader = new FileReader(Config);
			fin = new Scanner(reader);
			
			String currentLine = "";
			boolean beforeComma = true;
			String roomName = "";
			String roomChar = "";
			while(fin.hasNextLine()){
				int currentChar = 0;
				currentLine = fin.nextLine();
				while(currentChar < currentLine.length()){
					if(beforeComma){
						if(currentLine.charAt(currentChar) == ',' && currentLine.charAt(currentChar+1) == ' '){
							beforeComma = false;
							currentChar += 2;
						}
						else if(currentLine.charAt(currentChar) != ','){
							roomChar += currentLine.charAt(currentChar);
							currentChar++;
						}
						else{
							beforeComma = false;
							currentChar++;
						}
					}
					else if(!beforeComma && currentLine.charAt(currentChar) == ','){
						fin.close();
						throw new BadConfigFormatException("Encountered two declarations on the same line: " + currentLine);
					}
					else{
						roomName += currentLine.charAt(currentChar);
						currentChar++;
					}
				}
				if(roomChar.length() > 1){
					fin.close();
					throw new BadConfigFormatException("Your legend file was trying to associate " + roomChar + " with " + roomName +
							". Only one character is permitted to be identified with a room.");
				}
				else{
					rooms.put(roomChar.charAt(0),roomName);
					if(roomName.equals(WALKWAY_NAME)){
						WalkwayInitial = roomChar;
					}
					else if(roomName.equals(CLOSET_NAME)){
						ClosetInitial = roomChar;
					}
					beforeComma = true;
					roomName = "";
					roomChar = "";
				}
			}	
			if(WalkwayInitial == null || ClosetInitial == null){
				fin.close();
				throw new BadConfigFormatException("The legend file did not include any of the indicated walkway and closet names");
			}
			fin.close();
			try{
				reader.close();
			}catch(IOException e){
				System.out.println("Couldn't close the legend file?");
			}

			board = new Board(FileID, rooms,WalkwayInitial,ClosetInitial);
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
