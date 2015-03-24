package clueGame;

public abstract class BoardCell 
{
	
	private int r;
	private int c;
	private int pwidth;
	private int pheight;
	
	
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
	
	public void drawCell() 
	{
		
	}
}
