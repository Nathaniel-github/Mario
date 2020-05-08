
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class LevelTimer {
	private int timeLeft;
	Timer currentTimeLeft = new Timer(1000, new ActionListener() {

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
	
	public int getTimeLeft() {
		return timeLeft;
	}

}
