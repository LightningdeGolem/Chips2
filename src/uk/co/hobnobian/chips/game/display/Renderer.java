package uk.co.hobnobian.chips.game.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import uk.co.hobnobian.chips.game.backend.Direction;
import uk.co.hobnobian.chips.gui.FontsManager;
import uk.co.hobnobian.chips.main.Window;

public class Renderer extends JPanel implements KeyListener{
	private static final long serialVersionUID = -3818026926139086329L;
	
	public boolean paused = false;
	public boolean canplay = true;
	public boolean death = false;

	private FontsManager fonts;
	
	ClientConnectionLayer con;
	HashMap<int[], Image> blocks = null;
	HashMap<int[], Image> blocks2 = null;
	public final Image background;
	ArrayList<Image> inv = null;
	HashMap<int[], Image> players = null;
	
	ArrayList<Image> title = null;
	
	private Window window;
	
	
	private void initializeFonts() {
		fonts = new FontsManager();
		fonts.cache(new Font("Sans-serif", 1, 40), "PAUSED", Color.BLACK);
		
		Font f = new Font("Sans-serif",2,20);
		fonts.cache(f,"Press 'r' to restart game", Color.BLACK);
		fonts.cache(f, "Press 'e' to exit game", Color.BLACK);
		fonts.cache(f, "Press 'ESC' to exit menu", Color.BLACK);
		
		fonts.cache(new Font("Sans-serif", 1, 40), "YOU DIED", Color.RED);
		fonts.cache(new Font("Sans-serif", 1, 30), "Press space to continue", Color.YELLOW);
		
		
	}
	
	public Renderer(ClientConnectionLayer c, Image bkgrnd, Window w) {
		window = w;
		initializeFonts();
		
		
		//224x224 for 7x7 block layout
		//64x64 block
		//Add extra 3 blocks across for inventory
		this.setPreferredSize(new Dimension(640,448));
		background = bkgrnd;
		con = c;
		con.setUpdater(this);
		
		addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		if (death) {
			g2d.setColor(new Color(0,0,0));
			g2d.fillRect(0, 0, getSize().width ,getSize().height);
			
			g2d.setColor(Color.RED);
			int w = fonts.getImage("YOU DIED").getWidth(this);
			int h = fonts.getImage("YOU DIED").getHeight(this);
			fonts.render("YOU DIED", (getSize().width/2)-(w/2), (getSize().height/2)-(h/2)-60, g2d, this);
			
			w = fonts.getImage("Press space to continue").getWidth(this);
			h = fonts.getImage("Press space to continue").getHeight(this);
			fonts.render("Press space to continue",(getSize().width/2)-(w/2),(getSize().height/2)-(h/2),g2d,this);
			return;
		}
		
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
		
		for (int[] key : blocks2.keySet()) {
		    if (blocks2.get(key) == null) {
		        continue;
		    }
            setSquare(key[0], key[1], blocks2.get(key), g2d);
        }
		
		for (int[] key : players.keySet()) {
			setSquare(key[0], key[1], players.get(key), g2d);
		}
		
		//BLACK BORDER BETWEEN THIS AND INV
		for (int y = 0; y < 7; y++) {
		    setSquare(7,y,title.get(y), g2d);
		}
		
		int i = 0;
		for (int y = 0; y <7; y++) {
		    for(int x = 8; x < 10; x++) {
		        setSquare(x,y,background,g2d);
		        if (i < inv.size()) {
		            setSquare(x,y,inv.get(i), g2d);
		        }
		        i++;
		    }
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

	public void update(HashMap<int[], Image> blocks, HashMap<int[], Image> blocks2,HashMap<int[], Image> players, ArrayList<Image> inv, ArrayList<Image> logo) {
		this.blocks = blocks;
		this.blocks2 = blocks2;
		this.players = players;
		this.inv = inv;
		this.title = logo;
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
