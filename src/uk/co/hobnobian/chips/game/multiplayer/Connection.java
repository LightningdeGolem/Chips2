package uk.co.hobnobian.chips.game.multiplayer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
	private Socket sock;
	private BufferedOutputStream out;
	private BufferedInputStream in;
	
	public Connection(Socket s) throws IOException {
		sock = s;
		out = new BufferedOutputStream(sock.getOutputStream());
		in = new BufferedInputStream(sock.getInputStream());
	}
	
	public Connection(Socket s, BufferedOutputStream o, BufferedInputStream i) {
		sock = s;
		out = o;
		in = i;
	}
	
	public Socket getSock() {
		return sock;
	}

	public BufferedOutputStream getOut() {
		return out;
	}

	public BufferedInputStream getIn() {
		return in;
	}
}
