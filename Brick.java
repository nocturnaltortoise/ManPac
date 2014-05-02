import java.awt.Rectangle;

/* This is a generic rectangle class that has two constructors and two array * lists for creating rectangles.
* These rectangles are used as mapBorders */

public class Brick extends Rectangle {

	public Brick(int x, int y, int w, int h) 
	// Class constructor for the map					
	{
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	static int[][] brickArray = new int[][] { 
	// These are the coordinates for the bricks 
	//(x, y, width, height) all to be multiplied by 32
	{0, -1, 29, 2}, {-1, -1, 2, 25}, {2, 2, 4, 3}, 
	{7, 2, 5, 2}, {13, 1, 2, 3}, {16, 2, 5, 2}, {22, 2, 4, 3},
	{27, 1, 2, 23}, {2, 6, 4, 2}, {7, 4, 2, 6}, {10, 5, 8, 2},
	{19, 4, 2, 6}, {22, 6, 4, 2}, {1, 9, 5, 7}, {7, 11, 2, 5},
	{10, 8, 8, 4}, {19, 11, 2, 5}, {10, 13, 8, 3},
	{22, 9, 5, 7}, {2, 17, 2, 3 }, { 5, 17, 4, 4 },
	{10, 17, 8, 3}, {19, 17, 4, 4}, {24, 17, 2, 3},
	{1, 21, 1, 3}, {3, 21, 9, 2 }, { 13, 20, 2, 3},
	{16, 21, 9, 2}, {26, 21, 1, 3}, {-1, 24, 30, 2}, 
	};
}
