package uk.co.hobnobian.chips.game.server;

import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = 8878765228332183344L;
	
	private int x = 0;
	private int y = 0;
	
	private boolean alive = true;
	
	private PlayerType ptype = PlayerType.BROWN;
	
	private Direction facing = Direction.SOUTH;
	
	
	public Direction getDirection() {
		return facing;
	}
	
	public void setFacing(Direction f) {
		facing = f;
	}
	
	public void move(Direction d) {
		facing = d;
		int[] r = Direction.move(new int[] {x, y}, d);
		x = r[0];
		y = r[1];
	}
	
	public void go_to(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int[] getpos() {
		return new int[] {x,y};
	}

	public void tick(Game g, Player[] p) {}

	public String getImage(GameVariables vars) {
		String imageFile = "";
		if (ptype == PlayerType.BROWN) {
			imageFile = "player";
		}
		else if (ptype == PlayerType.BLUE) {
			imageFile = "player1";
		}
		else if (ptype == PlayerType.RED) {
			imageFile = "player2";
		}
		
		if (getDirection() == Direction.NORTH) {
			imageFile += "_n";
		}
		else if (getDirection() == Direction.SOUTH) {
			imageFile += "_s";
		}
		else if (getDirection() == Direction.WEST) {
			imageFile += "_w";
		}
		else if (getDirection() == Direction.EAST) {
			imageFile += "_e";
		}
		return imageFile+".png";
	}
	
	public PlayerType getType() {
		return ptype;
	}
	
	public void setType(PlayerType t) {
		ptype = t;
	}

	public void go_to(int[] pos) {
		go_to(pos[0], pos[1]);
		
	}
	
	public void kill() {
		alive  = false;
	}
	
	public void setAlive(boolean a) {
		alive = a;
	}
	
	public boolean isAlive() {
		return alive;
	}
}
