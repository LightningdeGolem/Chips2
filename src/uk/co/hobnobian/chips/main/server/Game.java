package uk.co.hobnobian.chips.main.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.hobnobian.chips.main.PlayerMoveListener;
import uk.co.hobnobian.chips.main.ConnectionLayers.GraphicsServerLayer;
import uk.co.hobnobian.chips.main.multiplayer.ConnectionManager;
import uk.co.hobnobian.chips.main.multiplayer.Serializer;

public class Game implements PlayerMoveListener, ConnectionManager{
	Player p = new Player();
	Player p2 = null;
	
	private List<Entity> changesEnts = new ArrayList<Entity>();
	private boolean shouldRecordChangeEnts = true;
	
	private boolean main = true;
	public boolean resetMapWhenDie = true;
	
	Map map;
	private String originalMap;
	GameVariables vars;;
	Timer tick = new Timer();
	GraphicsServerLayer con;
	
	public void addChangeEnts(Entity e) {
		if (shouldRecordChangeEnts) {
			changesEnts.add(e);
		}
	}
	
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
		if (!main) {
			shouldRecordChangeEnts = false;
		}
		updateEntities();
		shouldRecordChangeEnts = true;
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
	
	public void updateEntities() {
		List<Entity> entities = map.getEntities();
		ArrayList<Entity> toBeDeleted = new ArrayList<Entity>();
		for (Entity e : entities) {
			if (e.isAlive()) {
				if (p2 == null) {
					e.tick(this, new Player[] {p});
				}
				else {
					e.tick(this, new Player[] {p, p2});
				}
			}
			else {
				toBeDeleted.add(e);
			}
		}
		for (Entity e : toBeDeleted) {
			map.getEntities().remove(e);
		}
	}
	

	@Override
	public void onPlayerMove(Direction d) {
		if (map.getAt(p.getpos()[0], p.getpos()[1]).onLeave(p, d, vars)) {
			int[] newpos = Direction.move(p.getpos(), d);
			if (map.getAt(newpos[0], newpos[1]).onEnter(p, Direction.invert(d), vars)) {
				
				boolean canmove = true;
				for (Entity e : map.getEntities()) {
					if (e.isAlive()) {
						if (e.getpos()[0] == newpos[0] && e.getpos()[1] == newpos[1]) {
							canmove = e.onPlayerMove(this, p, Direction.invert(d), vars);
							break;
						}
					}
				}
				if (p2 != null) {
					if (canmove) {
						if (!(p2.getpos()[0] == newpos[0] && p2.getpos()[1] == newpos[1])) {
							p.move(d);
						}
					}
				}
				else if (canmove){
					p.move(d);
				}
				
			}
		}
		
		con.updateMap(map,vars, p, p2);
	}
	
	public boolean canEntityMove(Entity p, Direction d) {
		if (map.getAt(p.getpos()[0], p.getpos()[1]).onLeave(p, d, vars)) {
			int[] newpos = Direction.move(p.getpos(), d);
			if (map.getAt(newpos[0], newpos[1]).onEnter(p, Direction.invert(d), vars)) {
				
				boolean canmove = true;
				for (Entity e : map.getEntities()) {
					if (e.isAlive()) {
						if (e.getpos()[0] == newpos[0] && e.getpos()[1] == newpos[1]) {
							canmove = e.onPlayerMove(this, p, Direction.invert(d), vars);
							break;
						}
					}
				}
				if (p2 != null) {
					if (canmove) {
						if (!(p2.getpos()[0] == newpos[0] && p2.getpos()[1] == newpos[1])) {
							return true;
						}
						else {
							return false;
						}
					}
				}
				else if (canmove){
					return canmove;
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
	public List<Entity> getEntities() {
		return map.getEntities();
	}

	@Override
	public void setEntities(List<Entity> list) {
		map.entities = (ArrayList<Entity>) list;
	}

	@Override
	public void setMain(boolean value) {
		main = value;
		
	}

	@Override
	public List<Entity> getChanges() {
		return changesEnts;
	}

	@Override
	public void resetChanges() {
		changesEnts = new ArrayList<Entity>();
		
	}

	@Override
	public void setResetWhenDie(boolean value) {
		resetMapWhenDie = value;
		
	}
	
	public void reset() {
		if (main) {
			vars = new GameVariables();
			setMap((Map) Serializer.fromString(originalMap));
		}
	}
}
