package uk.co.hobnobian.chips.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import uk.co.hobnobian.chips.game.server.Map;

public class MapFileSaver {
	public static void saveMap(Map m, File f) {
		try {
			FileOutputStream fileOut = new FileOutputStream(f);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
	        objectOut.writeObject(m);
	        objectOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
