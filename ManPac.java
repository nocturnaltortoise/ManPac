import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ManPac extends JPanel implements KeyListener, ActionListener {

	public int redScore = 0;
	public int blueScore = 0;
	public int greenScore = 0;
	public int redWon = 0;
	public int blueWon = 0;
	public int greenWon = 0;
	int level = 0;
	String winner = "EmptyState";
	boolean isLevelComplete = false;
	boolean gameStarted = false;
	boolean instructionsDrawn =false;

	static Point redStart = new Point(13 * 32 + 16, 16 * 32);
	static Point blueStart = new Point(13 * 32 + 16, 12 * 32);
	static Point greenStart = new Point(13 * 32 + 16, 7 * 32);

	/*TilemapImg is used to store an image. This specific image is 
*drawn on the map in parts.*/

	private static Image TilemapImg;
	private static Image CreepImg;
	private static Image InstructionsImg;

/* TextureMap 2D array is used to store an integer.This integer    *determines which tile has to be drawn in that array position, 
*with top left corner being the origin and array position is *multiplied by 32. */

	public int[][] textureMap = new int[28][25];

	/* Creates object arrays of the game, such as the red, walls, 
 * pick-ups and enemies. Also initialises event object 'timer' 
 * and initialises invisible rectangle 'tempRect' that is used to 	 *calculate tile array values. */

	Brick[] bricks = new Brick[Brick.brickArray.length];
	Pellet[] pellets = new Pellet[Pellet.pelletArray.length];
	Powerup[] powerups = new Powerup[Powerup.powerupArray.length];
	static Player red = new Player(redStart.x, redStart.y);
	static Player blue = new Player(blueStart.x, blueStart.y);
	static Player green = new Player(greenStart.x, greenStart.y);
	Timer timer = new Timer(16, this);
	Rectangle tempRect = new Rectangle(0, 0, 32, 32);

	Rectangle redSprite = new Rectangle(0, 0, 32, 32);
	Rectangle greenSprite = new Rectangle(64, 0, 32, 32);
	Rectangle blueSprite = new Rectangle(128, 0, 32, 32);

	long startLevelTime, endLevelTime;
	static String lastText = "tempText";
static long timeSinceLastText = System.currentTimeMillis() - 1000;

	JButton play = new JButton("Play");
	JButton instructions = new JButton("How to play");
	JButton exit = new JButton("Exit");
	JButton close = new JButton("Close instructions");

	/* Constructor of the game. It initialises the objects by
 * pulling the data from respective arrays. */

	public ManPac() {
		for (int i = 0; i < bricks.length; i++) {
			bricks[i] = new Brick(Brick.brickArray[i][0] * 32,
Brick.brickArray[i][1] * 32, Brick.brickArray[i][2] * 32,
					Brick.brickArray[i][3] * 32);
		}
		for (int i = 0; i < pellets.length; i++) {
			pellets[i] = new Pellet(Pellet.pelletArray[i][0],
						 Pellet.pelletArray[i][1]);
		}
		for (int i = 0; i < powerups.length; i++) {
			powerups[i] = new Powerup(Powerup.powerupArray[i][0],
						 Powerup.powerupArray[i][1]);
		}

		

/*This part of code sets up the Jbuttons so they can be used * visible on the screen. */

play.setFocusable(false);
		this.add(play);
		play.addActionListener(this);
		play.setBounds(13 * 32, 5 * 32 + 16, 64, 32);
		
		instructions.setFocusable(false);
		this.add(instructions);
		instructions.addActionListener(this);
		instructions.setBounds(12 * 32, 14 * 32, 128, 32);
		
		close.setFocusable(false);
		this.add(close);
		close.addActionListener(this);
		close.setBounds(25 * 32, 1 * 32 + 16, 160, 32);
		
		exit.setFocusable(false);
		this.add(exit);
		exit.addActionListener(this);
		exit.setBounds(13 * 32, 18 * 32, 64, 32);
		this.setLayout(null);

/* It's a simple double for loop that runs mapTileCheck for every * array position. The values are stored for every tile position * on the map in a 2D array. This is later used to draw the map.  * This method is called only once, as the map does not change, * for optimisation reasons. */

		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 28; j++) {
				textureMap[j][i] = mapTileCheck(j, i);
			}
		}
	}

