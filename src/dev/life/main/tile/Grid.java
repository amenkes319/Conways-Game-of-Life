package dev.life.main.tile;

import java.awt.Graphics;

import dev.life.main.Game;
import dev.life.main.State;

public class Grid
{
	private Tile[][] grid;
	private Game game;
	
	public Grid(Game game, int length)
	{
		this.game = game;
		grid = new Tile[length][length];
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				grid[i][j] = new Tile(i * 25 + 50, j * 25 + 50, 20, 20);
			}
		}
	}
	
	public Tile[][] getTiles()
	{
		return this.grid;
	}
	
	public int getLength()
	{
		return this.grid.length;
	}
	
	public void tick()
	{
		if (game.getState() == State.START && game.getTicks() % (144 / 4) == 0)
			updateGrid();
		else if (game.getState() == State.STEP)
		{
			updateGrid();
			game.setState(State.STOP);
		}
		
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[0].length; j++)
			{
				grid[i][j].tick();
			}
		}
	}

	public void render(Graphics g)
	{
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[0].length; j++)
			{
				grid[i][j].render(g);
			}
		}
	}
	
	public void checkGridClicked()
	{
		
	}
	
	public void updateGrid()
	{
		Tile[][] temp = new Tile[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				temp[i][j] = new Tile(grid[i][j].getX(), grid[i][j].getY(), grid[i][j].getWidth(), grid[i][j].getHeight());
				int neighbors = 0;
				if (i == 0)
				{
					if (j == 0)
					{
						neighbors += grid[i + 1][j].getAlive() ? 1 : 0;
						neighbors += grid[i][j + 1].getAlive() ? 1 : 0;
						neighbors += grid[i + 1][j+ 1].getAlive() ? 1 : 0;
					}
					else if (j == grid[0].length - 1)
					{
						neighbors += grid[i][grid[0].length - 2].getAlive() ? 1 : 0;
						neighbors += grid[i + 1][grid[0].length - 2].getAlive() ? 1 : 0;
						neighbors += grid[i + 1][grid[0].length - 1].getAlive() ? 1 : 0;
					}
					else
					{
						neighbors += grid[i][j + 1].getAlive() ? 1 : 0;
						neighbors += grid[i][j - 1].getAlive() ? 1 : 0;
						neighbors += grid[i + 1][j - 1].getAlive() ? 1 : 0;
						neighbors += grid[i + 1][j].getAlive() ? 1 : 0;
						neighbors += grid[i + 1][j + 1].getAlive() ? 1 : 0;
					}
				}
				else if (i == grid.length - 1)
				{
					if (j == 0)
					{
						neighbors += grid[grid.length - 1][j + 1].getAlive() ? 1 : 0;
						neighbors += grid[grid.length - 2][j + 1].getAlive() ? 1 : 0;
						neighbors += grid[grid.length - 2][j].getAlive() ? 1 : 0;
					}
					else if (j == grid[0].length - 1)
					{
						neighbors += grid[grid.length - 1][grid[0].length - 2].getAlive() ? 1 : 0;
						neighbors += grid[grid.length - 2][grid[0].length - 2].getAlive() ? 1 : 0;
						neighbors += grid[grid.length - 2][grid[0].length - 1].getAlive() ? 1 : 0;
					}
					else
					{
						neighbors += grid[grid.length - 1][j - 1].getAlive() ? 1 : 0;
						neighbors += grid[grid.length - 1][j + 1].getAlive() ? 1 : 0;
						neighbors += grid[grid.length - 2][j].getAlive() ? 1 : 0;
						neighbors += grid[grid.length - 2][j - 1].getAlive() ? 1 : 0;
						neighbors += grid[grid.length - 2][j + 1].getAlive() ? 1 : 0;
					}
				}
				else if (j == 0)
				{
					neighbors += grid[i - 1][j].getAlive() ? 1 : 0;
					neighbors += grid[i + 1][j].getAlive() ? 1 : 0;
					neighbors += grid[i - 1][j + 1].getAlive() ? 1 : 0;
					neighbors += grid[i][j + 1].getAlive() ? 1 : 0;
					neighbors += grid[i + 1][j + 1].getAlive() ? 1 : 0;
				}
				else if (j == grid[0].length - 1)
				{
					neighbors += grid[i - 1][grid[0].length - 1].getAlive() ? 1 : 0;
					neighbors += grid[i + 1][grid[0].length - 1].getAlive() ? 1 : 0;
					neighbors += grid[i - 1][grid[0].length - 2].getAlive() ? 1 : 0;
					neighbors += grid[i][grid[0].length - 2].getAlive() ? 1 : 0;
					neighbors += grid[i + 1][grid[0].length - 2].getAlive() ? 1 : 0;
				}
				else
				{
					neighbors += grid[i - 1][j - 1].getAlive() ? 1 : 0;
					neighbors += grid[i - 1][j].getAlive() ? 1 : 0;
					neighbors += grid[i - 1][j + 1].getAlive() ? 1 : 0;
					neighbors += grid[i][j - 1].getAlive() ? 1 : 0;
					neighbors += grid[i][j + 1].getAlive() ? 1 : 0;
					neighbors += grid[i + 1][j - 1].getAlive() ? 1 : 0;
					neighbors += grid[i + 1][j].getAlive() ? 1 : 0;
					neighbors += grid[i + 1][j + 1].getAlive() ? 1 : 0;
				}
				
				if (grid[i][j].getAlive())
				{
					if(neighbors == 2 || neighbors == 3)
						temp[i][j].setAlive(true);
					else
						temp[i][j].setAlive(false);
				}
				else
				{
					if (neighbors == 3)
						temp[i][j].setAlive(true);
				}
			}
		}
		grid = temp;
	}
	
}
