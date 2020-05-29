package uk.co.hobnobian.chips.editor.options;

import uk.co.hobnobian.chips.editor.Editor;

public abstract class OptionMenu {
    public OptionMenu() {}
    
    public abstract String[] getRender();

	public abstract boolean keyPressed(int code, Editor editor);
}