/* This initialises the game values, and is maily used when the game * is started or restarted, so no results from the last game session * are retained. */
	private void startGame() {
		gameStarted = true;
		level = 0;
		redScore = 0;
		blueScore = 0;
		greenScore = 0;
		redWon = 0;
		blueWon = 0;
		greenWon = 0;
		startLevel();
	}

/* On a key press, it runs handlePlayerInput and passes the key 
* pressed. */

	public void keyPressed(KeyEvent event) {
		handlePlayerInput(event.getKeyCode());

	}

/* Checks which keys are pressed. Then it sets a specific direction * value for corresponding player by calling it’s setInputDir() 
* method.*/

	private void handlePlayerInput(int keyInt) {
		if (keyInt == KeyEvent.VK_S) {
			red.setInputDir(3);
		}
		if (keyInt == KeyEvent.VK_A) {
			red.setInputDir(0);
		}
		if (keyInt == KeyEvent.VK_D) {
			red.setInputDir(2);
		}
		if (keyInt == KeyEvent.VK_W) {
			red.setInputDir(1);
		}
		if (keyInt == KeyEvent.VK_K) {
			green.setInputDir(3);
		}
		if (keyInt == KeyEvent.VK_J) {
			green.setInputDir(0);
		}
		if (keyInt == KeyEvent.VK_L) {
			green.setInputDir(2);
		}
		if (keyInt == KeyEvent.VK_I) {
			green.setInputDir(1);
		}
		if (keyInt == KeyEvent.VK_DOWN) {
			blue.setInputDir(3);
		}
		if (keyInt == KeyEvent.VK_LEFT) {
			blue.setInputDir(0);
		}
		if (keyInt == KeyEvent.VK_RIGHT) {
			blue.setInputDir(2);
		}
		if (keyInt == KeyEvent.VK_UP) {
			blue.setInputDir(1);
		}
	}

	
