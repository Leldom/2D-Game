package gamesates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelHandler;
import main.Game;

public class Playing extends State implements Statemethods {
    private Player player;
	private LevelHandler levelHandler;

    public Playing(Game game) {
        super(game);
        initClasses();
        
    }

    private void initClasses() {
		levelHandler = new LevelHandler(game);
		player = new Player(200, 200, (int)(96*Game.SCALE), (int) (96*Game.SCALE));	
		player.loadLvlData(levelHandler.getCurrentLevel().getLevelData());
	}

    public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}

    @Override
    public void update() {
        levelHandler.update();
        player.update();
    }

    @Override
    public void draw(Graphics g) {
        levelHandler.draw(g);
        player.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
			player.setAttacking(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {				
	
			case KeyEvent.VK_A:
				player.setLeft(true);
				break;	
			case KeyEvent.VK_D:
				player.setRight(true);
				break;
			case KeyEvent.VK_SHIFT:
				player.setShift(true);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(true);
				break;
		}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {				
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;	
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_SHIFT:
				player.setShift(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(false);
				break;
		}
    }

}
