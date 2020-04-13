package uk.co.hobnobian.chips.game.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class GameVariables implements Serializable{
	private static final long serialVersionUID = 8976205449352240859L;
	private HashMap<Integer, Integer> data = new HashMap<Integer, Integer>();
	
	public GameVariables() {
		data.put(0, 0);//GREEN BLOCK VARIABLE
	}
	
	private GameVariables(HashMap<Integer, Integer> d) {
		data = d;
	}
	
	public GameVariables clone() {
		HashMap<Integer, Integer> d = new HashMap<Integer, Integer>(data);
		return new GameVariables(d);
	}
	
	public void set(int k, int value) {
		data.replace(k, value);
	}
	public int get(int k) {
		return data.get(k);
	}
	public Set<Integer> getKeys(){
		return data.keySet();
	}
}
