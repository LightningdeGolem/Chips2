package uk.co.hobnobian.chips.main;

import java.io.Serializable;

public class Position implements Serializable{
	private static final long serialVersionUID = 3902451489599268111L;
	
	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position(int[] pos) {
		this(pos[0], pos[1]);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Position: ("+x+","+y+")";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Position) {
			return ((Position) o).x== x && ((Position) o).y == y;
		}
		else {
			return super.equals(o);
		}
	}
}
