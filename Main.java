import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		GraphicsPanel window = new GraphicsPanel("Mario");
		window.setBounds(0, 0, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 80);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);

	}

}
