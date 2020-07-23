package dev.life.main.tile;

import java.awt.Color;
import java.awt.Graphics;

public class Tile 
{
	private int x, y, width, height;
	protected boolean bAlive;
	
	public Tile(int x, int y, int width, int height)
	{
		bAlive = Math.random() > .5;
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

	public void tick()
	{
		
	}
	
	public void render(Graphics g)
	{
		g.setColor(bAlive ? Color.WHITE : Color.GRAY);
		g.fillRect(x, y, width, height);
	}
}
