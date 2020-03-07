package uk.co.hobnobian.chips.main.entities;

import uk.co.hobnobian.chips.main.server.Direction;
import uk.co.hobnobian.chips.main.server.Entity;
import uk.co.hobnobian.chips.main.server.Game;
import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Player;

public class VanishingWall extends Entity {
	private static final long serialVersionUID = 8867297559775914464L;

	@Override
	public void tick(Game g, Player[] ps) {
		return;

	}

	@Override
	public String getImage(GameVariables vars) {
		return "solid.png";
	}

	@Override
	public boolean onPlayerEnter(Game g, Entity p, Direction d, GameVariables vars) {
		kill();
		return true;
	}

}
