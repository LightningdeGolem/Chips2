package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.BlockInfo;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.Game;
import uk.co.hobnobian.chips.game.backend.GameVariables;

public class FakeAir extends Block{
	private static final long serialVersionUID = 2971905158401304438L;

	public FakeAir() {
		setInfo(new BlockInfo(1));
		info.set(0, 0);//Stores whether to show block as wall
	}
	
	@Override
	public EnterLeaveEvent onEnter(int x, int y, Direction d, GameVariables vars, Game g) {
		info.set(0, 1);
		return EnterLeaveEvent.NO;
	}

	@Override
	public EnterLeaveEvent onLeave(int x, int y, Direction d, GameVariables vars, Game g) {
		return EnterLeaveEvent.YES;
	}

	@Override
	public String getImage(GameVariables vars) {
		if (info.get(0) == 0) {
			return "air.png";
		}
		return "solid.png";
	}

}