/* Update command runs when Timer actionListener calls it, which is * usually every 1/60th of a second. Update method handles all of the * required calls, such as calling collision checks, score updates, * and checking whether the game should finish now or not. */

	public void update() {
		red.mapCollision(bricks);
		blue.mapCollision(bricks);
		green.mapCollision(bricks);

		redScore = redScore + red.pelletCollision(pellets);
		redScore = redScore + red.powerupCollision(powerups);

		blueScore = blueScore + blue.pelletCollision(pellets);
		blueScore = blueScore + blue.powerupCollision(powerups);

		greenScore = greenScore + green.pelletCollision(pellets);
		greenScore = greenScore + green.powerupCollision(powerups);

/* This makes sure that the when the round starts all players start * moving at the same speed. This is done by checking if the round has * started and checking if ALL of the players have speed of 0. If that * is true, all players have their speed s3et to 2, allowing them to * move. The only case where all players can have speed of 0 is before * the round start. */

		while (System.currentTimeMillis() > startLevelTime
				&& red.playerSpeed == 0 && blue.playerSpeed == 0
				&& green.playerSpeed == 0) {
			red.playerSpeed = 2;
			blue.playerSpeed = 2;
			green.playerSpeed = 2;
		}
	
/* Just as it says, checks whether time has ran out or all pellets * were eaten, and ends the round. */

if (System.currentTimeMillis() > endLevelTime || allPelletsEaten()) {
			endLevel();
		}

	// Starts the new round 3 secons after the end of the last round.
		if (System.currentTimeMillis() > endLevelTime + 3000) {
			startLevel();
		}
		





	// Controls which buttons should be drawn at that moment.
		if(gameStarted || instructionsDrawn){
			play.setVisible(false);
			instructions.setVisible(false);
			exit.setVisible(false);
		}else{
			play.setVisible(true);
			instructions.setVisible(true);
			exit.setVisible(true);
		}
		if(instructionsDrawn){
			close.setVisible(true);
		}else{
			close.setVisible(false);
		}
	}

	/* startLevel() method is called whenever a new round is required. 
	* It increments the round number, refreshes pellets and powerups, 
* randomises the spawns of the players and restarts round 
* timers, such as startLevelTime and endLevelTime. */

	private void startLevel() {
		if(gameStarted){
			level++;

			for (int i = 0; i < pellets.length; i++) {
				pellets[i].setDestroyed(false);
			}
			
			for (int i = 0; i < powerups.length; i++) {
				powerups[i].setDestroyed(false);
powerups[i].caseNumber = (int) (3.d * Math.random());
			}
			int tempSpawnRed = 0, 
    tempSpawnBlue = 0, 
    tempSpawnGreen = 0;
			while (tempSpawnRed == tempSpawnBlue || 
  tempSpawnRed == tempSpawnGreen || 
  tempSpawnGreen == tempSpawnBlue) {
				tempSpawnRed = (int) (3.d * Math.random());
				tempSpawnBlue = (int) (3.d * Math.random());
				tempSpawnGreen = (int) (3.d * Math.random());
			}
			
			if (tempSpawnRed == 0) {
				red.x = redStart.x;
				red.y = redStart.y;
			} else if (tempSpawnRed == 1) {
				red.x = blueStart.x;
				red.y = blueStart.y;
			} else {
				red.x = greenStart.x;
				red.y = greenStart.y;
			}
			
			if (tempSpawnBlue == 0) {
				blue.x = redStart.x;
				blue.y = redStart.y;
			} else if (tempSpawnBlue == 1) {
				blue.x = blueStart.x;
				blue.y = blueStart.y;
			} else {
				blue.x = greenStart.x;
				blue.y = greenStart.y;
			}

			if (tempSpawnGreen == 0) {
				green.x = redStart.x;
				green.y = redStart.y;
			} else if (tempSpawnGreen == 1) {
				green.x = blueStart.x;
				green.y = blueStart.y;
			} else {
				green.x = greenStart.x;
				green.y = greenStart.y;
			}

			red.playerSpeed = 0;
			red.setCurrDir(0);
			redScore = 0;
			blue.playerSpeed = 0;
			blue.setCurrDir(0);
			blueScore = 0;
			green.playerSpeed = 0;
			green.setCurrDir(0);
			greenScore = 0;

			startLevelTime = System.currentTimeMillis() + 5000;
			endLevelTime = startLevelTime + 40000;
		}
	}

/* endLevel() method deals with the end of the round. It stops the players, determines who won the round. */

	private void endLevel() {
		red.playerSpeed = 0;
		blue.playerSpeed = 0;
		green.playerSpeed = 0;
		while (redWon + blueWon + greenWon < level) {
			if (redScore > greenScore && redScore > blueScore) {
				redWon++;
				winner = "Red player";
			} else if (blueScore > greenScore && blueScore > redScore) {
				blueWon++;
				winner = "Blue Player";
} else if (greenScore > redScore && greenScore > blueScore) {
				greenWon++;
				winner = "Green Player";
			}
		}
/* Buffer to make sure that the level does not start too early. */ 
		startLevelTime = System.currentTimeMillis() + 100000; 

		if (level == 7) {
			endLevelTime = System.currentTimeMillis();
			gameStarted = false;
		}
	}

	/* This method is used when checking if the round has ended by 
* all pellets being eaten. It lopps through all of the pellets, and * checks whether they been eaten. If all of them return true, the 
* method also returns true. */

	private boolean allPelletsEaten() {
		if (isLevelComplete) {
			return true;
		}
		for (int i = 0; i < pellets.length; i++) {
			if (!pellets[i].getDestroyed()) {
				return false;
			}
		}
		endLevelTime = System.currentTimeMillis();
		return true;
	}

