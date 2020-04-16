package uk.co.hobnobian.chips.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

import uk.co.hobnobian.chips.game.backend.GameVariables;
import uk.co.hobnobian.chips.game.backend.Map;

public class Editor {
	private JFrame window;
	private Canvas canvas;
	private Selector selector;

	private Map map;
	private GameVariables vars = new GameVariables();
	
	private ImageCache imageCache = new ImageCache();
	
	public GameVariables getVars() {
		return vars;
	}
	
	public Editor() {
		window = new JFrame();
		window.setLayout(new GridBagLayout());
		canvas = new Canvas(this);
		selector = new Selector(this);
		
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
	}

	public Map getMap() {
		return map;
	}

	public ImageCache getImageCache() {
		return imageCache;
	}
}
