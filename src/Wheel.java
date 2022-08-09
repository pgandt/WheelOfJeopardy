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
       return true;
    }

    public void spinWheel(Game.enumWrapper e) { //public Sector spinWheel(Player p)
        
        // Create instance of Random class
        Random rand = new Random();
  
        // Generate random integers in range 0 to 17
        int rand_int1 = rand.nextInt(17);
        //System.out.println(this.secs[rand_int1].getCat(rand_int1));
        spinCounter--;


        e.myEnum = this.sectors[rand_int1];
    }

    public int getSpinCounter() {
        return spinCounter;
    }

    /*public static void main(String[] args) {
        Wheel wheel1=new Wheel();
        for (int i=0; i<18;i++) {
            System.out.println(wheel1.spinWheel());
        }
        System.out.println(wheel1.getSpinCounter());
    }*/

}
