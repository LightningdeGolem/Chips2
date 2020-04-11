package uk.co.hobnobian.chips.main.multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import uk.co.hobnobian.chips.main.Main;

public class ProtocolCheckState {
	private boolean worked;
	public ProtocolCheckState(Socket sock, boolean send) throws IOException {
		DataInputStream in = new DataInputStream(sock.getInputStream());
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		
		if(send) {
			out.write(GameHandler.toByte(Main.protocolID));
			while (in.available() == 0) {}
			worked = (GameHandler.fromByte(in.readAllBytes()[0]) == 255);
		}
		else {
			while (in.available() == 0) {}
			int theirs = GameHandler.fromByte(in.readAllBytes()[0]);
			if (theirs == Main.protocolID) {
				out.write(GameHandler.toByte(255));
				worked = true;
			}
			else {
				worked = false;
				out.write(GameHandler.toByte(0));
				sock.close();
			}
		}
	}
	
	public boolean check() {
		return worked;
	}
}
