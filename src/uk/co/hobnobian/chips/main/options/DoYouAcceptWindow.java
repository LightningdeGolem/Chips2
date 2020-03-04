package uk.co.hobnobian.chips.main.options;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DoYouAcceptWindow extends JFrame implements MouseListener{
	private static final long serialVersionUID = 7505717310114001579L;
	private volatile short d = 0;
	private JButton accept;
	private JButton decline;
	 
	public DoYouAcceptWindow(String hostname) {
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		
		con.gridwidth = 2;
		add(new JLabel("Do you accept this connection?"), con);
		con.gridy = 1;
		add(new JLabel("Hostname: "+hostname), con);
		con.gridwidth = 1;
		con.gridy = 2;
		
		decline = new JButton("Decline");
		decline.addMouseListener(this);
		add(decline, con);
		con.gridx = 1;
		accept = new JButton("Accept");
		accept.addMouseListener(this);
		add (accept, con);
		
		pack();
		setVisible(true);
	}
	
	public short getDecided() {
		return d;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(decline)) {
			d = 1;
		}
		else if (e.getSource().equals(accept)) {
			d = 2;
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
