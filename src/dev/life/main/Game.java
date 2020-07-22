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
		
		display.getCanvas().addMouseListener(mouseInput);
	}
	
	public void updateGrid()
	{
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				
					
			}
		}
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
