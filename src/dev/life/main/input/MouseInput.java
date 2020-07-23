package dev.life.main.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import dev.life.main.Game;
import dev.life.main.State;

public class MouseInput implements MouseListener
{
	private Game game;
	private int x, y;
	
	public MouseInput(Game game)
	{
		this.game = game;
	}
	
	public void mouseClicked(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();
		
		if (x >= 50 && x <= 100 && y >= 10 && y <= 30)
			game.setState(State.START);
		
		if (x >= 130 && x <= 180 && y >= 10 && y <= 30)
			game.setState(State.STOP);
		
		if (x >= 210 && x <= 260 && y >= 10 && y <= 30)
			game.setState(State.STEP);
	}

	public void mousePressed(MouseEvent e)
	{

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
}
