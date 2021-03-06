package uk.co.hobnobian.chips.game.options;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import uk.co.hobnobian.chips.game.backend.Map;
import uk.co.hobnobian.chips.game.multiplayer.MapDataIO;

public class MapChooser{
	private JFileChooser files;
	private JComponent c;
	
	public Map choose(JComponent comp) {
		files = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Chips2 Map Files - *.chips", "chips");
		files.setFileFilter(filter);
		if (StartupMenu.pathToLastMap != null) {
			files.setSelectedFile(StartupMenu.pathToLastMap);
		}
		c = comp;
		
		return go();
		
	}
	private Map go() {
		int result = files.showOpenDialog(c);
		if (result == JFileChooser.APPROVE_OPTION) {
			File f = files.getSelectedFile();
			if (!f.canRead()) {
				new ErrorWindow("No read permission on file");
				return null;
			}
			try {
				FileInputStream fileIn = new FileInputStream(f);
				
				byte[] sizebytes = new byte[4];
				fileIn.read(sizebytes);
				
				int size = ByteBuffer.wrap(sizebytes).getInt();
				byte[] raw = new byte[size];
				
				for (int i = 0; i < size; i++) {
				    raw[i] = (byte)fileIn.read();
				}
				
				fileIn.close();
				
				Map m = MapDataIO.decodeBytes(raw);
				return m;
			} 
			catch (FileNotFoundException e) {
				new ErrorWindow("File Not Found");
			} catch (IOException e) {
				new ErrorWindow("Input output error");
			}
			catch(RuntimeException e) {
				new ErrorWindow("Invalid file format");
			}
		}
		else {
		}
		
		return null;
	}
}
