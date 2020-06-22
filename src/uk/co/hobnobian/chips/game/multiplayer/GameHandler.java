package uk.co.hobnobian.chips.game.multiplayer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.BlockInfo;
import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.game.backend.Game;
import uk.co.hobnobian.chips.game.backend.GameVariables;
import uk.co.hobnobian.chips.game.backend.Player;
import uk.co.hobnobian.chips.game.blocks.Air;
import uk.co.hobnobian.chips.main.Main;
import uk.co.hobnobian.chips.main.Position;

public class GameHandler{
	private Game con;
	private Socket sock;
	
	private boolean theyAreWinning = false;
	
	private HashMap<Position, Block> blockdatas = new HashMap<Position, Block>();
	GameVariables gameVarsCache;
	
	BufferedOutputStream out;
	BufferedInputStream in;
	
	private boolean readfirst;
	
	public GameHandler(Connection c, Game con, boolean readfirst) {
		this.readfirst = readfirst;
		this.con = con;
		sock = c.getSock();
		
		out = c.getOut();
		in = c.getIn();
	}
	
	public void start() {
		System.out.println("Initialising!");
		updateBlockCache();
		updateVarsCache();
		
		try {
			
			if (readfirst) {
				read();
			}
			else {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			
			while (true) {
				if (con.isClosing()) {
					System.out.println("Exit = true");
				}
				write();
				if (con.isClosing()) {
					break;
				}
				updateVarsCache();
				updateBlockCache();
				
				while (in.available() < 1) {}
				
				read();
				if (theyAreWinning && con.isWinning()) {
					con.won();
					break;
				}
			}
			out.close();
			in.close();
			sock.close();
			
			
		} catch (InvalidProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OtherClientQuitException e) {
			con.exit();
			try {
				out.close();
				in.close();
				sock.close();
			} catch (IOException e1) {}
		}
		
	}
	
	private void updateBlockCache() {
		blockdatas = getBlocks();
	}
	
	private void updateVarsCache() {
		gameVarsCache = con.getVars().clone();
	}
	
	private HashMap<Position, Block> getBlocks(){
		final int sizeToUpdate = 15;
		int[] centre = con.getOurPlayer().getpos();
		int offset = sizeToUpdate/2;
		
		HashMap<Position, Block> d = new HashMap<Position, Block>();
		
		for (int y = centre[1]-offset; y < centre[1]+offset; y++) {
			for (int x = centre[0]-offset; x < centre[0]+offset; x++) {
				Block data = con.getMap().getAt(x, y);
				Block second = con.getMap().getBlockSecondLayer(x, y);
				
				d.put(new Position(x,y),data.clone());
				if (second == null) {
				    d.put(new Position(x, y,1), new Air());
				}
				else {
				    d.put(new Position(x,y,1),second.clone());
				}
				
			}
		}
		return d;
	}
	
	private void read() throws InvalidProtocolException,IOException, OtherClientQuitException {
		
		int length = in.read()-128;
		
		
		byte[] raw = new byte[length];
		
		in.read(raw, 0, length);
		
		int[] data = new int[length];
		for (int i = 0; i < length; i++) {
			data[i] = fromByte(raw[i]);
		}
		
		//PROTOCOL CHECK
		if (data[0] != Main.protocolID) {
			throw new InvalidProtocolException("Protocol "+data[0]+" is not compatible with "+Main.protocolID);
		}
		
		Player p = new Player();
		int[] playerPos = {data[1], data[2]};//Player pos
		p.go_to(playerPos);
		p.setFacing(intToDirection(data[3]));//Player rotation
		
		con.setTheirPlayer(p);
		
		int offset = 5;
		for (int i = 0; i < data[4]; i++) {
			int x = data[offset];//Read x
			offset++;
			int y = data[offset];//Read y
			offset++;
			int layer = data[offset];//Read layer
            offset++;
			int id = data[offset];
			offset++;
			
			int[] blockdata = new int[data[offset]];
			offset++;
			
			for (int n = 0; n < blockdata.length; n++) {
				blockdata[n] = data[offset];
				offset++;
			}
			
			Class<?extends Block> blocktype = Block.blockIds.get(id);
			try {
				Block b = blocktype.getConstructor().newInstance();
				b.setInfo(new BlockInfo(blockdata));
				if (layer == 0) {
				    con.getMap().setBlock(x, y, b);
				}
				else {
				    if (blocktype.equals(Air.class)) {
				        con.getMap().setBlockSecondLayer(x, y, null);
				    }
				    else {
				        con.getMap().setBlockSecondLayer(x, y, b);
				    }
				    
				}
				
				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				continue;
			}
			
		}
		
		int gamevars = data[offset];
		offset++;
		
		for (int i = 0; i<gamevars; i++) {
			int id = data[offset];
			int d = data[offset+1];
			offset+=2;
			
			con.getVars().set(id, d);
		}
		
		boolean[] flags = toBits(data[offset]);
		if (flags[0]) {
			throw new OtherClientQuitException();
		}
		theyAreWinning = flags[1];
		
		
		
	}
	
	private void write() throws IOException {
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		
		bytes.add(toByte(Main.protocolID));//Write protocol id
		
		bytes.add(toByte(con.getOurPlayer().getpos()[0]));//Write player x
		bytes.add(toByte(con.getOurPlayer().getpos()[1]));//Write player y
		bytes.add(toByte(directionToInt(con.getOurPlayer().getDirection())));//Write player rotaion
		
		
		//====================
		//Get blocks that need to be sent
		List<Position> data = con.getAndClearBlockChanges();
		
		
		HashMap<Position, Block> currentData = getBlocks();
		
		for (Position pos : currentData.keySet()) {
			if (!data.contains(pos)) {
				
				boolean containsKey = false;
				Block oldblock = null;
				for (Position p : blockdatas.keySet()) {
					if (p.equals(pos)) {
						containsKey = true;
						oldblock = blockdatas.get(p);
						break;
					}
				}
				if (containsKey) {
					if (!oldblock.getClass().equals(currentData.get(pos).getClass())) {
						data.add(pos);
					}
					else if (oldblock.getInfo() == null && currentData.get(pos).getInfo() == null) {
						
					}
					else if (oldblock.getInfo() == null || currentData.get(pos).getInfo() == null) {
						data.add(pos);
					}
					else if (!oldblock.getInfo().equals(currentData.get(pos).getInfo())){
						data.add(pos);
					}
				}
			}
		}
		//=====================
		bytes.add(toByte(data.size()));//Write number of block updates to be sent
		
		//For each block to be sent
		for (Position pos : data) {
			Block toSend = con.getMap().getAt(pos.getX(), pos.getY());
			bytes.add(toByte(pos.getX()));//Write block x
			bytes.add(toByte(pos.getY()));//Write block y
			bytes.add(toByte(pos.getLayer()));//Write block layer
			
			int id = Block.inverseBlockIds.get(toSend.getClass());
			bytes.add(toByte(id));//Write block id
			
			if (toSend.getInfo() == null) {
				bytes.add(toByte(0));//Write that there is no block data
				continue;
			}
			
			int[] info = toSend.getInfo().getArray();
			
			bytes.add(toByte(info.length));//Write size of block info
			for (int i : info) {
				bytes.add(toByte(i));//Write info bytes
			}
		}
		
		
		//GAMEVARS
		ArrayList<Integer> changed = new ArrayList<Integer>();
		for (int key : con.getVars().getKeys()) {
			if (con.getVars().get(key) != gameVarsCache.get(key)) {
				changed.add(key);
			}
		}
		
		bytes.add(toByte(changed.size()));//Write number of game variables changes coming
		
		for (int key : changed) {
			bytes.add(toByte(key));//Write key of variable
			bytes.add(toByte(con.getVars().get(key)));//Write value of variable
		}
		
		boolean[] flags = new boolean[8];
		flags[0] = con.isClosing();//Flag value 0 is whether to exit
		flags[1] = con.isWinning();//Flag value 1 is whether are player is on a winning block
		
		if (flags[0]) {
			System.out.println("Sent exit");
		}
		bytes.add(toByte(fromBits(flags)));//Write flags
		
		
		byte[] toSend = new byte[bytes.size()];
		for (int i = 0; i<bytes.size(); i++) {
			toSend[i] = bytes.get(i);
		}

		out.write(toByte(toSend.length));//Write size of data
		out.write(toSend);//Write data
		out.flush();
	}
	
	private int directionToInt(Direction d) {
		switch(d) {
			case NORTH:
				return 0;
			case SOUTH:
				return 1;
			case EAST:
				return 2;
			case WEST:
				return 3;
		}
		return 0;
	}
	
	private Direction intToDirection(int i) {
		switch(i) {
			case 0:
				return Direction.NORTH;
			case 1:
				return Direction.SOUTH;
			case 2:
				return Direction.EAST;
			case 3:
				return Direction.WEST;
		}
		return Direction.NORTH;
	}
	
	private int fromBits(boolean[] bits) {
		int r = 0;
		for (int i = 0; i<bits.length; i++) {
			if (bits[i]) {
				r+=Math.pow(2, (bits.length-1)-i);
			}
		}
		return r;
	}
	
	private boolean[] toBits(int v) {
		boolean[] r = new boolean[8];
		for (int i = 0; i< 8; i++) {
			int powerOf2 = 7-i;
			r[i] = (((int)Math.pow(2, powerOf2) & v) == Math.pow(2, powerOf2));
		}
		return r;
	}
	
	
	public static int fromByte(byte b) {
		return ((int)b)+128;
	}
	public static byte toByte(int i) {
		while (i > 255) {
			i-=256;
		}
		while (i < 0) {
			i+=256;
		}
		return (byte) (i-128);
	}
}
