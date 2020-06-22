package uk.co.hobnobian.chips.game.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.hobnobian.chips.game.multiplayer.ConnectionManager;
import uk.co.hobnobian.chips.game.multiplayer.Serializer;
import uk.co.hobnobian.chips.gui.WonWindow;
import uk.co.hobnobian.chips.main.Position;

public class Game implements PlayerMoveListener, ConnectionManager{
	Player p = new Player();
	Player p2 = null;
	
	private boolean tickWhenPaused = true;
	
	private boolean winning = false;
	
	private volatile boolean exiting = false;
	
	
	private boolean paused = false;
	
	private boolean main = true;
	public boolean resetMapWhenDie = true;
	
	private ArrayList<int[]> blockChanges = new ArrayList<int[]>();
	private ArrayList<int[]> blockChangesSecondLayer = new ArrayList<int[]>();
	
	Map map;
	private String originalMap;
	GameVariables vars;
	Timer tick = new Timer();
	GraphicsServerLayer con;
	
	public void update() {
		con.updateMap(map, vars, p, p2);
	}
	
	public Game(GraphicsServerLayer layer, GameVariables vars) {
		this(layer, new Map(), vars);
		
	}
	public Game(GraphicsServerLayer layer, Map m, GameVariables vars) {
		setMap(m);
		con = layer;
		con.setGame(this);
		con.setPlayerListener(this);
		this.vars = vars;
		
	}
	
	@Override
	public void setPlayerType(PlayerType t) {
		p.setType(t);
	}
	
	public void setMap(Map m) {
		
		map = m;
		originalMap = Serializer.toString(m);
		if (main) {
			p.go_to(map.getP1StartPos());
		}
		else {
			p.go_to(map.getP2StartPos());
		}
		
		
	}
	
	public void start() {
		tick.schedule(new TimerTask() {

			@Override
			public void run() {
				tick();
			}
			
		}, 0, 100);
	}
	
	public void won() {
		new WonWindow();
		exit();
	}
	
	public void tick() {
		if (!tickWhenPaused && paused) {
			return;
		}
		if (p2 == null && winning) {
			won();
		}
		
		
		Block[][] blocksToUpdate = map.getSelection(25, 25, p.getpos()[0], p.getpos()[1]);
		for (int x =0; x < 25; x++) {
			for (int y = 0; y<25; y++) {
				Block b = blocksToUpdate[x][y];
				if (b instanceof Tickable) {
					((Tickable) b).tick(new GameTickData(this, p, p2, x+p.getpos()[0]-12, y+p.getpos()[1]-12));
				}
			}
		}
		
		Block[][] secondLayer = map.getSelectionSecondLayer(25, 25, p.getpos()[0], p.getpos()[1]);
        for (int x =0; x < 25; x++) {
            for (int y = 0; y<25; y++) {
                Block b = secondLayer[x][y];
                if (b instanceof Tickable) {
                    ((Tickable) b).tick(new GameTickData(this, p, p2, x+p.getpos()[0]-12, y+p.getpos()[1]-12));
                }
            }
        }
		if (!p.isAlive()) {
			if (main) {
				p.go_to(map.getP1StartPos());
			}
			else {
				p.go_to(map.getP2StartPos());
			}
			p.setAlive(true);
			
			if (resetMapWhenDie) {
				map = (Map) Serializer.fromString(originalMap);
				p.getInventory().clear();
			}
		}
		con.updateMap(map,vars, p, p2);
	}
	

	@Override
	public void onPlayerMove(Direction d) {
		if (paused) {
			return;
		}
//		if (map.getAt(p.getpos()[0], p.getpos()[1]).onLeave(p, d, vars)) {
//			int[] newpos = Direction.move(p.getpos(), d);
//			if (!(p2.getpos()[0] == newpos[0] && p2.getpos()[1] == newpos[1])) {
//				if (map.getAt(newpos[0], newpos[1]).onEnter(p, Direction.invert(d), vars)) {
//					if (p2 != null) {
//							p.move(d);
//					}
//					
//				}
//			}
//		}
		
		if (canPlayerMove(p, d)) {
			p.move(d);
		}
		
		con.updateMap(map,vars, p, p2);
	}
	
