package uk.co.hobnobian.chips.game.display;

import uk.co.hobnobian.chips.game.backend.Direction;

public interface ClientConnectionLayer {
	public void move(Direction d);
	public void reset();
	public void setUpdater(Renderer d);
	
	public void togglePause();
	public void exit();
	
}
