package uk.co.hobnobian.chips.main.server;

public class BlockInfo {
	private int[] data;
	
	public BlockInfo(int fields) {
		data = new int[fields];
	}
	
	public void set(int index, int data) {
		this.data[index] = data;
	}
	
	public int get(int index) {
		return data[index];
	}
}
