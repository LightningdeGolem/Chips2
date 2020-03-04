package uk.co.hobnobian.chips.main.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class GameVariables implements Serializable{
	private static final long serialVersionUID = 8976205449352240859L;
	private HashMap<String, Integer> data = new HashMap<String, Integer>();
	
	public GameVariables() {
		data.put("greenBlock", 0);
	}
	
	public void set(String k, int value) {
		data.replace(k, value);
	}
	public int get(String k) {
		return data.get(k);
	}
	public Set<String> getKeys(){
		return data.keySet();
	}
}
