package uk.co.hobnobian.chips.main.server;

import java.io.Serializable;

public abstract class Block implements Serializable{
	private static final long serialVersionUID = 4076482000484509547L;

	public abstract boolean onEnter(Entity e, Direction d, GameVariables vars);
	public abstract boolean onLeave(Entity e, Direction d, GameVariables vars);
	
	public abstract String getImage(GameVariables vars);
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
