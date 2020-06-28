package uk.co.hobnobian.chips.main;

import java.awt.Image;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import uk.co.hobnobian.chips.editor.Editor;
import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.EditableMap;
import uk.co.hobnobian.chips.game.backend.Game;
import uk.co.hobnobian.chips.game.backend.GameVariables;
import uk.co.hobnobian.chips.game.backend.GraphicsServerLayer;
import uk.co.hobnobian.chips.game.backend.Map;
import uk.co.hobnobian.chips.game.blocks.Button;
import uk.co.hobnobian.chips.game.blocks.ConveyorE;
import uk.co.hobnobian.chips.game.blocks.ConveyorN;
import uk.co.hobnobian.chips.game.blocks.ConveyorS;
import uk.co.hobnobian.chips.game.blocks.ConveyorW;
import uk.co.hobnobian.chips.game.blocks.ElectricDoor;
import uk.co.hobnobian.chips.game.blocks.FakeAir;
import uk.co.hobnobian.chips.game.blocks.FakeWall;
import uk.co.hobnobian.chips.game.blocks.Fire;
import uk.co.hobnobian.chips.game.blocks.FireBootsItem;
import uk.co.hobnobian.chips.game.blocks.Ice;
import uk.co.hobnobian.chips.game.blocks.IceBootsItem;
import uk.co.hobnobian.chips.game.blocks.Lamp;
import uk.co.hobnobian.chips.game.blocks.Lever;
import uk.co.hobnobian.chips.game.blocks.NoItem;
import uk.co.hobnobian.chips.game.blocks.NormalWire;
import uk.co.hobnobian.chips.game.blocks.RedKey;
import uk.co.hobnobian.chips.game.blocks.RedKeyhole;
import uk.co.hobnobian.chips.game.blocks.Rocket;
import uk.co.hobnobian.chips.game.blocks.SolidWire;
import uk.co.hobnobian.chips.game.blocks.Wall;
import uk.co.hobnobian.chips.game.blocks.WinningBlock;
import uk.co.hobnobian.chips.game.blocks.Zapper;
import uk.co.hobnobian.chips.game.display.Renderer;
import uk.co.hobnobian.chips.game.options.StartupMenu;

public class Main {
	public static final int protocolID = 1;
	public static final int defaultPort = 1234;
	
	public static void main(String[] args) {
		Block.setup();
		
		if (args.length > 0 && args[0].equalsIgnoreCase("--test")) {
		    Window w = new Window();
            GraphicsServerLayer l = new GraphicsServerLayer();
              
            Image i;
            try {
            i = ImageIO.read(Main.class.getResource("/uk/co/hobnobian/chips/assets/air.png")).getScaledInstance(64, 64, Image.SCALE_DEFAULT);;
            } catch (IOException e) {
            return;
            }
              
            Renderer r = new Renderer(l, i,w);
            w.add(r);
              
              
            GameVariables vars = new GameVariables();
            Game g = new Game(l,getMap(), vars);
            w.setup();
            g.update();
            g.start();
		}
		else if (args.length > 0 && args[0].equalsIgnoreCase("--editor")) {
			new Editor();
		}
		else {
		    StartupMenu.main_menu = new StartupMenu();
		}
		
	}
	
	private static Map getMap() {
	    EditableMap m = new EditableMap();
        
	    m.setBlock(2, 0, new WinningBlock());
	    m.setBlock(4, 0, new WinningBlock());
	    
        m.setBlock(2, 2, new ConveyorE());
        m.setBlock(4, 2, new ConveyorN());
        m.setBlock(6, 2, new ConveyorS());
        m.setBlock(8, 2, new ConveyorW());
        
        for (int y = 4; y < 7; y++) {
            for (int x = 2; x < 7; x++) {
                m.setBlock(x, y, new Ice());
            }
        }
        
        m.setBlock(2, 8, new FakeAir());
        m.setBlock(4, 8, new FakeWall());
        
        m.setBlock(3, 9, new Button());
        m.setBlock(2, 10, new Lever());
        m.setBlock(3, 10, new NormalWire());
        m.setBlock(4, 10, new NormalWire());
        m.setBlock(5, 10, new Lamp());
        
        m.setBlock(4, 11, new NormalWire());
        m.setBlock(4, 12, new ElectricDoor());
        
        m.setBlock(3, 11, new SolidWire());
        m.setBlock(3, 12, new SolidWire());
        m.setBlock(3, 13, new SolidWire());
        m.setBlock(3, 14, new Zapper());
        
        m.setBlock(2, 16, new IceBootsItem());
        m.setBlock(3, 16, new FireBootsItem());
        
        m.setBlock(2, 18, new Fire());
        m.setBlock(2, 20, new NoItem());
        
        m.setBlock(8, 22, new Rocket());
        m.setBlock(1, 22, new Wall());
        
        m.setBlock(2, 24, new RedKeyhole());
        m.setBlock(4, 24, new RedKeyhole());
        m.setBlock(2, 26, new RedKey());
        m.setBlock(4, 26, new RedKey());
        return m;
	}
	
	private static void saveMap() {
		Map m = getMap();
		
		
        try {
        	FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.home")+"/map.chips");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(m);
			objectOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
