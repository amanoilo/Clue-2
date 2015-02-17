package experiment;

import java.util.*;

public class IntBoard {
	private final int NUM_ROWS = 4;
	private final int NUM_COLS = 4;
	private BoardCell[][] board;
	private Map<BoardCell, LinkedList<BoardCell>> adj;
	private Set<BoardCell> targets;
	
	public IntBoard(){
		board = new BoardCell[NUM_ROWS][NUM_COLS];
		adj = new HashMap<BoardCell, LinkedList<BoardCell>>();
		targets = new HashSet<BoardCell>();
		for (int i = 0; i < board.length; i++){ //create the board
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
					adj.put(board[i][j], new LinkedList<BoardCell>()); //if the cell isn't in the map, add it
				}
				if(i > 0){
					adj.get(board[i][j]).add(board[i-1][j]);//If you're not on the left edge, add the cell to the left
				}
				if(i < board.length - 1){
					adj.get(board[i][j]).add(board[i+1][j]);//If you're not on the right edge, add the cell to the right
				}
				if(j > 0){
					adj.get(board[i][j]).add(board[i][j-1]);//etc
				}
				if(j < board[i].length - 1){
					adj.get(board[i][j]).add(board[i][j+1]);
				}
			}
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
					calcTargets(bc, moves-1, visited); //continue the path chain
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
	
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return adj.get(cell); //refer to the map we made earlier
	}
}
