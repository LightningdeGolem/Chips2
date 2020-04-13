package uk.co.hobnobian.chips.game;

import uk.co.hobnobian.chips.game.server.Direction;

public interface PlayerMoveListener {
	public void onPlayerMove(Direction d);
}
