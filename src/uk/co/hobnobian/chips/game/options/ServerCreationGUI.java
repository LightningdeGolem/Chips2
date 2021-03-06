package uk.co.hobnobian.chips.game.options;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import uk.co.hobnobian.chips.game.backend.Game;
import uk.co.hobnobian.chips.game.backend.GameVariables;
import uk.co.hobnobian.chips.game.backend.GraphicsServerLayer;
import uk.co.hobnobian.chips.game.backend.Map;
import uk.co.hobnobian.chips.game.display.Renderer;
import uk.co.hobnobian.chips.game.multiplayer.Connection;
import uk.co.hobnobian.chips.game.multiplayer.GameHandler;
import uk.co.hobnobian.chips.game.multiplayer.MapSender;
import uk.co.hobnobian.chips.game.multiplayer.ProtocolCheckState;
import uk.co.hobnobian.chips.main.Main;
import uk.co.hobnobian.chips.main.Window;

public class ServerCreationGUI extends JFrame implements MouseListener, Runnable{
	private static final long serialVersionUID = -7480438566638829610L;
	JLabel status;
	JButton cancel;
	
	Map map;
	
	ServerSocket server;
	
	public ServerCreationGUI(ServerSocket s, Map m) {
		setLayout(new FlowLayout());
		map = m;
		setTitle("Chips Server");
		
		status = new JLabel("Listening for connections");
		cancel = new JButton("STOP!");
		cancel.addMouseListener(this);
		
		server = s;
		
		add(status);
		add(cancel);
		
		pack();
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent event) {
		        mouseClicked(null);
		    }
		});
		
		setVisible(true);
	}
	
	public void start() {
		try {
			Socket s;
			while (true) {
				s = server.accept();
				DoYouAcceptWindow w = new DoYouAcceptWindow(s.getInetAddress().getHostName());
				short decided = w.getDecided();
				while (decided == 0) {
					decided = w.getDecided();
				}
				w.dispose();
				if (w.getDecided() == 1) {
					s.close();
				}
				else {
					break;
				}
			}
			server.close();
			Image i;
			try {
				i = ImageIO.read(Main.class.getResource("/uk/co/hobnobian/chips/assets/air.png")).getScaledInstance(64, 64, Image.SCALE_DEFAULT);;
			} catch (IOException e1) {
				return;
			}
			
			Window wi = new Window();
			GraphicsServerLayer l = new GraphicsServerLayer();
			
			
			Renderer r = new Renderer(l, i,wi);
			wi.add(r);
			
			GameVariables vars = new GameVariables();
			Game g = new Game(l,map, vars);
			
			
			Connection con = new Connection(s);
			
			if (!new ProtocolCheckState(con, false).check()) {
				new ErrorWindow("Protocol mismatch");
				return;
			}
			
			new MapSender(map, con);
			
			GameHandler handler = new GameHandler(con, g,false);
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					handler.start();
				}
			}).start();
			
			
			dispose();
			wi.setup();
			g.update();
			g.start();
			
		}
		catch(SocketException e) {}
		catch (IOException e) {
			e.printStackTrace();
			new ErrorWindow("I/O Error");
			dispose();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			server.close();
		} catch (IOException e1) {}
		dispose();
		StartupMenu.main_menu.setVisible(true);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		start();
		
	}
}
