package clueGame;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class Board extends JPanel
{	
	public static final int SCALE_FACTOR = 27;
	private int width, height;   
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private ArrayList<Player> players;

	private Map<BoardCell, LinkedList<BoardCell>> adj;
	private Set<BoardCell> targets;

	private final String WalkwayInitial = "W";
	private int numRows;
	private int numColumns;
	private String fileID;

	private int currentRow;
	private int currentCol;

	public Board(String fileID, Map<Character, String> rooms, ArrayList<Player> players) throws BadConfigFormatException {
		this.rooms = new HashMap<Character,String>(rooms);
		this.fileID = fileID;
		this.players = players;
		try
		{
			FileReader reader = new FileReader(fileID);
			Scanner fin = new Scanner(reader);
			String temp = "";

			while(fin.hasNextLine()) 
			{
				temp = fin.nextLine();
				numRows++;
			}

			int t = 0;
			while(t < temp.length()) 
			{
				if(temp.charAt(t) != ',') numColumns++;	
				t++;
			}

			fin.close();
			try 
			{
				reader.close();
			}

			catch(IOException e) { System.out.println(e.getMessage()); } 
		}

		catch(FileNotFoundException e) { System.out.println("That was not a valid map file name!");	}

		board = new BoardCell[numRows][numColumns];
		loadBoardConfig();
		rooms  = new HashMap<Character,String>();
		adj = new HashMap<BoardCell, LinkedList<BoardCell>>();
		targets = new HashSet<BoardCell>();

		width = numColumns * SCALE_FACTOR;
		height = numRows * SCALE_FACTOR; 
	}

	
	public void setPlayers(ArrayList<Player> players) 
	{
		this.players = players;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		for (BoardCell[] row : board)
		{
			for (BoardCell b : row)
			{
				b.draw(g);
			}
		}	
		
		for (BoardCell[] row : board)
		{
			for (BoardCell b : row)
			{
				System.out.println(b.canWrite());
				if (b.canWrite())
				{
					g.setColor(Color.BLACK);
					g.setFont(new Font("Papyrus", Font.BOLD, 18));
					g.drawString(rooms.get(((RoomCell)b).getInitial()), b.getC() * SCALE_FACTOR, b.getR() * SCALE_FACTOR);
					System.out.println(rooms.get(((RoomCell)b).getInitial()));
				}
			}
		}	
		
		
		for (Player p : players)
		{
			p.draw(g);
		}
		
		
	}

	public void loadBoardConfig() throws BadConfigFormatException{
		FileReader reader = null;
		Scanner fin = null;
		try{
			reader = new FileReader(fileID);
			fin = new Scanner(reader);
			currentRow = 0;
			currentCol = 0;
			String currentLine = "";
			String roomInitial = "";
			while(fin.hasNextLine()){
				currentLine = fin.nextLine();
				int numCommas = 0;
				int currentChar = 0;
				while(currentChar < currentLine.length()){
					if(currentLine.charAt(currentChar) != ','){
						roomInitial += currentLine.charAt(currentChar);
						//currentChar++;
					}
					if(currentLine.charAt(currentChar) == ',' || currentChar == currentLine.length()-1){
						if(currentLine.charAt(currentChar) == ','){
							numCommas++;
						}
						if(rooms.containsKey(roomInitial.charAt(0))){
							if(roomInitial.length() == 2 && rooms.containsKey(roomInitial.charAt(0))){
								if(roomInitial.charAt(1) == 'U' || roomInitial.charAt(1) == 'u'){
									board[currentRow][currentCol] = new RoomCell(currentRow,currentCol, SCALE_FACTOR, SCALE_FACTOR, roomInitial.charAt(0),RoomCell.DoorDirection.UP, false);
									editBoardPosition();
									//currentChar++;
								}
								else if(roomInitial.charAt(1) == 'L' || roomInitial.charAt(1) == 'l'){
									board[currentRow][currentCol] = new RoomCell(currentRow,currentCol, SCALE_FACTOR, SCALE_FACTOR, roomInitial.charAt(0),RoomCell.DoorDirection.LEFT, false);
									editBoardPosition();
									//currentChar++;
								}
								else if(roomInitial.charAt(1) == 'D' || roomInitial.charAt(1) == 'd'){
									board[currentRow][currentCol] = new RoomCell(currentRow, currentCol, SCALE_FACTOR, SCALE_FACTOR, roomInitial.charAt(0),RoomCell.DoorDirection.DOWN, false);
									editBoardPosition();
									//currentChar++;
								}
								else if(roomInitial.charAt(1) == 'R' || roomInitial.charAt(1) == 'r'){
									board[currentRow][currentCol] = new RoomCell(currentRow, currentCol, SCALE_FACTOR, SCALE_FACTOR, roomInitial.charAt(0),RoomCell.DoorDirection.RIGHT, false);
									editBoardPosition();
									//currentChar++;
								}
								else if(roomInitial.charAt(1) == 'N' || roomInitial.charAt(1) == 'n'){
									board[currentRow][currentCol] = new RoomCell(currentRow, currentCol, SCALE_FACTOR, SCALE_FACTOR, roomInitial.charAt(0),RoomCell.DoorDirection.NONE, false);
									editBoardPosition();
								}
								else if(roomInitial.charAt(1) == 'X' || roomInitial.charAt(1) == 'x'){
									board[currentRow][currentCol] = new RoomCell(currentRow, currentCol, SCALE_FACTOR, SCALE_FACTOR, roomInitial.charAt(0),RoomCell.DoorDirection.NONE, true);
									editBoardPosition();
								}
								else{
									fin.close();
									throw new BadConfigFormatException("That door had an illegal direction "
											+ "modifier at position (" + currentRow +"," + currentCol +").");
								}
							}
							else if(!rooms.containsKey(roomInitial.charAt(0))){
								fin.close();
								throw new BadConfigFormatException("Invalid room identifier at (" + currentRow + "," + currentCol + "). The initial was " + roomInitial.charAt(0) + ".");
							}
							else if(roomInitial.length() == 1 && !roomInitial.equals(WalkwayInitial)){
								if(!rooms.containsKey(roomInitial.charAt(0))){
									fin.close();
									throw new BadConfigFormatException("The character identifier at (" + currentRow + "," + currentCol + ") was invalid (" + roomInitial + ").");
								}
								else{
									board[currentRow][currentCol] = new RoomCell(currentRow, currentCol, SCALE_FACTOR, SCALE_FACTOR, roomInitial.charAt(0),RoomCell.DoorDirection.NONE, false);
									editBoardPosition();
								}
							}
							else{
								board[currentRow][currentCol] = new WalkWayCell(currentRow, currentCol, SCALE_FACTOR, SCALE_FACTOR);
								editBoardPosition();
							}
						}
						else{
							fin.close();
							throw new BadConfigFormatException("The character at position (" + currentRow + "," + currentCol + ") was "
									+ "not a valid board cell identifier");
						}
						roomInitial = "";
					}
					currentChar++;
				}
				if(numCommas != numColumns-1){
					fin.close();
					throw new BadConfigFormatException("You didn't have enough elements in row " + currentRow);
				}
			}

			fin.close();
			try{
				reader.close();
			}catch(IOException e){
				System.out.println(e.getMessage());
			}
		}catch(FileNotFoundException e){
			System.out.println("Couldn't find that map file!");
		}
	}

	private void editBoardPosition(){
		if(currentCol<board[currentRow].length-1){
			currentCol++;
		}
		else{
			currentCol=0;
			currentRow++;
		}
	}

	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return adj.get(cell); //refer to the map we made earlier
	}

	public LinkedList<BoardCell> getAdjList(int i, int j){
		return adj.get(getCellAt(i,j));
	}

	public void calcAdjacencies(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				if(!adj.containsKey(board[i][j])){
					adj.put(board[i][j], new LinkedList<BoardCell>()); //if the cell isn't in the map, add it
				}
				if(board[i][j].isDoorway()){
					if(((RoomCell)board[i][j]).getDoorDirection() == RoomCell.DoorDirection.LEFT){
						adj.get(board[i][j]).add(board[i][j-1]);
					}
					else if(((RoomCell)board[i][j]).getDoorDirection() == RoomCell.DoorDirection.UP){
						adj.get(board[i][j]).add(board[i-1][j]);
					}
					else if(((RoomCell)board[i][j]).getDoorDirection() == RoomCell.DoorDirection.DOWN){
						adj.get(board[i][j]).add(board[i+1][j]);
					}
					else if(((RoomCell)board[i][j]).getDoorDirection() == RoomCell.DoorDirection.RIGHT){
						adj.get(board[i][j]).add(board[i][j+1]);
					}
				}
				else if(!board[i][j].isRoom()){
					if(i > 0 && (board[i-1][j].isDoorway() || !board[i-1][j].isRoom())){
						if(board[i-1][j].isDoorway()){
							if(((RoomCell)board[i-1][j]).getDoorDirection() == RoomCell.DoorDirection.DOWN){
								adj.get(board[i][j]).add(board[i-1][j]);
							}
						}else{
							adj.get(board[i][j]).add(board[i-1][j]);//If you're not on the left edge, add the cell to the left
						}
					}
					if(i < board.length - 1 && (board[i+1][j].isDoorway() || !board[i+1][j].isRoom())){
						if(board[i+1][j].isDoorway()){
							if(((RoomCell)board[i+1][j]).getDoorDirection() == RoomCell.DoorDirection.UP){
								adj.get(board[i][j]).add(board[i+1][j]);
							}
						}else{
							adj.get(board[i][j]).add(board[i+1][j]);//If you're not on the right edge, add the cell to the right
						}
					}
					if(j > 0 && (board[i][j-1].isDoorway() || !board[i][j-1].isRoom())){
						if(board[i][j-1].isDoorway()){
							if(((RoomCell)board[i][j-1]).getDoorDirection() == RoomCell.DoorDirection.RIGHT){
								adj.get(board[i][j]).add(board[i][j-1]);
							}
						}else{
							adj.get(board[i][j]).add(board[i][j-1]);//etc
						}
					}
					if(j < board[i].length - 1 && (board[i][j+1].isDoorway() || !board[i][j+1].isRoom())){
						if(board[i][j+1].isDoorway()){
							if(((RoomCell)board[i][j+1]).getDoorDirection() == RoomCell.DoorDirection.LEFT){
								adj.get(board[i][j]).add(board[i][j+1]);
							}
						}
						else{
							adj.get(board[i][j]).add(board[i][j+1]);
						}
					}
				}
			}
		}
	}

	public Set<BoardCell> getTargets(){
		return targets;
	}

	public Set<BoardCell> calcTargets(BoardCell start, int moves){
		targets.clear(); //new targets set for each cell
		Set<BoardCell> visited = new HashSet<BoardCell>(); //Fresh visited list
		findTargets(start, moves, visited); //start the path chain from where we are and how many moves we have to make
		return targets;
	}


	private void findTargets(BoardCell start, int moves, Set<BoardCell> Visited){
		Set<BoardCell> visited = new HashSet<BoardCell>(Visited);//Copy the visited list for each new path branch
		if(moves == 0 && !visited.contains(start)){
			targets.add(start); //If at the end and not a visited place, add the target
		}
		else{
			if(!visited.contains(start)){ //check if you've been here before
				visited.add(start); //if not, add the current position
				LinkedList<BoardCell> a = getAdjList(start);
				for(BoardCell bc : a){ //add all cells in the adjacency list
					if(bc.isDoorway() && !visited.contains(bc)){
						targets.add(bc);
					}
					else{
						findTargets(bc, moves-1, visited); //continue the path chain
					}
				}
			}
		}
	}

	public BoardCell getCellAt(int x, int y){
		return board[x][y];
	}

	public RoomCell getRoomCellAt(int x, int y){
		if(board[x][y].isRoom()){
			return (RoomCell)board[x][y];
		}
		else{
			return null;
		}
	}

	public BoardCell[][] getBoard() {
		return board;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
}
