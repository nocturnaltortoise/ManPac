import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

public class Powerup extends Rectangle {

/* This is Powerup class. It is used to create and manage powerups for the players to use. */

	public int pelletScore = 50;
	private boolean isDestroyed = false;
	public int caseNumber;
	String name;

/*The constructor creates the powerup at the position specified in tile number nad randomises the powerup.*/

	public Powerup(int tileX, int tileY) {
		this.x = tileX * 32;
		this.y = tileY * 32;
		this.height = 16;
		this.width = 16;
		this.caseNumber = (int)(3.d * Math.random());
	}

	// Getter for private variable isDestroyed
	public boolean getDestroyed() {
		return isDestroyed;
	}
	
	public void setDestroyed(boolean arg) {
		isDestroyed = arg;
	}

/* onPickUp methods is used to determine the effect of the powerup. * It passes caseNumber into a switch statement to see which power is * going to be used. ManPac.timeSinceLastText is called here to start * the draw timer. ManPac.lastText is changed here to input the name * of the powerup to draw on the screen, as g2d.drawString cannot be * called form here. Some cases that rely on time delays use Timer class * for accurately determining time passed. Because the class is nested, * it goes out of scope when it is not needed anymore.*/ 
	
	public void onPickUp(Player player) {
		switch (caseNumber) {
		case 0: {
            ManPac.timeSinceLastText = System.currentTimeMillis();
            ManPac.lastText = "More points!!!";
			this.pelletScore = 200;
			break;
		}
		
case 1: {
                    Timer timer = new Timer();
ManPac.timeSinceLastText = System.currentTimeMillis();
                    ManPac.lastText = "Slow down";
                        if (player == ManPac.red){
                                class endSlowDown extends TimerTask{
                                public void run() {
                                ManPac.blue.playerSpeed =2;
                                ManPac.green.playerSpeed =2;
                                cancel();
                                }
                            }
                            ManPac.blue.playerSpeed = 1;
                            ManPac.green.playerSpeed = 1;
                            timer.schedule(new endSlowDown(), 2000);
                        }
			
                         else if (player == ManPac.blue) {
                             class endSlowDown extends TimerTask{
                                public void run() {
                                ManPac.red.playerSpeed =2;
                                ManPac.green.playerSpeed =2;
                                cancel();
                                }
                            }
                            ManPac.green.playerSpeed = 1;
                            ManPac.red.playerSpeed =1;
                            timer.schedule(new endSlowDown(), 2000);
			

                        } else if (player == ManPac.green) {
                            class endSlowDown extends TimerTask{
                                public void run() {
                                ManPac.red.playerSpeed =2;
                                ManPac.blue.playerSpeed =2;
                                cancel();
                                }
                            }
                            ManPac.blue.playerSpeed = 1;
                            ManPac.red.playerSpeed =1;
                            timer.schedule(new endSlowDown(), 2000);
			
                        }
                        break;
		}
		case 2: {
            			ManPac.timeSinceLastText = 									System.currentTimeMillis();
            			ManPac.lastText = "STOP!!!";
                        Timer timer = new Timer();
                        if (player == ManPac.red){
                            class endStop extends TimerTask{
                                public void run() {
                                ManPac.green.playerSpeed =2;
                                ManPac.blue.playerSpeed =2;
                                cancel();
                                }
                            }
                            switch((int)(Math.random()*2.d)){
                               case 0:
                               { 
                                  ManPac.blue.playerSpeed = 0;
                                  timer.schedule(new endStop(), 2000);
                                  break;
                               }
                               case 1:
                               { 
                                  ManPac.green.playerSpeed = 0;
                                  timer.schedule(new endStop(), 2000);
                                  break;
                               }
                        }
                        }
                        
                        if (player == ManPac.blue){
                            class endStop extends TimerTask{
                                public void run() {
                                ManPac.red.playerSpeed =2;
                                ManPac.green.playerSpeed =2;
                                cancel();
                                }
                            }
                            switch((int)(Math.random()*2.d)){
                               case 0:
                               { 
                                  ManPac.red.playerSpeed = 0;
                                  timer.schedule(new endStop(), 2000);
                                  break;
                               }
                               case 1:
                               { 
                                  ManPac.green.playerSpeed = 0;
                                  timer.schedule(new endStop(), 2000);
                                  break;
                               }
                        }
                        }
                        
                        if (player == ManPac.green){
                            class endStop extends TimerTask{
                                public void run() {
                                ManPac.red.playerSpeed =2;
                                ManPac.blue.playerSpeed =2;
                                cancel();
                                }
                            }
                            switch((int)(Math.random()*2.d)){
                               case 0:
                               { 
                                  ManPac.blue.playerSpeed = 0;
                                  timer.schedule(new endStop(), 2000);
                                  break;
                               }
                               case 1:
                               { 
                                  ManPac.red.playerSpeed = 0;
                                  timer.schedule(new endStop(), 2000);
                                  break;
                               }
                        }
                        }

			break;
		}
		default: {
			break;
		}
            }
	}

/* powerupArray stores the positions of the powerups in tile form, * and is used when powerups are constructed in ManPac constructor. */ 

	static int[][] powerupArray = new int[][] { 
{ 1, 4 }, { 26, 4 }, { 1, 18 }, { 26, 18 } 
};

}

