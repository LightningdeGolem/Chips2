package uk.co.hobnobian.chips.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 3226831824185259183L;
	private Editor editor;
	
	private boolean mouse = false;
	
	
	private int lastX = 0;
	private int lastY = 0;
	
	
	ImageCache imageCache;
	
	private double[] offsetcentre = {0,0};
	private int size = 64;
	
	public Canvas(Editor e) {
		editor = e;
		imageCache = editor.getImageCache();
		
		super.addMouseListener(this);
		super.addMouseMotionListener(this);
		
		super.setPreferredSize(new Dimension(448,448));
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0,0, getSize().width, getSize().height);

		
		double topleftx = offsetcentre[0]-((super.getSize().width/2.0)/size);
		double toplefty = offsetcentre[1]-((super.getSize().height/2.0)/size);
		
		int[] pixeloffset = new int[] {((int)(offsetcentre[0]*size))%size,((int)(offsetcentre[1]*size))%size};
		
		int px;
		int py = 0;
		
		
		int by = (int) toplefty;
		int bx;
		
		int finalx = 0;
		int finaly = 0;
		
		while (finaly < getSize().height) {
			px = 0;
			bx = (int) topleftx;
			finalx = 0;
			while (finalx < getSize().width) {
				Image i = imageCache.loadImage(editor.getMap().getAt(bx, by).getImage(editor.getVars()), size);
				
				finalx = px-pixeloffset[0];
				finaly = py-pixeloffset[1];
				
				setSquare(finalx, finaly, i, g2d);
				
				bx++;
				px+=size;
			}
			by++;
			py+=size;
		}
		
	}
	
	private void setSquare(int x, int y, Image image, Graphics2D g) {
		g.drawImage(image, x, y, this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		lastX = e.getX();
		lastY = e.getY();
		
		mouse = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouse = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getButton() == 1) {
			moveCamera(e);
		}
	}
	
	private int xChange = 0;
	private int yChange = 0;
	private void moveCamera(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		xChange += x-lastX;
		yChange += y-lastY;

		double c = 16.0;
		double actualXChange = (xChange/(double)size)/c;
		double actualYChange = (yChange/(double)size)/c;
		
		if (Math.abs(actualXChange) > 0.125) {
			offsetcentre[0]-=actualXChange;
			xChange = 0;
		}
		if (Math.abs(actualYChange) > 0.125) {
			offsetcentre[1]-=actualYChange;
			yChange = 0;
		}

		
		if (offsetcentre[0] < 0) {
			offsetcentre[0] = 0;
			xChange = 0;
		}
		else if (offsetcentre[0] > 255) {
			offsetcentre[0] = 255;
			xChange = 0;
		}
		if (offsetcentre[1] < 0) {
			offsetcentre[1] = 0;
			yChange = 0;
		}
		else if (offsetcentre[1] > 255) {
			offsetcentre[1] = 255;
			yChange = 0;
		}
		
		
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {}
}
