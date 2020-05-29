package uk.co.hobnobian.chips.editor;

import uk.co.hobnobian.chips.game.backend.Block;

public interface MapChangeBlockListener {
	public void change(int x, int y, Block old, Block newblock);
}
