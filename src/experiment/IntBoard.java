package experiment;

import java.util.*;

public class IntBoard {
	private BoardCell[][] board;
	private Map<BoardCell, LinkedList<BoardCell>> adj;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	public IntBoard(){
		board = new BoardCell[4][4];
		adj = new HashMap<BoardCell, LinkedList<BoardCell>>();
		visited = new HashSet<BoardCell>();
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
	
	private void calcTargets(BoardCell start, int moves){
		if(moves == 0){
			targets.add(start);
		}
		else{
			if(!visited.contains(start)){
				visited.add(start);
				for(BoardCell bc : getAdjList(start)){
					calcTargets(bc, moves-1);
				}
			}
		}
	}
	
	public Set<BoardCell> getTargets(BoardCell start, int moves){
		targets.clear();
		visited.clear();
		calcTargets(start, moves);
		return targets;
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return adj.get(cell);
	}
	
	
}
