import java.awt.Rectangle;
import java.awt.Window;

public class Player extends Rectangle {

/* This is the Player class. It contains all of the needed data about * the player, excluding score. Collision and movement model is 
* described here. */

	public int playerSpeed = 2;
	private int currDir; // 0=Left, 1=Up, 2=Right, 3=Down
	private int inputDir;

	public Player(int tempX, int tempY) 
// Creates the player in a specific
	// location on the map, with preset height and width
	{
		x = tempX;
		y = tempY;
		height = 32;
		width = 32;
	}

	public int getCurrDir() {
		return currDir;
	}

	public void setCurrDir(int currDir) {
		this.currDir = currDir;
	}

	/* mapCollsionmethod is called in ManPac.update() method. It deals with movement, management of input commands, collisions with the map, and also runs the method responsible for collisions with other players. Before any movement is done, current position of the player is saved as a backup. Then movement according to the inputDir variable is done. It checks in which direction the input is pointing (0 = left, 1 = up, 2 = right, 3 = down), and moves the player in that direction. Then it checks for collision with the map. If there is a collision with the map, it moves back to the positions that was saved at the start of the method. At this point playerCollision check is called, to see if any of the player are colliding. If they are, movement direction is inverted for both players, and input direction is overwritten to be same as the new movement direction. If there was no collision with the map at this point, the method is ended as the player has moved the required amount. If there is a collision with the map (and the player was returned to its original position), then the second part of the method begins. It is exactly the same, but instead of input direction it uses current movement direction. After that, another playerCollision check happens. This method allows for smooth playing experience, as the player does not need to be precise in pressing the buttons if he wants to turn, but it must happen ahead of the turn. */

	public void mapCollision(Brick[] map) {
		int tempX = this.x;
		int tempY = this.y;
		boolean collided = false;

		if (inputDir % 2 == 1) {
			this.y = this.y + (playerSpeed * (inputDir - 2));
		} else if (inputDir % 2 == 0) {
			this.x = this.x + (playerSpeed * (inputDir - 1));
		}
		for (int i = 0; i < map.length; i++) {
			if (this.intersects(map[i]) == true) {
				this.x = tempX;
				this.y = tempY;
				collided = true;
			}
		}

		if (playerCollision(tempX, tempY)) {
			return;
		}

		if (!collided) {
			setCurrDir(inputDir);
			return;
		}

		if (getCurrDir() == 1 || getCurrDir() == 3) {
			this.y = this.y + (playerSpeed * (getCurrDir() - 2));
		} else if (getCurrDir() == 0 || getCurrDir() == 2) {
			this.x = this.x + (playerSpeed * (getCurrDir() - 1));
		}
		for (int i = 0; i < map.length; i++) {
			if (this.intersects(map[i]) == true) {
				this.x = tempX;
				this.y = tempY;
			}
		}

		playerCollision(tempX, tempY);

		if (playerSpeed != 1) {
			if (this.x % 2 != 0 || this.y % 2 != 0) {
				this.x += this.x % 2;
				this.y += this.y % 2;
			}
		}

	}



	/* playerCollision method is called inside mapCollision method. It is used to check and manage collisions between the players. It does this by checking which player is calling the method, and then running corresponding if statements for that player. If a collision is detected, movement direction is inversed (this is doen by taking away 2 from currDir variable, and if it falls below 0, 4 is added to loop it back to the top). Then inputDir is overwritten to be the same as the new currDir, to prevent it from turning back to the old currDir in the next frame. */

private boolean playerCollision(int tempX, int tempY) {
		if (this == ManPac.red) {
			if (this.intersects(ManPac.blue) == true
					|| this.intersects(ManPac.green) == true) {
				this.x = tempX;
				this.y = tempY;
				setCurrDir(getCurrDir() - 2);
				if (getCurrDir() <= -1) {
					setCurrDir(getCurrDir() + 4);
				}
				setInputDir(getCurrDir());
				return true;
			}
		} else if (this == ManPac.blue) {
			if (this.intersects(ManPac.red) == true
					|| this.intersects(ManPac.green) == true) {
				this.x = tempX;
				this.y = tempY;
				setCurrDir(getCurrDir() - 2);
				if (getCurrDir() <= -1) {
					setCurrDir(getCurrDir() + 4);
				}
				setInputDir(getCurrDir());
				return true;
			}

		} else if (this == ManPac.green) {
			if (this.intersects(ManPac.red) == true
					|| this.intersects(ManPac.blue) == true) {
				this.x = tempX;
				this.y = tempY;
				setCurrDir(getCurrDir() - 2);
				if (getCurrDir() <= -1) {
					setCurrDir(getCurrDir() + 4);
				}
				setInputDir(getCurrDir());
				return true;
			}
		}
		return false;
	}

	

/* This method is used to check for collisions with the pellets. unlike * the map collisions, which use . intersects method, pellet collision * uses .contains method, which checks if the pellet is completely 
* surrounded by the player. There are no movement changes due to the * pellets, but if the collision happens, that pellet is set as 
* destroyed and score from the pellet is added to the game score. If * no collision happens, score 0 is returned to the game score, leaving * it unchanged. */

	public int pelletCollision(Pellet[] pellet) {
		for (int i = 0; i < pellet.length; i++) {
if (this.contains(pellet[i]) && pellet[i].getDestroyed()== false) {
				pellet[i].setDestroyed(true);
				return Pellet.pelletScore;
			}
		}
		return 0;
	}

	/* powerupCollision works exactly the same as pelletCollision. */

	public int powerupCollision(Powerup[] powerup) {
		for (int i = 0; i < powerup.length; i++) {
if (this.contains(powerup[i]) && powerup[i].getDestroyed() == false) {
				powerup[i].onPickUp(this);
				powerup[i].setDestroyed(true);
				return powerup[i].pelletScore;
			}
		}
		return 0;
	}

	public int getInputDir() {
		return inputDir;
	}

	public void setInputDir(int i) {
		inputDir = i;
	}

}

