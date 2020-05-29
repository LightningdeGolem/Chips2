package uk.co.hobnobian.chips.editor;

import java.util.ArrayList;
import java.util.List;

public class Selection {
    private List<int[]> line = new ArrayList<int[]>();
    private int[] area = null;
    
    private boolean hasSelectedArea = true;
    
    public Selection() {
        
    }
    
    public void mouseMoved(int x, int y) {
        if (!line.contains(new int[] {x,y})) {
            line.add(new int[] {x,y});
        }
        if (area == null) {
            area = new int[]{x, y, x,y};
        }
        else {
            area[2] = x;
            area[3] = y;
        }
    }
    
    public List<int[]> getDragSelection(){
        return line;
    }
    
    public List<int[]> getAreaSelection(){
        ArrayList<int[]> toreturn = new ArrayList<int[]>();
        
        int bigx;
        int smallx;
        int bigy;
        int smally;
        
        if (area[0] < area[2]) {
            bigx = area[2];
            smallx = area[0];
        }
        else {
            bigx = area[0];
            smallx = area[2];
        }
        
        if (area[1] < area[3]) {
            bigy = area[3];
            smally = area[1];
        }
        else {
            bigy = area[1];
            smally = area[3];
        }
        
        for (int y = smally; y <= bigy; y++) {
            for (int x = smallx; x<=bigx;x++) {
                toreturn.add(new int[] {x,y});
            }
        }
        return toreturn;
        
    }

	public boolean hasSelectedArea() {
		return hasSelectedArea;
	}

	public void setHasSelectedArea(boolean hasSelectedArea) {
		this.hasSelectedArea = hasSelectedArea;
	}
}
