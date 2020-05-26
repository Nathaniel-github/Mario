import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class LongTree implements Prop{
	// Fields
	private final Image IMAGE;
	private final ImageIcon IMAGEICON;
	private final int XCORD;
	private final int YCORD;
	
	public LongTree(int x, int y) {
		
		// Save x and y coordinates given when instantiated
		XCORD = x;
		YCORD = y;
		// Gets the ImageIcon of the short pipe
		IMAGEICON = new ImageIcon(getClass().getClassLoader().getResource("PropImages/LongTree.png"));
		// Gets the image from the ImageIcon
		IMAGE = IMAGEICON.getImage();
		
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
	public boolean isObstructive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Polygon getCollider() {
		// TODO Auto-generated method stub
		return null;
	}

}
