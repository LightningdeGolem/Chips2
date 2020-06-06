package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;

public class Air extends Block {
	private static final long serialVersionUID = 6187292863080124496L;

	@Override
	public String getImage(GetImageData data) {
		return "air.png";
	}

	@Override
	public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
		return EnterLeaveEvent.YES;
	}

	@Override
	public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
		return EnterLeaveEvent.YES;
	}
}
