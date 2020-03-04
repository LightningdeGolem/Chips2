package uk.co.hobnobian.chips.main.server;

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
}
