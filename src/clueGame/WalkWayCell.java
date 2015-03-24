package clueGame;

public class WalkWayCell extends BoardCell{
	
	public WalkWayCell(int r, int c, int pwidth, int pheight){
		super(r, c, pwidth, pheight);
	}
	
	@Override
	public boolean isWalkway(){
		return true;
	}
}
