package clueGame;

public class WalkWayCell extends BoardCell{
	
	public WalkWayCell(int r, int c){
		super(r, c);
	}
	
	@Override
	public boolean isWalkway(){
		return true;
	}
}
