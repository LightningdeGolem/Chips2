package uk.co.hobnobian.chips.game.backend;

import java.io.Serializable;

public class BlockInfo implements Serializable{
	private static final long serialVersionUID = -7081690354630842514L;
	private int[] data;
	
	public BlockInfo(int fields) {
		data = new int[fields];
	}
	
	public BlockInfo(int[] d) {
		data = d;
	}
	
	public BlockInfo(BlockInfo info) {
		if (info == null) {
			throw new RuntimeException("HERE!");
//			return;
		}
		data = new int[info.data.length];
		for (int i = 0; i < info.data.length; i++) {
			data[i] = info.data[i];
		}
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
	
	public String toString() {
	    String r = "BlockInfo: [";
	    for (int part : data) {
	        r+=part+",";
	    }
	    r = r.substring(0, r.length()-1);
	    r+="]";
	    return r;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof BlockInfo) {
			int[] data = ((BlockInfo) o).data;
			if (data.length != this.data.length) {
			    return false;
			}
			for (int i = 0; i<data.length; i++) {
			    if (data[i] != this.data[i]) {
			        return false;
			    }
			}
			return true;
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
