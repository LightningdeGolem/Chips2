package uk.co.hobnobian.chips.game.options;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.co.hobnobian.chips.game.backend.Game;
import uk.co.hobnobian.chips.game.backend.GameVariables;
import uk.co.hobnobian.chips.game.backend.GraphicsServerLayer;
import uk.co.hobnobian.chips.game.backend.Map;
import uk.co.hobnobian.chips.game.display.Renderer;
import uk.co.hobnobian.chips.main.Main;
import uk.co.hobnobian.chips.main.Window;

public class SinglePlayer extends JFrame implements MouseListener{
	private static final long serialVersionUID = 7789616312651236079L;
	private JPanel panel;
	
	private Map m;
	
	public SinglePlayer() {
		panel = new JPanel();
		add(panel);
		m = new MapChooser().choose(panel);
		if (m == null) {
			dispose();
			return;
		}
		
		JButton button = new JButton("Start");
		button.addMouseListener(this);
		button.setSize(256,256);
		panel.add(button);
		setSize(256,256);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void begin() {
		dispose();
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
		Game g = new Game(l,m, vars);
		w.setup();
		g.update();
		g.start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		begin();
		
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
