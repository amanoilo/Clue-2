package clueGame;

import java.io.*;
import java.util.*;
import java.awt.Color.*;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board board;
	private String Config;
	private String FileID;
	
	
	//******************* NEW **********************
	private ArrayList<Card> gameCards;
	private ArrayList<Player> gamePlayers;
	private int numPlayers;	//player names are: Nicol Bolas, Ugin, Sorin, Urza, Jace, Karn
	private int numWeapons; //weapons are: Sword, Pen, Mace, Laughing Gas, Endless Breadsticks, Heartbreak.		
	private int numRooms;	//rooms are: Maelstrom, Innistrad, Zendikar, Ravnica, Alara, Mirrodin, Phyrexia, Dominaria, Kamigawa, Shandalar	
	private int deckSize;
	
	public void createPlayers()		//player colors go: blue, red, teal, pink, white, black
	{								
		//assign names to Players from player file
		
		
	}
	
	public void createDeck()
	{
		
	}
	
	public void distributeCards()
	{
		
	}
	
	public void selectAnswer()
	{
		
	}
	
	public void handleSuggestion(String personGuess, String roomGuess, String weaponGuess, Player accuser)
	{
		
	}
	
	public boolean checkAccusation(Solution solution)
	{
		
		return true;
	}
	  
	public int getPlayers()
	{
		return numPlayers;
	}
	  
	public int getRooms()
	{
		return numRooms;
	}
	
	public int getWeapons()
	{
		return numWeapons;
	}
	
	public int getDeckSize()
	{
		return deckSize;
	}
	
	public boolean deckContains(String CardName)
	{
		return false;
	}
	
	
	//******************* END NEW **********************
	
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
					beforeComma = true;
					roomName = "";
					roomChar = "";
				}
			}	
			if(!rooms.containsKey('W')){
				fin.close();
				throw new BadConfigFormatException("The legend file did not include any w as an initial, indicating there are no valid walkways.");
			}
			fin.close();
			try{
				reader.close();
			}catch(IOException e){
				System.out.println("Couldn't close the legend file?");
			}

			board = new Board(FileID, rooms);
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
					beforeComma = true;
					roomName = "";
					roomChar = "";
				}
			}	
			if(!rooms.containsKey('W')){
				fin.close();
				throw new BadConfigFormatException("The legend file did not include any w as an initial, indicating there are no valid walkways.");
			}
			fin.close();
			try{
				reader.close();
			}catch(IOException e){
				System.out.println("Couldn't close the legend file?");
			}

			board = new Board(FileID, rooms);
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
