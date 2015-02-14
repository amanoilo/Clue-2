package experiment;

import java.util.*;

public class IntBoard {
	//All kinds of static/instance variables are gonna need to go here
	public IntBoard(){
		
	}
	
	public Map<BoardCell, LinkedList<BoardCell>> calcAdjacencies(){
		Map<BoardCell, LinkedList<BoardCell>> masterList = new HashMap<BoardCell, LinkedList<BoardCell>>();
		return masterList;//void?
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
