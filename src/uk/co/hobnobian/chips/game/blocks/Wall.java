package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.server.Block;
import uk.co.hobnobian.chips.game.server.Direction;
import uk.co.hobnobian.chips.game.server.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.server.Game;
import uk.co.hobnobian.chips.game.server.GameVariables;

public class Wall extends Block{
	private static final long serialVersionUID = -6043144278495149519L;

	@Override
	public String getImage(GameVariables var) {
		return "solid.png";
	}

	@Override
	public EnterLeaveEvent onEnter(int x, int y, Direction d, GameVariables vars, Game g) {
		return EnterLeaveEvent.NO;
	}

	@Override
	public EnterLeaveEvent onLeave(int x, int y, Direction d, GameVariables vars, Game g) {
		return EnterLeaveEvent.NO;
	}


}
