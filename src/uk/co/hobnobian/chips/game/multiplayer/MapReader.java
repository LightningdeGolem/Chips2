package uk.co.hobnobian.chips.game.multiplayer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import uk.co.hobnobian.chips.game.server.Map;

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
		in.read(data);

		Map m = (Map) Serializer.fromByteArray(data);
		map = m;
	}
	
	public Map getMap() {
		return map;
	}
}
