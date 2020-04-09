package uk.co.hobnobian.chips.main.multiplayer;

import java.io.Serializable;
import java.util.HashMap;

import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Player;

public class ClientPacket implements Serializable{
	private static final long serialVersionUID = 7844613814136349063L;
	private HashMap<String, Integer> changes;
	private Player player;
	
	public ClientPacket(GameVariables newVars, GameVariables oldVars, Player p) {
		player = p;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public HashMap<String, Integer> getChanges(){
		return changes;
	}
}
