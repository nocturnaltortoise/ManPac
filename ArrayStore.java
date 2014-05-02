// ArrayStore class. I use this class to separate the arrays  
// that are used in compareCase method.
// They are written in a style similar to a 2D array, but they 
// are 1D arrays. This style is used to 
// make it easier to visualise the cases 
// (0 = no collision, 1 = collision with the map)
// For example, array mapTileCase 1 will draw top left corner 
// of the tilemap.

public class ArrayStore {

	/*ArrayStore.equals(array1, array2) is used to compare arrrays. This is done by first comparing the length, and then comparing individual values of arrays. if there are any mismatches, the method returns false. Otherwise, it returns true at the end of the for loop. There is alternative method Arrays.equals available for use, but due to certain complications I had to make my own method. */

	static public boolean equals(int[] array1, int[] array2) 	{
		if (array1.length != array2.length) {
			return false;
		}

		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				return false;
			}
		}

		return true;
	}
	
        static int[] mapTileCase1 = new int[]{
             0, 0, 0,
             0, 1, 1,
             0, 1, 1
        };
        
        static int[]mapTileCase2 = new int[]{
             0, 0, 0,
             1, 1, 1,
             1, 1, 1
        };
        
        static int[]mapTileCase3 = new int[]{
             0, 0, 0,
             1, 1, 0,
             1, 1, 0
        };
        
        

   static int[]mapTileCase4 = new int[]{
             0, 1, 1,
             0, 1, 1,
             0, 1, 1
        };
        
        static int[]mapTileCase5 = new int[]{
             1, 1, 0,
             1, 1, 0,
             1, 1, 0
        };
        
        static int[]mapTileCase6 = new int[]{
             0, 1, 1,
             0, 1, 1,
             0, 0, 0
        };
        
        static int[]mapTileCase7 = new int[]{
             1, 1, 1,
             1, 1, 1,
             0, 0, 0
        };
        
        static int[] mapTileCase8 = new int[]{
             1, 1, 0,
             1, 1, 0,
             0, 0, 0
        };
        
        static int[] mapTileCase9 = new int[]{
             1, 1, 1,
             1, 1, 1,
             1, 1, 0
        };
        
        static int[] mapTileCase10 = new int[]{
             1, 1, 1,
             1, 1, 1,
             0, 1, 1
        };
        
        static int[] mapTileCase11 = new int[]{
             1, 1, 0,
             1, 1, 1,
             1, 1, 1
        };
        
        static int[] mapTileCase12 = new int[]{
             0, 1, 1,
             1, 1, 1,
             1, 1, 1
        };
        
        static int[] mapTileCase13 = new int[]{
            0, 0, 1,
            1, 1, 1,
            1, 1, 1
        };
        
        static int[] mapTileCase14 = new int[]{
            1, 0, 0,
            1, 1, 1,
            1, 1, 1
        };
        
        static int[] mapTileCase15 = new int[]{
            1, 1, 1,
            0, 1, 1,
            0, 1, 1
        };
        
        static int[] mapTileCase16 = new int[]{
            0, 1, 1,
            0, 1, 1,
            1, 1, 1
        };
        
        static int[] mapTileCase17 = new int[]{
            1, 1, 0,
            1, 1, 0,
            1, 1, 1
        };
        
        static int[] mapTileCase18 = new int[]{
            1, 1, 1,
            1, 1, 0,
            1, 1, 0
        };
        
        static int[] mapTileCase19 = new int[]{
            1, 1, 1,
            1, 1, 1,
            1, 0, 0
        };
        
        static int[] mapTileCase20 = new int[]{
            1, 1, 1,
            1, 1, 1,
            0, 0, 1
        };
}

    
