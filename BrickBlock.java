import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class BrickBlock implements Block{

	// Fields
	final Image IMAGE;
	final ImageIcon IMAGEICON;
	final int XCORD;
	final int YCORD;
	
	public BrickBlock(int x, int y) {
		
		// Save x and y coordinates given when instantiated
		XCORD = x;
		YCORD = y;
		// Gets the ImageIcon of the brick block
		IMAGEICON = new ImageIcon(getClass().getClassLoader().getResource("TileImages/BrickBlock.png"));
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
	public Icon getImageIcon() {
		
		return IMAGEICON;
		
	}

	// Returns the rectangle that surrounds the image
	@Override
	public Rectangle getRectangle() {
		
		return new Rectangle(XCORD, YCORD, IMAGEICON.getIconWidth(), IMAGEICON.getIconHeight());
		
	}

}
