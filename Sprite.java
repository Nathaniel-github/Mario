import java.awt.Image;

public interface Sprite extends Prop {
	
	public boolean moves();
	
	public int killPoints();
	
	public Image nextImage();
	
	public void kill();
	
	public boolean isAlive();
	
	public int getKillArea();
	
	public void shiftX();
	
	public void shiftY(int y);
	
	public void setDirection(int direction);

}
