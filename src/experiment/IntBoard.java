package experiment;

import java.util.*;

public class IntBoard {
	private LinkedList<BoardCell> board;
	
	public IntBoard(){
		board = new LinkedList<BoardCell>();
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++){
				BoardCell cell = new BoardCell(i,j);
				board.add(cell);
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
