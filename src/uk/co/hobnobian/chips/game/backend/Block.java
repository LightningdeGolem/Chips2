package uk.co.hobnobian.chips.game.backend;


import java.io.Serializable;
import java.util.ArrayList;
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
import uk.co.hobnobian.chips.game.blocks.RedKey;
import uk.co.hobnobian.chips.game.blocks.RedKeyhole;
import uk.co.hobnobian.chips.game.blocks.Rocket;
import uk.co.hobnobian.chips.game.blocks.SolidWire;
import uk.co.hobnobian.chips.game.blocks.Wall;
import uk.co.hobnobian.chips.game.blocks.WinningBlock;
import uk.co.hobnobian.chips.game.blocks.Zapper;

public abstract class Block implements Serializable,Cloneable{
	private static final long serialVersionUID = 4076482000484509547L;
	public static HashMap<Integer, Class<?extends Block>> blockIds = new HashMap<Integer, Class<?extends Block>>();
	public static HashMap<Class<?extends Block>, Integer> inverseBlockIds = new HashMap<Class<?extends Block>, Integer>();
	public static ArrayList<Class<?extends Block>> blocks = new ArrayList<Class<?extends Block>>();
	
	
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
		blocks.add(Air.class);
		blocks.add(Wall.class);
		blocks.add(GreenBlock.class);
		blocks.add(GreenButton.class);
		blocks.add(MoveableBlock.class);
		blocks.add(WinningBlock.class);
		blocks.add(ConveyorN.class);
		blocks.add(ConveyorE.class);
		blocks.add(ConveyorS.class);
		blocks.add(ConveyorW.class);
		blocks.add(Ice.class);
		blocks.add(FakeAir.class);
		blocks.add(FakeWall.class);
		blocks.add(NormalWire.class);
		blocks.add(Lamp.class);
		blocks.add(Lever.class);
		blocks.add(ElectricDoor.class);
		blocks.add(SolidWire.class);
		blocks.add(Zapper.class);
		blocks.add(IceBootsItem.class);
		blocks.add(FireBootsItem.class);
		blocks.add(Fire.class);
		blocks.add(NoItem.class);
		blocks.add(Rocket.class);
		blocks.add(Button.class);
		blocks.add(RedKeyhole.class);
		blocks.add(RedKey.class);
		
		for (int i = 0; i < blocks.size(); i++) {
			blockIds.put(i, blocks.get(i));
		}
		
		for (int key : blockIds.keySet()) {
			inverseBlockIds.put(blockIds.get(key), key);
		}
	}
}
