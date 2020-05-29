package uk.co.hobnobian.chips.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.co.hobnobian.chips.editor.options.OptionsDisplayer;
import uk.co.hobnobian.chips.gui.ImageCache;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener{
	private static final long serialVersionUID = 3226831824185259183L;
	private Editor editor;
	private OptionsDisplayer options;
	
	private static final double trackSensitivity = 4;
	
	private int lastX = 0;
	private int lastY = 0;
	
	
	private ImageCache imageCache;
	
	private double[] offsetcentre = {0,0};
	private int size = 64;
	
	public Canvas(Editor e) {
		editor = e;
		options = new OptionsDisplayer(editor);
		imageCache = editor.getImageCache();
		
		super.addMouseListener(this);
		super.addMouseMotionListener(this);
		super.addMouseWheelListener(this);
		
		
		super.setPreferredSize(new Dimension(448,448));
		super.setMinimumSize(new Dimension(448,448));
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g;
	    
	    int[] windowRadius = new int[] {getSize().width/2, getSize().height/2};
		int[] topleftblockcoord = new int[] {((int)offsetcentre[0])-(windowRadius[0]/size),((int)offsetcentre[1])-(windowRadius[1]/size)};
		double[] offset = new double[] {(offsetcentre[0]*size)%size, (offsetcentre[1]*size)%size};
		
		int py = 0;
		int by = topleftblockcoord[1];
		
		int px = 0;
		int bx = topleftblockcoord[0];
		while (py < getSize().height) {
		    while (px < getSize().width) {
		        int finalx = (int) (px-offset[0]);
		        int finaly = (int) (py-offset[1]);
		        
		        Image i = imageCache.loadImage(editor.getMap().getAt(bx, by).getImage(editor.getVars()), size);
		        setSquare(finalx, finaly, i, g2d);
		        
		        bx++;
		        px+=size;
		    }
		    py+=size;
		    by++;
		    px = 0;
		    bx = topleftblockcoord[0];
		}
		
		if (editor.getSelected() != null) {
			if (editor.getSelected().hasSelectedArea()) {
				g2d.setColor(Color.RED);
			}
			else {
				g2d.setColor(Color.YELLOW);
			}
			
			for (int[] pos : editor.getSelected().getSelection()) {
                g2d.drawRect(toXY(pos)[0], toXY(pos)[1], size, size);
            }
		}
		
		options.display(g2d, getSize().width, getSize().height);
	}
	
	private void setSquare(int x, int y, Image image, Graphics2D g) {
		g.drawImage(image, x, y, this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	    if (e.getButton() == 1) {
	    	if (!e.isShiftDown()) {
	    		editor.setSelected(new Selection());
	    	}
	        
	        editor.getSelected().mouseMoved(getRoughXY(e)[0], getRoughXY(e)[1]);
	        repaint();
	    }
		
	}

	private double[] getXY(MouseEvent e) {
		double relativex = e.getX()/(double)size;
		double relativey = e.getY()/(double)size;
		
		relativex-=(getSize().width/2)/size;
		relativey-=(getSize().height/2)/size;
		
		relativex+=offsetcentre[0];
		relativey+=offsetcentre[1];
		return new double[]{relativex,relativey};
	}
	
	private int[] getRoughXY(MouseEvent e) {
		double[] precise = getXY(e);
		int[] rough = new int[2];
		if (precise[0] < 0) {
			precise[0]-=1;
		}
		if (precise[1] < 0) {
			precise[1]-=1;
		}
		rough[0] = (int)precise[0];
		rough[1] = (int)precise[1];
		return rough;
	}
	
	private int[] toXY(int x1, int y1) {
	    double x = x1;
	    double y = y1;
	    
	    x-=offsetcentre[0];
        y-=offsetcentre[1];
	    x+=(getSize().width/2)/(double)size;
        y+=(getSize().height/2)/(double)size;
	    return new int[] {(int) (x*size)-(size/2), (int) (y*size)-(size/2)};
	}
	
	private int[] toXY(int[] pos) {
	    return toXY(pos[0], pos[1]);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		lastX = e.getX();
		lastY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		double[] actualpos = getXY(e);
		if (e.getClickCount() == 1) {
			if (e.getButton() == 1) {
				editor.setBlock(getRoughXY(e)[0], getRoughXY(e)[1]);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		boolean b1 = SwingUtilities.isLeftMouseButton(e);
		boolean b2 = SwingUtilities.isRightMouseButton(e);
		boolean b3 = SwingUtilities.isMiddleMouseButton(e);
		if (b3 || (b1 && e.isControlDown())) {
			moveCamera(e);
		}
		else if (b1) {
		    if (editor.getSelected() == null) {
		        editor.setSelected(new Selection());
		    }
		    editor.getSelected().mouseMoved(getRoughXY(e)[0], getRoughXY(e)[1]);
		    repaint();
		    
		}
	}
	

	private void moveCamera(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int moveOffsetX = lastX-x;
		int moveOffsetY = lastY-y;
		
		offsetcentre[0] += moveOffsetX/(double)size;
		offsetcentre[1] += moveOffsetY/(double)size;
		
		lastX = x;
		lastY = y;

		
		checkCentre();
		
		
		
		repaint();
	}
	
	private void checkCentre() {
		if (offsetcentre[0] < 0) {
			offsetcentre[0] = 0;
		}
		else if (offsetcentre[0] > 255) {
			offsetcentre[0] = 255;
		}
		if (offsetcentre[1] < 0) {
			offsetcentre[1] = 0;
		}
		else if (offsetcentre[1] > 255) {
			offsetcentre[1] = 255;
		}
	}
	
	private void checkZoom() {
	    if (size < 4) {
	        size = 4;
	    }
	    else if (size > 64) {
	        size = 64;
	    }
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == 37) {
			offsetcentre[0]-=0.125;
		}
		else if (code == 39) {
			offsetcentre[0]+=0.125;
		}
		if (code == 38){
			offsetcentre[1]-=0.125;
		}
		else if (code == 40) {
			offsetcentre[1]+=0.125;
		}
		
		if (e.getKeyChar() == '[') {
		    size/=2;
		    checkZoom();
		}
		else if (e.getKeyChar() == ']') {
		    size*=2;
            checkZoom();
		}
		
		//ESC
		if (e.getKeyCode() == 27) {
		    editor.setSelected(null);
		}
		
		checkCentre();
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown()) {
            int r = e.getWheelRotation();
            if (r < 0) {
                size*=2;
            }
            else if (r > 0){
                size/=2;
            }
            checkZoom();
        }
        
        if (e.isShiftDown()) {
            offsetcentre[0]+=(e.getWheelRotation()/(double)size)*trackSensitivity;
        }
        else {
            offsetcentre[1]+=(e.getWheelRotation()/(double)size)*trackSensitivity;
        }
        checkCentre();
        
        repaint();
        
    }
}
