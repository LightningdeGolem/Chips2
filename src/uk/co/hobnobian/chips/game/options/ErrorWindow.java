package uk.co.hobnobian.chips.game.options;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ErrorWindow extends JFrame implements MouseListener{
	private static final long serialVersionUID = 57184210676401715L;
	
	
	public ErrorWindow(String msg) {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		
		JButton b = new JButton("Close");
		b.addMouseListener(this);
		
		p.add(new JLabel(msg));
		p.add(b);
		
		add(p);
		pack();
		setTitle("Error!!!");
		setVisible(true);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		dispose();
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
