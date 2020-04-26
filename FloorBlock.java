import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class FloorBlock implements Block {

	final Image IMAGE;
	final ImageIcon IMAGEICON;
	final int XCORD;
	final int YCORD;
	
	public FloorBlock(int x, int y) {
		
		XCORD = x;
		YCORD = y;
		IMAGEICON = new ImageIcon(new ImageIcon("res/TileImages/FloorBlock.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		IMAGE = IMAGEICON.getImage();
		
	}
	
	@Override
	public Image getImage() {
		
		return IMAGE;
		
	}

	@Override
	public int getXCord() {
		
		return XCORD;
		
	}

	@Override
	public int getYCord() {
		
		return YCORD;
		
	}

	@Override
	public Icon getImageIcon() {
		
		return IMAGEICON;
		
	}

}
