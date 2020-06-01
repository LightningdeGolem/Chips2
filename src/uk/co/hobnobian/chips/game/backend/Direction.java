package uk.co.hobnobian.chips.game.backend;

public enum Direction {
	NORTH,
	SOUTH,
	EAST,
	WEST;
	
	public static Direction invert(Direction d) {
		if (d == NORTH) {
			return SOUTH;
		}
		else if (d == SOUTH) {
			return NORTH;
		}
		else if (d == WEST) {
			return EAST;
		}
		else {
			return WEST;
		}
	}
	
	public static int[] move(int[] pos, Direction d) {
		int x = pos[0];
		int y = pos[1];
		if (d == NORTH) {
			y-=1;
		}
		else if (d == SOUTH) {
			y+=1;
		}
		else if (d == WEST) {
			x-=1;
		}
		else {
			x+=1;
		}
		return new int[] {x,y};
	}
	
	public static int toInt(Direction d) {
		switch(d) {
		case NORTH:
			return 0;
		case SOUTH:
			return 1;
		case EAST:
			return 2;
		case WEST:
			return 3;
		}
		return 0;
	}
	
	public static Direction fromInt(int i) {
		switch(i) {
		case 0:
			return NORTH;
		case 1:
			return SOUTH;
		case 2:
			return EAST;
		case 3:
			return WEST;
		}
		return NORTH;
	}
}
