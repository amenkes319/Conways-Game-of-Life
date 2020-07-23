package dev.life.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game implements Runnable
{
	private final Game instance = this;
	
	private Display display;
	
	public final int WIDTH, HEIGHT;
	
	private Thread thread;
	
	private boolean bRunning;

	private BufferStrategy bs;
	private Graphics g;
	
	private MouseInput mouseInput;
	
	private Tile[][] grid;

	public Game(String title, int width, int height)
	{
		this.WIDTH = width;
		this.HEIGHT = height;
		
		this.display = new Display(title, WIDTH, HEIGHT);
		this.mouseInput = new MouseInput();
		this.grid = new Tile[20][20];
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				grid[i][j] = new Tile();
			}
		}
		display.getCanvas().addMouseListener(mouseInput);
	}
	
	public void updateGrid()
	{
		Tile[][] temp = new Tile[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				temp[i][j] = new Tile();
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
						neighbors += grid[i - 1][grid[0].length - 2].getAlive() ? 1 : 0;
						neighbors += grid[i - 1][grid[0].length - 1].getAlive() ? 1 : 0;	
					}
					else
					{
						neighbors += grid[i][j + 1].getAlive() ? 1 : 0;
						neighbors += grid[i][j - 1].getAlive() ? 1 : 0;
						neighbors += grid[i - 1][j - 1].getAlive() ? 1 : 0;
						neighbors += grid[i- 1][j].getAlive() ? 1 : 0;
						neighbors += grid[i - 1][j + 1].getAlive() ? 1 : 0;
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
				else
				{
					neighbors += grid[i - 1][j - 1].getAlive() ? 1 : 0;
					neighbors += grid[i - 1][j].getAlive() ? 1 : 0;
					neighbors += grid[i - 1][j + 1].getAlive() ? 1 : 0;
					neighbors += grid[i][j + 1].getAlive() ? 1 : 0;
					neighbors += grid[i - 1][j + 1].getAlive() ? 1 : 0;
					neighbors += grid[i - 1][j].getAlive() ? 1 : 0;
					neighbors += grid[i - 1][j - 1].getAlive() ? 1 : 0;
					neighbors += grid[i][j - 1].getAlive() ? 1 : 0;
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
	
	private void tick()
	{
		
	}
	
	private void render()
	{
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//render(g)
		
		bs.show();
		g.dispose();
	}

	public void run()
	{
		int fps = 144;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while (this.bRunning)
		{
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			
			lastTime = now;
			
			if (delta >= 1)
			{
				tick();
				render();
				ticks++;
				delta--;
			}
			
			if (timer >= 1000000000)
			{
//				System.out.println("Ticks: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
	}
	
	public synchronized void start()
	{
		if (this.bRunning)
			return;
		
		this.bRunning = true;
		thread = new Thread(instance);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if (!this.bRunning)
			return;
		
		this.bRunning = false;
		try
		{
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Game game = new Game("Life", 1280, 720);
		game.start();
	}
}