/* This is used to calculate which tile should be drawn from the 
* tilemap, and where. First it creates a temporary array, which stores * the data. Then it uses earlier created tempRect to see if a specific * position has a map collision, and stores data in the tempArray. Once * all 9 positions around the square have been calculated, method 
* compareCase is called, with tempArray as the argument. mapTileCheck * can return values from 0 to 12, each representing a tilemap position. */

	public int mapTileCheck(int tileX, int tileY) {
		int[] tempArray = new int[9];
		int tempCounter = 0;

		
for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tempRect.x = (tileX + j - 1) * 32;
				tempRect.y = (tileY + i - 1) * 32;

	//tempCounter is used to determine where the value will be stored

				tempCounter = i * 3 + j;

				for (int k = 0; k < bricks.length; k++) {
					if (tempRect.intersects(bricks[k])) {
						tempArray[tempCounter] = 1;
					}
				}
				if (tempArray[tempCounter] != 1) {
					tempArray[tempCounter] = 0;
				}
			}
		}

		return compareCase(tempArray);

	}

	public static void main(String[] args) { 
//    Window initialisers(Title, size, misc. stuff)
		ManPac window = new ManPac();
		JFrame frame = new JFrame("ManPac: The Game!!!");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1100, 828);
		frame.setResizable(false);
		frame.setContentPane(window);

		// Allows the window to be seen on the screen

		frame.setVisible(true);
		frame.addKeyListener(window);

/* This part loads the files needed for the game. Those files * are surrounded in try catch block, to check if file can be * loaded. If it cannot, an error is thrown and filename is 
* displayed. */

		try {
			TilemapImg = ImageIO.read(new File("Tileset.png"));
		} catch (IOException ex) {
			Logger.getLogger(ManPac.class.getName())
					.log(Level.SEVERE, null, ex);
		}

		try {
			CreepImg = ImageIO.read(new File("creeppellet.png"));
		} catch (IOException ex) {
			Logger.getLogger(ManPac.class.getName())
					.log(Level.SEVERE, null, ex);
		}
		
		try {
InstructionsImg = ImageIO.read(new File("instructions.png"));
		} catch (IOException ex) {
			Logger.getLogger(ManPac.class.getName())
					.log(Level.SEVERE, null, ex);
		}


	// Starts the update sequence, allowing for input to be taken and used.
		window.timer.start();
	}

	

	@Override
	public void keyReleased(KeyEvent event) {
		//TODO handlePlayerInput(event.getKeyChar());
	}

	
	// This function is required in the code at all times, but is not used
	

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

/* actionPerformed is called whenever any of the actionListeners have  * been called. That may be Timer reaching 16 miliseconds since the * last update or a Jbutton being pressed. */ 

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == timer) {
			update();
			repaint();
		}

		if (event.getSource() == play) {
			startGame();
		}
		
		if (event.getSource() == instructions) {
			instructionsDrawn = true;
		}
		
		if(event.getSource() == exit){
			System.exit(0);
		}
		if(event.getSource() == close){
			instructionsDrawn = false;
		}

	}

