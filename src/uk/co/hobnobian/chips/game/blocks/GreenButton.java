package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;

//USES GAMEVAR 0
public class GreenButton extends Block {
	private static final long serialVersionUID = -4847454343260538041L;

	@Override
	public String getImage(GetImageData d) {
		return "greenButton.png";
	}

	@Override
	public EnterLeaveEvent onEnter(PlayerMoveEventData d) {
		if (d.getVars().get(0) == 0) {
			d.getVars().set(0, 1);
		}
		else {
			d.getVars().set(0, 0);
		}
		return EnterLeaveEvent.YES;
	}

	@Override
	public EnterLeaveEvent onLeave(PlayerMoveEventData d) {
		return EnterLeaveEvent.YES;
	}

}
