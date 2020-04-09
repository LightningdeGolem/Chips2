package uk.co.hobnobian.chips.main.server;

import java.util.Timer;
import java.util.TimerTask;

import uk.co.hobnobian.chips.main.PlayerMoveListener;
import uk.co.hobnobian.chips.main.ConnectionLayers.GraphicsServerLayer;
import uk.co.hobnobian.chips.main.multiplayer.ConnectionManager;
import uk.co.hobnobian.chips.main.multiplayer.Serializer;

public class Game implements PlayerMoveListener, ConnectionManager{
	Player p = new Player();
	Player p2 = null;
	
	private boolean tickWhenPaused = true;
	
	public boolean exiting = false;
	
	
	private boolean paused = false;
	
	private boolean main = true;
	public boolean resetMapWhenDie = true;
	
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
	
	public void tick() {
		if (!tickWhenPaused && paused) {
			return;
		}
		if (!p.isAlive()) {
			if (main) {
				p.go_to(map.getP1StartPos());
			}
			else {
				p.go_to(map.getP2StartPos());
			}
			
			if (resetMapWhenDie) {
				map = (Map) Serializer.fromString(originalMap);
			}
		}
		con.updateMap(map,vars, p, p2);
	}
	

	@Override
	public void onPlayerMove(Direction d) {
		if (paused) {
			return;
		}
		if (map.getAt(p.getpos()[0], p.getpos()[1]).onLeave(p, d, vars)) {
			int[] newpos = Direction.move(p.getpos(), d);
			if (map.getAt(newpos[0], newpos[1]).onEnter(p, Direction.invert(d), vars)) {
				if (p2 != null) {
					if (!(p2.getpos()[0] == newpos[0] && p2.getpos()[1] == newpos[1])) {
						p.move(d);
					}
				}
				
			}
		}
		
		con.updateMap(map,vars, p, p2);
	}
	
	public boolean canPlayerMove(Player p, Direction d) {
		if (map.getAt(p.getpos()[0], p.getpos()[1]).onLeave(p, d, vars)) {
			int[] newpos = Direction.move(p.getpos(), d);
			
			if (map.getAt(newpos[0], newpos[1]).onEnter(p, Direction.invert(d), vars)) {
				if (p2 != null) {
					if (!(p2.getpos()[0] == newpos[0] && p2.getpos()[1] == newpos[1])) {
						return true;
					}
					else {
						return false;
					}
				}
				
				
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
	
	@Override
	public boolean isClosing() {
		return exiting;
	}
	
	@Override
	public void exit() {
		tick.cancel();
		con.closeWindow();
		exiting = true;
	}
}
