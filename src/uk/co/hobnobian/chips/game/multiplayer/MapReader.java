package uk.co.hobnobian.chips.game.multiplayer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import uk.co.hobnobian.chips.game.backend.Map;

public class MapReader {
	private Map map;
	
	public MapReader(Connection c) throws IOException {
		BufferedInputStream in = c.getIn();
		while (in.available() < 1) {}
		
		byte[] len = new byte[8];
		in.read(len);
		
		
		ByteBuffer wrapped = ByteBuffer.wrap(len);
		long size = wrapped.getLong();
		
		System.out.println(size);
		
		byte[] data = new byte[(int) size];
		for (int i = 0; i < size; i++) {
		    data[i] = (byte)in.read();
		}

		Map m = MapDataIO.decodeBytes(data);
		map = m;
	}
	
	public Map getMap() {
		return map;
	}
}
