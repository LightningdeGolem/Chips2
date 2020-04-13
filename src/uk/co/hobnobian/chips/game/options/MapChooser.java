package uk.co.hobnobian.chips.game.options;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import uk.co.hobnobian.chips.game.server.Map;

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
				ObjectInputStream ois = new ObjectInputStream(fileIn);
				Object o  = ois.readObject();
				ois.close();
				Map m = (Map) o;
				return m;
			} 
			catch(StreamCorruptedException e) {
				new ErrorWindow("File format invalid - have you got the correct file?");
			}
			catch (FileNotFoundException e) {
				new ErrorWindow("File Not Found");
			} catch (ClassNotFoundException e) {
				new ErrorWindow("File format invalid - have you got the correct file? (classnotfound)");
			} catch (IOException e) {
				new ErrorWindow("Input output error");
			}
		}
		else {
		}
		
		return null;
	}
}
