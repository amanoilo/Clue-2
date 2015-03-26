package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkWayCell extends BoardCell{
	
	public WalkWayCell(int r, int c, int pwidth, int pheight){
		super(r, c, pwidth, pheight);
	}
	
	@Override
	public boolean isWalkway(){
		return true;
	}

	@Override
	public void draw(Graphics g) 
	{
		// create square
		g.setColor(Color.YELLOW);
		g.fillRect(column * pwidth, row * pheight, pwidth, pheight);
		
		// create border
		g.setColor(Color.BLACK);
		g.drawRect(column * pwidth, row * pheight, pwidth, pheight);
	}
}
