package uk.co.hobnobian.chips.editor;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageCache {
	private HashMap<String, Image> cache = new HashMap<String, Image>();
	
	public Image loadImage(String path, int size) {
		if (cache.containsKey(path)) {
//			return cache.get(path).getScaledInstance(size, size, Image.SCALE_DEFAULT);
			return cache.get(path);
		}
		else {
			Image i = loadImageFile(path);
			cache.put(path, i.getScaledInstance(size, size, Image.SCALE_DEFAULT));
			return i.getScaledInstance(size, size, Image.SCALE_DEFAULT);
		}
	}
	
	private Image loadImageFile(String path) {
		path = "/uk/co/hobnobian/chips/assets/"+path;
		try {
			Image i = ImageIO.read(getClass().getResource(path));
			return i;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
