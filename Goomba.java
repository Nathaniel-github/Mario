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
	private final Polygon COLLIDER;
	private int XCORD;
	private int YCORD;
	private ImageIcon IMAGEICON;
	private Image IMAGE;
	private int frame = 0;
	private final int SPEED = 100;
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
		int [] xPoints = {XCORD + 18, XCORD + 3, XCORD, XCORD, XCORD + 50, XCORD + 50, XCORD + 47, XCORD + 32};
		int [] yPoints = {YCORD, YCORD + 15, YCORD + 21, YCORD + 50, YCORD + 50, YCORD + 21, YCORD + 15, YCORD};
		int nPoints = xPoints.length;
		COLLIDER = new Polygon(xPoints, yPoints, nPoints);
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
		return COLLIDER;
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
		return 200;
	}

	@Override
	public Image nextImage() {
		
		if(killGoomba.isRunning()) {
			
			return IMAGE;
			
		}
		
		if(frame % SPEED <= SPEED/2) {
			IMAGEICON = goomba1;
		}
		else {
			IMAGEICON = goomba2;
		}
		frame ++;
		if (frame >= SPEED) {
			frame = 0;
		}
		IMAGE = IMAGEICON.getImage();
		return IMAGE;
	}

	@Override
	public void kill() {
		IMAGEICON = dead;
		IMAGE = IMAGEICON.getImage();
		killGoomba.start();
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return !killed;
	}

}
