package uk.co.hobnobian.chips.main;

import java.io.Serializable;

public class Position implements Serializable{
	private static final long serialVersionUID = 3902451489599268111L;
	
	private int x;
	private int y;
	
	private int z;
	
	public Position(int x, int y) {
		this(x,y,0);
	}
	
	public Position(int x, int y, int z) {
	    this.x = x;
        this.y = y;
        this.z = z;
	}

	public Position(int[] pos) {
	    this(pos[0], pos[1], 0);
	}

	public Position(int[] change, int z) {
        this(change[0], change[1], z);
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
	
	public int getLayer() {
        return z;
    }
	
	@Override
	public String toString() {
		return "Position: ("+x+","+y+") Layer: "+z;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Position) {
			return ((Position) o).x== x && ((Position) o).y == y && ((Position) o).z == z;
		}
		else {
			return super.equals(o);
		}
	}
}
