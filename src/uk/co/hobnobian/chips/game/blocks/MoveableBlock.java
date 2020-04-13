package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.Game;
import uk.co.hobnobian.chips.game.backend.GameVariables;

public class MoveableBlock extends Block{
	private static final long serialVersionUID = -697806509042984041L;
	
	@Override
	public String getImage(GameVariables vars) {
		return "movable.png";
	}

	@Override
	public EnterLeaveEvent onEnter(int x, int y, Direction d, GameVariables vars, Game g) {
		int[] newpos = Direction.move(new int[] {x,y},Direction.invert(d));
		g.setBlock(x, y, new Air());
		g.setBlock(newpos[0], newpos[1], this);
		
		return EnterLeaveEvent.YES;
	}

	@Override
	public EnterLeaveEvent onLeave(int x, int y, Direction d, GameVariables vars, Game g) {
		return EnterLeaveEvent.YES;
	}

}
