package uk.co.hobnobian.chips.editor.options;

import uk.co.hobnobian.chips.editor.Editor;
import uk.co.hobnobian.chips.game.blocks.Air;

public class BaseOption extends OptionMenu {

    @Override
    public String[] getRender() {
        return new String[] {"C - CLEAR", "F - FILL"};
    }
    
    @Override
    public boolean keyPressed(int code, Editor e) {
    	if (code == 'F') {
    		for (int[] pos : e.getSelected().getSelection()) {
    			if (e.getMap().inBounds(pos[0], pos[1])) {
    				e.setBlock(pos[0], pos[1]);
    			}
    		}
    		e.getCanvas().repaint();
    		return true;
    	}
    	else if (code == 'C') {
    		for (int[] pos : e.getSelected().getSelection()) {
    			if (e.getMap().inBounds(pos[0], pos[1])) {
    				e.getMap().setBlock(pos[0], pos[1], new Air());
    			}
    		}
    		e.getCanvas().repaint();
    		return true;
    	}
    	return false;
    }

}
