package uk.co.hobnobian.chips.game.backend;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayerInventory implements Iterable<InventoryItem>{
	private ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
	
	public PlayerInventory() {
		
	}
	
	public boolean contains(InventoryItem i) {
		return items.contains(i);
	}
	
	public boolean contains(Class<?extends InventoryItem> i) {
        for (InventoryItem item : items) {
            if (item.getClass().equals(i)) {
                return true;
            }
        }
        return false;
    }
	
	public void add(InventoryItem i) {
		items.add(i);
	}
	
	public void removeAll(InventoryItem i) {
		for (int index = 0; index < items.size(); index++) {
			if (items.get(index) == i) {
				items.remove(index);
				index--;
			}
		}
	}
	
	public void remove(InventoryItem i) {
		for (int index = 0; index < items.size(); index++) {
			if (items.get(index) == i) {
				items.remove(index);
				return;
			}
		}
	}
	
	public void clear() {
		items.clear();
	}

    @Override
    public Iterator<InventoryItem> iterator() {
        return items.iterator();
    }
}
