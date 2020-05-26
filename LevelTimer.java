//Author: Nathaniel Thomas, Aayush Lakhotia, and Dhruv Masurekar(Team Effort)
//Notes: this class is used to set the time for 
// each level,and it also sets how fast the time decreases
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class LevelTimer {
	private int timeLeft;
	Timer currentTimeLeft = new Timer(400, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(timeLeft > 0) {
				timeLeft = timeLeft - 1;
			}
			
		}
		
	});
	
	
	public LevelTimer(int timeLeft) {
		this.timeLeft = timeLeft;	
	}
	
	public void startTimer() {

		currentTimeLeft.start();		

	}
	public void restartTimer() {
		timeLeft = 400;
	}
	
	public void stopTimer() {
		
		currentTimeLeft.stop();
		
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}
	
	public String timeStartingZeros() {
		int tl = 3 - Integer.toString(timeLeft).length();
		String displayTime = "";
		for(int i = 0;i < tl;i++) {
			displayTime += "0";
		}
		return displayTime;
	}
	
	//For the ending Time to Points conversion
	public void decreaseTime() {
		timeLeft--;
	}

}
