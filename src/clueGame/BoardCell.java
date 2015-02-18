package clueGame;

public abstract class BoardCell {
	private int r;
	private int c;
	
	public BoardCell(int r, int c){
		this.r = r;
		this.c = c;
	}
	
	public boolean isWalkway(){
		return false;
	}
	
	public boolean isRoom(){
		return false;
	}
	
	public boolean isDoorway(){
		return false;
	}
}
