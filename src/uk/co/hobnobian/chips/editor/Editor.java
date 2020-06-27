package uk.co.hobnobian.chips.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.JFrame;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.GameVariables;
import uk.co.hobnobian.chips.game.backend.Map;
import uk.co.hobnobian.chips.game.blocks.Air;
import uk.co.hobnobian.chips.game.options.MapChooser;
import uk.co.hobnobian.chips.game.options.MapSaver;
import uk.co.hobnobian.chips.gui.ImageCache;

public class Editor implements MapChangeBlockListener {
	private JFrame window;
	private Canvas canvas;
	private BlockSelector selector;
	
	private Stack<List<BlockChangeEvent>> undoStack = new Stack<List<BlockChangeEvent>>();
	private ArrayList<BlockChangeEvent> currentUndoEvent = new ArrayList<BlockChangeEvent>();
	
	private Selection currentlySelected = null;

	private Map map;
	private GameVariables vars = new GameVariables();
	
	private ImageCache imageCache = new ImageCache();
	
	public GameVariables getVars() {
		return vars;
	}
	
	public void setBlock(int x, int y) {
		if (x < 0 || x > 255 || y < 0 || y > 255) {
			return;
		}
		Block b = new Air();
		try {
			b = selector.getSelected().getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		map.setBlock(x, y, b);
		window.repaint();
	}
	
	
	public Editor() {
		window = new JFrame();
		window.setLayout(new GridBagLayout());
		canvas = new Canvas(this);
		selector = new BlockSelector(this);
		
		window.addKeyListener(canvas);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 4;
		c.ipady = 4;
		c.gridwidth = 1;
		window.add(canvas,c);
		c.gridy = 1;
		window.add(selector,c);

		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		map = new Map();
		map.setMapChangeBlockListener(this);
	}

	public Map getMap() {
		return map;
	}

	public ImageCache getImageCache() {
		return imageCache;
	}
	
	public Selection getSelected() {
	    return currentlySelected;
	}
	
	public void setSelected(Selection s) {
	    currentlySelected = s;
	}
	
	public JFrame getWindow() {
	    return window;
	}
	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void change(int x, int y, Block old, Block newblock) {
		currentUndoEvent.add(new BlockChangeEvent(x,y,old,newblock));
	}
	
	public void pushChanges() {
		undoStack.push(currentUndoEvent);
		currentUndoEvent = new ArrayList<BlockChangeEvent>();
	}
	
	public void undo() {
		if (undoStack.size() == 0) {
			return;
		}
		
		for (BlockChangeEvent change : undoStack.pop()) {
			map.setBlockNoRecord(change.getX(), change.getY(), change.getFrom());
		}
		
	}

	public void save() {
		new MapSaver().save(canvas, map);
	}

	public void open() {
		map = new MapChooser().choose(canvas);
		
	}
}
