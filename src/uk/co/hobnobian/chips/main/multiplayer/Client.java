package uk.co.hobnobian.chips.main.multiplayer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Map;
import uk.co.hobnobian.chips.main.server.PlayerType;

public class Client implements Runnable{
	private BufferedWriter bw;
	private BufferedReader br;
	
	private Map map;
	private ConnectionManager con;
	
	private GameVariables old;
	
	public Client(Socket s, ConnectionManager con) {
		try {
			this.con = con;
			con.setPlayerType(PlayerType.RED);
			con.setResetWhenDie(false);
			con.setMain(false);
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			while (!br.ready()) {}
			String rawdata = br.readLine();
			Map m = (Map) Serializer.fromString(rawdata);
			map = m;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void go() {
		
		try {
			while (!br.ready()) {}
			String raw = br.readLine();
			ServerPacket data = (ServerPacket) Serializer.fromString(raw);
			old = data.getVars();
			con.setVars(data.getVars());
			con.setTheirPlayer(data.getPlayer());
			con.setEntities(data.getEntities());
			
			while (true) {
				ClientPacket toSend = new ClientPacket(con.getVars(), old,con.getChanges(), con.getOurPlayer());
				con.resetChanges();
				bw.write(Serializer.toString(toSend)+"\n");
				bw.flush();
				
				while (!br.ready()) {}
				String rawdata = br.readLine();
				data = (ServerPacket) Serializer.fromString(rawdata);
				old = con.getVars();
				con.setVars(data.getVars());
				con.setTheirPlayer(data.getPlayer());
				con.setEntities(data.getEntities());
				Thread.sleep(100);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Map getMap() {
		return map;
	}

	@Override
	public void run() {
		go();
		
	}
}
