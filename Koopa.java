//Notes: This class is used to add enemies to
//the level which will kill Mario if he touches them
// but they will die if he jumps on top of them
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Koopa implements Sprite{
	
	private int XCORD;
	private int YCORD;
	private ImageIcon IMAGEICON;
	private Image IMAGE;
	private int frame = 1;
	private int direction = -1;
	private boolean killed = false;
	private boolean back = true;
	private ImageIcon koopa1 = new ImageIcon(getClass().getClassLoader().getResource("EnemySpriteImages/Koopa.png"));
	private ImageIcon koopa2 = new ImageIcon(getClass().getClassLoader().getResource("EnemySpriteImages/Koopa2.png"));
	private ImageIcon koopa1_back = new ImageIcon(getClass().getClassLoader().getResource("EnemySpriteImages/Koopa_back.png"));
	private ImageIcon koopa2_back = new ImageIcon(getClass().getClassLoader().getResource("EnemySpriteImages/Koopa2_back.png"));
	private ImageIcon dead = new ImageIcon(getClass().getClassLoader().getResource("EnemySpriteImages/KoopaDead.png"));
	private Timer killKoopa = new Timer(750, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			killed = true;
			killKoopa.stop();
		}
		
	});


	public Koopa(int x, int y) {
		XCORD = x;
		YCORD = y + 8;
		IMAGEICON = koopa1_back;
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
		int [] xPoints = {XCORD + 36, XCORD + 47, XCORD + 50, XCORD + 50, XCORD, XCORD, XCORD + 28, XCORD + 28};
		int [] yPoints = {YCORD + 24, YCORD + 37, YCORD + 48, YCORD + 100, YCORD + 100, YCORD + 54, YCORD + 54, YCORD + 35};
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
		return 200;
	}

	@Override
	public Image nextImage() {
		if(killKoopa.isRunning()) {
			
			return IMAGE;
			
		}
		if(frame == 1 && !back) {
			IMAGEICON = koopa1;
			frame = 2;
		} else if (frame == 2 && !back){
			IMAGEICON = koopa2;
			frame = 1;
		} else if (frame == 1 && back){
			IMAGEICON = koopa1_back;
			frame = 2;
		} else if (frame == 2 && back){
			IMAGEICON = koopa2_back;
			frame = 1;
		}

		IMAGE = IMAGEICON.getImage();
		return IMAGE;
	}

	@Override
	public void kill() {
		IMAGEICON = dead;
		IMAGE = IMAGEICON.getImage();
		YCORD += 56;
		killKoopa.start();
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return !killed;
	}

	@Override
	public int getKillArea() {
		return YCORD + 64;
	}

	@Override
	public void shiftX() {
		
		if(!killKoopa.isRunning()) {
			XCORD += direction;
		}
		
	}

	@Override
	public void shiftY() {
		
		YCORD ++;
		
	}

	@Override
	public void reverseDirection() {
		
		if(back) {
			if (frame == 1) {
				IMAGEICON = koopa1;
			} else {
				IMAGEICON = koopa2;
			}
			back = false;
		} else {
			if (frame == 1) {
				IMAGEICON = koopa1_back;
			} else {
				IMAGEICON = koopa2_back;
			}
			back = true;
		}
		IMAGE = IMAGEICON.getImage();
		direction *= -1;
		
	}

	@Override
	public boolean isDying() {
		// TODO Auto-generated method stub
		return killKoopa.isRunning();
	}

}
