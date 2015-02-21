package clueGame;

import java.io.*;
import java.util.*;

public class Board {
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adj;
	private Set<BoardCell> targets;
	
	private int numRows;
	private int numColumns;
	private String fileID;
	private String WalkwayInitial;
	
	private int currentRow;
	private int currentCol;
	
	public Board(String fileID, Map<Character, String> rooms) {
		this.rooms = rooms;
		try{
			FileReader reader = new FileReader(fileID);
			Scanner fin = new Scanner(reader);
			String temp = "";
			while(fin.hasNextLine()){
				temp = fin.nextLine();
				numRows++;
			}
			int t = 0;
			while(t != temp.length()){
				if(temp.charAt(t) != ','){
					numColumns++;
				}
			}
			fin.close();
			try{
				reader.close();
			}catch(IOException e){
				System.out.println(e.getMessage());
			}
		}catch(FileNotFoundException e){
			System.out.println("That was not a valid map file name!");
		}
		
		board = new BoardCell[numRows][numColumns];
		rooms  = new HashMap<Character,String>();
		adj = new HashMap<BoardCell, LinkedList<BoardCell>>();
		targets = new HashSet<BoardCell>();
		calcAdjacencies();
	}
	
	public void loadBoardConfig() throws BadConfigFormatException{
		try{
			FileReader reader = new FileReader(fileID);
			Scanner fin = new Scanner(reader);
			currentRow = 0;
			currentCol = 0;
			String currentRowValues = "";
			String roomInitial = "";
			while(fin.hasNextLine()){
				currentRowValues = fin.nextLine();
				int currentChar = 0;
				while(currentChar != currentRowValues.length()){
					if(currentRowValues.charAt(currentChar) != ','){
						roomInitial += currentRowValues.charAt(currentChar);
					}
					if(currentRowValues.charAt(currentChar) == ',' || currentChar == currentRowValues.length()-1){
						if(rooms.containsKey(roomInitial.charAt(0))){
							if(roomInitial.length() == 2){
								if(roomInitial.charAt(0) == 'U' || roomInitial.charAt(0) == 'u'){
									board[currentRow][currentCol] = new RoomCell(currentRow,currentCol,roomInitial.charAt(0),DoorDirection.UP);
									editBoardPosition();
								}
								else if(roomInitial.charAt(0) == 'L' || roomInitial.charAt(0) == 'l'){
									board[currentRow][currentCol] = new RoomCell(currentRow,currentCol,roomInitial.charAt(0),DoorDirection.LEFT);
									editBoardPosition();
								}
								else if(roomInitial.charAt(0) == 'D' || roomInitial.charAt(0) == 'd'){
									board[currentRow][currentCol] = new RoomCell(currentRow,currentCol,roomInitial.charAt(0),DoorDirection.DOWN);
									editBoardPosition();
								}
								else if(roomInitial.charAt(0) == 'R' || roomInitial.charAt(0) == 'r'){
									board[currentRow][currentCol] = new RoomCell(currentRow,currentCol,roomInitial.charAt(0),DoorDirection.RIGHT);
									editBoardPosition();
								}
								else{
									throw new BadConfigFormatException("That door had an illegal direction "
											+ "modifier at position (" + currentRow +"," + currentCol +").");
								}
							}//TODO: LOAD LEGEND FIRST
							else if(roomInitial.length() == 1 && roomInitial != WalkwayInitial){
								board[currentRow][currentCol] = new RoomCell(currentRow,currentCol,roomInitial.charAt(0),DoorDirection.NONE);
								editBoardPosition();
							}
							else{
								board[currentRow][currentCol] = new WalkWayCell(currentRow,currentCol);
								editBoardPosition();
							}
						}
						else{
							throw new BadConfigFormatException("The character at position (" + currentRow + "," + currentCol + ") was "
									+ "not a valid board cell identifier");
						}
						roomInitial = "";
					}
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
		if(currentCol<board[currentRow].length){
			currentCol++;
		}
		else{
			currentCol=0;
			currentRow++;
		}
	}
	
	private void calcTargets(BoardCell start, int moves, Set<BoardCell> Visited){
		Set<BoardCell> visited = new HashSet<BoardCell>(Visited);//Copy the visited list for each new path branch
		if(moves == 0){
			if(!visited.contains(start)) targets.add(start); //If at the end and not a visited place, add the target
		}
		else{
			if(!visited.contains(start)){ //check if you've been here before
				visited.add(start); //if not, add the current position
				for(BoardCell bc : getAdjList(start)){ //add all cells in the adjacency list
					if(bc.isDoorway()){
						targets.add(bc);
					}
					else{
						calcTargets(bc, moves-1, visited); //continue the path chain
					}
				}
			}
		}
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return adj.get(cell); //refer to the map we made earlier
	}
	
	public void calcAdjacencies(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				if(!adj.containsKey(board[i][j])){
					adj.put(board[i][j], new LinkedList<BoardCell>()); //if the cell isn't in the map, add it
				}
				if(i > 0 && (board[i-1][j].isDoorway() || !board[i-1][j].isRoom())){
					if(board[i-1][j].isDoorway()){
						if(((RoomCell)board[i-1][j]).getDoorDirection() == DoorDirection.DOWN){
							adj.get(board[i][j]).add(board[i-1][j]);
						}
					}else{
						adj.get(board[i][j]).add(board[i-1][j]);//If you're not on the left edge, add the cell to the left
					}
				}
				if(i < board.length - 1 && (board[i+1][j].isDoorway() || !board[i+1][j].isRoom())){
					if(board[i+1][j].isDoorway()){
						if(((RoomCell)board[i+1][j]).getDoorDirection() == DoorDirection.UP){
							adj.get(board[i][j]).add(board[i+1][j]);
						}
					}else{
						adj.get(board[i][j]).add(board[i+1][j]);//If you're not on the right edge, add the cell to the right
					}
				}
				if(j > 0 && (board[i][j-1].isDoorway() || !board[i][j-1].isRoom())){
					if(board[i][j-1].isDoorway()){
						if(((RoomCell)board[i][j-1]).getDoorDirection() == DoorDirection.RIGHT){
							adj.get(board[i][j]).add(board[i][j-1]);
						}
					}else{
						adj.get(board[i][j]).add(board[i][j-1]);//etc
					}
				}
				if(j < board[i].length - 1 && (board[i][j+1].isDoorway() || !board[i][j+1].isRoom())){
					if(board[i][j+1].isDoorway()){
						if(((RoomCell)board[i][j+1]).getDoorDirection() == DoorDirection.LEFT){
							adj.get(board[i][j]).add(board[i][j+1]);
						}
					}
					adj.get(board[i][j]).add(board[i][j+1]);
				}
			}
		}
	}
	
	public Set<BoardCell> getTargets(BoardCell start, int moves){
		targets.clear(); //new targets set for each cell
		Set<BoardCell> visited = new HashSet<BoardCell>(); //Fresh visited list
		calcTargets(start, moves, visited); //start the path chain from where we are and how many moves we have to make
		return targets;
	}
	
	public BoardCell getBoardCell(int x, int y){
		return board[x][y];
	}
	
	public RoomCell getRoomCell(int x, int y) throws Exception{
		if(board[x][y].isRoom()){
			return (RoomCell)board[x][y];
		}
		else{
			throw new Exception("That cell is not a RoomCell!");
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
