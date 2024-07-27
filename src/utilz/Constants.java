package utilz;

public class Constants {
	
	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class PlayerConstants{
		public static final int ATTACK_1 = 0;
		public static final int ATTACK_2 = 1;
		public static final int ATTACK_3 = 2;
		public static final int DEAD = 3;
		public static final int HIT = 4;
		public static final int IDLE = 5;
		public static final int JUMP = 6;
		public static final int RUNNING = 7;
		public static final int SHIELD = 8;
		public static final int WALKING = 9;
		
		public static int getSpriteAmount(int player_action) {
			
			switch(player_action) {
			
			case ATTACK_2:
				return 4;
			case ATTACK_3:
			case DEAD:
				return 3;
			case ATTACK_1:
			case IDLE:
				return 6;
			case JUMP:
				return 12;
			case HIT:
			case SHIELD:
				return 2;
			case RUNNING:
			case WALKING:
				return 8;
			default:
				return 1;
			
			}
			
		}
	}

}
