package clueGame;

import java.util.*;

public class Board {
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;
	
	public Board(int rows, int cols) {
		board = new BoardCell[numRows][numColumns];
		rooms  = new HashMap<Character,String>();
		numRows = rows;
		numColumns = cols;
	}
	
	public void loadBoardConfig(){
		
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
