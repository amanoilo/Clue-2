package clueGame;

import java.io.FileNotFoundException;
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
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException{
		
	}
	
	public BoardCell getBoardCell(int x, int y){
		return board[x][y];
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
