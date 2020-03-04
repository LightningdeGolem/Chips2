package uk.co.hobnobian.chips.main.blocks;

import uk.co.hobnobian.chips.main.server.Block;
import uk.co.hobnobian.chips.main.server.Direction;
import uk.co.hobnobian.chips.main.server.Entity;
import uk.co.hobnobian.chips.main.server.GameVariables;

public class GreenBlock extends Block {
	private static final long serialVersionUID = -5308525202743352116L;

	@Override
	public boolean onEnter(Entity e, Direction d, GameVariables vars) {
		return vars.get("greenBlock") == 1;
	}

	@Override
	public boolean onLeave(Entity e, Direction d, GameVariables vars) {
		return true;
	}

	@Override
	public String getImage(GameVariables vars) {
		if (vars.get("greenBlock") == 1) {
			return "greenEmpty.png";
		}
		else {
			return "greenSolid.png";
		}
	}

}
