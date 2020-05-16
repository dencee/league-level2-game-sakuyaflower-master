import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	public static BufferedImage imageBackground;
	public static BufferedImage image;
	public static BufferedImage pc;
	
	public static boolean needImage = true;
	public static boolean gotImage = false;

	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;
	int currentState = MENU;
	Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
	Font smallerFont = new Font("Times New Roman", Font.PLAIN, 30);
	Timer fruitSpawn;
	PlayerBoy boy = new PlayerBoy(400, 800, 50, 50);
	ObjectManager manager = new ObjectManager(boy);
	
	//
	// Fruits order
	//
	static String[] fruitOrder = { "Strawberry", "Tomato", "Apple", "Yellow Nectarine", "Honeydew", "Pear", "Pineapple", "Lemon", "Elderberry" };

	public void paintComponent(Graphics g) {
	    super.paintComponent( g );
	    
		if (currentState == MENU) {
			drawMenuState(g);
		} else if (currentState == GAME) {
			drawGameState(g);
		} else if (currentState == END) {
			drawEndState(g);
		}
	}

	void startGame() {
		fruitSpawn = new Timer(1000, manager);
		fruitSpawn.start();
		Timer frameDraw = new Timer(1000 / 60, this);
		frameDraw.start();
	}

	void endGame() {
		fruitSpawn.stop();
		currentState = END;
	}

	GamePanel() {
		if (needImage) {
			loadImage("boy.png");
		}
	}

	JPanel JP = new JPanel();

	void updateMenuState() {

	}

	void updateGameState() {
		manager.update();
		if (boy.isActive == false) {
			endGame();
		}
	}

	void updateEndState() {

	}

	void drawMenuState(Graphics g) {
		g.drawImage(imageBackground, 0, 0, Puzzle.WIDTH, Puzzle.HEIGHT, null);
		g.setFont(titleFont);
		g.setColor(Color.WHITE);
		g.drawString("A Twist", 280, 200);
		g.setFont(smallerFont);
		g.setColor(Color.WHITE);
		g.drawString("Press ENTER to start", 255, 400);
		g.setFont(smallerFont);
		g.setColor(Color.WHITE);
		g.drawString("Press SPACE for instructions", 230, 600);
	}

	void drawGameState(Graphics g) {
		g.drawImage(pc, 0, 0, Puzzle.WIDTH, Puzzle.HEIGHT, null);
		manager.draw(g);
		

        //
        // Score
        //
	    Font font = new Font( "Arial", Font.BOLD, 30 );
	    g.setFont( font );
	    g.setColor( Color.WHITE );
		g.drawString("score:" + manager.getScore(), 20, 50);

        //
        // Next fruit
        //
		font = new Font( "Arial", Font.BOLD, 50 );
		g.setFont( font );
		g.setColor( Color.BLACK );
		String fruit = fruitOrder[ manager.getNextFruit() ];
	    g.drawString( fruit, Puzzle.WIDTH / 2 - (fruit.length() * 10), 100 );
	}

	void drawEndState(Graphics g) {
		g.drawImage(imageBackground, 0, 0, Puzzle.WIDTH, Puzzle.HEIGHT, null);
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("You Won The Game", 100, 300);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			JOptionPane.showMessageDialog(null,
			"Using your arrow keys to move, catch all the fruits in order in which is being called. ");
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == END) {
				endGame();
				currentState = MENU;
			} else {
				currentState++;
				if (currentState == GAME) {
				    //
				    // print fruit order instructions
				    //
				    String instructions = "Collect these fruits in this order: " + String.join( ", ", fruitOrder );
					JOptionPane.showMessageDialog(null, instructions );
				}
				startGame();
			}
		}
	
		

		if (e.getKeyCode() == KeyEvent.VK_LEFT && boy.x >= 0) {
			boy.left();
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT && boy.x <= 750) {
			boy.right();

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	    boy.speed = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (currentState == MENU) {
			updateMenuState();
		} 
		else if (currentState == GAME) {
			updateGameState();
		}
		else if (currentState == END) {
			updateEndState();
		}
		
		repaint();
		manager.update();
	}

	void loadImage(String imageFile) {
		if (needImage) {
			try {
				imageBackground = ImageIO.read(this.getClass().getResourceAsStream("galaxy.jpg"));
				image = ImageIO.read(this.getClass().getResourceAsStream("foresy.jpg"));
				pc = ImageIO.read(this.getClass().getResourceAsStream("pc.jpg"));	
			} catch (Exception e) {

			}
			needImage = false;
		}
	}
}
