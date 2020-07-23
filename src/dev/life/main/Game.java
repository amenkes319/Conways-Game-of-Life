package dev.life.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.life.main.display.Display;
import dev.life.main.input.MouseInput;
import dev.life.main.tile.Grid;
import dev.life.main.tile.Tile;

public class Game implements Runnable
{
	private final Game instance = this;
	
	private Display display;
	
	public int width, height;
	
	private Thread thread;
	
	private int ticks;
	
	private boolean bRunning;
	private State state;

	private BufferStrategy bs;
	private Graphics g;
	
	private MouseInput mouseInput;
	
	private Grid grid;

	public Game(String title, int width, int height)
	{
		state = State.STOP;
		this.width = width;
		this.height = height;
		
		this.ticks = 0;
		
		this.display = new Display(title, width, height);
		this.mouseInput = new MouseInput(instance);
		
		this.grid = new Grid(this, 30);
		
		display.getCanvas().addMouseListener(mouseInput);
	}
	
	public int getTicks()
	{
		return this.ticks;
	}
	
	public State getState()
	{
		return this.state;
	}
	
	public void setState(State state)
	{
		this.state = state;
	}
	
	private void tick()
	{
		this.width = display.getFrame().getWidth();
		this.height = display.getFrame().getHeight();
		
		grid.tick();
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
		
		g.clearRect(0, 0, width, height);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, height);
		
		grid.render(g);
		
		g.setColor(Color.GREEN);
		g.fillRect(50, 10, 50, 20);
		g.setColor(Color.RED);
		g.fillRect(130, 10, 50, 20);
		g.setColor(Color.BLUE);
		g.fillRect(210, 10, 50, 20);
		
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
		System.setProperty("sun.awt.noerasebackground", "true");
		Game game = new Game("Life", 1280, 1080);
		game.start();
	}
}
