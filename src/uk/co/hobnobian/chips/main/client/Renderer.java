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
	
	ClientConnectionLayer con;
	HashMap<int[], Image> entities = null;
	HashMap<int[], Image> blocks = null;
	public final Image background;
	HashMap<int[], Image> players = null;
	
	public Renderer(ClientConnectionLayer c, Image bkgrnd) {
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
			
			
			Font f = new Font("Sans-serif", 0, 48);
			g2d.setFont(f);
			g2d.setColor(Color.BLACK);
			g2d.drawString("PAUSED", 120, 224);
			f = new Font("Sans-serif", 0, 18);
			g2d.setFont(f);
			g2d.drawString("Press 'ESC' to exit", 120, 260);
			
			
			if (canplay) {
				f = new Font("Sans-serif", 0, 18);
				g2d.setFont(f);
				g2d.drawString("Press 'P' to play", 120, 300);
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
		else if (e.getKeyChar() == 'r') {
			con.reset();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
