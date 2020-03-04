package uk.co.hobnobian.chips.main.options;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StartupMenu extends JFrame implements MouseListener{
	private static final long serialVersionUID = 6005122172405513856L;
	
	public static File pathToLastMap = null;
	
	private JButton join;
	private JButton singleplayer;
	private JButton host;
	
	public StartupMenu() {
		setTitle("Chips Challenge 2");
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		Font titleFont = new Font("Sans-serif", 0, 40);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		JLabel jlabel = new JLabel("Chips Challenge 2");
		jlabel.setFont(titleFont);
		add(jlabel, c);
		
		c.gridy = 1;
		
		c.gridwidth = 1;
		
		c.gridx = 0;
		singleplayer = new JButton("SINGLEPLAYER");
		singleplayer.addMouseListener(this);
		singleplayer.setIcon(new ImageIcon(getClass().getResource("/uk/co/hobnobian/chips/assets/player_s.png")));
		add(singleplayer, c);
		
		c.gridx = 1;
		join = new JButton("JOIN A SERVER");
		join.addMouseListener(this);
		join.setIcon(new ImageIcon(getClass().getResource("/uk/co/hobnobian/chips/assets/multiplayer.png")));
		add(join, c);
		
		c.gridx = 2;
		host = new JButton("HOST A SERVER");
		host.addMouseListener(this);
		host.setIcon(new ImageIcon(getClass().getResource("/uk/co/hobnobian/chips/assets/multiplayer.png")));
		add(host, c);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getComponent().equals(singleplayer)) {
			new SinglePlayer();
		}
		else if (e.getComponent().equals(join)){
			new MultiplayerClient();
		}
		else if (e.getComponent().equals(host)) {
			new MultiplayerServer();
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
