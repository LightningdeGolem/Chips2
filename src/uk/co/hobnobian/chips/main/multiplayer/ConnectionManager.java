package uk.co.hobnobian.chips.main.multiplayer;

import java.util.List;

import uk.co.hobnobian.chips.main.server.Entity;
import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Player;
import uk.co.hobnobian.chips.main.server.PlayerType;

public interface ConnectionManager {
	public void setVars(GameVariables v);
	public GameVariables getVars();
	
	public Player getOurPlayer();
	public void setPlayerType(PlayerType t);
	public void setTheirPlayer(Player p);
	
	
	public List<Entity> getEntities();
	public void setEntities(List<Entity> list);
	
	public void setMain(boolean value);
	public void setResetWhenDie(boolean value);
	
	public List<Entity> getChanges();
	public void resetChanges();
	public boolean isClosing();
	public void exit();
}
