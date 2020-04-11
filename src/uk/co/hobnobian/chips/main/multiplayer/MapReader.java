package uk.co.hobnobian.chips.main.multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import uk.co.hobnobian.chips.main.server.Map;

public class MapReader {
	private Map map;
	
	public MapReader(Socket sock) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		while (!br.ready()) {}
		String data = br.readLine();
		Map m = (Map) Serializer.fromString(data);
		map = m;
	}
	
	public Map getMap() {
		return map;
	}
}
