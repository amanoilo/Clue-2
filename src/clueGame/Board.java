package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
import java.util.*;

public class Board {
	private BoardCell[][] board;
	private Map<Character, String> rooms;
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
	
	public void editBoardPosition(){
		if(currentCol<board[currentRow].length){
			currentCol++;
		}
		else{
			currentCol=0;
			currentRow++;
		}
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
