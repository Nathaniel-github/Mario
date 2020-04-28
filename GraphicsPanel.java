import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.net.URL;

import javax.swing.*;

import java.util.LinkedList;

public class GraphicsPanel extends JFrame {

	// Fields
	
	//These are the names of the images that are in the resource folder
	final String STANDINGIMAGE = "MarioStanding";
	final String JUMPINGIMAGE = "MarioJumping";
	final String WALKINGIMAGE1 = "MarioWalking1";
	final String WALKINGIMAGE2 = "MarioWalking2";
	
	// This is the extension to access those images
	final String EXTENSION = "MarioImages/";
	
	// Boolean that says if Mario is facing backwards or not, this is to determine the currentImage
	private boolean back = false;
	
	// Boolean for whether or not Mario is jumping, also used to determine currentImage
	private boolean jumping = false;
	
	// An ImageObserver is an interface for determining states of images in its window, this is used 
	// whenever the getWidth and getHeight methods are called on images, as they require a parameter of 
	// the ImageObserver
	private ImageObserver observer = this;
	
	// The width and height of the window rendered
	private final int SCREENWIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final int SCREENHEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100;
	
	// This is the current image for Mario, whether that be him jumping, moving or whatever, whenever his 
	// state changes, this variable changes as well (keep it this way)
	private Image currentImage = getCurrentImage();
	
	// This is the background image for the window
	private Image backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Backgrounds/BasicMarioBackground.png")).getImage().getScaledInstance(SCREENWIDTH, SCREENHEIGHT, Image.SCALE_DEFAULT);
	
	// The frame is basically how many times Mario has received input to move, this is required to have 
	// because since the action events fire so fast we can't have Mario change his image that many times as that would make it look really weird
	private int frame = 0;
	
	// The x coordinate of Mario relative to the window, this is used for drawing Mario on the JPanel
	private int xCord = 100;
	
	// The x coordinate of Mario relative to the level, this is used to determine his position in relation
	// to stage hazards and other things that are location specific in a level
	private int trueX = 100;
	
	// This the y coordinate of Mario, there isn't a need for one relative to the level because the y axis
	// does not scroll (at least for what I have right now, I don't think I will scroll it anyway)
	private int yCord = 600 - currentImage.getHeight(observer);
	
	// This variable is NOT the number of times Mario has jumped, but actually the number of times his 
	// movement for a jump is rendered, this is used because when you jump the program will move you a 
	// little bit every frame so that you can actually see the jump animation, because of this it also 
	// needs to know when to stop making you move up and start moving you back to the ground which is 
	// why this variable is there
	private int jumpCount = 0;
	
	// These three coordinates are the current x coordinates in relation to the JPanel of the background,
	// since the technique used to scroll here is to loop multiple backgrounds, and then when one is 
	// finished displaying it gets moved to the end of the queue of backgrounds ready to render (this will
	// make more sense once you take a look at the scrolling method)
	private int backX = 0;
	private int backX2 = SCREENWIDTH;
	private int backX3 = SCREENWIDTH * 2;
	
	// This is the number of pixels the stage has been scrolled already, this is used to render stage 
	// hazards and other blocks that are location specific in a level
	private int scroll = 0;
	
	//This is how much Mario moves every frame (in pixels)
	private final int MOVELENGTH = 2;
	
	// This is how fast the panel is refreshed (in milliseconds)
	private final int REFRESHRATE = 1;
	
	// This is how quickly a character will move when you press an arrow key (it is in milliseconds an you
	// will understand why when you look at how a character is moved
	private final int SPEED = 5;
	
	// This is the height of a jump (in pixels)
	private final int JUMPHEIGHT = 150;
	
	// This is the y coordinate of the base floor (the fraction is there so that different sizes of the 
	// window will not cause the base floor coordinate to be inaccurate)
	private final int BASEFLOOR = SCREENHEIGHT - (int)(SCREENHEIGHT / 7.47663551402);
	
	// This is the width of one block (in pixels)
	private final double BLOCKWIDTH = 53.3;
	
	// This list stores all of the data for every block that needs to rendered throughout a level
	private LinkedList<Block> allBlocks = new LinkedList<Block>();
	
	// This is the list for the blocks that need to be rendered, since java can not handle rendering every
	// block that needs to be rendered throughout the level and then constantly refreshing all those 
	// blocks every millisecond we need to keep a different list that holds the blocks that are the
	// closest to the player so that the JPanel only renders them, then when the player moves past the 
	// view of that block it will be removed from this list and no longer rendered (this list is updated
	// every millisecond on a timer because that way all of those calculations can be done on a 
	// different thread and not cause any delays in this one which renders the window)
	private LinkedList<Block> renderBlocks = new LinkedList<Block>();
	
