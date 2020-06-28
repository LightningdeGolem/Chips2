package uk.co.hobnobian.chips.game.backend;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import uk.co.hobnobian.chips.game.display.ClientConnectionLayer;
import uk.co.hobnobian.chips.game.display.Renderer;

public class GraphicsServerLayer implements ClientConnectionLayer{
	
	PlayerMoveListener movement;
	Renderer du;
	HashMap<String, Image> cache = new HashMap<String, Image>();
	Game game;
	
	public void setGame(Game g) {
		game = g;
	}
	
	public void setDead(boolean b) {
		du.death = b;
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
		
		for (int y = 0; y < b.length; y++) {
			for (int x = 0; x < b[y].length; x++) {
				blocks.put(new int[] {x,y}, loadImage(b[x][y].getImage(new GetImageData(vars,m, x+p.getpos()[0]-3, y+p.getpos()[1]-3))));
			}
		}
		
		Block[][] second = m.getSelectionSecondLayer(7, 7, p.getpos()[0], p.getpos()[1]);
		HashMap<int[], Image> blocks2 = new HashMap<int[], Image>();
		for (int y = 0; y < second.length; y++) {
            for (int x = 0; x < second[y].length; x++) {
                if (second[x][y] == null) {
                    continue;
                }
                blocks2.put(new int[] {x,y}, loadImage(second[x][y].getImage(new GetImageData(vars,m, x+p.getpos()[0]-3, y+p.getpos()[1]-3))));
            }
        }
		
		HashMap<int[], Image> players = new HashMap<int[], Image>();
		players.put(new int[] {3,3}, loadImage(p.getImage(vars)));
		if (p2 != null) {
			int[] pos = new int[] {p2.getpos()[0] - p.getpos()[0] +3, p2.getpos()[1] - p.getpos()[1]+3};
			players.put(pos, loadImage(p2.getImage(vars)));
		}
		
		ArrayList<Image> inv = new ArrayList<Image>();
		for (InventoryItem item : p.getInventory()) {
		    inv.add(loadImage(item.getImage()));
		}
		
		ArrayList<Image> title = new ArrayList<Image>();
        String[] letters = {"c","h","i","p","s","space","2"};
        for (String letter : letters) {
            String filename = "letters/"+letter+".png";
            title.add(loadImage(filename));
        }
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				du.update(blocks, blocks2, players, inv, title);
			}
			
		});
		
		
		
	}
	
	public void setPaused(boolean p) {
		du.paused = p;
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
	public void togglePause() {
		game.togglePause();
		
	}

	@Override
	public void exit() {
		game.exit();
		
	}

	public void closeWindow() {
		du.close();
		
	}

	@Override
	public void respawn() {
		game.respawn();
		
	}
}
