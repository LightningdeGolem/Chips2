package uk.co.hobnobian.chips.game.backend;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import uk.co.hobnobian.chips.editor.MapChangeBlockListener;
import uk.co.hobnobian.chips.game.blocks.Air;
import uk.co.hobnobian.chips.game.blocks.Wall;

public class Map implements Serializable{
    public String title = "Unknown Name";
    public String author = "Unknown Author";
    
	private transient MapChangeBlockListener mapchangeblocklistener = null;
	private static final long serialVersionUID = -2109585197244870226L;
	protected Block[][] blocks = new Block[256][256];
	protected Block[][] upperLayer = new Block[256][256];
	protected final Class<?extends Block> defaultOutOfBounds = Wall.class;
	
	private int[] p1StartPos = {0,0};
	private int[] p2StartPos = {0,0};
	
	public boolean inBounds(int x, int y) {
		if (x < 0 || y < 0 || x >= blocks.length || y >= blocks[0].length) {
			return false;
		}
		return true;
	}
	
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
	
    public Block[][] getSelectionSecondLayer(int width, int height, int xcenter, int ycenter) {
        Block[][] data = new Block[width][height];
        for (int y = ycenter; y < ycenter+height; y++) {
            for (int x = xcenter; x < xcenter+width; x++) {
                data[x-xcenter][y-ycenter] = getBlockSecondLayer((int)(x-Math.floor(width/2)),(int)(y-Math.floor(height/2)));
            }
        }
        return data;
    }
	
	protected final Block getBlockDataAtXY(int x, int y) {
		if (x > -1 && x < blocks.length && y > -1 && y < blocks[0].length) {
			if (blocks[x][y] == null) {
				return new Air();
			}
			return blocks[x][y];
		}
		try {
			try {
				return defaultOutOfBounds.getDeclaredConstructor().newInstance();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return new Wall();
	}
	
	public final Block getAt(int x, int y) {
		return getBlockDataAtXY(x,y);
	}

	public int[] getP1StartPos() {
		return p1StartPos;
	}

	public int[] getP2StartPos() {
		return p2StartPos;
	}
	
	public void setBlock(int x, int y, Block b) {
		Block oldblock = blocks[x][y];
		if (blocks[x][y] != null) {
		    if (blocks[x][y].getInfo() != null) {
		        oldblock.setInfo(new BlockInfo(blocks[x][y].getInfo()));
		    }
			
		}
		if (mapchangeblocklistener != null) {
			mapchangeblocklistener.change(x, y, oldblock, b);
		}
		setBlockNoRecord(x,y,b);
	}
	
	public void setBlockSecondLayer(int x, int y, Block b) {
	    upperLayer[x][y] = b;
	}
	
	public Block getBlockSecondLayer(int x, int y) {
	    if (x > -1 && x < blocks.length && y > -1 && y < blocks[0].length) {
            if (upperLayer[x][y] == null) {
                return null;
            }
            return upperLayer[x][y];
        }
	    return null;
	}
	
	public void setBlockNoRecord(int x, int y, Block b) {
		
		
		blocks[x][y] = b;
		
	}

	public void setMapChangeBlockListener(MapChangeBlockListener mapchangeblocklistener) {
		this.mapchangeblocklistener = mapchangeblocklistener;
	}

    public void setP1StartingPosition(int[] pos) {
        p1StartPos = pos;
    }
    public void setP2StartingPosition(int[] pos) {
        p2StartPos = pos;
    }


}
