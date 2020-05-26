//Author: Nathaniel Thomas, Aayush Lakhotia, and Dhruv Masurekar(Team Effort)
//Notes: this is a class which is used as a base-line
//to create the mushroom class
public interface PowerUp extends Prop {
	
	public boolean moves();
	
	public int killPoints();
	
	public void kill();
	
	public boolean isAlive();
	
	public void shiftX();
	
	public void shiftY();
	
	public void appear();
	
	public boolean isAppearing();
	
	public boolean hasAppeared();
	
	public void reverseDirection();

}
