package uk.co.hobnobian.chips.game.backend;

import java.util.ArrayList;

public class PlayerInventory {
	private ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
	
	public PlayerInventory() {
		
	}
	
	public boolean contains(InventoryItem i) {
		return items.contains(i);
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
}
