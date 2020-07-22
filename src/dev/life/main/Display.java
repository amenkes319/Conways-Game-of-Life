package dev.life.main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display
{
	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	private int width, height;
	
	public Display(String title, int width, int height)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		
		createDisplay();
	}
	
	private void createDisplay()
	{
		this.frame = new JFrame(this.title);
		this.frame.setSize(this.width, this.height);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
		
		this.canvas = new Canvas();
		this.canvas.setPreferredSize(new Dimension(this.width, this.height));
		this.canvas.setMinimumSize(new Dimension(this.width, this.height));
		this.canvas.setMaximumSize(new Dimension(this.width, this.height));
		this.canvas.setFocusable(false);
		
		this.frame.add(this.canvas);
		this.frame.pack();
	}
	
	public Canvas getCanvas()
	{
		return this.canvas;
	}
	
	public JFrame getFrame()
	{
		return this.frame;
	}
}