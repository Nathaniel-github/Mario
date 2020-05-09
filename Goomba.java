import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Goomba implements Sprite{
	
	// Fields
	private int XCORD;
	private int YCORD;
	private ImageIcon IMAGEICON;
	private Image IMAGE;
	private int frame = 1;
	private int direction = 1;
	private boolean killed = false;
	private ImageIcon goomba1 = new ImageIcon(getClass().getClassLoader().getResource("EnemySpriteImages/Goomba.png"));
	private ImageIcon goomba2 = new ImageIcon(getClass().getClassLoader().getResource("EnemySpriteImages/Goomba2.png"));
	private ImageIcon dead = new ImageIcon(getClass().getClassLoader().getResource("EnemySpriteImages/GoombaDead.png"));
	private Timer killGoomba = new Timer(750, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			killed = true;
			killGoomba.stop();
		}
		
	});


	public Goomba(int x, int y) {
		
		XCORD = x;
		YCORD = y + 4;
		killGoomba.setInitialDelay(750);
		IMAGEICON = goomba1;
		IMAGE = IMAGEICON.getImage();
	}

	@Override
	public boolean isObstructive() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Polygon getCollider() {
		// TODO Auto-generated method stub
		int [] xPoints = {XCORD + 18, XCORD + 3, XCORD, XCORD, XCORD + 50, XCORD + 50, XCORD + 47, XCORD + 32};
		int [] yPoints = {YCORD, YCORD + 15, YCORD + 21, YCORD + 50, YCORD + 50, YCORD + 21, YCORD + 15, YCORD};
		int nPoints = xPoints.length;
		return new Polygon(xPoints, yPoints, nPoints);
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return IMAGE;
	}

	@Override
	public int getXCord() {
		// TODO Auto-generated method stub
		return XCORD;
	}

	@Override
	public int getYCord() {
		// TODO Auto-generated method stub
		return YCORD;
	}

	@Override
	public Icon getImageIcon() {
		// TODO Auto-generated method stub
		return IMAGEICON;
	}

	@Override
	public Rectangle getRectangle() {
		// TODO Auto-generated method stub
		return new Rectangle(XCORD, YCORD, IMAGEICON.getIconWidth(), IMAGEICON.getIconHeight());
	}

	@Override
	public boolean moves() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int killPoints() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public Image nextImage() {
		
		if(killGoomba.isRunning()) {
			
			return IMAGE;
			
		}
		
		if(frame == 1) {
			IMAGEICON = goomba1;
			frame = 2;
		}
		else {
			IMAGEICON = goomba2;
			frame = 1;
		}
		IMAGE = IMAGEICON.getImage();
		return IMAGE;
	}

	@Override
	public void kill() {
		IMAGEICON = dead;
		IMAGE = IMAGEICON.getImage();
		YCORD += 13;
		killGoomba.start();
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return !killed;
	}

	@Override
	public int getKillArea() {
		return YCORD +  20;
	}

	@Override
	public void shiftX() {
		
		if (!killGoomba.isRunning()) {
			XCORD += direction;
		}
		
	}

	@Override
	public void shiftY() {
		
		YCORD ++;
		
	}

	@Override
	public void reverseDirection() {
		
		direction *= -1;
		
	}

	@Override
	public boolean isDying() {
		// TODO Auto-generated method stub
		return killGoomba.isRunning();
	}

}
