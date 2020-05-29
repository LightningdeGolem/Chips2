package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.Game;
import uk.co.hobnobian.chips.game.backend.GameVariables;

public class WinningBlock extends Block {
	private static final long serialVersionUID = -481408805492551240L;

	@Override
	public EnterLeaveEvent onEnter(int x, int y, Direction d, GameVariables vars, Game g) {
		g.setWinning(true);
		return EnterLeaveEvent.YES;
	}

	@Override
	public EnterLeaveEvent onLeave(int x, int y, Direction d, GameVariables vars, Game g) {
		g.setWinning(false);
		return EnterLeaveEvent.YES;
	}

	@Override
	public String getImage(GameVariables vars) {
		return "solid.png";
	}

}
