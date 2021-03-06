//Author: Nathaniel Thomas, Aayush Lakhotia, and Dhruv Masurekar(Team Effort)
// Notes: an interface that sets the base line for other classes
// that are blocks
import java.awt.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public interface Block {

	// Should return the block's image
	public Image getImage();
	
	// Should return the x coordinate of the block
	public int getXCord();
	
	// Should return the y coordinate of the block
	public int getYCord();
	
	// Should return the ImageIcon of the block
	public ImageIcon getImageIcon();
	
	// Should return the rectangle that encompasses this block, this is used as the collider
	public Rectangle getRectangle();
	
}
