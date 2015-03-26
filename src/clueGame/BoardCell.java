package clueGame;

import java.awt.Graphics;

public abstract class BoardCell 
{
	
	protected int r;
	protected int c;
	protected int pwidth;
	protected int pheight;
	private boolean canWrite;
	
	public BoardCell(int r, int c, int pwidth, int pheight)
	{
		this.r = r;
		this.c = c;
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
	
	
	
	public int getR() 
	{
		return r;
	}

	public int getC() 
	{
		return c;
	}

	public abstract void draw(Graphics g);
	
	
}
