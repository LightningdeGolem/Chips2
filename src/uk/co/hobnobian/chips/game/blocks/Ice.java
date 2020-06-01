package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.BlockInfo;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.Game;
import uk.co.hobnobian.chips.game.backend.GameVariables;
import uk.co.hobnobian.chips.game.backend.Player;
import uk.co.hobnobian.chips.game.backend.Tickable;

public class Ice extends Block implements Tickable, SlidingBlock{
	private static final long serialVersionUID = -1963639319437118307L;
	
	public Ice() {
		setInfo(new BlockInfo(1));
		info.set(0, 0); // Stores the direction the player enters from
	}

	@Override
	public EnterLeaveEvent onEnter(int x, int y, Direction d, GameVariables vars, Game g) {
		info.set(0, Direction.toInt(Direction.invert(d)));
		return EnterLeaveEvent.YES;
	}

	@Override
	public EnterLeaveEvent onLeave(int x, int y, Direction d, GameVariables vars, Game g) {
		return EnterLeaveEvent.NO;
	}

	@Override
	public String getImage(GameVariables vars) {
		return "ice.png";
	}

	@Override
	public void tick(Game game, Player p1, Player p2, int x, int y) {
		if (p1.getpos()[0] == x && p1.getpos()[1] == y) {
			Direction slidingDirection = Direction.fromInt(info.get(0));
			int[] newpos = Direction.move(p1.getpos(), slidingDirection);
			
			Block movingTo = game.getMap().getAt(newpos[0], newpos[1]);
			
			if (game.canPlayerEnter(p1, slidingDirection, true)) {
				if (movingTo instanceof SlidingBlock) {
					((SlidingBlock) movingTo).setSlidingFromDirection(slidingDirection);
				}
				p1.move(slidingDirection);
			}
			else {
				info.set(0, Direction.toInt(Direction.invert(Direction.fromInt(info.get(0))))); // Invert the direction the player goes backwards
			}			
		}
	}

	@Override
	public void setSlidingFromDirection(Direction d) {
		info.set(0, Direction.toInt(d));
	}

}
