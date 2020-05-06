import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Pipe implements Prop{

	// Fields
	final Image IMAGE;
	final ImageIcon IMAGEICON;
	final int XCORD;
	final int YCORD;
	final Polygon COLLIDER;
	
	public Pipe(int x, int y) {
		
		// Save x and y coordinates given when instantiated
		XCORD = x;
		YCORD = y;
		// Gets the ImageIcon of the short pipe
		IMAGEICON = new ImageIcon(getClass().getClassLoader().getResource("PropImages/Pipe.png"));
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
		
		return true;
		
	}

	// Returns the polygon collider that encompasses the image
	@Override
	public Polygon getCollider() {
		
		return COLLIDER;
		
	}

}