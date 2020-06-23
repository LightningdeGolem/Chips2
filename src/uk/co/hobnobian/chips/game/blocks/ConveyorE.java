package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GameTickData;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.Player;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;
import uk.co.hobnobian.chips.game.backend.Tickable;

public class ConveyorE extends Block implements Tickable{
	private static final long serialVersionUID = -1253491499916355869L;

	@Override
	public EnterLeaveEvent onEnter(PlayerMoveEventData d) {
	    if (d.getDirection() == Direction.EAST) {
	        return EnterLeaveEvent.NO;
	    }
		return EnterLeaveEvent.YES;
	}

	@Override
	public EnterLeaveEvent onLeave(PlayerMoveEventData d) {
		return EnterLeaveEvent.YES;
	}

	@Override
	public String getImage(GetImageData g) {
		return "conveyor_e.png";
	}

	@Override
	public void tick(GameTickData data) {
	    Player p1 = data.getP1();
		if (data.isPlayer1OnBlock()) {
			p1.move(Direction.EAST);
		}
		
	}

}
