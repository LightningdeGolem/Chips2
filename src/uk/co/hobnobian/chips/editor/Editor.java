package uk.co.hobnobian.chips.editor;

import uk.co.hobnobian.chips.game.backend.GameVariables;
import uk.co.hobnobian.chips.game.backend.Map;
import uk.co.hobnobian.chips.game.blocks.Wall;
import uk.co.hobnobian.chips.main.Window;

public class Editor {
	private Window window;
	private Canvas canvas;

	private Map map;
	private GameVariables vars = new GameVariables();
	
	public GameVariables getVars() {
		return vars;
	}
	
	public Editor() {
		window = new Window();
		canvas = new Canvas(this);
		window.add(canvas);
		window.setup();
		
		map = new Map();
//		map.setBlock(0, 0, new Wall());
		System.out.println(map.getAt(0, 0));
		System.out.println(map.getAt(1, -1));
	}

	public Map getMap() {
		return map;
	}
}
