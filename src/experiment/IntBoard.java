package experiment;

import java.util.*;

public class IntBoard {
	private BoardCell[][] board;
	private Map<BoardCell, LinkedList<BoardCell>> adj;
	
	public IntBoard(){
		board = new BoardCell[4][4];
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				BoardCell cell = new BoardCell(i,j);
				board[i][j] = cell;
			}
		}
		adj = calcAdjacencies();
	}
	
	public BoardCell getCell(int x, int y){
		return board[x][y];
	}
	
	private Map<BoardCell, LinkedList<BoardCell>> calcAdjacencies(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				
			}
		}
		
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
