package uk.co.hobnobian.chips.main.multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import uk.co.hobnobian.chips.main.Main;

public class GameHandler {
	private ConnectionManager con;
	private Socket sock;
	
	DataOutputStream out;
	DataInputStream in;
	
	public GameHandler(Socket s, ConnectionManager con) {
		this.con = con;
		sock = s;
		try {
			out = new DataOutputStream(s.getOutputStream());
			in = new DataInputStream(s.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() {
		
	}
	
	private void write() {
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		
		bytes.add(toByte(Main.protocolID));//Write protocol id
		
		bytes.add(toByte(con.getOurPlayer().getpos()[0]));//Write player x
		bytes.add(toByte(con.getOurPlayer().getpos()[1]));//Write player y
		
		
	}
	
	
	private int fromByte(byte b) {
		return b+128;
	}
	private byte toByte(int i) {
		while (i > 255) {
			i-=256;
		}
		while (i < 0) {
			i+=256;
		}
		return (byte) i;
	}
}
