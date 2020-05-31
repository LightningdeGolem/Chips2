package uk.co.hobnobian.chips.game.blocks;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.EnterLeaveEvent;
import uk.co.hobnobian.chips.game.backend.Game;
import uk.co.hobnobian.chips.game.backend.GameVariables;
import uk.co.hobnobian.chips.game.backend.Player;
import uk.co.hobnobian.chips.game.backend.Tickable;

public class ConveyorN extends Block implements Tickable{
	private static final long serialVersionUID = -1253491499916355869L;

	@Override
	public EnterLeaveEvent onEnter(int x, int y, Direction d, GameVariables vars, Game g) {
		return EnterLeaveEvent.YES;
	}

	@Override
	public EnterLeaveEvent onLeave(int x, int y, Direction d, GameVariables vars, Game g) {
		return EnterLeaveEvent.YES;
	}

	@Override
	public String getImage(GameVariables vars) {
		return "conveyor_n.png";
	}

	@Override
	public void tick(Game game, Player p1, Player p2, int x, int y) {
		if (p1.getpos()[0] == x && p1.getpos()[1] == y) {
			p1.move(Direction.NORTH);
		}
		
	}

}
