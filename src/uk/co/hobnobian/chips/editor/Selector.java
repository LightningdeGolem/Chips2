package uk.co.hobnobian.chips.editor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import javax.swing.JPanel;

import uk.co.hobnobian.chips.game.backend.Block;

public class Selector extends JPanel {
	private static final long serialVersionUID = 8982547834654819270L;
	
	public static final int ROWS = 28;
	public static final int COLUMNS = 28;
	
	public static final int size = 64;
	
	private Editor editor;
	private ImageCache cache;
	
	public Selector(Editor e) {
		setLayout(null);
		editor = e;
		cache = e.getImageCache();
		System.out.println(COLUMNS*size);
//		setSize(COLUMNS*size, ROWS*size);
		super.setPreferredSize(new Dimension(1, ROWS*size));
//		super.setMaximumSize(new Dimension(1, ROWS*size));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.fillRect(0, 0, getSize().width, getSize().height);
		Set<Class<?extends Block>> blockC = Block.inverseBlockIds.keySet();
		String[] blocks = new String[blockC.size()];
		int i = 0;
		for (Class<?extends Block> c: blockC) {
			try {
				Block b = c.getConstructor().newInstance();
				blocks[i] = b.getImage(editor.getVars());
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				i++;
				continue;
			}
			i++;
			
			
		}
		
		i = 0;
		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLUMNS; x++) {
				if (blocks.length <= i) {
					break;
				}
				
				g.drawImage(cache.loadImage(blocks[i], size), x*size,y*size, this);
				i++;
				
			}
			if (blocks.length <= i) {
				break;
			}
		}
		}
		
}
