import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import javax.swing.*;

import java.util.LinkedList; 
import java.util.Queue; 

public class GraphicsPanel extends JFrame {

	final String STANDINGIMAGE = "MarioStanding.png";
	final String JUMPINGIMAGE = "MarioJumping.png";
	final String WALKINGIMAGE1 = "MarioWalking1.png";
	final String WALKINGIMAGE2 = "MarioWalking2.png";
	final String EXTENSION = "res/MarioImages/";
	
	private boolean back = false;
	private boolean jumping = false;
	
	private ImageObserver observer = this;
	private JPanel bottomPanel = new JPanel();
	
	private Image currentImage = getCurrentImage();
	private Image backgroundImage = new ImageIcon("res/Backgrounds/BasicMarioBackground.png").getImage().getScaledInstance((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 700, Image.SCALE_DEFAULT);
	
	private int frame = 0;
	private int xCord = 100;
	private int yCord = 700 - currentImage.getHeight(observer);
	private int jumpCount = 0;
	private final int MOVELENGTH = 1;
	private final int REFRESHRATE = 10;
	private final int SPEED = 5;
	private final int JUMPHEIGHT = 150;
	
	private LinkedList<Block> allBlocks = new LinkedList<Block>();
	
	private Timer timer = new Timer(REFRESHRATE, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			currentImage = getCurrentImage();
			
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
				
				Image temp;
				
				if (frame % (int)(Math.pow(SPEED, 2)) < (int)(Math.pow(SPEED, 2)/2)) {
					
					temp = new ImageIcon(EXTENSION + "Front/" + WALKINGIMAGE1).getImage();
					
				} else {
					
					temp = new ImageIcon(EXTENSION + "Front/" + WALKINGIMAGE2).getImage();
					
				}
				
				if (yCord + temp.getHeight(observer) < getFloor()) {
					
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

			g.drawImage(backgroundImage, 0, 0, observer);
			
			if (xCord + currentImage.getWidth(observer) >= getWall()) {
				
				xCord = getWall() - currentImage.getWidth(observer) - 10;
				
			} else if (xCord <= 0) {
				
				xCord = 10;
				
			}
			
			if (currentImage != null) {
				g.drawImage(currentImage, xCord, yCord, observer);
			}
			
		}
		
	};
	
	public GraphicsPanel(String name) {
		
		super(name);
		
		mainPanel.setLayout(new BorderLayout());
		
		setKeyBindings();
		
		setBlocks();
		
		startAnimation();
		
	}
	
	private void updateAll() {
		
		mainPanel.updateUI();
		
	}
	
	private int getFloor() {
		
		return 700;
		
	}
	
	private int getWall() {
		
		return getWidth();
		
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
	
	private Image getCurrentImage() {
		
		Image answer;
		
		if (frame == 0) {
			
			return new ImageIcon(getExt(STANDINGIMAGE)).getImage();
			
		}
		
		if (frame % (int)(Math.pow(SPEED, 2)) < (int)(Math.pow(SPEED, 2)/2)) {
			
			answer = new ImageIcon(getExt(WALKINGIMAGE1)).getImage();
			
		} else {
			
			answer = new ImageIcon(getExt(WALKINGIMAGE2)).getImage();
			
		}
		
		return answer;
		
	}
	
	private void setBlocks() {
		
		for (int i = 0; i < 1440/20; i ++) {
			
			for (int k = 700; k <= 800; k +=20) {
				
				allBlocks.add(new FloorBlock(i * 20, k));
				
			}
			
		}
		
	}
	
	private void addAllBlocks() {
		
		for (int i = 0; i < allBlocks.size(); i ++) {
			
			bottomPanel.add(new JLabel(allBlocks.get(i).getImageIcon()));
			
		}
		
	}

	private void startAnimation() {
		
		Container c = getContentPane();
		
		timer.start();
		
		bottomPanel.setLayout(new GridLayout(0, 1440/20));
		
		addAllBlocks();
		
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		c.add(mainPanel);
		
	}
	
}
