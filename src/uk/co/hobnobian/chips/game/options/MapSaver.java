package uk.co.hobnobian.chips.game.options;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import uk.co.hobnobian.chips.game.backend.Map;
import uk.co.hobnobian.chips.game.multiplayer.MapDataIO;

public class MapSaver{
	private JFileChooser files;
	private JComponent c;
	
	public void save(JComponent comp, Map toSave) {
		files = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Chips2 Map Files - *.chips", "chips");
		files.setFileFilter(filter);
		if (StartupMenu.pathToLastMap != null) {
			files.setSelectedFile(StartupMenu.pathToLastMap);
		}
		c = comp;
		
		go(toSave);
	}
	private void go(Map m) {
		int result = files.showOpenDialog(c);
		if (result == JFileChooser.APPROVE_OPTION) {
			File f = files.getSelectedFile();
//			if (!f.canWrite()) {
//				new ErrorWindow("No write permission on file");
//			}
			try {
				FileOutputStream fileOut = new FileOutputStream(f);
				byte[] data = MapDataIO.mapToBytes(m);
				
				byte[] size = ByteBuffer.allocate(4).putInt(data.length).array();
				
				fileOut.write(size);
				fileOut.write(data);
				fileOut.flush();
				fileOut.close();
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
	}
}

