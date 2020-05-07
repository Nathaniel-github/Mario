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

	// This is the extension to access those images
	private final String EXTENSION = "MarioImages/";

	// These are the names of the images that are in the resource folder
	private final Image STANDINGIMAGE = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "MarioStanding.png")).getImage();
	private final Image JUMPINGIMAGE = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "MarioJumping.png")).getImage();
	private final Image WALKINGIMAGE1 = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "MarioWalking1.png")).getImage();
	private final Image WALKINGIMAGE2 = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "MarioWalking2.png")).getImage();
	private final Image WALKINGIMAGE3 = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "MarioWalking3.png")).getImage();
	private final Image STANDINGIMAGE_BACK = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "MarioStanding_back.png")).getImage();
	private final Image JUMPINGIMAGE_BACK = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "MarioJumping_back.png")).getImage();
	private final Image WALKINGIMAGE1_BACK = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "MarioWalking1_back.png")).getImage();
	private final Image WALKINGIMAGE2_BACK = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "MarioWalking2_back.png")).getImage();
	private final Image WALKINGIMAGE3_BACK = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "MarioWalking3_back.png")).getImage();
	
	// Boolean that says if Mario is facing backwards or not, this is to determine
	// the currentImage
	private boolean back = false;

	// Boolean for whether or not Mario is jumping, also used to determine
	// currentImage
	private boolean jumping = false;

	// Boolean for if Mario is standing or not
	private boolean stand = false;

	// Boolean for if the registered jump is a short hop or not
	private boolean shortHop = true;

	private boolean fixJump = false;

	private boolean endingAnimation = false;

	private boolean visible = true;
	
	private boolean turnAround = false;
	// An ImageObserver is an interface for determining states of images in its
	// window, this is used
	// whenever the getWidth and getHeight methods are called on images, as they
	// require a parameter of
	// the ImageObserver
	private ImageObserver observer = this;

	// The width and height of the window rendered
	private final int SCREENWIDTH = 1440;
	private final int SCREENHEIGHT = 800;

	// This is the current image for Mario, whether that be him jumping, moving or
	// whatever, whenever his
	// state changes, this variable changes as well (keep it this way)
	private Image currentImage = getCurrentImage();

	// This is the background image for the window
	private Image backgroundImage = new ImageIcon(
			getClass().getClassLoader().getResource("Backgrounds/BasicMarioBackground.png")).getImage()
					.getScaledInstance(SCREENWIDTH, SCREENHEIGHT, Image.SCALE_DEFAULT);

	// The frame is basically how many times Mario has received input to move, this
	// is required to have
	// because since the action events fire so fast we can't have Mario change his
	// image that many times as that would make it look really weird
	private int frame = 0;

	// The x coordinate of Mario relative to the window, this is used for drawing
	// Mario on the JPanel
	private int xCord = 100;

	// The x coordinate of Mario relative to the level, this is used to determine
	// his position in relation
	// to stage hazards and other things that are location specific in a level
	private int trueX = 100;

	// This the y coordinate of Mario, there isn't a need for one relative to the
	// level because the y axis
	// does not scroll (at least for what I have right now, I don't think I will
	// scroll it anyway)
	private int yCord = 600 - currentImage.getHeight(observer);

	// This variable is NOT the number of times Mario has jumped, but actually the
	// number of times his
	// movement for a jump is rendered, this is used because when you jump the
	// program will move you a
	// little bit every frame so that you can actually see the jump animation,
	// because of this it also
	// needs to know when to stop making you move up and start moving you back to
	// the ground which is
	// why this variable is there
	private int jumpCount = 0;

	// These three coordinates are the current x coordinates in relation to the
	// JPanel of the background,
	// since the technique used to scroll here is to loop multiple backgrounds, and
	// then when one is
	// finished displaying it gets moved to the end of the queue of backgrounds
	// ready to render (this will
	// make more sense once you take a look at the scrolling method)
	private int backX = 0;
	private int backX2 = SCREENWIDTH;
	private int backX3 = SCREENWIDTH * 2;

	// This is the number of pixels the stage has been scrolled already, this is
	// used to render stage
	// hazards and other blocks that are location specific in a level
	private int scroll = 0;

	// This is how much Mario moves every frame (in pixels)
	private final int MOVELENGTH = 2;

	// This is how fast the panel is refreshed (in milliseconds)
	private final int REFRESHRATE = 1;

	// This is how quickly a character will move when you press an arrow key (it is
	// in milliseconds an you
	// will understand why when you look at how a character is moved

	private final int SPEED = 5;

	// This is the y coordinate of the base floor
	private final int BASEFLOOR = SCREENHEIGHT - 107;

	// This is the width of one block (in pixels)
	private final int BLOCKWIDTH = 54;

	// This is the value used to calculate the placement of blocks
	private final double BLOCKSPACING = 53.35;

	// This is the height of a jump (in pixels)
	private final int JUMPHEIGHT = BLOCKWIDTH * 5;

	private SoundPlayer backgroundMusic = new SoundPlayer("MarioBasicBackgroundMusic.wav");
	private SoundPlayer jumpSound = new SoundPlayer("MarioJumpMusic.wav");

	// This list stores all of the data for every block that needs to be rendered
	// throughout a level
	private LinkedList<Block> allBlocks = new LinkedList<Block>();

	// This list stores all of the data for every prop that needs to be rendered
	// throughout a level
	private LinkedList<Prop> allProps = new LinkedList<Prop>();
	
	// This stores all the data for sprites that need to be rendered throughout the level
	private LinkedList<Sprite> allSprites = new LinkedList<Sprite>();

	// This list is the same as the renderBlocks except it stores all rendered sprites
	private LinkedList<Sprite> renderSprites = new LinkedList<Sprite>();

	// This is the object that reads the text
	private DataReader levelData = new DataReader("Mario-1-1.txt");

	private Flag flag ;
	private FlagPole flagPole;
	private EndCastle castle;
	

	// This is the list for the blocks that need to be rendered, since java can not
	// handle rendering every
	// block that needs to be rendered throughout the level and then constantly
	// refreshing all those
	// blocks every millisecond we need to keep a different list that holds the
	// blocks that are the
	// closest to the player so that the JPanel only renders them, then when the
	// player moves past the
	// view of that block it will be removed from this list and no longer rendered
	// (this list is updated
	// every millisecond on a timer because that way all of those calculations can
	// be done on a
	// different thread and not cause any delays in this one which renders the
	// window)
	private LinkedList<Block> renderBlocks = new LinkedList<Block>();

	// This list is the same as the renderBlocks except it stores all rendered props
	private LinkedList<Prop> renderProps = new LinkedList<Prop>();

	// This is the timer that will fire every millisecond and refresh the panel and
	// update the image of
	// Mario
	private Timer refresh = new Timer(REFRESHRATE, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			currentImage = getCurrentImage();

			mainPanel.updateUI();

		}

	});
	
	

	private Timer slideDownPole = new Timer(SPEED * 2, new ActionListener()  {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (yCord + currentImage.getHeight(observer) < flagPole.getYCord() + flagPole.getImageIcon().getIconHeight() && yCord <= flag.getYCord()) {
				yCord += MOVELENGTH; 
			} else if (yCord + currentImage.getHeight(observer) + MOVELENGTH >= flagPole.getYCord() + flagPole.getImageIcon().getIconHeight()){
				jump.stop();
				xCord = flagPole.getXCord() + flagPole.getImageIcon().getIconWidth() - scroll - 31;
				trueX = xCord + scroll;
				slideDownPole.stop();
				turnAround = true;
				walkToCastle.setInitialDelay(750);
				walkToCastle.start();
			}
			if (flag.getYCord() + flag.getImageIcon().getIconHeight() < flagPole.getYCord() + flagPole.getImageIcon().getIconHeight()) {
				flag.changeYCord(MOVELENGTH);
			}

		}

	});
	
	private Timer walkToCastle = new Timer(SPEED * 3, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			turnAround = false;
			if(trueX + currentImage.getWidth(observer) < castle.getXCord()+145) {
				moveForward();
			}
			else {
				visible=false;
			}
			
		}
		
	});

	// This is the timer that will update the renderBlocks list so that only nearby
	// blocks are rendered.
	// It is done like this so that the update will occur in a separate thread and
	// not delay this one
	// since the number of blocks that are in a level could be in the hundreds
	private Timer render = new Timer(REFRESHRATE, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			for (int i = 0; i < allBlocks.size(); i++) {

				// If the block is within "one stage" of Mario
				if (allBlocks.get(i).getXCord() - backgroundImage.getWidth(observer) <= trueX
						&& !renderBlocks.contains(allBlocks.get(i))) {

					// Adds the block from those that need to be rendered
					renderBlocks.add(allBlocks.get(i));

				}

				// If the block is "one stage" behind Mario
				if (allBlocks.get(i).getXCord() + backgroundImage.getWidth(observer) <= trueX) {

					// Removes the block from those that need to be rendered
					renderBlocks.remove(allBlocks.get(i));

				}

			}

			for (int i = 0; i < allProps.size(); i++) {

				if (allProps.get(i).getXCord() - backgroundImage.getWidth(observer) <= trueX
						&& !renderProps.contains(allProps.get(i))) {

					// Adds the prop from those that need to be rendered
					renderProps.add(allProps.get(i));

				}

				// If the prop is "one stage" behind Mario
				if (allProps.get(i).getXCord() + backgroundImage.getWidth(observer) <= trueX) {

					// Removes the prop from those that need to be rendered
					renderProps.remove(allProps.get(i));

				}

			}
			
			for (int i = 0; i < allSprites.size(); i++) {

				if (allSprites.get(i).getXCord() - backgroundImage.getWidth(observer) <= trueX
						&& !renderSprites.contains(allSprites.get(i))) {

					// Adds the sprite from those that need to be rendered
					renderSprites.add(allSprites.get(i));

				}

				// If the sprite is "one stage" behind Mario
				if (allSprites.get(i).getXCord() + backgroundImage.getWidth(observer) <= trueX) {

					// Removes the sprite from those that need to be rendered
					renderSprites.remove(allSprites.get(i));

				}

			}

		}

	});

	// This is the timer that handles gravity, it is done like this so that it very
	// easy and efficient
	// to determine if gravity needs to be in effect and if so activate it
	private Timer gravity = new Timer(SPEED, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			// Checks if Mario is currently jumping because if so we don't want to inflict
			// gravity
			// Mario obeys no physics :)
			if (!jump.isRunning()) {

				Image temp = getFrameImage();

				for (int i = 0; i < MOVELENGTH; i++) {

					// If Mario is above the floor
					if (yCord + temp.getHeight(observer) < getFloor()) {

						yCord++;

					} else {

						break;

					}

				}
			}
		}

	});

	// This timer is for esthetics more than anything else. What it does is whenever
	// the player is not moving
	// for 1 second it will reset the image to the basic standing image.
	private Timer standing = new Timer(1000, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			stand = true;

			standing.stop();
		}

	});

	// This is the timer that handles a jump, this timer updates every frame that a
	// jump is in motion.
	// It is done like this so that there is a smooth animation for jumps, and so
	// that the key input for
	// it is handled nicely
	private Timer jump = new Timer(SPEED, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (!endingAnimation) {

				int factor = 0;

				// If he is supposed to do a short hop
				if (shortHop) {
					factor = 2;
				} else {
					factor = 1;
				}

				// Makes the jump take off smoother
				if (jumping && jumpCount <= 5) {
					fixMovement();
				}

				// If Mario has jumped the given jump height
				if (jumpCount * MOVELENGTH < JUMPHEIGHT / factor && yCord > getBottomFloor()) {

					fixMovement();
					yCord -= MOVELENGTH;
					jumping = true;
					jumpCount++;

				} else {

					// Gets what Mario's image would be if he wasn't jumping or standing
					Image temp = getFrameImage();

					// If the jump needs to be fixed
					if (!fixJump && jumpCount != 0) {
						fixJump = true;
					}

					// Sets the jumpCount to the max because if it is at this point in the program
					// it is done
					// jumping
					jumpCount = JUMPHEIGHT / MOVELENGTH;

					// If Mario is not on the floor
					if (yCord + temp.getHeight(observer) < getFloor()) {

						yCord += MOVELENGTH;
						jumping = true;
						jumpCount++;

						// Makes landing smoother
						while (yCord + temp.getHeight(observer) + MOVELENGTH >= getFloor() && fixJump) {

							yCord--;
							fixJump = false;

						}

						fixMovement();

					} else { // If he is on the floor

						jumping = false;
						shortHop = true;

						fixMovement();

						jumpCount = 0;
						jump.stop();
						jumpSound.restart();

						// This is so that the standing timer doesn't overlap with the movement timers
						// and make the image standing while the player is moving
						if (!rightMove.isRunning() && !leftMove.isRunning()) {

							standing.start();

						}
					}

				}
			}

		}

	});

	private Timer shortJump = new Timer(300, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			shortHop = false;
			shortJump.stop();

		}

	});

	// This is the timer that handles movement to the left of the screen (similar to
	// jump timer)
	private Timer leftMove = new Timer(SPEED, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			// If Mario is currently moving right
			if (!rightMove.isRunning() && !endingAnimation) {
				
				if (!jump.isRunning()) {
					fixMovement();
				}
				
				moveBack();
				
				back = true;

				// If Mario is running into a barrier
				while (inBlock() && !jump.isRunning()) {

					moveForward(); // Undo the moveBack

				}
			}

		}

	});

	// This is the timer that handles movement to the right of the screen (similar
	// to left movement timer)
	private Timer rightMove = new Timer(SPEED, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (!leftMove.isRunning() && !endingAnimation) {
				
				if (!jump.isRunning()) {
					fixMovement();
				}
				
				moveForward();
				

				// This is so that the frame doesn't get too large, since we are modding it
				// anyway it can
				// reset

				if (frame == 100) {

					frame = 0;

				}
				back = false;

				// If Mario is running into a barrier
				while (inBlock() && !jump.isRunning()) {

					moveBack(); // Undo the moveForward

				}
			}

		}

	});

	// This is where all the action happens, this the main JPanel that renders
	// everything
	private JPanel mainPanel = new JPanel() {

		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);

			if (flagPole.getCollider().intersects(getMarioRectangle()) && !endingAnimation) {

				playEndingAnimation();

			}

			// Draws the 3 backgrounds
			g.drawImage(backgroundImage, backX, 0, observer);
			g.drawImage(backgroundImage, backX2, 0, observer);
			g.drawImage(backgroundImage, backX3, 0, observer);

			// Draws the blocks that need to be rendered
			for (int i = 0; i < renderBlocks.size(); i++) {

				g.drawImage(renderBlocks.get(i).getImage(), renderBlocks.get(i).getXCord() - scroll,
						renderBlocks.get(i).getYCord(), observer);

			}

			// Draws the props that need to be rendered
			for (int i = 0; i < renderProps.size(); i++) {

				g.drawImage(renderProps.get(i).getImage(), renderProps.get(i).getXCord() - scroll,
						renderProps.get(i).getYCord(), observer);

			}
			
			for (int i = 0; i < renderSprites.size(); i++) {

				g.drawImage(renderSprites.get(i).nextImage(), renderSprites.get(i).getXCord() - scroll,
						renderSprites.get(i).getYCord(), observer);

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

		mainPanel.setLayout(new BorderLayout()); // Not needed, but is there for the moment

		setKeyBindings(); // Sets up the key input tracker so that key inputs are monitored

		setStage(levelData); // Sets all the interactions for the current level

		startAnimation(); // Starts up the panel and renders the animation

		// Mute the sounds
		muteSounds();

		backgroundMusic.loop();
		backgroundMusic.play();

	}

	// This is the method that determines where the floor is in relation to Mario's
	// x coordinate
	private int getFloor() {

		// The default return is the brick floor
		int answer = SCREENHEIGHT - 107;

		for (int i = 0; i < renderBlocks.size(); i++) {

			// If this block is higher up than the // If this block is below Mario
			// If the block is where Mario is currently at // current answer
			if ((endOfImageXOverlaps(i, renderBlocks) || startOfImageXOverlaps(i, renderBlocks))
					&& renderBlocks.get(i).getYCord() < answer && yCord < renderBlocks.get(i).getYCord()) {

				answer = renderBlocks.get(i).getYCord();

			}

		}

		for (int i = 0; i < renderProps.size(); i++) {

			// If this prop is closer to Mario than the current
			// If the prop is where Mario is currently at // answer // If this prop is below
			// Mario // If the prop is obstructive to Mario
			if ((endOfImageXOverlaps(i, renderProps, true) || startOfImageXOverlaps(i, renderProps, true))
					&& renderProps.get(i).getYCord() < answer && yCord < renderProps.get(i).getYCord()
					&& renderProps.get(i).isObstructive()) {

				answer = renderProps.get(i).getYCord();

			}

		}

		return answer;

	}

	private int getBottomFloor() {

		// The default return is one jump height above the top of the screen
		int answer = -JUMPHEIGHT;

		for (int i = 0; i < renderBlocks.size(); i++) {

			// If this block is closer to Mario than the current // If this block is above
			// Mario
			// If the block is where Mario is currently at // answer
			if ((endOfImageXOverlaps(i, renderBlocks) || startOfImageXOverlaps(i, renderBlocks))
					&& yCord - (renderBlocks.get(i).getYCord() + BLOCKWIDTH) < yCord - answer
					&& yCord > renderBlocks.get(i).getYCord()) {

				answer = renderBlocks.get(i).getYCord() + BLOCKWIDTH;

			}

		}

		return answer;

	}

	// Returns true or false based on whether or not the right side of Mario's image
	// overlaps with a block
	private boolean endOfImageXOverlaps(int i, LinkedList<Block> list) {

		return (trueX + currentImage.getWidth(observer) > list.get(i).getXCord()
				&& trueX + currentImage.getWidth(observer) < list.get(i).getXCord() + BLOCKWIDTH);

	}

	// Returns true or false based on whether or not the left side of Mario's image
	// overlaps with a block
	private boolean startOfImageXOverlaps(int i, LinkedList<Block> list) {

		return (trueX > list.get(i).getXCord() && trueX < list.get(i).getXCord() + BLOCKWIDTH);

	}

	// Returns true or false based on whether or not the right side of Mario's image
	// overlaps with a prop
	private boolean endOfImageXOverlaps(int i, LinkedList<Prop> list, boolean prop) {

		return (trueX + currentImage.getWidth(observer) > list.get(i).getXCord() && trueX
				+ currentImage.getWidth(observer) < list.get(i).getXCord() + list.get(i).getImageIcon().getIconWidth());

	}

	// Returns true or false based on whether or not the left side of Mario's image
	// overlaps with a prop
	private boolean startOfImageXOverlaps(int i, LinkedList<Prop> list, boolean prop) {
 
		return (trueX > list.get(i).getXCord()
				&& trueX < list.get(i).getXCord() + list.get(i).getImageIcon().getIconWidth());

	}

	// This method now works! It returns true or false based on whether or not Mario
	// has collided with an image
	private boolean inBlock() {

		boolean answer = false;

		for (int i = 0; i < renderBlocks.size(); i++) {

			if (renderBlocks.get(i).getRectangle().intersects(getMarioRectangle())) {

				answer = true;

				return answer;

			}

		}

		for (int i = 0; i < renderProps.size(); i++) {

			if (renderProps.get(i).getCollider().intersects(getMarioRectangle())
					&& renderProps.get(i).isObstructive()) {

				answer = true;

				return answer;

			}

		}

		return answer;

	}

	// Returns the invisible rectangle that is around Mario
	private Rectangle getMarioRectangle() {

		return new Rectangle(trueX, yCord, currentImage.getWidth(observer), currentImage.getHeight(observer));

	}

	// Returns the full path for the current image
	private Image getExt(Image image) {

		// This method is mainly self explanatory

		Image answer;

		if (!back) {

			if (jumping) {

				answer = JUMPINGIMAGE;

			} else {

				answer = image;

			}

		} else {

			if (jumping) {

				answer = JUMPINGIMAGE_BACK;

			} else {

				answer = getBackImage(image);

			}
		}

		return answer;

	}
	
	private Image getBackImage(Image image) {
		
		if(image.equals(WALKINGIMAGE1)) {
			
			return WALKINGIMAGE1_BACK;
			
		} else if(image.equals(WALKINGIMAGE2)) {
			
			return WALKINGIMAGE2_BACK;
			
		} else if(image.equals(WALKINGIMAGE3)) {
			
			return WALKINGIMAGE3_BACK;
			
		} else {
			
			return STANDINGIMAGE;
			
		}
		
	}

	// Sets up the key inputs (now this method is probably the most complex (at
	// least for me) because
	// I did not know what input maps and action maps are, so if you don't
	// understand don't worry about it)
	private void setKeyBindings() {
		// The action map for the main panel (theoretically this could be for any object
		// but I chose this
		// one)
		ActionMap actionMap = mainPanel.getActionMap();

		// The input map
		InputMap inputMap = mainPanel.getInputMap();

		// Assigns the key strokes with a "key"
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "left_pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "right_pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "space_pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "space_released");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "left_released");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "right_released");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "up_pressed");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "up_released");

		// Assigns the "key" to an action
		actionMap.put("left_pressed", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {

				leftMove.start();
				standing.stop();
				stand = false;

			}
		});
		
		actionMap.put("left_released", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {

				leftMove.stop();

				if (!rightMove.isRunning()) {
					standing.start();
				}
			}
		});
		
		actionMap.put("right_pressed", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {

				rightMove.start();
				standing.stop();
				stand = false;

			}
		});
		
		actionMap.put("right_released", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {

				rightMove.stop();
				if (!leftMove.isRunning()) {
					standing.start();
				}
			}
		});
		
		actionMap.put("space_pressed", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {

				standing.stop();
				stand = false;

				if (!jump.isRunning()) {

					jumpSound.play();
					jump.start();
					shortJump.start();

				}

			}
		});

		actionMap.put("space_released", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {

				if (shortJump.isRunning()) {
					shortJump.stop();
				}

			}
		});
		
		actionMap.put("up_pressed", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {

				standing.stop();
				stand = false;

				if (!jump.isRunning()) {

					jumpSound.play();
					jump.start();
					shortJump.start();

				}

			}
		});

		actionMap.put("up_released", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent actionEvt) {

				if (shortJump.isRunning()) {
					shortJump.stop();
				}

			}
		});

	}

	// Mutes all sound effects
	private void muteSounds() {

		backgroundMusic.setVolume(0);
		jumpSound.setVolume(0);

	}

	// Method for moving towards the left side of the screen
	private void moveBack() {

		xCord -= MOVELENGTH;
		trueX -= MOVELENGTH;
		frame++;

	}

	// Method for moving towards the right side of the screen
	private void moveForward() {

		xCord += MOVELENGTH;
		trueX += MOVELENGTH;
		frame++;

	}

	// Fixes Mario's current image so that it doesn't clip into blocks
	private void fixMovement() {

		if (inBlock()) {
			if (back) {
				moveForward();
			} else {
				moveBack();
			}
		}

	}

	// This is the method that determines the current image based on multiple
	// factors such as direction
	// Mario is facing and the frame number
	private Image getCurrentImage() {

		Image answer;
		if(!visible) {
			return new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "BlankImage.png")).getImage();
		}
		if (!endingAnimation ) {

			// Starting frame
			if (frame == 0 && !jumping) {

				return STANDINGIMAGE;

			}

			if (stand) {

				if (!back) {

					return STANDINGIMAGE;

				} else {

					return STANDINGIMAGE_BACK;

				}

			}

			// Some calculations that are way more complex than it needs to be but its is
			// like this so it is
			// more easily changeable

			if (frame % (int) (Math.pow(SPEED, 2)) < (int) (Math.pow(SPEED, 2) / 3)) {

				answer = getExt(WALKINGIMAGE1);

			} else if (frame % (int) (Math.pow(SPEED, 2)) < (int) (Math.pow(SPEED, 2) / 3 * 2)) {

				answer = getExt(WALKINGIMAGE2);

			} else {

				answer = getExt(WALKINGIMAGE3);

			}

		} else if(slideDownPole.isRunning()) {
			answer = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "MarioFlagPole.png")).getImage();
		}
		else if(turnAround) {
			answer = new ImageIcon(getClass().getClassLoader().getResource(EXTENSION + "MarioFlagPole_back.png")).getImage();
		}
		else {
			back = false;
			answer = getFrameImage();
		}

		return answer;

	}

	// This is a method merely to get rid of repeating code I made it because in
	// multiple places I needed
	// to know the height of the image standing, instead of jumping even though the
	// current image was
	// jumping
	private Image getFrameImage() {

		Image answer;

		if (frame % (int) (Math.pow(SPEED, 2)) < (int) (Math.pow(SPEED, 2) / 3)) {

			answer = WALKINGIMAGE1;

		} else if (frame % (int) (Math.pow(SPEED, 2)) < (int) (Math.pow(SPEED, 2) / 3 * 2)) {

			answer = WALKINGIMAGE2;

		} else {

			answer = WALKINGIMAGE3;

		}

		return answer;

	}

	// This method is where all the blocks for the level are set, this is where most
	// of our development
	// is going to go from now on as it is the basis for the entire level
	private void setStage(DataReader data) {

		LinkedList<String[]> temp = data.getAllStairBlocks();

		for (int i = 0; i < temp.size(); i++) {

			allBlocks.add(new StairBlock((int) (BLOCKSPACING * (Integer.parseInt(temp.get(i)[1])) - 1),
					(int) (BASEFLOOR - (Integer.parseInt(temp.get(i)[2]) * BLOCKSPACING))));

		}

		LinkedList<String[]> temp2 = data.getAllBrickBlocks();

		for (int i = 0; i < temp2.size(); i++) {

			allBlocks.add(new BrickBlock((int) (BLOCKSPACING * (Integer.parseInt(temp2.get(i)[1])) - 1),
					(int) (BASEFLOOR - (Integer.parseInt(temp2.get(i)[2]) * BLOCKSPACING))));

		}

		LinkedList<String[]> temp3 = data.getAllQuestionMarkBlocks();

		for (int i = 0; i < temp3.size(); i++) {

			allBlocks.add(new QuestionMarkBlock((int) (BLOCKSPACING * (Integer.parseInt(temp3.get(i)[1])) - 1),
					(int) (BASEFLOOR - (Integer.parseInt(temp3.get(i)[2]) * BLOCKSPACING))));

		}

		LinkedList<String[]> temp4 = data.getAllSmallPipes();

		for (int i = 0; i < temp4.size(); i++) {

			allProps.add(new ShortPipe((int) (BLOCKSPACING * (Integer.parseInt(temp4.get(i)[1])) - 1),
					(int) (BASEFLOOR - (Integer.parseInt(temp4.get(i)[2]) * BLOCKSPACING))));

		}

		LinkedList<String[]> temp5 = data.getAllPipes();

		for (int i = 0; i < temp5.size(); i++) {

			allProps.add(new Pipe((int) (BLOCKSPACING * (Integer.parseInt(temp5.get(i)[1])) - 1),
					(int) (BASEFLOOR - (Integer.parseInt(temp5.get(i)[2]) * BLOCKSPACING))));

		}

		LinkedList<String[]> temp6 = data.getAllLongPipes();

		for (int i = 0; i < temp6.size(); i++) {

			allProps.add(new LongPipe((int) (BLOCKSPACING * (Integer.parseInt(temp6.get(i)[1])) - 1),
					(int) (BASEFLOOR - (Integer.parseInt(temp6.get(i)[2]) * BLOCKSPACING))));

		}
		
		LinkedList<String[]> temp7 = data.getAllGoombas();

		for (int i = 0; i < temp7.size(); i++) {

			allSprites.add(new Goomba((int) (BLOCKSPACING * (Integer.parseInt(temp7.get(i)[1])) - 1),
					(int) (BASEFLOOR - (Integer.parseInt(temp7.get(i)[2]) * BLOCKSPACING))));

		}
		
		LinkedList<String[]> temp8 = data.getAllKoopas();

		for (int i = 0; i < temp8.size(); i++) {

			allSprites.add(new Koopa((int) (BLOCKSPACING * (Integer.parseInt(temp8.get(i)[1])) - 1),
					(int) (BASEFLOOR - (Integer.parseInt(temp8.get(i)[2]) * BLOCKSPACING))));

		}
		
		flagPole = new FlagPole((int) (BLOCKSPACING * (Integer.parseInt(data.getFlagPole()[1])) - 1),
		(int) (BASEFLOOR - (Integer.parseInt(data.getFlagPole()[2]) * BLOCKSPACING)));
		allProps.add(flagPole);
		flag = (new Flag((int) (BLOCKSPACING * (Integer.parseInt(data.getFlagPole()[1])) - 1),
				(int) (BASEFLOOR - ((Integer.parseInt(data.getFlagPole()[2]) - 1) * BLOCKSPACING))));
		allProps.add(flag);
		castle = new EndCastle((int) (BLOCKSPACING * (Integer.parseInt(data.getEndCastle()[1])) - 1),
				(int) (BASEFLOOR - (Integer.parseInt(data.getEndCastle()[2]) * BLOCKSPACING)));
		allProps.add(castle);

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

	private void playEndingAnimation() {

		trueX = flagPole.getXCord()+flagPole.getImageIcon().getIconWidth() - 24 - new ImageIcon(getClass().getClassLoader().getResource("MarioImages/MarioFlagPole.png")).getIconWidth();
		xCord=trueX-scroll;
		endingAnimation = true;
		slideDownPole.start();
		

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