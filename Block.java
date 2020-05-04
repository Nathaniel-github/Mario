import java.awt.*;

import javax.swing.Icon;

public interface Block {

	// Should return the block's image
	public Image getImage();
	
	// Should return the x coordinate of the block
	public int getXCord();
	
	// Should return the y coordinate of the block
	public int getYCord();
	
	// Should return the ImageIcon of the block
	public Icon getImageIcon();
	
	// Should return the rectangle that encompasses this block
	public Rectangle getRectangle();
	
}
