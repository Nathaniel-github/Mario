import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class BarrierBlock implements Block {

	// Fields
	private final Image IMAGE;
	private final ImageIcon IMAGEICON;
	private final int XCORD;
	private final int YCORD;
	
	public BarrierBlock(int x, int y) {
		
		// Save x and y coordinates given when instantiated
		XCORD = x;
		YCORD = y;
		// Gets the ImageIcon of the brick block
		IMAGEICON = new ImageIcon(getClass().getClassLoader().getResource("TileImages/BarrierBlock.png"));
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

	// Returns the rectangle that surrounds the image (used as collider)
	@Override
	public Rectangle getRectangle() {
		
		return new Rectangle(XCORD, YCORD, IMAGEICON.getIconWidth(), IMAGEICON.getIconHeight());
		
	}

}
