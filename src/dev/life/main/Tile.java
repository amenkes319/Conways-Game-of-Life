package dev.life.main;

public class Tile 
{
	private boolean bAlive;
	
	public Tile()
	{
		bAlive = false;
	}
	
	public boolean getAlive()
	{
		return this.bAlive;
	}
	
	public void setAlive(boolean bAlive)
	{
		this.bAlive = bAlive;
	}
}
