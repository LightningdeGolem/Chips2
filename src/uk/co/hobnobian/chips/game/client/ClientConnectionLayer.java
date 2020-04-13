package uk.co.hobnobian.chips.game.client;

import uk.co.hobnobian.chips.game.server.Direction;

public interface ClientConnectionLayer {
	public void move(Direction d);
	public void reset();
	public void setUpdater(Renderer d);
	
	public void togglePause();
	public void exit();
	
}
