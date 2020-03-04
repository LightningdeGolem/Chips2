package uk.co.hobnobian.chips.main.multiplayer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import uk.co.hobnobian.chips.main.server.Entity;
import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Player;

public class ClientPacket implements Serializable{
	private static final long serialVersionUID = 7844613814136349063L;
	private HashMap<String, Integer> changes;
	private List<Entity> changesEnts;
	private Player player;
	
	public ClientPacket(GameVariables newVars, GameVariables oldVars, List<Entity> entChanges, Player p) {
		player = p;
		changes = new HashMap<String, Integer>();
		for (String key : newVars.getKeys()) {
			if (newVars.get(key) != oldVars.get(key)) {
				changes.put(key, newVars.get(key));
			}
		}
		changesEnts = entChanges;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public HashMap<String, Integer> getChanges(){
		return changes;
	}
	public List<Entity> getChangesEntity(){
		return changesEnts;
	}
}
