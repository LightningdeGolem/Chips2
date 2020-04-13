package uk.co.hobnobian.chips.game.multiplayer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import uk.co.hobnobian.chips.main.Main;

public class ProtocolCheckState {
	private boolean worked;
	public ProtocolCheckState(Connection c, boolean send) throws IOException {
		BufferedInputStream in = c.getIn();
		BufferedOutputStream out = c.getOut();
		
		if(send) {
			out.write(GameHandler.toByte(Main.protocolID));
			out.flush();
			while (in.available() == 0) {}
			worked = (GameHandler.fromByte(in.readAllBytes()[0]) == 255);
		}
		else {
			while (in.available() == 0) {}
			byte[] idbytes = new byte[1];
			in.read(idbytes);
			
			int theirs = GameHandler.fromByte(idbytes[0]);
			
			if (theirs == Main.protocolID) {
				out.write(GameHandler.toByte(255));
				worked = true;
			}
			else {
				worked = false;
				out.write(GameHandler.toByte(0));
				
				out.close();
				in.close();
				c.getSock().close();
			}
		}
	}
	
	public boolean check() {
		return worked;
	}
}
