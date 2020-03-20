package uk.co.hobnobian.chips.main.client;

import uk.co.hobnobian.chips.main.server.Direction;

public interface ClientConnectionLayer {
	public void move(Direction d);
	public void reset();
	public void setUpdater(Renderer d);
	
	public void togglePause();
	public void exit();
	
}
