import javax.swing.JFrame;

public class Puzzle {
public static final int WIDTH = 800;
public static final int HEIGHT = 1000;
GamePanel game = new GamePanel();
	public static void main(String[] args) {
	Puzzle p = new Puzzle();
	p.setup();

	}
	void setup() {	
		JFrame JF = new JFrame();
		JF.setSize(WIDTH, HEIGHT);
		JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JF.setVisible(true);
		JF.add(game);
		JF.addKeyListener(game);
	}
	}


