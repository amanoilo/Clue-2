package clueGame;

import java.awt.Graphics;

public abstract class BoardCell 
{
	
	protected int row;
	protected int column;
	protected int pwidth;
	protected int pheight;
	private boolean canWrite;
	private boolean isTargeted;
	
	public BoardCell(int r, int c, int pwidth, int pheight)
	{
		this.row = r;
		this.column = c;
		this.pwidth = pwidth;
		this.pheight = pheight; 
	}
	
	public boolean isWalkway()
	{
		return false;
	}
	
	public boolean isRoom()
	{
		return false;
	}
	
	public boolean isDoorway()
	{
		return false;
	}
	
	public boolean canWrite()
	{
		return false; 
	}
	
	public boolean isTargeted(){
		return isTargeted;
	}
	public void setisTargeted(boolean bool){
		isTargeted = bool;
	}
	
	public int getR() 
	{
		return row;
	}

	public int getC() 
	{
		return column;
	}

	public abstract void draw(Graphics g);
	
	
}
