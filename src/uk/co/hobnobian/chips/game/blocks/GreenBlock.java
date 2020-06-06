package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;


//USES GAMEVAR 0
public class GreenBlock extends Block {
	private static final long serialVersionUID = -5308525202743352116L;

	@Override
	public String getImage(GetImageData d) {
		if (d.getVars().get(0) == 1) {
			return "greenEmpty.png";
		}
		else {
			return "greenSolid.png";
		}
	}

	@Override
	public EnterLeaveEvent onEnter(PlayerMoveEventData d) {
		if (d.getVars().get(0) == 1) {
			return EnterLeaveEvent.YES;
		}
		return EnterLeaveEvent.NO;
	}

	@Override
	public EnterLeaveEvent onLeave(PlayerMoveEventData d) {
		return EnterLeaveEvent.YES;
	}

}
