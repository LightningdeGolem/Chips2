package uk.co.hobnobian.chips.game.backend;


import java.io.Serializable;
import java.util.HashMap;

import uk.co.hobnobian.chips.game.blocks.Air;
import uk.co.hobnobian.chips.game.blocks.Button;
import uk.co.hobnobian.chips.game.blocks.ConveyorE;
import uk.co.hobnobian.chips.game.blocks.ConveyorN;
import uk.co.hobnobian.chips.game.blocks.ConveyorS;
import uk.co.hobnobian.chips.game.blocks.ConveyorW;
import uk.co.hobnobian.chips.game.blocks.ElectricDoor;
import uk.co.hobnobian.chips.game.blocks.FakeAir;
import uk.co.hobnobian.chips.game.blocks.FakeWall;
import uk.co.hobnobian.chips.game.blocks.Fire;
import uk.co.hobnobian.chips.game.blocks.FireBootsItem;
import uk.co.hobnobian.chips.game.blocks.GreenBlock;
import uk.co.hobnobian.chips.game.blocks.GreenButton;
import uk.co.hobnobian.chips.game.blocks.Ice;
import uk.co.hobnobian.chips.game.blocks.IceBootsItem;
import uk.co.hobnobian.chips.game.blocks.Lamp;
import uk.co.hobnobian.chips.game.blocks.Lever;
import uk.co.hobnobian.chips.game.blocks.MoveableBlock;
import uk.co.hobnobian.chips.game.blocks.NoItem;
import uk.co.hobnobian.chips.game.blocks.NormalWire;
import uk.co.hobnobian.chips.game.blocks.Rocket;
import uk.co.hobnobian.chips.game.blocks.SolidWire;
import uk.co.hobnobian.chips.game.blocks.Wall;
import uk.co.hobnobian.chips.game.blocks.WinningBlock;
import uk.co.hobnobian.chips.game.blocks.Zapper;

public abstract class Block implements Serializable,Cloneable{
	private static final long serialVersionUID = 4076482000484509547L;
	public static HashMap<Integer, Class<?extends Block>> blockIds = new HashMap<Integer, Class<?extends Block>>();
	public static HashMap<Class<?extends Block>, Integer> inverseBlockIds = new HashMap<Class<?extends Block>, Integer>();

	protected BlockInfo info = null;
	
	public abstract EnterLeaveEvent onEnter(PlayerMoveEventData data);
	public abstract EnterLeaveEvent onLeave(PlayerMoveEventData data);
	
	public Block() {}
	
	public Block clone() {
		try {
			Block newblock = (Block) super.clone();
			if (this.info == null) {
				newblock.setInfo(null);
			}
			else {
				newblock.setInfo(new BlockInfo(info));
			}
			return newblock;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public abstract String getImage(GetImageData data);
	
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
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Block) {
			if (((Block) o).getClass().equals(getClass())) {
				if ((info == null && ((Block) o).getInfo() == null) || ((Block) o).getInfo().equals(info)) {
					return true;
				}
				return false;
			}
			else {
				return false;
			}
		}
		return super.equals(o);
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
		b.put(11, FakeAir.class);
		b.put(12, FakeWall.class);
		b.put(13, NormalWire.class);
		b.put(14, Lamp.class);
		b.put(15, Lever.class);
		b.put(16, ElectricDoor.class);
		b.put(17, SolidWire.class);
		b.put(18, Zapper.class);
		b.put(19, IceBootsItem.class);
		b.put(20, FireBootsItem.class);
		b.put(21, Fire.class);
		b.put(22, NoItem.class);
		b.put(23, Rocket.class);
		b.put(24, Button.class);
		
		
		for (int key : b.keySet()) {
			inverseBlockIds.put(b.get(key), key);
		}
	}
}
