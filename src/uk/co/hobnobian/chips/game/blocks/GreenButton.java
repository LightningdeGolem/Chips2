package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.server.Block;
import uk.co.hobnobian.chips.game.server.Direction;
import uk.co.hobnobian.chips.game.server.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.server.Game;
import uk.co.hobnobian.chips.game.server.GameVariables;

//USES GAMEVAR 0
public class GreenButton extends Block {
	private static final long serialVersionUID = -4847454343260538041L;

	@Override
	public String getImage(GameVariables vars) {
		return "greenButton.png";
	}

	@Override
	public EnterLeaveEvent onEnter(int x, int y, Direction d, GameVariables vars, Game g) {
		if (vars.get(0) == 0) {
			vars.set(0, 1);
		}
		else {
			vars.set(0, 0);
		}
		return EnterLeaveEvent.YES;
	}

	@Override
	public EnterLeaveEvent onLeave(int x, int y, Direction d, GameVariables vars, Game g) {
		return EnterLeaveEvent.YES;
	}

}
