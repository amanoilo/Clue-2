package experiment;

import java.util.*;

public class IntBoard {
	private BoardCell[][] board;
	
	public IntBoard(){
		board = new BoardCell[4][4];
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				BoardCell cell = new BoardCell(i,j);
				board[i][j] = cell;
			}
		}
	}
	
	public BoardCell getCell(int x, int y){
		BoardCell test = new BoardCell(x,y);
		return test;
	}
	
	public Map<BoardCell, LinkedList<BoardCell>> calcAdjacencies(){
		Map<BoardCell, LinkedList<BoardCell>> masterList = new HashMap<BoardCell, LinkedList<BoardCell>>();
		return masterList;
	}
	
	public void calcTargets(BoardCell start, int moves){
		Set<BoardCell> visited = new HashSet<BoardCell>(); //this probably ends up being somthing that goes at the top
	}
	
	public Set<BoardCell> getTargets(){
		return new HashSet<BoardCell>();
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return new LinkedList<BoardCell>();
	}
	
	
}
