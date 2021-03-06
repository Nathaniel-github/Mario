//Author: Nathaniel Thomas, Aayush Lakhotia, and Dhruv Masurekar(Team Effort)
// Notes: this class is used to add a pipe to the game
// which mario can jump on top of
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ShortPipe extends Pipe {

	// Fields
	private final Image IMAGE;
	private final ImageIcon IMAGEICON;
	private final int XCORD;
	private final int YCORD;
	private final Polygon COLLIDER;
	
	public ShortPipe(int x, int y) {
		
		super(x, y);
		
		// Save x and y coordinates given when instantiated
		XCORD = x;
		YCORD = y;
		// Gets the ImageIcon of the short pipe
		IMAGEICON = new ImageIcon(getClass().getClassLoader().getResource("PropImages/ShortPipe.png"));
		// Gets the image from the ImageIcon
		IMAGE = IMAGEICON.getImage();
		
		int [] xPoints = {XCORD, XCORD, XCORD + 6, XCORD + 6, XCORD + 102, XCORD + 102, XCORD + IMAGEICON.getIconWidth(), XCORD + IMAGEICON.getIconWidth()};
		int [] yPoints = {YCORD, YCORD + 50, YCORD + 50, YCORD + IMAGEICON.getIconHeight(), YCORD + IMAGEICON.getIconHeight(), YCORD + 50, YCORD + 50, YCORD};
		int nPoints = xPoints.length;
		COLLIDER = new Polygon(xPoints, yPoints, nPoints);
	}
	
	// Returns the image
	@Override
	public Image getImage() {
		
		return IMAGE;
		
	}

	// Returns the x coordinate
	@Override
	public int getXCord() {
		
		return XCORD;
		
	}

	// Returns the y coordinate
	@Override
	public int getYCord() {
		
		return YCORD;
		
	}

	// Returns the ImageIcon
	@Override
	public ImageIcon getImageIcon() {
		
		return IMAGEICON;
		
	}
	
	// Returns the rectangle that surrounds the image
	@Override
	public Rectangle getRectangle() {
		
		return new Rectangle(XCORD, YCORD, IMAGEICON.getIconWidth(), IMAGEICON.getIconHeight());
		
	}

	// Returns the polygon collider that encompasses the image
	@Override
	public Polygon getCollider() {
		
		return COLLIDER;
		
	}

}
