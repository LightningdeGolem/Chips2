package uk.co.hobnobian.chips.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import uk.co.hobnobian.chips.game.display.Renderer;

public class FontsManager {
	HashMap<String, BufferedImage> data = new HashMap<String, BufferedImage>();
	
	
	public void cache(Font f, String s, Color c) {
	    if (data.keySet().contains(s)) {
	        return;
	    }
		data.put(s, fontToBI(f,s,c));
	}
	
	public void render(String s, int x, int y, Graphics g, Renderer r) {
		if (data.keySet().contains(s)) {
			g.drawImage(data.get(s), x, y, r);
		}
	}
	
	public Image getImage(String s) {
		return data.get(s);
	}
	
	private BufferedImage fontToBI(Font f, String text, Color c) {
		BufferedImage test = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = test.createGraphics();
		g.setFont(f);
		int width = g.getFontMetrics().stringWidth(text);
		int height = g.getFontMetrics().getHeight();
		
		g.dispose();
		
		BufferedImage b = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = b.createGraphics();
		g2.setBackground(new Color(0f,0f,0f,0f));
		g2.setColor(c);
		g2.setFont(f);
		g2.drawString(text, 0, height);
		g2.dispose();
		
		return b;
	}
}
