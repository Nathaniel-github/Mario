//Notes: this class is used to display all the points that the player has
public class Points {
	private int points;
	
	public Points() {
		this.points = 0;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void addPoints(int x) {
		points += x;
	}
	public String timeStartingZeros() {
		int tl = 6 - Integer.toString(points).length();
		String displayTime = "";
		for(int i = 0;i < tl;i++) {
			displayTime += "0";
		}
		return displayTime;
	}

}
