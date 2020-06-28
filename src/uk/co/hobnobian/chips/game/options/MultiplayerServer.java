package uk.co.hobnobian.chips.game.options;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.co.hobnobian.chips.game.backend.Map;

public class MultiplayerServer extends JFrame implements MouseListener{
	private static final long serialVersionUID = -1569000305549814972L;
	JTextField port;
	Map map;
	
	public MultiplayerServer() {
		JPanel p = new JPanel();
		add(p);
		map = new MapChooser().choose(p);
		if (map == null) {
			dispose();
			StartupMenu.main_menu.setVisible(true);
			return;
		}
		
		port = new JTextField();
		port.setPreferredSize(new Dimension(60,30));
		port.setText("1234");
		
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		
		setTitle("Create Game");
		
		con.gridx = 0;
		con.gridy = 0;
		
		con.gridwidth = 2;
		add(new JLabel("Multiplayer"), con);
		
		con.gridy = 1;
		add(new JLabel("Map Name: "+map.title), con);
		
		
		con.gridy = 2;
		add(new JLabel("Map Author: "+map.author), con);
		con.gridwidth = 1;
		
		con.gridy = 3;
		con.gridx = 0;
		add(new JLabel("Port:"), con);
		
		con.gridx = 1;
		add(port, con);
		
		
		
		JButton ok = new JButton("CREATE");
		ok.addMouseListener(this);
		con.gridy = 4;
		con.gridx = 0;
		con.gridwidth = 2;
		add(ok, con);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent event) {
		        StartupMenu.main_menu.setVisible(true);
		        dispose();
		    }
		});
		
		
		pack();
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			int p = Integer.parseInt(port.getText());
			if (p > 65535 || p < 1) {
				throw new InvalidPortException();
			}
			ServerSocket server = new ServerSocket(p);
			
			ServerCreationGUI scg = new ServerCreationGUI(server, map);
			new Thread(scg).start();
			
			dispose();
		}
		catch(NumberFormatException e1) {
			new ErrorWindow("Invalid port");
		}
		catch(InvalidPortException e1) {
			new ErrorWindow("Invalid port");
		} catch (IOException e1) {
			new ErrorWindow("Could not create server - I/O Exception");
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
