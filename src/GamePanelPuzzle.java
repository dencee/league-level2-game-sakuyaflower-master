import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanelPuzzle extends JPanel implements ActionListener, KeyListener {
	public static BufferedImage imageBackground;
	public static BufferedImage image;
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

	public void paintComponent(Graphics g) {

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

	GamePanelPuzzle() {
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
		
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, Puzzle.WIDTH, Puzzle.HEIGHT);
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("A Twist", 280, 200);
		g.setFont(smallerFont);
		g.setColor(Color.YELLOW);
		g.drawString("Press ENTER to start", 250, 400);
		g.setFont(smallerFont);
		g.setColor(Color.YELLOW);
		g.drawString("Press SPACE for instructions", 230, 600);
	}

	void drawGameState(Graphics g) {

		if (gotImage) {
			g.drawImage(imageBackground, 0, 0, Puzzle.WIDTH, Puzzle.HEIGHT, null);
		} else {
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, Puzzle.WIDTH, Puzzle.HEIGHT);
		}
		g.setColor(Color.WHITE);
		g.drawString("score:" + manager.getScore(), 50, 50);
	}

	void drawEndState(Graphics g) {
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("You Won The Game", 400, 300);
	}

	void loadImage(String imageFile) {
		if (needImage) {
			try {
				image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
				imageBackground = ImageIO.read(this.getClass().getResourceAsStream("galaxy.jpg"));
				gotImage = true;
			} catch (Exception e) {

			}
			needImage = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (currentState == END) {
				endGame();
				currentState = MENU;
				boy = new PlayerBoy(400, 800, 50, 50);
				manager = new ObjectManager(boy);
			}
			if (e.getKeyCode() == KeyEvent.VK_UP&& boy.y >= 0) {
				boy.down();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN && boy.y <= 700) {
				boy.up();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT && boy.x >= 0) {
				boy.left();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT && boy.x <= 450) {
				boy.right();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (currentState == MENU) {
			updateMenuState();
		} else if (currentState == GAME) {
			updateGameState();

		} else if (currentState == END) {
			updateEndState();

		}
	repaint();
	}
}
