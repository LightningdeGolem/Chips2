package uk.co.hobnobian.chips.main.server;

import java.io.Serializable;

public abstract class Entity implements Serializable{
	private static final long serialVersionUID = -6215096498441491536L;
	
	protected int x = 0;
	protected int y = 0;
	
	private long uuid = 0;
	
	public long getId() {
		return uuid;
	}
	public void setId(long i) {
		uuid = i;
	}
	
	protected boolean dead = false;
	
	
	
	public int[] getpos() {
		return new int[] {x,y};
	}
	public void go_to(int[] p) {
		x = p[0];
		y = p[1];
	}
	
	public void kill() {
		dead = true;
	}
	
	public boolean isAlive() {
		return !dead;
	}
	
	public void setAlive(boolean b) {
		dead = !b;
	}
	
	public abstract void tick(Game g, Player[] ps);
	public abstract String getImage(GameVariables vars);
	
	public void move(Direction d) {
		int[] r = Direction.move(new int[] {x, y}, d);
		x = r[0];
		y = r[1];
	}
	
	public abstract boolean onPlayerEnter(Game g, Entity p, Direction d, GameVariables vars);
	public boolean onPlayerMove(Game g, Entity e, Direction d, GameVariables vars) {
		g.addChangeEnts(this);
		return onPlayerEnter(g, e, d, vars);
	}
	
	public static long entity_id = 0;
	public static Entity createEntity(Class<?extends Entity> c) {
		Entity e;
		try {
			e = c.newInstance();
			e.setId(entity_id);
			entity_id ++;
			return e;
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Entity) {
			return ((Entity)o).uuid == this.uuid;
		}
		return false;
	}
	
	public boolean equalsOld(Object o) {
		return super.equals(o);
	}
}
