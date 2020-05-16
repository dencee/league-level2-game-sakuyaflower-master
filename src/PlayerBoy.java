import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class PlayerBoy extends GameObject {
	public static BufferedImage boy;
	public static boolean needImage = true;
	public static boolean gotImage = false;
	

	PlayerBoy(int x, int y, int width, int height) {
		super(x, y, width, height);
		super.speed = 0; 
		if (needImage) {
			loadImage("boy.png");
		}
	}

	void draw(Graphics g) {
		
		if (gotImage) {
		    //
		    // Moved drawn image to center on collision box
		    //
			g.drawImage(boy, x - width, y, width*3, height*3, null);
		} else {
			g.setColor(Color.BLUE);
			g.drawRect(x, y, width, height );
		}
	}

	void loadImage(String imageFile) {
		if (needImage) {
			try {
				boy = ImageIO.read(this.getClass().getResourceAsStream("boy.png"));
				gotImage = true;
			} catch (Exception e) {

			}
			needImage = false;
		}
	}
	
	public void update() {
	    x += speed;
	    
	    super.update();
	}
	
	public void right() {
		speed = 6;
	}

	public void left() {
		speed = -6;
	}

}

