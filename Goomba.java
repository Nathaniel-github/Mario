import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Goomba implements Sprite{
	
	private final Polygon COLLIDER;
	private int XCORD;
	private int YCORD;
	private ImageIcon IMAGEICON;
	private Image IMAGE;
	private int frame;
	private boolean killed = false;
	private URL goomba1 = getClass().getClassLoader().getResource("EnemySpriteImages/Goomba.png");
	private URL goomba2 = getClass().getClassLoader().getResource("EnemySpriteImages/Goomba2.png");
	private URL dead = getClass().getClassLoader().getResource("EnemySpriteImages/GoombaDead.png");
	private Timer killGoomba = new Timer(750, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			killed = true;
			killGoomba.stop();
		}
		
	});


	public Goomba(int x, int y) {
		
		XCORD = x;
		YCORD = y;
		int [] xPoints = {XCORD + 18, XCORD + 3, XCORD, XCORD, XCORD + 50, XCORD + 50, XCORD + 47, XCORD + 32};
		int [] yPoints = {YCORD, YCORD + 15, YCORD + 21, YCORD + 50, YCORD + 50, YCORD + 21, YCORD + 15, YCORD};
		int nPoints = xPoints.length;
		COLLIDER = new Polygon(xPoints, yPoints, nPoints);
		
		
		IMAGEICON = new ImageIcon(goomba1);
		IMAGE = IMAGEICON.getImage();
		frame = 1;
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
		if(frame == 1) {
			IMAGEICON = new ImageIcon(goomba1);
			frame = 2;
		}
		else {
			IMAGEICON = new ImageIcon(goomba2);
			frame = 1;
		}
		IMAGE = IMAGEICON.getImage();
		return IMAGE;
	}

	@Override
	public void kill() {
		IMAGEICON = new ImageIcon(dead);
		IMAGE = IMAGEICON.getImage();
		killGoomba.start();
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return !killed;
	}

}
