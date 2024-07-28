package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.Keyboardinputs;
import inputs.Mouseinputs;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel  extends JPanel{
	
	private Mouseinputs mouseInputs;
	private Game game;
	public GamePanel(Game game) {
		mouseInputs = new Mouseinputs(this);
		this.game = game;
		setPanelSize();
		addKeyListener(new Keyboardinputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
		
	}

	private void setPanelSize() {
		Dimension size = new Dimension(1280,800);
		setPreferredSize(size);		
	}
	
	public void updateGame() {

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		game.render(g);
	}
	
	public Game getGame() {
		return game;
	}
}
