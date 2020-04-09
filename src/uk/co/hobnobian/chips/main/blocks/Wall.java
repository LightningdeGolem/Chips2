package uk.co.hobnobian.chips.main.blocks;

import uk.co.hobnobian.chips.main.server.Block;
import uk.co.hobnobian.chips.main.server.Direction;
import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Player;

public class Wall extends Block{
	private static final long serialVersionUID = -6043144278495149519L;

	@Override
	public String getImage(GameVariables var) {
		return "solid.png";
	}

	@Override
	public boolean onEnter(Player e, Direction d, GameVariables vars) {
		//Deny any movement
		return false;
		
	}

	@Override
	public boolean onLeave(Player e, Direction d, GameVariables vars) {return true;}


}
