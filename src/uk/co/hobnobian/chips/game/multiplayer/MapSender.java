package uk.co.hobnobian.chips.game.multiplayer;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import uk.co.hobnobian.chips.game.backend.Map;

public class MapSender {
	public MapSender(Map m, Connection c) throws IOException {
		BufferedOutputStream bo = c.getOut();
		byte[] data = Serializer.toByteArray(m);
		
		long total = data.length;
		System.out.println(total);
		
		byte[] len = ByteBuffer.allocate(8).putLong(total).array();
		
		bo.write(len);
		bo.write(data);
		bo.flush();
	}
}
