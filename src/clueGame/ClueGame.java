package clueGame;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;

public class ClueGame extends JFrame{
	private Map<Character, String> rooms;	//rooms are: Maelstrom, Innistrad, Zendikar, Ravnica, Alara, Mirrodin, Phyrexia, Dominaria, Kamigawa, Shandalar
	private Board board;
	private String Config;
	private String FileID;
	private String PlayerConfig = "interactables/People.txt";
	private String WeaponsConfig = "interactables/Weapons.txt";
	private Solution solution;
	private ArrayList<Card> gameCards;
	private ArrayList<Player> gamePlayers;  //player names are: Jon, Mary, Carl, Bjorn Bjornson, Alabama, Chet
	private ArrayList<String> gameWeapons;  //weapons are: Sword, Pen, Mace, Laughing Gas, Endless Breadsticks, Heartbreak.
	
	private ArrayList<String> gameRooms;
	
	private Detective detective;

	public ClueGame(String fileID, String config) 
	{
		
		rooms = new HashMap<Character, String>();
		Config = config;
		FileID = fileID;

		loadConfigFiles();
		Map<Character, String> tempRooms = new Map<Character, String>(rooms);
		
		tempRooms.remove('y');
		tempRooms.remove('W');
		gameRooms = new ArrayList<String>(rooms.values());

		createDeck();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("CLUE - The Game");
		setSize(960,720);
		
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		JMenuItem detectiveNotes = new JMenuItem("Detective Notes");
		detectiveNotes.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				if (detective == null) detective = new Detective(gamePlayers, gameRooms, gameWeapons);
				detective.setVisible(true);
			}
		});
		
		setJMenuBar(menuBar);
		menuBar.add(file);
		file.add(detectiveNotes);
		file.add(exit);
		
		JPanel controlPanel = new Control();
		add(controlPanel,BorderLayout.SOUTH);
		add(board, BorderLayout.CENTER);
		
	}

	public void createPlayers()		//player colors go: blue, red, cyan, pink, white, black
	{								
		//assign names to Players from player file

		gamePlayers = new ArrayList<Player>();
		BufferedReader reader;
		String line = "";
		String delimiter = ",";

		int playerCount = 0;

		try 
		{
			reader = new BufferedReader(new FileReader(PlayerConfig));

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
	}

	// used in createPlayers() method to convert color names in a text file to Color objects
	public Color convertColor(String strColor) 
	{
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

	public void createWeapons()
	{
		gameWeapons = new ArrayList<String>();
		BufferedReader reader;
		String line = "";

		try 
		{
			reader = new BufferedReader(new FileReader(WeaponsConfig));

			while ((line = reader.readLine()) != null) 
			{
				gameWeapons.add(line);
			}
		}

		// Swallowing exceptions and other poorly thought out things
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createDeck()
	{
		gameCards = new ArrayList<Card>();
		createPlayers();
		createWeapons();



		// create player cards
		for (Player p : gamePlayers)
		{
			gameCards.add(new Card(CardType.PERSON, p.getName()));

		}

		// create weapon cards
		for (String s : gameWeapons)
		{
			gameCards.add(new Card(CardType.WEAPON, s));

		}

		// Gives each computer player a copy of the deck to use as reference
		// room cards excluded because computers can only guess the room 
		// they are currently in
		for (Player p : gamePlayers)
		{
			if(!p.isHuman()) ((ComputerPlayer)p).setPossibleChoices(gameCards);
		}

		// create room cards
		for (char c : rooms.keySet())
		{
			if (c != 'y' && c != 'W')
				gameCards.add(new Card(CardType.ROOM, rooms.get(c)));		 
		}

		// Shuffles the deck
		Collections.shuffle(gameCards);


	}

	public void distributeCards()
	{
		// removes 3 cards from deck
		selectAnswer();

		int index = 0;
		for (Card c : gameCards)
		{
			gamePlayers.get(index % gamePlayers.size()).giveCard(c);
			index++;
		}
	}

	public void selectAnswer()
	{
		ArrayList<Card> temp = new ArrayList<Card>(gameCards);
		String person, weapon, room;
		person = weapon = room = "";

		for (Card c : gameCards)
		{
			if (c.getType() == CardType.PERSON && person.equals(""))
			{
				person = c.getName();
				temp.remove(c);
			}

			if (c.getType() == CardType.WEAPON && weapon.equals(""))
			{
				weapon = c.getName();
				temp.remove(c);
			}

			if (c.getType() == CardType.ROOM && room.equals(""))
			{
				room = c.getName();
				temp.remove(c);
			}
		}
		gameCards = new ArrayList<Card>(temp);
		solution = new Solution(person, weapon, room);
	}

	public Card handleSuggestion(String personGuess, String roomGuess, String weaponGuess, Player accuser)
	{
		Solution solution = new Solution(personGuess, roomGuess, weaponGuess);
		return handleSuggestion(solution, accuser);
	}

	// NOTE: debating whether having an accuser even matters.
	// It currently uses the information to not check the 
	// accuser's hand, but that may not be the correct way
	// of doing things. The accuser may guess cards in
	// their own hand, after all. 
	public Card handleSuggestion(Solution s, Player accuser)
	{	
		ArrayList<Player> tempPlayerList = new ArrayList<Player>(gamePlayers);
		tempPlayerList.remove(accuser);
		Card evidence = null;
		for (Player p : tempPlayerList)
		{
			for (Card c : p.getCards())
			{
				if(c.getName() == s.getPerson() || c.getName() == s.getWeapon() || c.getName() == s.getRoom())
					evidence = c;
			}	
		}

		if (evidence != null) updateSeen(evidence);

		return evidence;	
	}

	private void updateSeen(Card card) {
		for (Player p : gamePlayers)
		{
			if(!p.isHuman()) ((ComputerPlayer)p).updateSeen(card);
		}

	}

	public boolean checkAccusation(Solution solution)
	{
		if (this.solution == solution) return true;
		return false;
	}

	public int getPlayerQuantity()
	{
		return gamePlayers.size();
	}

	public int getRoomQuantity()
	{
		// returns size of rooms, but removes hallways ("blind eternities") and closet ("maelstrom")
		return rooms.size() - 2;
	}

	public int getWeaponQuantity()
	{
		return gameWeapons.size();
	}

	public int getDeckSize()
	{
		return gameCards.size();
	}

	public Player getPlayer(int n)
	{
		return gamePlayers.get(n);

	}

	public boolean deckContains(String CardName)
	{

		for (Card c : gameCards)
		{
			if (c.getName().equalsIgnoreCase(CardName)) return true;
		}

		return false;
	}

	public void loadConfigFiles()
	{

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

			
			board = new Board(FileID, rooms, null);
			createPlayers();
			board.setPlayers(gamePlayers);
			

		}catch(FileNotFoundException e){
			System.out.println("Couldn't find that legend file!");
		}catch(BadConfigFormatException e){
			System.out.println(e);
		}
		
		
		board.calcAdjacencies();
	}

	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException
	{
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

		board = new Board(FileID, rooms, null);
	}

	public Board getBoard() { return board;	}


	public static void main(String[] args) 
	{
		ClueGame game = new ClueGame("map/Clue Map2.csv", "map/legend.txt");
	
		game.setVisible(true);
		
	}
}
