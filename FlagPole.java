import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class FlagPole implements Prop{

	// Fields
	final Image IMAGE;
	final ImageIcon IMAGEICON;
	final int XCORD;
	final int YCORD;
	final Polygon COLLIDER;
	
	public FlagPole(int x, int y) {
		
		// Save x and y coordinates given when instantiated
		XCORD = x;
		YCORD = y;
		// Gets the ImageIcon of the flag pole
		IMAGEICON = new ImageIcon(getClass().getClassLoader().getResource("PropImages/FlagPole.png"));
		// Gets the image from the ImageIcon
		IMAGE = IMAGEICON.getImage();
		
		int [] xPoints = {XCORD + 68, XCORD + 68, XCORD + 77, XCORD + 77, XCORD + 84, XCORD + 84, XCORD + 93, XCORD + 93};
		int [] yPoints = {YCORD + 21, YCORD + 47, YCORD + 47, YCORD + 533, YCORD + 533, YCORD + 47, YCORD + 47, YCORD + 21};
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
	public Icon getImageIcon() {
		
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

	// Returns the polygon collider that encompasses the image
	@Override
	public Polygon getCollider() {
		
		return COLLIDER;
		
	}

}