package clueGame;

public abstract class RoomCell extends BoardCell {
	private DoorDirection doorDirection;
	private char roomInitial;
	
	public boolean isRoom(){
		return true;
	}

	public RoomCell() {
		// TODO Auto-generated constructor stub
	}

}
