package clueGame;

public abstract class RoomCell extends BoardCell {
	private DoorDirection doorDirection;
	private char roomInitial;
	
	public boolean isRoom(){
		return true;
	}

	public RoomCell(int r, int c){
		super(r,c);
	}
	
	public DoorDirection getDoorDirection(){
		return doorDirection;
	}
	
	public char getRoomInitial(){
		return roomInitial;
	}
}
