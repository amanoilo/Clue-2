package clueGame;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.awt.Color;
import java.awt.Color.*;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board board;
	private String Config;
	private String FileID;
	
	
	//******************* NEW **********************
	
	private ArrayList<Card> gameCards;
	private ArrayList<Player> gamePlayers;
	private int numPlayers;	//player names are: Jon, Mary, Carl, Bjorn Bjornson, Alabama, Chet
	private int numWeapons; //weapons are: Sword, Pen, Mace, Laughing Gas, Endless Breadsticks, Heartbreak.		
	private int numRooms;	//rooms are: Maelstrom, Innistrad, Zendikar, Ravnica, Alara, Mirrodin, Phyrexia, Dominaria, Kamigawa, Shandalar	
	private int deckSize;
	
	public void createPlayers()		//player colors go: blue, red, teal, pink, white, black
	{								
		//assign names to Players from player file
		
		gamePlayers = new ArrayList<Player>();
		BufferedReader reader;
		String line = "";
		String delimiter = ",";
		
		int playerCount = 0;
		
		try 
		{
			reader = new BufferedReader(new FileReader("interactables/People.txt"));
			
			while ((line = reader.readLine()) != null) 
			{
				
				// use comma as separator
				String[] data = line.split(delimiter);
				if (data.length != 4) throw new BadConfigFormatException(". Invalid format on people file. Error in createPlayers()");
				
				String player = data[0];
				String strColor = data[1].trim();
				int row = Integer.parseInt(data[2].trim());
				int col = Integer.parseInt(data[3].trim());

				if (playerCount == 0) gamePlayers.add(new HumanPlayer(player, convertColor(strColor), board.getCellAt(row, col)));
				else gamePlayers.add(new ComputerPlayer(player, convertColor(strColor), board.getCellAt(row, col)));
				
				playerCount++;
			}
			
			
			// reader.close();
			
		}
		// Swallowing exceptions and other poorly thought out things
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		numPlayers = playerCount;
	}
	
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined 
		}
		return color;
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
