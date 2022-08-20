//package WheelOfJeopardy.src;

import java.util.Random;

public class Wheel {

    private int spinCounter;
    public Sector[] sectors = {Sector.CATEGORY1, Sector.CATEGORY1,
                               Sector.CATEGORY2, Sector.CATEGORY2,
                               Sector.CATEGORY3, Sector.CATEGORY3,
                               Sector.CATEGORY4, Sector.CATEGORY4,
                               Sector.CATEGORY5, Sector.CATEGORY5,
                               Sector.CATEGORY6, Sector.CATEGORY6,
                               Sector.FREE_TURN, Sector.LOSE_TURN,
                               Sector.SPIN_AGAIN, Sector.BANKRUPT,
                               Sector.PLAYER_CHOICE, Sector.OPPONENT_CHOICE};
    //hold all of the sectors in a data structure: will be in an array of enum Sectors

    public Wheel() {
        this.spinCounter = 50;
    }
    
    public boolean spinsRemaining()
    {
    	if(this.spinCounter > 0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
       
    }

    public Sector spinWheel() { //public Sector spinWheel(Player p)
        
        // Create instance of Random class
        Random rand = new Random();
  
        // Generate random integers in range 0 to 17
        int rand_int1 = rand.nextInt(17);
        //System.out.println(this.secs[rand_int1].getCat(rand_int1));
        spinCounter--;


        return this.sectors[rand_int1];
    }
    
    public void setSpinsRemaining(int spinsRemaining)
    {
    	this.spinCounter = spinsRemaining;
    }
    
    public void resetCounter()
    {
    	this.spinCounter = 50;
    }

    public int getSpinCounter() {
        return spinCounter;
    }


}
