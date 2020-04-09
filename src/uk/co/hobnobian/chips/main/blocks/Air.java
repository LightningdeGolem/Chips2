package uk.co.hobnobian.chips.main.blocks;

import uk.co.hobnobian.chips.main.server.Block;
import uk.co.hobnobian.chips.main.server.Direction;
import uk.co.hobnobian.chips.main.server.Entity;
import uk.co.hobnobian.chips.main.server.GameVariables;

public class Air extends Block {
	private static final long serialVersionUID = 6187292863080124496L;

	@Override
	public boolean onEnter(Entity e, Direction d, GameVariables vars) {
		return true;
	}

	@Override
	public String getImage(GameVariables var) {
		return "air.png";
	}

	@Override
	public boolean onLeave(Entity e, Direction d, GameVariables vars) {
		return true;
	}
}
