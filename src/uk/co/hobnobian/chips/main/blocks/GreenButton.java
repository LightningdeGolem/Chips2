package uk.co.hobnobian.chips.main.blocks;

import uk.co.hobnobian.chips.main.server.Block;
import uk.co.hobnobian.chips.main.server.Direction;
import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Player;

//USES GAMEVAR 0
public class GreenButton extends Block {
	private static final long serialVersionUID = -4847454343260538041L;

	@Override
	public boolean onEnter(Player p, Direction d, GameVariables vars) {
		if (vars.get(0) == 1) {
			vars.set(0, 0);
		}
		else {
			vars.set(0, 1);
		}
		return true;
	}

	@Override
	public boolean onLeave(Player p, Direction d, GameVariables vars) {
		return true;
	}

	@Override
	public String getImage(GameVariables vars) {
		return "greenButton.png";
	}

}
