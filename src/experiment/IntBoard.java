package experiment;

import java.util.*;

public class IntBoard {
	private BoardCell[][] board;
	private Map<BoardCell, LinkedList<BoardCell>> adj;
	private Set<BoardCell> targets;
	
	public IntBoard(){
		board = new BoardCell[4][4];
		adj = new HashMap<BoardCell, LinkedList<BoardCell>>();
		targets = new HashSet<BoardCell>();
		for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[i].length; j++){
				BoardCell cell = new BoardCell(i,j);
				board[i][j] = cell;
			}
		}
		calcAdjacencies();
	}
	
	public BoardCell getCell(int x, int y){
		return board[x][y];
	}
	
	private void calcAdjacencies(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				if(!adj.containsKey(board[i][j])){
					adj.put(board[i][j], new LinkedList<BoardCell>());
				}
				if(i > 0){
					adj.get(board[i][j]).add(board[i-1][j]);
				}
				if(i < board.length - 1){
					adj.get(board[i][j]).add(board[i+1][j]);
				}
				if(j > 0){
					adj.get(board[i][j]).add(board[i][j-1]);
				}
				if(j < board[i].length - 1){
					adj.get(board[i][j]).add(board[i][j+1]);
				}
			}
		}
	}
	
	private void calcTargets(BoardCell start, int moves, Set<BoardCell> Visited){
		Set<BoardCell> visited = new HashSet<BoardCell>(Visited);
		if(moves == 0){
			if(!visited.contains(start)) targets.add(start);
		}
		else{
			if(!visited.contains(start)){
				visited.add(start);
				for(BoardCell bc : getAdjList(start)){
					calcTargets(bc, moves-1, visited);
				}
			}
		}
	}
	
	public Set<BoardCell> getTargets(BoardCell start, int moves){
		targets.clear();
		Set<BoardCell> visited = new HashSet<BoardCell>();
		calcTargets(start, moves, visited);
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return adj.get(cell);
	}
	
	
}
