import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		// Code for setting up window
		
		GraphicsPanel window = new GraphicsPanel("Mario");
		// Sets the screen size to the width of the computer screen and the height to 80 pixels less than the computer screen height
		window.setBounds(0, 0, 1440, 820); 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Makes it so that the window can not be resized
		window.setResizable(false);
		window.setVisible(true);

	}

}