	public boolean canPlayerMove(Player p, Direction d) {
		int[] newpos = Direction.move(p.getpos(), d);
		int[] pos = p.getpos();
		EnterLeaveEvent leave = map.getAt(pos[0], pos[1]).onLeave(new PlayerMoveEventData(newpos[0], newpos[1], d, vars, this));
		
		EnterLeaveEvent leave2;
        if (map.getBlockSecondLayer(newpos[0], newpos[1]) == null) {
            leave2 = null;
        }
        else {
            leave2 = map.getBlockSecondLayer(newpos[0], newpos[1]).onLeave(new PlayerMoveEventData(newpos[0], newpos[1], Direction.invert(d), vars, this));
        }
		
		if (leave == EnterLeaveEvent.YES) {
		    if (leave2 == null || leave2 == EnterLeaveEvent.YES) {
		        if (canPlayerEnter(p,d,true)) {
	                return true;
	            }
		    }
		    else if (leave2 == EnterLeaveEvent.DEATH) {
	            p.kill();
	        }
			
		}
		else if (leave == EnterLeaveEvent.DEATH) {
			p.kill();
		}
		return false;
	}
	
	public boolean canPlayerEnter(Player p, Direction d, boolean countOtherPlayer) {
		int[] newpos = Direction.move(p.getpos(), d);

		if (!countOtherPlayer || p2 == null || !(p2.getpos()[0] == newpos[0] && p2.getpos()[1] == newpos[1])) {
			EnterLeaveEvent enter = map.getAt(newpos[0], newpos[1]).onEnter(new PlayerMoveEventData(newpos[0], newpos[1], Direction.invert(d), vars, this));
			EnterLeaveEvent enter2;
			if (map.getBlockSecondLayer(newpos[0], newpos[1]) == null) {
			    enter2 = null;
			}
			else {
			    enter2 = map.getBlockSecondLayer(newpos[0], newpos[1]).onEnter(new PlayerMoveEventData(newpos[0], newpos[1], Direction.invert(d), vars, this));
			}
			
			if (enter == EnterLeaveEvent.YES) {
			    if (enter2 == null || enter2 == EnterLeaveEvent.YES) {
			        return true;
			    }
			    else if (enter2 == EnterLeaveEvent.DEATH) {
			        p.kill();
			    }
			}
			else if (enter == EnterLeaveEvent.DEATH) {
				p.kill();
			}
		}	
		return false;
	}

	@Override
	public void setVars(GameVariables v) {
		vars = v;
		
	}

	@Override
	public GameVariables getVars() {
		return vars;
	}

	@Override
	public Player getOurPlayer() {
		return p;
	}

	@Override
	public void setTheirPlayer(Player p) {
		p2 = p;
		
	}

	@Override
	public void setMain(boolean value) {
		main = value;
		
	}

	@Override
	public void setResetWhenDie(boolean value) {
		resetMapWhenDie = value;
		
	}
	
	public void togglePause() {
		setPaused(!paused);
	}
	private void setPaused(boolean p) {
		paused = p;
		con.setPaused(paused);
		con.updateMap(map,vars, this.p, p2);
	}
	
	public void reset() {
		if (main) {
			vars = new GameVariables();
			setMap((Map) Serializer.fromString(originalMap));
		}
	}

	public boolean isTickWhenPaused() {
		return tickWhenPaused;
	}

	public void setTickWhenPaused(boolean tickWhenPaused) {
		this.tickWhenPaused = tickWhenPaused;
	}
	
	public void setBlock(int x, int y, Block b) {
		blockChanges.add(new int[] {x,y});
		map.setBlock(x, y, b);
	}
	
	public void setBlockSecondLayer(int x, int y, Block b) {
	    blockChangesSecondLayer.add(new int[] {x,y});
	    map.setBlockSecondLayer(x, y, b);
	}
	
	public Block getBlockSecondLayer(int x, int y) {
	    return map.getBlockSecondLayer(x, y);
	}
	
	public void swapBlockLayers(int x, int y) {
	    Block b = map.getBlockSecondLayer(x, y);
	    setBlockSecondLayer(x,y, map.getBlockDataAtXY(x, y));
	    setBlock(x,y,b);
	}
	
	@Override
	public Map getMap() {
		return map;
	}
	
	@Override
	public List<Position> getAndClearBlockChanges(){
		List<Position> re = new ArrayList<Position>();
		for (int[] change : blockChanges) {
			re.add(new Position(change));
		}
		for (int[] change : blockChangesSecondLayer) {
		    re.add(new Position(change, 1));
		}
		blockChanges.clear();
		blockChangesSecondLayer.clear();
		return re;
	}
	
	@Override
	public synchronized boolean isClosing() {
		return exiting;
	}
	
	@Override
	public synchronized void exit() {
		tick.cancel();
		con.closeWindow();
		exiting = true;
		System.out.println("Exiting is true");
	}

	public void setWinning(boolean b) {
		winning = b;
	}
	
	public boolean isWinning() {
		return winning;
	}
}
