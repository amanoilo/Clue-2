package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell {
	public enum DoorDirection{UP,DOWN,LEFT,RIGHT,NONE}
	
	private DoorDirection doorDirection;
	private char roomInitial;
	private final int DOOR_PIXEL_WIDTH = 5;
	private boolean canWrite;
	
	public boolean isRoom(){
		return true;
	}
	
	public boolean isDoorway(){
		if(doorDirection == DoorDirection.NONE)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean canWrite()
	{
		return canWrite;
	}
	
	public RoomCell(int r, int c, int pwidth, int pheight, char initial, DoorDirection d, boolean canWrite){
		super(r,c, pwidth, pheight);
		this.canWrite = canWrite;
		roomInitial = initial;
		doorDirection = d;
	}
	
	public DoorDirection getDoorDirection(){
		return doorDirection;
	}
	
	public char getInitial(){
		return roomInitial;
	}

	@Override
	public void draw(Graphics g) 
	{
		g.setColor(Color.GRAY);
		g.fillRect(c * pwidth, r * pheight, pwidth, pheight);
		
		switch(doorDirection)
		{
		case UP:
			g.setColor(Color.BLUE);
			g.fillRect(c * pwidth, r * pheight, pwidth, DOOR_PIXEL_WIDTH);
			break;
			
		case DOWN:
			g.setColor(Color.BLUE);
			g.fillRect(c * pwidth, r * pheight + pheight - DOOR_PIXEL_WIDTH, pwidth, DOOR_PIXEL_WIDTH);
			break;
			
		case LEFT:
			g.setColor(Color.BLUE);
			g.fillRect(c * pwidth, r * pheight, DOOR_PIXEL_WIDTH, pheight);
			break; 
			
		case RIGHT:
			g.setColor(Color.BLUE);
			g.fillRect(c * pwidth + pwidth - DOOR_PIXEL_WIDTH, r * pheight, DOOR_PIXEL_WIDTH, pheight);
			break;
			
		case NONE:
			break;		
		}
	}
}
