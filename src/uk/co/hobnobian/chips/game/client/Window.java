package uk.co.hobnobian.chips.game.client;

import javax.swing.JFrame;

public class Window extends JFrame{
	private static final long serialVersionUID = 2518130250429353741L;
	public Window() {
		this.setSize(448,448);
	}
	public void setup() {
		this.setVisible(true);
		this.setTitle("Chips Challenge 2");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
