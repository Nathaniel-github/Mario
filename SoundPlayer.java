import java.io.*;

import javax.sound.sampled.*;

public class SoundPlayer {
	
	AudioInputStream myobj;
	Clip clip;
	String fileName;
	FloatControl volume;
	final float muteVolume;
	
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
		while(clip.isRunning()) {}
		clip.setMicrosecondPosition(0);
	}
	
}