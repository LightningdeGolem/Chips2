package uk.co.hobnobian.chips.editor;

import uk.co.hobnobian.chips.game.backend.Block;

public class BlockChangeEvent {
	private int x;
	private int y;
	private Block from;
	private Block to;
	
	public BlockChangeEvent(int x, int y, Block from, Block to) {
		this.x = x;
		this.y = y;
		this.from = from;
		this.to = to;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Block getFrom() {
		return from;
	}
	
	public Block getTo() {
		return to;
	}
}
