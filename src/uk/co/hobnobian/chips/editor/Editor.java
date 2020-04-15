package uk.co.hobnobian.chips.editor;

import java.awt.GridLayout;

import javax.swing.JFrame;

import uk.co.hobnobian.chips.game.backend.GameVariables;
import uk.co.hobnobian.chips.game.backend.Map;
import uk.co.hobnobian.chips.main.Window;

public class Editor {
	private Window window;
	private Canvas canvas;
	private Selector selector;

	private Map map;
	private GameVariables vars = new GameVariables();
	
	private ImageCache imageCache = new ImageCache();
	
	public GameVariables getVars() {
		return vars;
	}
	
	public Editor() {
		window = new Window();
		window.setLayout(new GridLayout(0,2));
		canvas = new Canvas(this);
		selector = new Selector(this);
		window.add(canvas);
		window.add(selector);

		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		map = new Map();
//		map.setBlock(0, 0, new Wall());
		System.out.println(map.getAt(0, 0));
		System.out.println(map.getAt(1, -1));
	}

	public Map getMap() {
		return map;
	}

	public ImageCache getImageCache() {
		return imageCache;
	}
}