/* This method is called in a mapTileCheck return line. It compares * array that is passed as an argument with another all of the arrays * from ArrayStore class. If a match is found, it returns corresponding * value as tempInt to mapTileCheck, which then returns that integer.
	*/

	private int compareCase(int[] tempArray) {
		int tempInt = 0;
		if (ArrayStore.equals(tempArray, ArrayStore.mapTileCase1)) {
			tempInt = 1;
} else if (ArrayStore.equals(tempArray, ArrayStore.mapTileCase2)
|| ArrayStore.equals(tempArray, ArrayStore.mapTileCase13)
|| ArrayStore.equals(tempArray, ArrayStore.mapTileCase14)) {
			tempInt = 2;
} else if (ArrayStore.equals(tempArray, ArrayStore.mapTileCase3)) {
			tempInt = 3;
} else if (ArrayStore.equals(tempArray, ArrayStore.mapTileCase4)
|| ArrayStore.equals(tempArray, ArrayStore.mapTileCase15)
|| ArrayStore.equals(tempArray, ArrayStore.mapTileCase16)) {
			tempInt = 4;
} else if (ArrayStore.equals(tempArray, ArrayStore.mapTileCase5)
|| ArrayStore.equals(tempArray, ArrayStore.mapTileCase17)
|| ArrayStore.equals(tempArray, ArrayStore.mapTileCase18)) {
			tempInt = 5;
} else if (ArrayStore.equals(tempArray, ArrayStore.mapTileCase6)) {
			tempInt = 6;
} else if (ArrayStore.equals(tempArray, ArrayStore.mapTileCase7)
|| ArrayStore.equals(tempArray, ArrayStore.mapTileCase19)
|| ArrayStore.equals(tempArray, ArrayStore.mapTileCase20)) {
			tempInt = 7;
} else if (ArrayStore.equals(tempArray, ArrayStore.mapTileCase8)) {
			tempInt = 8;
} else if (ArrayStore.equals(tempArray, ArrayStore.mapTileCase9)) {
			tempInt = 9;
} else if (ArrayStore.equals(tempArray, ArrayStore.mapTileCase10)) {
			tempInt = 10;
} else if (ArrayStore.equals(tempArray, ArrayStore.mapTileCase11)) {
			tempInt = 11;
} else if (ArrayStore.equals(tempArray, ArrayStore.mapTileCase12)) {
			tempInt = 12;
		}

		return tempInt;
	}

/*This is the main part of the code that handle all of the drawing * on the screen. First the graphics object is created, and then 2D * graphics class is inherited. That object will be used to draw 
* everything Background color is set to black. textureMap array is * iterated in a double for loop, comparing saved int value in a 
* textureMap array to a case. If a match is found, position of the * variable in the array is used to calculate x and y values, and a * small portion of tilemap texture is drawn in that position.
* This is repeated until all of the array is used. */

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		setBackground(Color.black);
		
		

		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 28; j++) {
				switch (textureMap[j][i]) {
				case 1: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
							(i + 1) * 32, 0, 0, 32, 32, null);
					break;
				}
				case 2: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
							(i + 1) * 32, 32, 0, 64, 32, null);
					break;
				}
				case 3: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
							(i + 1) * 32, 64, 0, 96, 32, null);
					break;
				}
				case 4: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
							(i + 1) * 32, 0, 32, 32, 64, null);
					break;
				}
				case 5: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
							(i + 1) * 32, 64, 32, 96, 64, null);
					break;
				}
				case 6: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
							(i + 1) * 32, 0, 64, 32, 96, null);
					break;
				}
				case 7: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
							(i + 1) * 32, 32, 64, 64, 96, null);
					break;
				}
				case 8: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
							(i + 1) * 32, 64, 64, 96, 96, null);
					break;
				}
				case 9: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
							(i + 1) * 32, 0, 96, 32, 128, null);
					break;
				}
				case 10: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
							(i + 1) * 32, 32, 96, 64, 128, null);
					break;
				}
				case 11: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
							(i + 1) * 32, 0, 128, 32, 160, null);
					break;
				}
				case 12: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
						(i + 1) * 32, 32, 128, 64, 160, null);
					break;
				}
				
default: {
			g2d.drawImage(TilemapImg, j * 32, i * 32, (j + 1) * 32,
							(i + 1) * 32, 32, 32, 64, 64, null);
					break;
				}
				}
			}
		}

/*There are two exceptions in the above code that do not match  * with any of the cases. It is easier and less resource intensive * to drawm the manually, than to create another two arrays and * if statements just to find the matches. */
		
