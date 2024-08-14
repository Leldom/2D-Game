package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.PlayerConstants.getSpriteAmount;
import static utilz.HelpMethods.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity{	
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;
	private boolean moving = false, running = false, attacking = false;
	
	private boolean left, up, right, down, shift, jump;
	private float playerSpeed = 1.0f * Game.SCALE;
	private float runningSpeed = 0.5f * Game.SCALE;
	private int[][] lvlData;
	private float xDrawOffset = 37 * Game.SCALE;
	private float yDrawOffset = 38 * Game.SCALE;
	
	//Jumping/Gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;
	
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y,(int) (23 * Game.SCALE),(int) (57 * Game.SCALE));		
	}
	
	public void update() {	
		updatePos();
		updateAnimationTick();
		setAnimation();
			
	}
	
	public void render(Graphics g) {		
		g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), width, height, null);	
//		drawHitbox(g);
	}
	
	private void updateAnimationTick() {
		
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= getSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
		}
		
	}
	private void setAnimation() {
		int startAni = playerAction;
		
		if(moving)
			playerAction = WALKING;
		else if(running)
			playerAction = RUNNING;
		else
			playerAction = IDLE;
		
		if(inAir) 		
				playerAction = JUMP;
				
		if(attacking)
			playerAction = ATTACK_1;
		
		if(startAni != playerAction)
			resetAniTick();
	}	
	
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
		
	}

	private void updatePos() {
		
		moving = false;
		running  = false;
		
		if(jump)
			jump();
		if(!left && !right && !inAir)
			return;
		
		float xSpeed = 0;
		
		//Walking
		if(left) 
			xSpeed -= playerSpeed;			
		if(right) 
			xSpeed += playerSpeed;	
					
		//Running
		if(shift && left)
			xSpeed -= runningSpeed;			
		else if(shift && right) 
			xSpeed += runningSpeed;
		
		if(!inAir)
			if(!IsEntityOnFloor(hitbox, lvlData)) 
				inAir = true;
			
		
		
		if(inAir) {
			if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			}else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if(airSpeed > 0) 
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}	
		}else 
			updateXPos(xSpeed);
		
		moving = true;
			if(shift) {
				moving = false;
				running = true;
			}
		
	}
	
	private void jump() {
		if(inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;
		
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
		
	}

	private void updateXPos(float xSpeed) {
		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
		hitbox.x +=xSpeed;
		}else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}
	}

	private void loadAnimations() {
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			
			animations = new BufferedImage[10][12];		
			for(int j = 0; j < animations.length; j++)
			for(int i= 0; i < animations[j].length; i++) 
				animations[j][i] = img.getSubimage(i*128, j*128, 128, 128);
					
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}
	
	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;		
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
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
	public void setJump(boolean jump) {
		this.jump = jump;
	}
}