	// This is the timer that will fire every millisecond and refresh the panel and update the image of 
	// Mario
	private Timer refresh = new Timer(REFRESHRATE, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			currentImage = getCurrentImage();
			
			mainPanel.updateUI();
			
		}
		
	});
	
	// This is the timer that will update the renderBlocks list so that only nearby blocks are rendered.
	// It is done like this so that the update will occur in a separate thread and not delay this one 
	// since the number of blocks that are in a level could be in the hundreds
	private Timer render = new Timer(REFRESHRATE, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			for (int i = 0; i < allBlocks.size(); i ++) {
				
				// If the block is within "one stage" of Mario
				if (allBlocks.get(i).getXCord() - backgroundImage.getWidth(observer) <= trueX && !renderBlocks.contains(allBlocks.get(i))) {
					
					// Adds the block from those that need to be rendered
					renderBlocks.add(allBlocks.get(i)); 
					
				}
				
				// If the block is "one stage" behind Mario
				if (allBlocks.get(i).getXCord() + backgroundImage.getWidth(observer) <= trueX) {
					
					// Removes the block from those that need to be rendered
					renderBlocks.remove(allBlocks.get(i));
					
				}
				
			}
			
		}
		
	});
	
	// This is the timer that handles gravity, it is done like this so that it very easy and efficient 
	// to determine if gravity needs to be in effect and if so activate it
	private Timer gravity = new Timer(SPEED, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Checks if Mario is currently jumping because if so we don't want to inflict gravity 
			// Mario obeys no physics :)
			if (!jump.isRunning()) {
				
				Image temp = getFrameImage();

				if (yCord + temp.getHeight(observer) < getFloor()) {

					yCord += MOVELENGTH;

				}
			}
		}
		
	});
	
	// This is the timer that handles a jump, this timer updates every frame that a jump is in motion.
	// It is done like this so that there is a smooth animation for jumps, and so that the key input for
	// it is handled nicely
	private Timer jump = new Timer(SPEED, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// If Mario has jumped the given jump height
			if (jumpCount * MOVELENGTH < JUMPHEIGHT) {
				
				yCord -= MOVELENGTH;
				jumping = true;
				jumpCount ++;
				
			} else {
				
				Image temp = getFrameImage();
				
				// If Mario is not on the floor
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
	
	// This is the timer that handles movement to the left of the screen (similar to jump timer)
	private Timer leftMove = new Timer(SPEED, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// If Mario is currently moving right
			if (!rightMove.isRunning()) {
				moveBack();
				frame ++;
				back = true;
				
				// If Mario is running into a barrier
				if (yCord + currentImage.getHeight(observer) - 1 > getFloor()) {
	
					moveForward();
	
				}
			}
			
		}
		
	});
	
	// This is the timer that handles movement to the right of the screen (similar to left movement timer)
	private Timer rightMove = new Timer(SPEED, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (!leftMove.isRunning()) {
				moveForward();
				frame ++;
				
				// This is so that the frame doesn't get too large, since we are modding it anyway it can
				// reset
				if (frame == 25) {
					
					frame = 0;
					
				}
				back = false;
				
				// If Mario is running into a barrier
				if (yCord + currentImage.getHeight(observer) - 1 > getFloor()) {
	
					moveBack();
	
				}
			}
			
		}
		
	});
	
	// This is where all the action happens, this the main JPanel that renders everything
	private JPanel mainPanel = new JPanel() {
		
		@Override
		protected void paintComponent(Graphics g) {
			
			super.paintComponent(g);

			// Draws the 3 backgrounds
			g.drawImage(backgroundImage, backX, 0, observer);
			g.drawImage(backgroundImage, backX2, 0, observer);
			g.drawImage(backgroundImage, backX3, 0, observer);
			
			// Draws the blocks that need to be rendered
			for (int i = 0; i < renderBlocks.size(); i ++) {
				
				g.drawImage(renderBlocks.get(i).getImage(), renderBlocks.get(i).getXCord() - scroll, renderBlocks.get(i).getYCord(), observer);
				
			}
			
			// If Mario is at the point in the stage where the stage needs to scroll
			if (xCord + currentImage.getWidth(observer) > getWidth() - 500) {
				
				xCord -= MOVELENGTH;
				scrollStage();
				
			} else if (xCord <= 0) { // If Mario is at the left end of the screen
				
				moveForward();
				
			}
			
			if (currentImage != null) {
				g.drawImage(currentImage, xCord, yCord, observer);
			}
			
		}
		
	};
	
	// Constructor
	public GraphicsPanel(String name) {
		
		super(name);
		
		mainPanel.setLayout(new BorderLayout()); // Not needed but is there for the moment
		
		setKeyBindings(); // Sets up the key input tracker so that key inputs are monitored
		
		setBlocks(); // Sets all the blocks for the current level
		
		startAnimation(); // Starts up the panel and renders the animation
		
	}
	
	// Not needed method right now but in my previous programs is has been very useful
	private void updateAll() { 
		
		mainPanel.updateUI();
		
	}
	
	// This is the method that determines where the floor is in relation to Mario's x coordinate
	private int getFloor() {
		
		int answer = SCREENHEIGHT - 107;
		
		for (int i = 0; i < renderBlocks.size(); i ++) {
			
			// If the block is where Mario is currently at
			if ((trueX + currentImage.getWidth(observer) >= renderBlocks.get(i).getXCord() && trueX + currentImage.getWidth(observer) <= renderBlocks.get(i).getXCord() + BLOCKWIDTH) || (trueX >= renderBlocks.get(i).getXCord() && trueX < renderBlocks.get(i).getXCord() + BLOCKWIDTH)) {
				
				answer = renderBlocks.get(i).getYCord();
				
			}
			
		}
		
		return answer;
		
	}
	
	// Returns the full path for the current image
	private URL getExt(String name) {
		
		// This method is mainly self explanatory
		
		URL answer;
		
		if (!back) {
			
			if (jumping) {
				
				answer = getClass().getClassLoader().getResource(EXTENSION + JUMPINGIMAGE + ".png");
				
			} else {
			
				answer = getClass().getClassLoader().getResource(EXTENSION + name + ".png");
			
			}
			
		} else {
			
			if (jumping) {
				
				answer = getClass().getClassLoader().getResource(EXTENSION + JUMPINGIMAGE + "_back.png");
				
			} else {
				
				answer = getClass().getClassLoader().getResource(EXTENSION + name + "_back.png");
			
			}
		}
		
		return answer;
		
	}
	
	// Sets up the key inputs (now this method is probably the most complex (at least for me) because 
	// I did not know what input maps and action maps are, so if you don't understand don't worry about it)
	private void setKeyBindings() {
		// The action map for the main panel (theoretically this could be for any object but I chose this
		// one)
		ActionMap actionMap = mainPanel.getActionMap(); 
		
		// The integer that represents when the program in in focus/currently open
		int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		
		// The input map for the condition (in this case for whenever you are in the window)
		InputMap inputMap = mainPanel.getInputMap(condition);

		// Assigns the key strokes with a "key"
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left_pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right_pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "space_pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left_released");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right_released");

		// Assigns the "key" to an action
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
	
	// Method for moving towards the left side of the screen
	private void moveBack() {
		
		xCord -= MOVELENGTH;
		trueX -= MOVELENGTH;
		
	}
	
	// Method for moving towards the right side of the screen
	private void moveForward() {
		
		xCord += MOVELENGTH;
		trueX += MOVELENGTH;
		
	}
	
	// This is the method that determines the current image based on multiple factors such as direction
	// Mario is facing and the frame number
	private Image getCurrentImage() {
		
		Image answer;
		
		// Starting frame
		if (frame == 0) {
			
			return new ImageIcon(getExt(STANDINGIMAGE)).getImage();
			
		}
		
		// Some calculations that are way more complex than it needs to be but its is like this so it is
		// more easily changeable
		if (frame % (int)(Math.pow(SPEED, 2)) < (int)(Math.pow(SPEED, 2)/2)) {
			
			answer = new ImageIcon(getExt(WALKINGIMAGE1)).getImage();
			
		} else {
			
			answer = new ImageIcon(getExt(WALKINGIMAGE2)).getImage();
			
		}
		
		return answer;
		
	}
	
	// This is a method merely to get rid of repeating code I made it because in multiple places I needed 
	// to know the height of the image standing, instead of jumping even though the current image was 
	// jumping
	private Image getFrameImage() {
		
		Image answer;
		
		if (frame % (int)(Math.pow(SPEED, 2)) < (int)(Math.pow(SPEED, 2)/2)) {
			
			answer = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + WALKINGIMAGE1 + ".png")).getImage();
			
		} else {
			
			answer = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + WALKINGIMAGE2 + ".png")).getImage();
			
		}
		
		return answer;
		
	}
	
	// This method is where all the blocks for the level are set, this is where most of our development
	// is going to go from now on as it is the basis for the entire level (Note: Right now I can't get
	// it so that you can add blocks in any order without messing up the getFloor method,
	// so for now just add blocks in ascending order, with the lowest blocks on a certain x coordinate
	// added first
	private void setBlocks() {
		
		allBlocks.add(new StairBlock((int)(BLOCKWIDTH * 10), (int)(BASEFLOOR - BLOCKWIDTH)));
		allBlocks.add(new StairBlock((int)(BLOCKWIDTH * 10), (int)(BASEFLOOR - BLOCKWIDTH * 2)));
		
	}
	
	// This method scrolls the stage
	private void scrollStage() {
		
		if (backX < 0 - backgroundImage.getWidth(observer)) {
			
			backX = SCREENWIDTH * 2 - MOVELENGTH;
			
		} else if (backX2 < 0 - backgroundImage.getWidth(observer)) {
			
			backX2 = SCREENWIDTH * 2 - MOVELENGTH;
			
		} else if (backX3 < 0 - backgroundImage.getWidth(observer)) {
			
			backX3 = SCREENWIDTH * 2 - MOVELENGTH;
			
		}
		
		backX -= MOVELENGTH;
		backX2 -= MOVELENGTH;
		backX3 -= MOVELENGTH;
		
		scroll += MOVELENGTH;
		
	}

	// Starts the animation
	private void startAnimation() {
		
		Container c = getContentPane();
		
		refresh.start();
		gravity.start();
		render.start();
		
		c.add(mainPanel);
		
	}
	
}
