package uk.co.hobnobian.chips.main.entities;

import uk.co.hobnobian.chips.main.server.Direction;
import uk.co.hobnobian.chips.main.server.Entity;
import uk.co.hobnobian.chips.main.server.Game;
import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Player;

public class MovableBlock extends Entity {
	private static final long serialVersionUID = -1285985386766971864L;

	@Override
	public void tick(Game g, Player[] ps) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getImage(GameVariables vars) {
		return "movable.png";
	}

	@Override
	public boolean onPlayerEnter(Game g, Entity e, Direction d, GameVariables vars) {
		if (e instanceof Player) {
			if (g.canEntityMove(this, Direction.invert(d))) {
				move(Direction.invert(d));
				return true;
			}
		}
		return false;
	}

}
