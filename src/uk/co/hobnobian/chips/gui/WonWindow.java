package uk.co.hobnobian.chips.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import uk.co.hobnobian.chips.game.options.StartupMenu;

public class WonWindow extends JFrame implements MouseListener {
	private static final long serialVersionUID = -5345728043960766581L;
	
	
	public WonWindow() {
		setLayout(new GridLayout(0, 1));
		Font title = new Font("Sans-serif", 40 ,40);
		
		JLabel yw = new JLabel("YOU WON!!!");
		yw.setFont(title);
		add(yw);
		
		JButton ok = new JButton("Back to main menu");
		ok.addMouseListener(this);
		add(ok);
		
		pack();
		setTitle("YOU WON!");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        exit();
		    }
		});
	}
	
	private void exit() {
		dispose();
		StartupMenu.main_menu.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		exit();
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
