//Notes: this class is used to add a mushroom to
// the level which makes mario grow if he touches them
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Mushroom implements PowerUp {
	
	// Fields
	private int XCORD;
	private int YCORD;
	private final int INITY;
	private final ImageIcon IMAGEICON;
	private final Image IMAGE;
	private int direction = 1;
	private boolean killed = false;
	private boolean appeared = false;
	private Timer appear = new Timer(10, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (YCORD > INITY - 54) {
				
				YCORD -= 2;
				
			} else {
				
				appear.stop();
				appeared = true;
				
			}
			
		}
		
	});

	public Mushroom(int x, int y) {
		
		XCORD = x;
		YCORD = y;
		INITY = y;
		IMAGEICON = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("PowerUpImages/Mushroom.png")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
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
	public ImageIcon getImageIcon() {
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
	public void kill() {
		
		killed = true;
		
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return !killed;
	}

	@Override
	public void shiftX() {
		
		if (!killed && appeared) {
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
	public void appear() {
		
		if (!appeared && !appear.isRunning()) {
			appear.start();
		}
		
	}

	@Override
	public boolean isAppearing() {
		// TODO Auto-generated method stub
		return appear.isRunning();
	}

	@Override
	public boolean hasAppeared() {
		// TODO Auto-generated method stub
		return appeared;
	}

}
