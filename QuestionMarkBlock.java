import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class QuestionMarkBlock implements Block{

	// Fields
	final Image IMAGE;
	final ImageIcon IMAGEICON;
	final int XCORD;
	final int YCORD;
	
	public QuestionMarkBlock(int x, int y) {
		
		// Save x and y coordinates given when instantiated
		XCORD = x;
		YCORD = y;
		// Gets a scaled version of the question mark block
		IMAGEICON = new ImageIcon(new ImageIcon(getClass().getClassLoader().getResource("TileImages/QuestionMarkBlock.png")).getImage().getScaledInstance(54, 54, Image.SCALE_DEFAULT));
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

}
