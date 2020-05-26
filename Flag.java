//Notes: This class is used to add a flag to the game
// which moves when the player reaches the end of the 
// level and touches the flag pole
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Flag implements Prop{

	// Fields
	private final Image IMAGE;
	private final ImageIcon IMAGEICON;
	private final int XCORD;
	private final int ORIGINALYCORD;
	private int YCORD;
	private final Polygon COLLIDER;
	
	public Flag(int x, int y) {
		
		// Save x and y coordinates given when instantiated
		XCORD = x;
		YCORD = y;
		ORIGINALYCORD = y;
		// Gets the ImageIcon of the flag pole
		IMAGEICON = new ImageIcon(getClass().getClassLoader().getResource("PropImages/Flag.png"));
		// Gets the image from the ImageIcon
		IMAGE = IMAGEICON.getImage();
		
		// These points are not corresponding to the flag because the flag is not an obstruction to mario
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

	// Returns whether or not the prop is obstructive
	@Override
	public boolean isObstructive() {
		
		return false;
		
	}

	// Returns the polygon collider that encompasses the image (in this case it isn't accurate but it doesn't
	// matter because it is not used)
	@Override
	public Polygon getCollider() {
		
		return COLLIDER;
		
	}
	
	public void changeYCord(int change) {
		YCORD+=change;
	}
	
	public void resetYCord() {
		
		YCORD = ORIGINALYCORD;
	}

}
