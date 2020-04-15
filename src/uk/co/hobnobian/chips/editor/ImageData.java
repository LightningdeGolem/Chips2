package uk.co.hobnobian.chips.editor;

public class ImageData {
	private String path;
	private int size;
	
	public ImageData(String path, int size) {
		this.path = path;
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public int getSize() {
		return size;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ImageData) {
			return ((ImageData) o).path.equals(path) && ((ImageData)o).size == size;
		}
		else {
			return super.equals(o);
		}
	}
	
	@Override
	public String toString() {
		return "Image Data = "+path+" at "+size+" x "+size;
	}
}
