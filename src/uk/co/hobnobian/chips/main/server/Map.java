package uk.co.hobnobian.chips.main.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.co.hobnobian.chips.main.blocks.Air;
import uk.co.hobnobian.chips.main.blocks.Wall;

public class Map implements Serializable{
	private static final long serialVersionUID = -2109585197244870226L;
	protected Block[][] blocks = new Block[256][256];
	protected ArrayList<Entity> entities = new ArrayList<Entity>();
	protected final Class<?extends Block> defaultOutOfBounds = Wall.class;
	
	private int[] p1StartPos = {0,0};
	private int[] p2StartPos = {4,4};
	
	protected final void onstart() {
		
	}
	
	public Map() {
		onstart();
	}
	
	public final Block[][] getSelection(int width, int height, int xcenter, int ycenter) {
		Block[][] data = new Block[width][height];
		for (int y = ycenter; y < ycenter+height; y++) {
			for (int x = xcenter; x < xcenter+width; x++) {
				data[x-xcenter][y-ycenter] = getBlockDataAtXY((int)(x-Math.floor(width/2)),(int)(y-Math.floor(height/2)));
			}
		}
		return data;
	}
	public final List<Entity> getEntities(int width, int height, int xcenter, int ycenter) {
		ArrayList<Entity> es = new ArrayList<Entity>();
		for (Entity e : entities) {
			if (e.getpos()[0] >= xcenter - Math.floor(width/2)
					&& e.getpos()[0] <= xcenter + Math.floor(width/2)
					&& e.getpos()[1] >= ycenter - Math.floor(height/2)
					&& e.getpos()[1] <= ycenter + Math.floor(height/2))
			{
				es.add(e);
			}
		}
		return es;
	}
	
	protected final Block getBlockDataAtXY(int x, int y) {
		if (x > -1 && x < blocks.length && y > -1 && y < blocks[0].length) {
			if (blocks[x][y] == null) {
				return new Air();
			}
			return blocks[x][y];
		}
		try {
			return defaultOutOfBounds.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return new Wall();
	}
	
	public final Block getAt(int x, int y) {
		return getBlockDataAtXY(x,y);
	}
	
	public List<Entity> getEntities(){
		return entities;
	}

	public int[] getP1StartPos() {
		return p1StartPos;
	}

	public int[] getP2StartPos() {
		return p2StartPos;
	}
}
