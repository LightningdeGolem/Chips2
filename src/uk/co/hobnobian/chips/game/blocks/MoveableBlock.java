package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.PlayerMoveEventData;

public class MoveableBlock extends Block{
	private static final long serialVersionUID = -697806509042984041L;
	
	@Override
	public String getImage(GetImageData d) {
		return "movable.png";
	}

	@Override
	public EnterLeaveEvent onEnter(PlayerMoveEventData data) {
	    int x = data.getX();
	    int y = data.getY();
	    
		int[] newpos = Direction.move(new int[] {x,y},Direction.invert(data.getDirection()));
		data.getGame().setBlock(x, y, new Air());
		data.getGame().setBlock(newpos[0], newpos[1], this);
		
		return EnterLeaveEvent.YES;
	}

	@Override
	public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
		return EnterLeaveEvent.YES;
	}

}
