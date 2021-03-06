package clueGame;

import gui.Control;
import gui.Detective;
import gui.HumanAccusation;
import gui.PlayerHand;
import gui.SplashScreen;
import gui.HumanSuggestion;






import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
	private ArrayList<Card> allCards;
	private ArrayList<Player> gamePlayers;  //player names are: Jon, Mary, Carl, Bjorn Bjornson, Alabama, Chet
	private Map<String, Player> playerNames;
	private ArrayList<String> gameWeapons;  //weapons are: Sword, Pen, Mace, Laughing Gas, Endless Breadsticks, Heartbreak.
	private HumanPlayer human;

	private Control controlPanel;
	private PlayerHand playerHand;
	private HumanSuggestion suggestion;
	private HumanAccusation accusation;
	
	private int dieRoll;
	private int whoseTurn = 0;
	private Player currentPlayer;
	private boolean firstTurn = true;
	private Set<BoardCell> toClear;
	
	public Solution lastGuess = new Solution("","","");
	public String lastResponse = "";

	private static final int Y_OFFSET = 54;
	private static final int X_OFFSET = 8;
	
	private ArrayList<String> gameRooms;
	
	private Detective detective;

	public ClueGame(String fileID, String config) 
	{
		
		rooms = new HashMap<Character, String>();
		Config = config;
		FileID = fileID;

		loadConfigFiles();
		Map<Character, String> tempRooms = new HashMap<Character, String>(rooms);
		
		tempRooms.remove('y');
		tempRooms.remove('W');
		gameRooms = new ArrayList<String>(tempRooms.values());

		createDeck();
		distributeCards();
		
		board.setPlayers(gamePlayers);
		
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
		controlPanel = new Control(board);
		playerHand = new PlayerHand(human);
		add(playerHand, BorderLayout.EAST);
		add(controlPanel,BorderLayout.SOUTH);
		add(board, BorderLayout.CENTER);
		controlPanel.getNext().addActionListener(new TurnListener());
		controlPanel.getAccuse().addActionListener(new AccuseButtonListener());

		addMouseListener(new BoardListener() );

	}
	
	//Debug constructor to test LoadRoomConfig
	public ClueGame(String fileID, String config, String debug){
		rooms = new HashMap<Character, String>();
		Config = config;
		FileID = fileID;
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

				if (playerCount == 0)
				{
					HumanPlayer human = new HumanPlayer(player, convertColor(strColor), board.getCellAt(row, col));
					this.human = human;
					gamePlayers.add(human);
					currentPlayer = human;
				
				}
				else gamePlayers.add(new ComputerPlayer(player, convertColor(strColor), board.getCellAt(row, col)));

				playerCount++;
			}
			// reader.close();
		}
		// Swallowing exceptions and other poorly thought out things
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		
		//Associate players with their names (for calling them by name later on)
		playerNames = new HashMap<String, Player>();
		for(Player p: gamePlayers){
			playerNames.put(p.getName(), p);
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
			e.printStackTrace();
		} catch (IOException e) {
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
			gameCards.add(new Card(Card.CardType.PERSON, p.getName()));

		}

		// create weapon cards
		for (String s : gameWeapons)
		{
			gameCards.add(new Card(Card.CardType.WEAPON, s));

		}


		// create room cards
		for (char c : rooms.keySet())
		{
			if (c != 'y' && c != 'W')
				gameCards.add(new Card(Card.CardType.ROOM, rooms.get(c)));		 
		}
		// Gives each computer player a copy of the deck to use as reference
		
		for (Player p : gamePlayers)
		{
			if(!p.isHuman()) ((ComputerPlayer)p).setPossibleChoices(new ArrayList<Card>(gameCards));
		}
		// Shuffles the deck
		Collections.shuffle(gameCards);
		//allCards = new ArrayList<Card>(gameCards);

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
		Random rand = new Random();
		while (person == "" || weapon == "" || room == "")
		{
			int randChoice = rand.nextInt(temp.size());
			Card c = temp.get(randChoice);
			
			if (c.getType() == Card.CardType.PERSON && person.equals(""))
			{
				person = c.getName();
				gameCards.remove(c);
			}

			if (c.getType() == Card.CardType.WEAPON && weapon.equals(""))
			{
				weapon = c.getName();
				gameCards.remove(c);
			}

			if (c.getType() == Card.CardType.ROOM && room.equals(""))
			{
				room = c.getName();
				gameCards.remove(c);
			}
			
			temp.remove(c);
		}
		solution = new Solution(person, weapon, room);
	}
	
	
	public boolean checkAccusation(Solution solution)
	{
		return this.solution.equals(solution);
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
	
	//GAME LISTENERS AND GAME FLOW -----------------------------------------------------------------------------------
	//
	//
	//
	
	public class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("pressed");
		}
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
		
		System.out.println(s.toString());
		playerNames.get(s.getPerson()).setLocation(accuser.getLocation());
		
		
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
		repaint();
		return evidence;	
	
	}
	
	private void updateSeen(Card card) {
		for (Player p : gamePlayers)
		{
			if(!p.isHuman()) ((ComputerPlayer)p).updateSeen(card);
		}

	}
	
	private class AccuseButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(currentPlayer.canAccuse()){
				accusation = new HumanAccusation(gamePlayers,new ArrayList<String>(rooms.values()), gameWeapons);
				accusation.getSubmitButton().addActionListener(new AccusationListener());
				accusation.setVisible(true);
			}else{
				JOptionPane.showMessageDialog( null, "You cannot make an accusation at this time!");
			}
			
		}
	}
	private class AccusationListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			Solution newAccusation = new Solution(accusation.get_Name().getSelectedItem().toString(), 
												accusation.getWeapon().getSelectedItem().toString(), 
												accusation.getRoom().getSelectedItem().toString());
			boolean response = checkAccusation(newAccusation);
			if(response){
				JOptionPane.showMessageDialog( null, "Congratulations, you won!");
				accusation.setVisible(false);
			}else{
				JOptionPane.showMessageDialog( null, "Sorry, not correct!");
				currentPlayer.setCanAccuse(false);
				currentPlayer.setCanAdvance(true);
				accusation.setVisible(false);
			}
		}

		
	}
	private class SuggestionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Solution newSuggestion = new Solution(suggestion.get_Name().getSelectedItem().toString(), 
												suggestion.getWeapon().getSelectedItem().toString(), 
												suggestion.getRoom().getSelectedItem().toString());
			
			Card response = handleSuggestion(newSuggestion, currentPlayer);	
			controlPanel.getGuess().setText(newSuggestion.toString());
			controlPanel.getResponse().setText(response.getName());
			suggestion.setVisible(false);
			repaint();
			
		}
		
	}
	private class BoardListener implements MouseListener {
		@Override
		public void mousePressed (MouseEvent e)
		{
			if(currentPlayer.isHuman()){
				if(!currentPlayer.canAdvance()){
					Point newPoint = e.getPoint();
					BoardCell clickedCell = board.getBoard()[(((int)newPoint.getY()-Y_OFFSET))/board.SCALE_FACTOR][(((int)newPoint.getX()) - X_OFFSET)/board.SCALE_FACTOR];
					if(clickedCell.isTargeted())
					{
						currentPlayer.setLocation(clickedCell);
						currentPlayer.setCanAccuse(false);
						currentPlayer.setCanAdvance(true);
						if(clickedCell.isRoom())
						{
						suggestion = new HumanSuggestion(gamePlayers, rooms.get(((RoomCell)clickedCell).getInitial()), gameWeapons); 
						suggestion.getSubmitButton().addActionListener(new SuggestionListener());
						suggestion.setVisible(true);
						}}
						else
						{
							JOptionPane.showMessageDialog( null, "Invalid Move Selection");
						}
					}
				repaint();
			}
		}
	
		
		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}
	
	private class TurnListener implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		advanceTurn();
    		controlPanel.getCurrent().setText(currentPlayer.getName());
    		controlPanel.getDie().setText(Integer.toString(dieRoll));
    		controlPanel.getGuess().setText(lastGuess.toString());
    		controlPanel.getResponse().setText(lastResponse);
    		
    	}
    }
	
	public void advanceTurn(){
		Random rand = new Random();
		if (firstTurn){
			currentPlayer = gamePlayers.get(whoseTurn);
			currentPlayer.setCanAccuse(true);
			
		}


		if(!currentPlayer.canAdvance()){
			dieRoll = rand.nextInt(6)+1;
		}
		if(toClear == null){
			board.calcTargets(currentPlayer.getLocation(), dieRoll);
		}

		if(toClear != null && !currentPlayer.canAdvance()){
			JOptionPane.showMessageDialog(null, "You must move before ending your turn!");
		}

		if(currentPlayer.isHuman()){
			for(BoardCell x: board.getTargets()){
				x.setisTargeted(true);
			}
			if(currentPlayer.canAdvance() && toClear != null){
				for(BoardCell x: toClear){
					x.setisTargeted(false);
				}

				toClear = null;
				nextPlayer();
				currentPlayer.setCanAdvance(false);	
				board.calcTargets(currentPlayer.getLocation(), dieRoll);


			}else{
				
				toClear = new HashSet<BoardCell>(board.getTargets());
			}

		}

		if(!currentPlayer.isHuman()){
			if(currentPlayer.canAdvance()){
				nextPlayer();
				currentPlayer.setCanAdvance(false);
				advanceTurn();
			}else{
				if (((ComputerPlayer)currentPlayer).hasSolution()){
					Solution newAccusation = ((ComputerPlayer)currentPlayer).makeAccusation();
					System.out.println("ready" + newAccusation.toString());
					if(checkAccusation(newAccusation)){
						JOptionPane.showMessageDialog(null, currentPlayer.getName()+ " has won with " + newAccusation.toString());
					}else{
						JOptionPane.showMessageDialog(null, currentPlayer.getName()+ " was incorrect with " + newAccusation.toString());
						((ComputerPlayer)currentPlayer).notReadyToAccuse();
					}
				}else{
					((ComputerPlayer) currentPlayer).move(board.getTargets());
					if(currentPlayer.getLocation().isRoom()){
						Solution guess;
						guess = ((ComputerPlayer) currentPlayer).createSuggestion(
								rooms.get(
										((RoomCell)currentPlayer.getLocation()).getInitial()
										)
								);
						lastGuess = guess;
						Card newResponse = handleSuggestion(guess, currentPlayer);
						if(newResponse!= null){
							lastResponse = newResponse.getName();
						}else{
							((ComputerPlayer)currentPlayer).readyToAccuse();
							lastResponse = "No New Clue";
						}




					}
				}
				
				currentPlayer.setCanAdvance(true);
			}


		}
		repaint();
	}

	
	public void nextPlayer(){
		if(whoseTurn < gamePlayers.size()-1){
			whoseTurn++;
		}else{
			whoseTurn = 0;
		}
		currentPlayer = gamePlayers.get(whoseTurn);
		currentPlayer.setCanAccuse(true);
	}
	
	public Board getBoard() { return board;	}
	

	public static void main(String[] args) 
	{
		ClueGame game = new ClueGame("map/Clue Map2.csv", "map/legend.txt");
		game.setVisible(true);
		SplashScreen splash = new SplashScreen(game);

		
	}
}
