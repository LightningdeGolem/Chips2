package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GameTickData;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;
import uk.co.hobnobian.chips.game.backend.Tickable;

public class ConveyorN extends Block implements Tickable{
	private static final long serialVersionUID = -1253491499916355869L;

	@Override
	public EnterLeaveEvent onEnter(PlayerMoveEventData d) {
		return EnterLeaveEvent.YES;
	}

	@Override
	public EnterLeaveEvent onLeave(PlayerMoveEventData d) {
		return EnterLeaveEvent.YES;
	}

	@Override
	public String getImage(GetImageData d) {
		return "conveyor_n.png";
	}

	@Override
	public void tick(GameTickData d) {
		if (d.isPlayer1OnBlock()) {
			d.getP1().move(Direction.NORTH);
		}
		
	}

}
