package uk.co.hobnobian.chips.main.multiplayer;

public class ServerInfo {
	public final String name;
	public final String mapName;
	
	
	@Override
	public String toString() {
		String r = "Server name: "+name+" - Map name: "+mapName;
		return r;
	}
	
	public ServerInfo(String n, String m) {
		name = n;
		mapName = m;
	}
	
}
