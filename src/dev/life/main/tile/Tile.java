package dev.life.main.tile;

import java.awt.Color;
import java.awt.Graphics;

public class Tile 
{
	private int x, y, width, height;
	private boolean bAlive;
	
	public Tile(int x, int y, int width, int height)
	{
		bAlive = false;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean getAlive()
	{
		return this.bAlive;
	}
	
	public void setAlive(boolean bAlive)
	{
		this.bAlive = bAlive;
	}
	
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
	
	public boolean withinX(int x)
	{
		return x >= this.x && x <= this.x + this.width;
	}
	
	public boolean withinY(int y)
	{
		return y >= this.y && y <= this.y + this.height;
	}

	public void tick()
	{
		
	}
	
	public void render(Graphics g)
	{
		g.setColor(bAlive ? Color.WHITE : Color.GRAY);
		g.fillRect(x, y, width, height);
	}
}