g2d.drawImage(TilemapImg, 1 * 32, 21 * 32, (1 + 1) * 32, 
(21 + 1) * 32, 64, 0, 96, 32, null);
		g2d.drawImage(TilemapImg, 26 * 32, 21 * 32, (26 + 1) * 32,
				(21 + 1) * 32, 0, 0, 32, 32, null);

/* This code draws the pellets on the map. As this part is below * the code that draws the map (is done later) it is drawn on * top of the map. Pellets that are 'destroyed' (eaten by the * red) are not drawn. */

		g2d.setColor(Color.white);
		for (int i = 0; i < pellets.length; i++) {
			if (!pellets[i].getDestroyed()) {
				g2d.fillOval(pellets[i].x, pellets[i].y, 10, 10);
			}
		}

/* This part of the code draws the powerups by getting their * case number, and deciding which sprite to use. */
		for (int i = 0; i < powerups.length; i++) {
			if (!powerups[i].getDestroyed()) {
g2d.drawImage(CreepImg, powerups[i].x, powerups[i].y, powerups[i].x + 32, powerups[i].y + 32, 32 * powerups[i].caseNumber, 64,
				32 * powerups[i].caseNumber + 32, 96, null);
			}
		}
		
// Drows most of the text in the game.
		
Font serif = new Font("Sans-Serif", Font.BOLD, 32);
		g2d.setFont(serif);
		g2d.drawString("Red Score:", 28 * 32 - 16, 3 * 32);
		g2d.drawString("" + redScore, 28 * 32 - 16, 4 * 32);
		g2d.drawString("Blue Score:", 28 * 32 - 16, 6 * 32);
		g2d.drawString("" + blueScore, 28 * 32 - 16, 7 * 32);
		g2d.drawString("Green Score:", 28 * 32 - 16, 9 * 32);
		g2d.drawString("" + greenScore, 28 * 32 - 16, 10 * 32);

		g2d.drawString("Time left:", 24 * 32 - 16, 12 * 32);
		
		//Draws time until round end
if (System.currentTimeMillis() > startLevelTime
				&& System.currentTimeMillis() < endLevelTime) {
			g2d.drawString(""+ (int) ((endLevelTime - 								System.currentTimeMillis()) / 1000 + 1),
					24 * 32 - 16, 13 * 32);
		} 
/* These two if statements are used to draw either 40 or 0, 
* depending on the state of the game. */
else if (System.currentTimeMillis() < startLevelTime
				&& System.currentTimeMillis() < endLevelTime) {
			g2d.drawString("" + 40, 24 * 32 - 16, 13 * 32);
		} else if (System.currentTimeMillis() < startLevelTime
				&& System.currentTimeMillis() > endLevelTime) {
			g2d.drawString("" + 0, 24 * 32 - 16, 13 * 32);
		}

		g2d.drawString("Red Won:", 28 * 32 - 16, 15 * 32);
		g2d.drawString("" + redWon, 28 * 32 - 16, 16 * 32);
		g2d.drawString("Blue Won:", 28 * 32 - 16, 18 * 32);
		g2d.drawString("" + blueWon, 28 * 32 - 16, 19 * 32);
		g2d.drawString("Green Won:", 28 * 32 - 16, 21 * 32);
		g2d.drawString("" + greenWon, 28 * 32 - 16, 22 * 32);

		/* This part draws the players using drawPlayer method */

		drawPlayer(blue, blueSprite, g2d);
		drawPlayer(red, redSprite, g2d);
		drawPlayer(green, greenSprite, g2d);

/* This if statement is used to draw the names of the powerups * when they are picked up. */

		if (System.currentTimeMillis() - timeSinceLastText <= 1000) {
			g2d.drawString(lastText, 11 * 32 - 16, 10 * 32 + 12);
		}

