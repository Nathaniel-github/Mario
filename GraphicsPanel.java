import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;


public class GraphicsPanel extends JFrame {

	final String STANDINGIMAGE = "MarioStanding.png";
	final String JUMPINGIMAGE = "MarioJumping.png";
	final String WALKINGIMAGE1 = "MarioWalking1.png";
	final String WALKINGIMAGE2 = "MarioWalking2.png";
	final String EXTENSION = "res/MarioImages/";
	
	private boolean back = false;
	private boolean jumping = false;
	
	private Image currentImage = new ImageIcon(getExt(STANDINGIMAGE)).getImage();
	
	private int frame = 0;
	private int xCord = 100;
	private int yCord = 400;
	private int jumpCount = 0;
	private final int MOVELENGTH = 1;
	private final int FPS = 10;
	private final int SPEED = 5;
	private final int JUMPHEIGHT = 150;
	
	private Timer timer = new Timer(FPS, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (frame % (int)(Math.pow(SPEED, 2)) < (int)(Math.pow(SPEED, 2)/2)) {
				
				currentImage = new ImageIcon(getExt(WALKINGIMAGE1)).getImage();
				
			} else {
				
				currentImage = new ImageIcon(getExt(WALKINGIMAGE2)).getImage();
				
			}
			
			mainPanel.updateUI();
			
		}
		
	});
	
	private Timer jump = new Timer(SPEED, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (jumpCount * MOVELENGTH < JUMPHEIGHT) {
				
				yCord -= MOVELENGTH;
				jumping = true;
				jumpCount ++;
				
			} else {
				
				if (yCord < getFloor()) {
					
					yCord += MOVELENGTH;
					jumping = true;
					jumpCount ++;
					
				} else {
					
					jumping = false;
					jumpCount = 0;
					jump.stop();
					
				}
				
			}
			
		}
		
	});
	
	private Timer leftMove = new Timer(SPEED, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			xCord -= MOVELENGTH;
			frame ++;
			back = true;
			
		}
		
	});
	
	private Timer rightMove = new Timer(SPEED, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			xCord += MOVELENGTH;
			frame ++;
			back = false;
			
		}
		
	});
	
	private JPanel mainPanel = new JPanel() {
		
		@Override
		protected void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			
			if (xCord + currentImage.getWidth(this) >= this.getWidth()) {
				
				xCord = getWidth() - currentImage.getWidth(this) - 10;
				
			} else if (xCord <= 0) {
				
				xCord = 10;
				
			}
			
			if (currentImage != null) {
				g.drawImage(currentImage, xCord, yCord, this);
			}
			
		}
		
	};
	
	public GraphicsPanel(String name) {
		
		super(name);
		
		mainPanel.setLayout(new OverlayLayout(mainPanel));
		
		startAnimation();
		
		setKeyBindings();
		
	}
	
	private void updateAll() {
		
		mainPanel.updateUI();
		
	}
	
	private int getFloor() {
		
		return 400;
		
	}
	
	private String getExt(String name) {
		
		String answer = "";
		
		if (!back) {
			
			if (jumping) {
				
				answer = EXTENSION + "Front/" + JUMPINGIMAGE;
				
			} else {
			
				answer = EXTENSION + "Front/" + name;
			
			}
			
		} else {
			
			if (jumping) {
				
				answer = EXTENSION + "Back/" + JUMPINGIMAGE;
				
			} else {
				
				answer = EXTENSION + "Back/" + name;
			
			}
		}
		
		return answer;
		
	}
	
	private void setKeyBindings() {
		ActionMap actionMap = mainPanel.getActionMap();
		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = mainPanel.getInputMap(condition);

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left_pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right_pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "space_pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left_released");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right_released");

		actionMap.put("left_pressed", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {
				
				leftMove.start();
				
			}
		});
		actionMap.put("left_released", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {
				
				leftMove.stop();
				
			}
		});
		actionMap.put("right_pressed", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {
				
				rightMove.start();
				
			}
		});
		actionMap.put("right_released", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {
				
				rightMove.stop();
				
			}
		});
		actionMap.put("space_pressed", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {
				
				if (!jump.isRunning()) {
					
					jump.start();
					
				}
				
			}
		});

	}

	private void startAnimation() {
		
		Container c = getContentPane();
		
		timer.start();
		
		c.add(mainPanel);
		
	}
	
}
