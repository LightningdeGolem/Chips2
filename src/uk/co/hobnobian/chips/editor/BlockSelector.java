package uk.co.hobnobian.chips.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.JPanel;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.GetImageData;
import uk.co.hobnobian.chips.game.blocks.Air;
import uk.co.hobnobian.chips.gui.ImageCache;

public class BlockSelector extends JPanel implements MouseListener{
	private static final long serialVersionUID = 8982547834654819270L;
	
	public static final int ROWS = 4;
	public static final int COLUMNS = 14;
	
	public static final int size = 32;
	
	private Class<?extends Block> selected = Air.class;
	
	ArrayList<Class<?extends Block>> classes;
	
	private Editor editor;
	private ImageCache cache;
	
	public BlockSelector(Editor e) {
		setLayout(null);
		editor = e;
		cache = e.getImageCache();
		super.setPreferredSize(new Dimension(COLUMNS*size, ROWS*size));
		super.setMinimumSize(new Dimension(COLUMNS*size, ROWS*size));
		
		classes = Block.blocks;
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, getSize().width, getSize().height);
		
		String[] blocks = new String[classes.size()];
		int i = 0;
		for (Class<?extends Block> c : classes) {
			try {
				blocks[i] = c.getConstructor().newInstance().getImage(new GetImageData(editor.getVars(), editor.getMap(), 0, 0, true));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				i++;
				continue;
			}
			i++;
			
			
		}
		
		i = 0;
		g.setColor(Color.RED);
		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLUMNS; x++) {
				if (blocks.length <= i) {
					break;
				}
				
				g.drawImage(cache.loadImage(blocks[i], size), x*size,y*size, this);
				if (classes.get(i).equals(selected)) {
					g.drawRect(x*size,y*size, size, size);
				}
				i++;
				
			}
			if (blocks.length <= i) {
				break;
			}
		}
		
		}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX()/size;
		int y = e.getY()/size;
		
		int pos = (y*COLUMNS)+x;
		if (!(pos < classes.size())) {
			return;
		}
		selected = classes.get(pos);
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	public Class<?extends Block> getSelected() {
		return selected;
	}
		
}
