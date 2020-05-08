import java.awt.Image;

public interface Sprite extends Prop {
	
	public boolean moves();
	
	public int killPoints();
	
	public Image nextImage();
	
	public void kill();
	
	public boolean isAlive();
	
	public boolean isDying();
	
	public int getKillArea();
	
	public void shiftX();
	
	public void shiftY();
	
	public void reverseDirection();

}
