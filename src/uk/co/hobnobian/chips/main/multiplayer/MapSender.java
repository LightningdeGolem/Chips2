package uk.co.hobnobian.chips.main.multiplayer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import uk.co.hobnobian.chips.main.server.Map;

public class MapSender {
	public MapSender(Map m, Socket sock) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		bw.write(Serializer.toString(m)+"\n");
		bw.flush();
	}
}
