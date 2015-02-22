package clueGame;

public class RoomCell extends BoardCell {
	private DoorDirection doorDirection;
	private char roomInitial;
	
	public boolean isRoom(){
		return true;
	}
	
	public boolean isDoorway(){
		if(doorDirection == DoorDirection.NONE){
			return false;
		}
		else{
			return true;
		}
	}
	
	public RoomCell(int r, int c, char initial, DoorDirection d){
		super(r,c);
		roomInitial = initial;
		doorDirection = d;
	}
	
	public DoorDirection getDoorDirection(){
		return doorDirection;
	}
	
	public char getRoomInitial(){
		return roomInitial;
	}
}
