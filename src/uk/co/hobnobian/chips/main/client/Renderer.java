package uk.co.hobnobian.chips.main.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JPanel;

import uk.co.hobnobian.chips.main.server.Direction;

public class Renderer extends JPanel implements KeyListener{
	private static final long serialVersionUID = -3818026926139086329L;
	
	public boolean paused = false;
	public boolean canplay = true;
	
	private FontsManager fonts;
	
	ClientConnectionLayer con;
	HashMap<int[], Image> entities = null;
	HashMap<int[], Image> blocks = null;
	public final Image background;
	HashMap<int[], Image> players = null;
	
	private Window window;
	
	
	private void initializeFonts() {
		fonts = new FontsManager();
		fonts.cache(new Font("Sans-serif", 1, 40), "PAUSED");
		
		Font f = new Font("Sans-serif",2,20);
		fonts.cache(f,"Press 'r' to restart game");
		fonts.cache(f, "Press 'e' to exit game");
		fonts.cache(f, "Press 'ESC' to exit menu");
		
		
	}
	
	public Renderer(ClientConnectionLayer c, Image bkgrnd, Window w) {
		window = w;
		initializeFonts();
		//224x224 for 7x7 block layout
		//64x64 block
		background = bkgrnd;
		con = c;
		con.setUpdater(this);
		this.setPreferredSize(new Dimension(448,448));
		addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (int x = 0; x < 7;x++) {
			for (int y = 0; y < 7; y++) {
				setSquare(x, y, background, g2d);
			}
		}
		if (blocks == null) {
			return;
		}
		for (int[] key : blocks.keySet()) {
			setSquare(key[0], key[1], blocks.get(key), g2d);
		}
		
		if (entities != null) {
			for (int[] key : entities.keySet()) {
				setSquare(key[0], key[1], entities.get(key), g2d);
			}
		}
		
		for (int[] key : players.keySet()) {
			setSquare(key[0], key[1], players.get(key), g2d);
		}
		
		if (paused) {
			g2d.setColor(new Color(255,255,255,150));
			g2d.fillRect(0, 0, 448, 448);
			
			
			
			g2d.setColor(Color.BLACK);
			fonts.render("PAUSED", 120, 210, g2d, this);
			
			fonts.render("Press 'r' to restart game", 120, 260, g2d, this);
			fonts.render("Press 'e' to exit game", 120, 280, g2d, this);
			
			
			if (canplay) {
				fonts.render("Press 'ESC' to exit menu", 120, 300, g2d, this);
			}
		}
		
	}
	
	private void setSquare(int x, int y, Image image, Graphics2D g) {
		int px = x*64;
		int py = y*64;
		g.drawImage(image, px, py, this);
	}

	public void update(HashMap<int[], Image> blocks,HashMap<int[], Image> e , HashMap<int[], Image> players) {
		this.blocks = blocks;
		this.entities = e;
		this.players = players;
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if (!paused) {
			if (k == 37) {
				//LEFT
				con.move(Direction.WEST);
			}
			else if (k == 38) {
				//UP
				con.move(Direction.NORTH);
			}
			else if (k == 39) {
				//RIGHT
				con.move(Direction.EAST);
			}
			else if (k == 40) {
				//DOWN
				con.move(Direction.SOUTH);
			}
		}
		if (e.getKeyChar() == 'r') {
			if (paused) {
				paused = !paused;
				con.togglePause();
			}
			con.reset();
		}
		
		else if (e.getKeyCode() == 69 && paused) {
			con.exit();
		}
		//ESC
		else if (e.getKeyCode() == 27){
			con.togglePause();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void close() {
		window.dispose();
	}
}