/* This part is used for the countdown sequence. First, round * number is drawn, and then after 3 seconds a countdown begins. * Once it finishes, the level starts. */
		
		if (startLevelTime - System.currentTimeMillis() <= 6000
&& startLevelTime - System.currentTimeMillis() >= 3000){
			g2d.drawString("Level " + level, 11 * 32 - 16, 								10 * 32 + 12);
		



} else if (startLevelTime - System.currentTimeMillis() <= 3000
			&& startLevelTime - System.currentTimeMillis() >= 0) {
			g2d.drawString(""+ (int) ((startLevelTime - System
.currentTimeMillis()) / 1000 + 1), 
11 * 32 - 16, 10 * 32 + 12);
		} else if (startLevelTime - System.currentTimeMillis() <= 0
			&& startLevelTime - System.currentTimeMillis() >= -1000){
			g2d.drawString("Go!!!", 11 * 32 - 16, 10 * 32 + 12);
		}

		if (endLevelTime - System.currentTimeMillis() <= 0
			&& endLevelTime - System.currentTimeMillis() >= -3000) {
			g2d.drawString(winner, 11 * 32 - 16, 10 * 32 - 4);
			if (level == 7) {
				g2d.drawString("is Victorious!!!", 11 * 32 - 16, 
11 * 32 - 4);
			} else
				g2d.drawString("Wins!!!", 11 * 32 - 16, 11 * 32 - 4);
		}
	}

/* drawPlayer method handles not only at what position the playe has * to be drawn, but also animates the player. This is achieved by having * multiple sprites (one for each state) and using the position and * orientation to decide which one should be drawn. First, a sprite * is chosen out of 4 posible options (mouth closed looking left, mouth * open looking left, mouth closed looking right, mought open looking * right). Then the sprite is rotated either 90 up or down, depending * on direction. Rotation is a bit complicated, as rotation involves * rotating the canvas, so after drawing the image, the canvas has to * be rotated back to it’s original position, otherwise artifacts will * occur.*/

private void drawPlayer(Player player, Rectangle tile, Graphics2D g2d) {
		if (player.getCurrDir() == 0) {
			if (player.x % 32 >= 16 || player.y % 32 >= 16) {
g2d.drawImage(CreepImg, player.x, player.y, player.x + 32, player.y + 32, tile.x, tile.y + 32, tile.x + 32, tile.y + 64, null);
			} else {
g2d.drawImage(CreepImg, player.x, player.y, player.x + 32, player.y + 32, tile.x, tile.y, 
tile.x + 32, tile.y + 32, null);
			}
		} else if (player.getCurrDir() == 2) {
			if (player.x % 32 >= 16 || player.y % 32 >= 16) {
g2d.drawImage(CreepImg, player.x, player.y, player.x + 32, player.y + 32, tile.x + 32, tile.y + 32, tile.x + 64, tile.y + 64, null);
			} else {
g2d.drawImage(CreepImg, player.x, player.y, player.x + 32, player.y + 32, tile.x + 32, tile.y, tile.x + 64, tile.y + 32, null);
			}
		} else {
			if (player.x % 32 >= 16 || player.y % 32 >= 16) {
g2d.rotate(Math.PI * player.getCurrDir() / 2, player.x + 16, player.y + 16);
g2d.drawImage(CreepImg, player.x, player.y, player.x + 32, player.y + 32, tile.x, tile.y + 32, tile.x + 32, tile.y + 64, null);
g2d.rotate(-Math.PI * player.getCurrDir() / 2, player.x + 16, player.y + 16);
			} else {
g2d.rotate(Math.PI * player.getCurrDir() / 2, player.x + 16, player.y + 16);
g2d.drawImage(CreepImg, player.x, player.y, player.x + 32, player.y + 32, tile.x, tile.y, 
tile.x + 32, tile.y + 32, null);
g2d.rotate(-Math.PI * player.getCurrDir() / 2, player.x + 16, player.y + 16);
			}

		}
		if(instructionsDrawn){
			g2d.drawImage(InstructionsImg, 5*32, 0, 800, 800, null);
		}

	}

}
