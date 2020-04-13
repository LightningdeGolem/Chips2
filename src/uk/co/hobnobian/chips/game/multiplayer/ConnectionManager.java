package uk.co.hobnobian.chips.game.multiplayer;

import java.util.List;

import uk.co.hobnobian.chips.game.backend.GameVariables;
import uk.co.hobnobian.chips.game.backend.Map;
import uk.co.hobnobian.chips.game.backend.Player;
import uk.co.hobnobian.chips.game.backend.PlayerType;

public interface ConnectionManager {
	public void setVars(GameVariables v);
	public GameVariables getVars();
	
	public Player getOurPlayer();
	public void setPlayerType(PlayerType t);
	public void setTheirPlayer(Player p);
	
	
	public void setMain(boolean value);
	public void setResetWhenDie(boolean value);
	
	public boolean isClosing();
	public void exit();
	public List<int[]> getAndClearBlockChanges();
	public Map getMap();
}
