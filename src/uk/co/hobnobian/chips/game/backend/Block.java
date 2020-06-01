package uk.co.hobnobian.chips.game.backend;

import java.io.Serializable;
import java.util.HashMap;

import uk.co.hobnobian.chips.game.blocks.Air;
import uk.co.hobnobian.chips.game.blocks.ConveyorE;
import uk.co.hobnobian.chips.game.blocks.ConveyorN;
import uk.co.hobnobian.chips.game.blocks.ConveyorS;
import uk.co.hobnobian.chips.game.blocks.ConveyorW;
import uk.co.hobnobian.chips.game.blocks.GreenBlock;
import uk.co.hobnobian.chips.game.blocks.GreenButton;
import uk.co.hobnobian.chips.game.blocks.Ice;
import uk.co.hobnobian.chips.game.blocks.MoveableBlock;
import uk.co.hobnobian.chips.game.blocks.Wall;
import uk.co.hobnobian.chips.game.blocks.WinningBlock;

public abstract class Block implements Serializable{
	private static final long serialVersionUID = 4076482000484509547L;
	public static HashMap<Integer, Class<?extends Block>> blockIds = new HashMap<Integer, Class<?extends Block>>();
	public static HashMap<Class<?extends Block>, Integer> inverseBlockIds = new HashMap<Class<?extends Block>, Integer>();

	protected BlockInfo info = null;
	
	public abstract EnterLeaveEvent onEnter(int x, int y, Direction d, GameVariables vars, Game g);
	public abstract EnterLeaveEvent onLeave(int x, int y, Direction d, GameVariables vars, Game g);
	
	public Block() {}
	
	public abstract String getImage(GameVariables vars);
	
	public void setInfo(BlockInfo i) {
		info = i;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
	public BlockInfo getInfo() {
		return info;
	}
	
	public static final void setup(){
		HashMap<Integer, Class<?extends Block>> b = blockIds;
		
		b.put(0, Air.class);
		b.put(1, Wall.class);
		b.put(2, GreenBlock.class);
		b.put(3, GreenButton.class);
		b.put(4, MoveableBlock.class);
		b.put(5, WinningBlock.class);
		b.put(6, ConveyorN.class);
		b.put(7, ConveyorE.class);
		b.put(8, ConveyorS.class);
		b.put(9, ConveyorW.class);
		b.put(10, Ice.class);
		
		
		for (int key : b.keySet()) {
			inverseBlockIds.put(b.get(key), key);
		}
	}
}
