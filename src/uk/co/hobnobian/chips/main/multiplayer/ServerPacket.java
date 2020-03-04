package uk.co.hobnobian.chips.main.multiplayer;

import java.io.Serializable;
import java.util.List;

import uk.co.hobnobian.chips.main.server.Entity;
import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Player;

public class ServerPacket implements Serializable {
	private static final long serialVersionUID = 2238736948820219060L;
	
	private GameVariables vars;
	private List<Entity> entities;
	private Player player;
	
	public ServerPacket(GameVariables v, List<Entity> e, Player p) {
		vars = v;
		entities = e;
		player = p;
	}

	public GameVariables getVars() {
		return vars;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public Player getPlayer() {
		return player;
	}
}
