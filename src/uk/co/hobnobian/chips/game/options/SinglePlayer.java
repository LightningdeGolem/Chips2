package uk.co.hobnobian.chips.game.options;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		panel.setLayout(new GridLayout(0,1));
		add(panel);
		m = new MapChooser().choose(panel);
		if (m == null) {
			dispose();
			StartupMenu.main_menu.setVisible(true);
			return;
		}
		
		JButton button = new JButton("Start");
		button.addMouseListener(this);
		setTitle("Singleplayer - "+m.title);
		
		panel.add(new JLabel("Map Title: "+m.title));
		panel.add(new JLabel("Map Author: "+m.author));
		panel.add(button);
		pack();
		
		
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent event) {
		        StartupMenu.main_menu.setVisible(true);
		        dispose();
		    }
		});
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
