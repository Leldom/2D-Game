package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.PlayerConstants.getSpriteAmount;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Player extends Entity{	
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;
	private boolean moving = false;
	private boolean running = false;
	private boolean left, up, right, down, shift;
	private float playerSpeed = 2.0f;
	private float runningSpeed = 3.0f;
	
	
	public Player(float x, float y) {
		super(x, y);
		loadAnimations();
	}
	
	public void update() {	
		updatePos();
		updateAnimationTick();
		setAnimation();
			
	}
	
	public void render(Graphics g) {		
		g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 256, 256, null);		
	}
	
	private void updateAnimationTick() {
		
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= getSpriteAmount(playerAction)) {
				aniIndex = 0;
			}
		}
		
	}
	private void setAnimation() {
		
		if(moving)
			playerAction = WALKING;
		else if(running)
			playerAction = RUNNING;
		else
			playerAction = IDLE;
		
	}	
	
	private void updatePos() {
		
		moving = false;
		running  = false;
		//Walking
		if(left && !right) {
			x -= playerSpeed;
			moving = true;
		}else if(right && !left) {
			x += playerSpeed;
			moving = true;
		}
		
		if(up && !down) {
			y -= playerSpeed;
			moving = true;
		}else if(down && !up) {
			y += playerSpeed;
			moving = true;
		}
		//Running
		if(shift && left && !right) {
			x -= runningSpeed;
			moving = false;
			running = true;
		}else if(shift && right && !left) {
			x += runningSpeed;
			moving = false;
			running = true;
		}		
		
	}
	
	private void loadAnimations() {
		InputStream is = getClass().getResourceAsStream("/Samurai_Sprite.png");		
		try {
			BufferedImage img = ImageIO.read(is);
			
			animations = new BufferedImage[10][12];		
			for(int j = 0; j < animations.length; j++)
			for(int i= 0; i < animations[j].length; i++) 
				animations[j][i] = img.getSubimage(i*128, j*128, 128, 128);
			
		} catch (IOException e) {
			e.printStackTrace();
		}	finally {
			try {
				is.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	
	public boolean isShift() {
		return shift;
	}
	public void setShift(boolean shift) {
		this.shift = shift;
	}
	
	
	
}
