//Author: Nathaniel Thomas, Aayush Lakhotia, and Dhruv Masurekar(Team Effort)
// Notes: This class is used to add an end castle to the game
// If the player reaches the castle they move on to the next level
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class EndCastle implements Prop{

	// Fields
	private final Image IMAGE;
	private final ImageIcon IMAGEICON;
	private final int XCORD;
	private final int YCORD;
	
	public EndCastle(int x, int y) {
		
		// Save x and y coordinates given when instantiated
		XCORD = x;
		YCORD = y;
		// Gets the ImageIcon of the end castle
		IMAGEICON = new ImageIcon(getClass().getClassLoader().getResource("PropImages/EndCastle.png"));
		// Gets the image from the ImageIcon
		IMAGE = IMAGEICON.getImage();
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
		
		return null;
		
	}
	
}
