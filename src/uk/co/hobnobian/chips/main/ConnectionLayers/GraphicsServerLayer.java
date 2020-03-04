package uk.co.hobnobian.chips.main.ConnectionLayers;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import uk.co.hobnobian.chips.main.PlayerMoveListener;
import uk.co.hobnobian.chips.main.client.ClientConnectionLayer;
import uk.co.hobnobian.chips.main.client.Renderer;
import uk.co.hobnobian.chips.main.server.Block;
import uk.co.hobnobian.chips.main.server.Direction;
import uk.co.hobnobian.chips.main.server.Entity;
import uk.co.hobnobian.chips.main.server.Game;
import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Map;
import uk.co.hobnobian.chips.main.server.Player;

public class GraphicsServerLayer implements ClientConnectionLayer{
	
	PlayerMoveListener movement;
	Renderer du;
	HashMap<String, Image> cache = new HashMap<String, Image>();
	Game game;
	
	public void setGame(Game g) {
		game = g;
	}
	
	private Image loadImage(String path) {
		if (cache.containsKey(path)) {
			return cache.get(path);
		}
		else {
			Image i = loadImageFile(path);
			cache.put(path, i);
			return i;
		}
	}
	
	private Image loadImageFile(String path) {
		path = "/uk/co/hobnobian/chips/assets/"+path;
		try {
			Image i = ImageIO.read(getClass().getResource(path));
			return i.getScaledInstance(64, 64, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateMap(Map m, GameVariables vars, Player p, Player p2) {
		Block[][] b = m.getSelection(7, 7, p.getpos()[0], p.getpos()[1]);
		HashMap<int[], Image> blocks = new HashMap<int[], Image>();
		HashMap<int[], Image> ents = new HashMap<int[], Image>();
		
		for (int y = 0; y < b.length; y++) {
			for (int x = 0; x < b[y].length; x++) {
				blocks.put(new int[] {x,y}, loadImage(b[x][y].getImage(vars)));
			}
		}
		for (Entity e : m.getEntities(7, 7, p.getpos()[0], p.getpos()[1])) {
			Image img = loadImage(e.getImage(vars));
			int[] pos = new int[] {e.getpos()[0] - p.getpos()[0] +3, e.getpos()[1] - p.getpos()[1]+3};
			ents.put(pos, img);
		}
		
		HashMap<int[], Image> players = new HashMap<int[], Image>();
		players.put(new int[] {3,3}, loadImage(p.getImage(vars)));
		if (p2 != null) {
			int[] pos = new int[] {p2.getpos()[0] - p.getpos()[0] +3, p2.getpos()[1] - p.getpos()[1]+3};
			players.put(pos, loadImage(p2.getImage(vars)));
		}
		
		du.update(blocks, ents, players);
		
		
	}

	public void setPlayerListener(PlayerMoveListener p) {
		movement = p;
	}

	public void move(Direction d) {
		movement.onPlayerMove(d);
	}

	public void setUpdater(Renderer d) {
		du = d;
	}
	
	public void setPlaying(boolean playing, boolean canunplay) {
		du.paused = !playing;
		du.canplay = canunplay;
	}

	@Override
	public void reset() {
		game.reset();
		
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
}
