package dev.life.main.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import dev.life.main.Game;
import dev.life.main.State;
import dev.life.main.tile.Tile;

public class MouseInput implements MouseListener, MouseMotionListener
{
	private Game game;
	private int x, y, button;
	
	public MouseInput(Game game)
	{
		this.game = game;
	}
	
	private void checkStateClicked()
	{
		if (x >= 50 && x <= 100 && y >= 10 && y <= 30)
			game.setState(State.START);
		
		if (x >= 130 && x <= 180 && y >= 10 && y <= 30)
			game.setState(State.STOP);
		
		if (x >= 210 && x <= 260 && y >= 10 && y <= 30)
			game.setState(State.STEP);
	}
	
	public void checkGridPressed()
	{
		Tile[][] grid = game.getGrid().getTiles();
		if (game.getState() != State.START && x >= grid[0][0].getX() && x <= grid[grid.length - 1][grid[0].length - 1].getX() + grid[grid.length - 1][grid[0].length - 1].getWidth() && 
											  y >= grid[0][0].getY() && y <= grid[grid.length - 1][grid[0].length - 1].getY() + grid[grid.length - 1][grid[0].length - 1].getHeight())
		{
			for (int i = 0; i < grid.length; i++)
			{
				for (int j = 0; j < grid[0].length; j++)
				{
					if (x >= grid[i][j].getX() && x <= grid[i][j].getX() + grid[i][j].getWidth() && 
						y >= grid[i][j].getY() && y <= grid[i][j].getY() + grid[i][j].getHeight())
					{
						if (button == 1)
							grid[i][j].setAlive(true);
						else if (button == 3)
							grid[i][j].setAlive(false);
						return;
					}
				}
			}
		}
	}
	
	public void mouseClicked(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();
		
		checkStateClicked();
	}

	public void mousePressed(MouseEvent e)
	{
		button = e.getButton();
	}

	public void mouseReleased(MouseEvent e)
	{
		
	}

	public void mouseEntered(MouseEvent e)
	{

	}

	public void mouseExited(MouseEvent e)
	{
		
	}

	public void mouseDragged(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();
		checkGridPressed();
	}

	public void mouseMoved(MouseEvent e)
	{
		
	}
}
