package uk.co.hobnobian.chips.editor.options;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import uk.co.hobnobian.chips.editor.Editor;

public class OptionsDisplayer implements KeyListener{
    private static final int HEIGHT = 32;
    
    OptionMenu selected = new BaseOption();
    
    public OptionsDisplayer(Editor editor) {
        editor.getWindow().addKeyListener(this);
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
        int textwidth = g2d.getFontMetrics().stringWidth(total);
        
        int fontsize = 22;
        while (textwidth > width) {
            g2d.setFont(new Font("Purisa", Font.PLAIN, fontsize));
            textwidth = g2d.getFontMetrics().stringWidth(total);
        }
        
        int startx = (width-textwidth)/2;
        
        g2d.setColor(Color.RED);
        g2d.drawString(total, startx, height-HEIGHT+20);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == '.') {
            
        }
        else if (c == ',') {
            
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
}
