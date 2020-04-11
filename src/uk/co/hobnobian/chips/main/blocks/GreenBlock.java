package uk.co.hobnobian.chips.main.blocks;

import uk.co.hobnobian.chips.main.server.Block;
import uk.co.hobnobian.chips.main.server.Direction;
import uk.co.hobnobian.chips.main.server.EnterLeaveEvent;
import uk.co.hobnobian.chips.main.server.Game;
import uk.co.hobnobian.chips.main.server.GameVariables;


//USES GAMEVAR 0
public class GreenBlock extends Block {
	private static final long serialVersionUID = -5308525202743352116L;

	@Override
	public String getImage(GameVariables vars) {
		if (vars.get(0) == 1) {
			return "greenEmpty.png";
		}
		else {
			return "greenSolid.png";
		}
	}

	@Override
	public EnterLeaveEvent onEnter(int x, int y, Direction d, GameVariables vars, Game g) {
		if (vars.get(0) == 1) {
			return EnterLeaveEvent.YES;
		}
		return EnterLeaveEvent.NO;
	}

	@Override
	public EnterLeaveEvent onLeave(int x, int y, Direction d, GameVariables vars, Game g) {
		return EnterLeaveEvent.YES;
	}

}
