import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.sound.sampled.*;
import javax.swing.Timer;

public class SoundPlayer {
	
	private AudioInputStream myobj;
	private Clip clip;
	private String fileName;
	private FloatControl volume;
	private final float muteVolume;
	private Timer restartSound = new Timer(10, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (!clip.isRunning()) {
				
				clip.setMicrosecondPosition(0);
				
			}
			
		}
		
	});
	
	public SoundPlayer(String FileName) {	
		fileName = FileName;
		
		try {
			myobj = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("Sounds/" + fileName));
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			clip.open(myobj);
			volume = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		muteVolume = volume.getMinimum();
		
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void changeVolume(float f) {
		volume.setValue(f);
	}
	
	public void setVolume(float f) {
		volume.setValue(f + muteVolume);
	}
	
	public void stop() {
		clip.stop();
		clip.close();
	}
	
	public void restart() {
		
		restartSound.start();
		
	}
	
}