package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;

public class WinningBlock extends Block {
	private static final long serialVersionUID = -481408805492551240L;

	@Override
	public EnterLeaveEvent onEnter(PlayerMoveEventData d) {
		d.getGame().setWinning(true);
		return EnterLeaveEvent.YES;
	}

	@Override
	public EnterLeaveEvent onLeave(PlayerMoveEventData d) {
		d.getGame().setWinning(false);
		return EnterLeaveEvent.YES;
	}

	@Override
	public String getImage(GetImageData d) {
		return "finish.png";
	}

}
