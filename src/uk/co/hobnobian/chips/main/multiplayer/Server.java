package uk.co.hobnobian.chips.main.multiplayer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import uk.co.hobnobian.chips.main.server.Entity;
import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Map;
import uk.co.hobnobian.chips.main.server.PlayerType;

public class Server implements Runnable{
	BufferedReader br;
	BufferedWriter bw;
	
	ConnectionManager con;
	
	public Server(Socket s, Map m, ConnectionManager con) {
		try {
			this.con = con;
			con.setPlayerType(PlayerType.BLUE);
			con.setResetWhenDie(false);
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			String map = Serializer.toString(m);
			bw.write(map+"\n");
			bw.flush();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		game();
	}
	
	private void game() {
		
		try {
			while (true) {
				if (con.isClosing()) {
					try {
						bw.write(Serializer.toString(new EndGamePacket())+"\n");
						bw.flush();
						bw.close();
					}
					catch (IOException e) {}
					break;
					
					
				}
				
				ServerPacket packet = new ServerPacket(con.getVars(), con.getEntities(), con.getOurPlayer());
				bw.write(Serializer.toString(packet)+"\n");
				bw.flush();
				while (!br.ready()) {}
				String rawdata = br.readLine();
				
				ClientPacket data;
				Object o = Serializer.fromString(rawdata);
				if (o instanceof EndGamePacket) {
					EndGamePacket e = (EndGamePacket) o;
					break;
				}
				else if (o instanceof ClientPacket) {
					data = (ClientPacket) o;
					con.setTheirPlayer(data.getPlayer());
					
					GameVariables current = con.getVars();
					for (String key : data.getChanges().keySet()) {
						current.set(key, data.getChanges().get(key));
					}
					List<Entity> entities = con.getEntities();
					for (Entity e : data.getChangesEntity()) {
						if (entities.contains(e)) {
							int index = entities.indexOf(e);
							entities.set(index, e);
						}
					}
					con.setEntities(entities);
					con.setVars(current);
				}
				
				
				Thread.sleep(10);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
