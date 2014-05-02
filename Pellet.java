import java.awt.Rectangle;

public class Pellet extends Rectangle {
    public static int pelletScore = 10;
    private boolean isDestroyed = false;
    
/*The pellet class is used to create and store the pellets that are * eaten by the player. It also has isDestroyed variable. This is a * very powerful variable, as it allows to effectively remove the 
* object from the game without actually destroying it, meaning that * it needs to be created only once. If isDestroyed is true, the pellet * stops interacting with the world and goes invisible, meaning that * the player eat it more than once. At level restart, the pellets are * bought back to the game by setting their isDestroyed variable to * true */

    public Pellet(int tileX, int tileY)
    {
/* width and height are preset to 10, and 11 is added to both x and * y axes to centre the pellet. Not adding these numbers will draw the * pellet at the corner of the tile. */
        this.x = tileX*32 + 11;
        this.y = tileY*32 + 11;
        this.height = 10;
        this.width = 10;
    }
    
	// Setter for private variable isDestroyed

	public void setDestroyed(boolean arg) {
		isDestroyed = arg;
	}

	// Getter for private variable isDestroyed

	public boolean getDestroyed() {
		return isDestroyed;
	}
    
    /* This 2D array stores all of the positions for each individual pellet. 
     * Each number is multiplied by 32 to get the actual x and y values.
     */
    
    static int[][] pelletArray = new int[][]{
{1, 1}, {2, 1}, {3, 1}, {4, 1}, {5, 1}, {6, 1}, {7, 1}, {8, 1}, {9, 1}, {10, 1}, {11, 1}, {12, 1},
{15, 1}, {16, 1}, {17, 1}, {18, 1}, {19, 1}, {20, 1}, {21, 1}, {22, 1}, {23, 1}, {24, 1}, {25, 1}, {26, 1},
{1, 2}, {6, 2}, {12, 2}, {15, 2}, {21, 2}, {26, 2}, {1, 3}, 
{6, 3}, {12, 3}, {15, 3}, {21, 3}, {26, 3},
{6, 4}, {9, 4}, {10, 4}, {11, 4}, {12, 4}, {13, 4}, {14, 4}, {15, 4}, {16, 4}, {17, 4}, {18, 4}, {21, 4}, 
{1, 5}, {2, 5}, {3, 5}, {4, 5}, {5, 5}, {6, 5}, {9, 5}, {18, 5}, {21, 5}, {22, 5}, {23, 5}, {24, 5}, {25, 5}, {26, 5},
{1, 6}, {6, 6}, {9, 6}, {18, 6}, {21, 6}, {26, 6}, {1, 7}, {6, 7}, {21, 7}, {26,7},
{1, 8}, {2, 8}, {3, 8}, {4, 8}, {5, 8}, {6, 8}, {21, 8}, {22, 8}, {23, 8}, {24, 8}, {25, 8}, {26, 8},
        {6, 9}, {21, 9}, {6, 10}, {7, 10}, {8, 10}, {9, 10}, {18, 10}, 	   {19, 10}, {20, 10}, {21, 10},
{6, 11}, {9, 11}, {18, 11}, {21, 11}, {6, 12}, {9, 12}, {18, 12}, {21, 12}, {6, 13}, {9, 13}, {18, 13}, {21, 13},
{6, 14}, {9, 14}, {18, 14}, {21, 14}, {6, 15}, {9, 15}, {18, 15}, {21, 15}, 
{1, 16}, {2, 16}, {3, 16}, {4, 16}, {5, 16}, {6, 16}, {7, 16},	 {8, 16}, {9, 16}, 
{18, 16}, {19, 16}, {20, 16}, {21, 16}, {22, 16}, {23, 16}, 	  {24, 16}, {25, 16}, {26, 16},
{1, 17}, {4, 17}, {9, 17}, {18, 17}, {23, 17}, {26, 17}, {4, 18}, {9, 18}, {18, 18}, {23, 18},  
        {1, 19}, {4, 19}, {9, 19}, {18, 19}, {23, 19}, {26, 19}, 
{1, 20}, {2, 20}, {3, 20}, {4, 20}, {9, 20}, {10, 20}, {11, 20}, {12, 20}, {15, 20}, {16, 20}, {17, 20}, {18, 20},
{23, 20}, {24, 20}, {25, 20}, {26, 20}, {2, 21}, {12, 21},	 {15, 21}, {25, 21}, 
        {2, 22}, {12, 22}, {15, 22}, {25, 22}, 					
        {2, 23}, {3, 23}, {4, 23}, {5, 23}, {6, 23}, {7, 23}, {8, 23}, 	   {9, 23}, {10, 23}, {11, 23}, {12, 23}, {13, 23}, 
{14, 23}, {15, 23}, {16, 23}, {17, 23}, {18, 23}, {19, 23},	 {20, 23}, {21, 23}, {22, 23}, {23, 23}, {24, 23}, {25, 23}, 
    };
}


