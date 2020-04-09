package uk.co.hobnobian.chips.main.multiplayer;

import java.io.Serializable;

import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Player;

public class ServerPacket implements Serializable {
	private static final long serialVersionUID = 2238736948820219060L;
	
	private GameVariables vars;
	private Player player;
	
	public ServerPacket(GameVariables v, Player p) {
		vars = v;
		player = p;
	}

	public GameVariables getVars() {
		return vars;
	}

	public Player getPlayer() {
		return player;
	}
}
