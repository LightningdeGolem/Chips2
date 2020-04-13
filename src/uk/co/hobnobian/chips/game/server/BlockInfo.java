package uk.co.hobnobian.chips.game.server;

public class BlockInfo {
	private int[] data;
	
	public BlockInfo(int fields) {
		data = new int[fields];
	}
	
	public BlockInfo(int[] d) {
		data = d;
	}
	
	public void set(int index, int data) {
		this.data[index] = data;
	}
	
	public int get(int index) {
		return data[index];
	}

	public int[] getArray() {
		return data;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof BlockInfo) {
			int[] data = ((BlockInfo) o).data;
			return data.equals(this.data);
		}
		else if (o instanceof int[]) {
			int[] data = (int[]) o;
			return data.equals(this.data);
		}
		else {
			return super.equals(o);
		}
	}
}
