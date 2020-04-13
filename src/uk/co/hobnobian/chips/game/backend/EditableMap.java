package uk.co.hobnobian.chips.game.backend;

public class EditableMap extends Map {
	private static final long serialVersionUID = -5475389902704051308L;

	public void setAt(Block b, int x, int y) {
		setBlockAtXY(b,x,y);
	}
	
	protected void setBlockAtXY(Block b, int x, int y) {
		if (x > -1 && x < blocks.length && y > -1 && y < blocks[0].length) {
			blocks[x][y] = b;
		}
	}
}
