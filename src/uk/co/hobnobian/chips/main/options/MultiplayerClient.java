package uk.co.hobnobian.chips.main.options;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import uk.co.hobnobian.chips.main.Main;
import uk.co.hobnobian.chips.main.ConnectionLayers.GraphicsServerLayer;
import uk.co.hobnobian.chips.main.client.Renderer;
import uk.co.hobnobian.chips.main.client.Window;
import uk.co.hobnobian.chips.main.multiplayer.GameHandler;
import uk.co.hobnobian.chips.main.multiplayer.MapReader;
import uk.co.hobnobian.chips.main.server.Game;
import uk.co.hobnobian.chips.main.server.GameVariables;
import uk.co.hobnobian.chips.main.server.Map;

public class MultiplayerClient extends JFrame implements MouseListener{
	private static final long serialVersionUID = -1575267924670350536L;
	
	private JTextField host;
	private JTextField port;

	public MultiplayerClient() {
		host = new JTextField();
		host.setPreferredSize(new Dimension(200,30));
		host.setText("localhost");
		port = new JTextField();
		port.setPreferredSize(new Dimension(60,30));
		port.setText("1234");
		
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		
		setTitle("Join Game");
		
		con.gridx = 0;
		con.gridy = 0;
		
		con.gridwidth = 2;
		add(new JLabel("Multiplayer"), con);
		
		con.gridy = 1;
		con.gridwidth = 1;
		add(new JLabel("Host name:"), con);
		
		con.gridx = 1;
		add(host, con);
		
		
		con.gridy = 2;
		con.gridx = 0;
		add(new JLabel("Port:"), con);
		
		con.gridx = 1;
		add(port, con);
		
		
		JButton ok = new JButton("CONNECT");
		ok.addMouseListener(this);
		con.gridy = 3;
		con.gridx = 0;
		con.gridwidth = 2;
		add(ok, con);
		
		
		pack();
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String h = host.getText();
		try {
			int p = Integer.parseInt(port.getText());
			if (p > 65535 || p < 1) {
				throw new InvalidPortException();
			}
			
			
			Socket s = new Socket(h, p);
			Window w = new Window();
			GraphicsServerLayer l = new GraphicsServerLayer();
			Image i;
			try {
				i = ImageIO.read(Main.class.getResource("/uk/co/hobnobian/chips/assets/air.png")).getScaledInstance(64, 64, Image.SCALE_DEFAULT);;
			} catch (IOException er) {
				s.close();
				return;
			}
			Renderer r = new Renderer(l, i, w);
			w.add(r);
			
			GameVariables vars = new GameVariables();
			Game g = new Game(l, vars);
			
//			if (!new ProtocolCheckState(s, true).check()) {
//				System.out.println("Invalid protocol");
//				return;
//			}
			Map m = new MapReader(s).getMap();
			
			GameHandler handler = new GameHandler(s, g, true);
			
			g.setMap(m);
			new Thread(new Runnable() {

				@Override
				public void run() {
					handler.start();
				}
				
			}).start();
			w.setup();
			g.update();
			g.start();
			dispose();
			
		}
		catch (InvalidPortException e1) {
			new ErrorWindow("Invalid port number!");
			return;
		}
		catch(NumberFormatException n) {
			new ErrorWindow("Invalid port number!");
			return;
		} catch (UnknownHostException e1) {
			new ErrorWindow("Unknown host - "+h);
		} catch (IOException e1) {
			new ErrorWindow("I/O Error");
		}
		
		
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
}
