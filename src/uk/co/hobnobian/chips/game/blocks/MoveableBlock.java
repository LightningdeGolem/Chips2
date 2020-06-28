package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.Game;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.backend.Map;
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
	    Map map = data.getGame().getMap();
	    Game game = data.getGame();
	    
	    
	    
		if (data.move(this, Direction.invert(data.getDirection())) != EnterLeaveEvent.NO) {
			int[] newpos = Direction.move(new int[] {data.getX(),data.getY()}, Direction.invert(data.getDirection()));
			
			if (map.getAt(x, y).getClass().equals(getClass())) {
		    	game.setBlock(x, y, new Air());
		    }
			
			map.getAt(newpos[0], newpos[1]).onEnter(new PlayerMoveEventData(newpos[0], newpos[1],data.getDirection(), game.getVars(), game));
			game.setBlockSecondLayer(x, y, null);
			game.setBlockSecondLayer(newpos[0], newpos[1], this);
			return EnterLeaveEvent.YES;
		}
		return EnterLeaveEvent.NO;
		
		
	}

	@Override
	public EnterLeaveEvent onLeave(PlayerMoveEventData data) {
		return EnterLeaveEvent.YES;
	}

}
