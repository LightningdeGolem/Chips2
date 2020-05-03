package uk.co.hobnobian.chips.editor.options;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class OptionsDisplayer {
    private static final int HEIGHT = 32;
    
    private OptionMenu selected = new BaseOption();
    
    public OptionsDisplayer() {
        
    }
    
    public void display(Graphics2D g2d, int width, int height) {
        g2d.setFont(new Font("Purisa", Font.PLAIN, 22));
        
        g2d.setColor(new Color(0,0,0, 196));
        g2d.fillRect(0, height-HEIGHT, width, HEIGHT);
        
        String total = "";
        boolean first = true;
        for (String part : selected.getRender()) {
            if (first) {
                total+=part;
            }
            else {
                total+=" | "+part;
            }
            
            first = false;
        }
        
        g2d.setColor(Color.RED);
        g2d.drawString(total, 0, height-HEIGHT+20);
    }
}
