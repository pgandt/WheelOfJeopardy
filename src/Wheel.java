package WheelOfJeopardy.src;

import java.util.Random;

public class Wheel {

    private int spinCounter;
    public Sectors[] secs;
    //hold all of the sectors in a data structure: will be in an array of enum Sectors

    public Wheel() {
        this.spinCounter = 50;
        secs=Sectors.values();
    }
    
    public boolean spinsRemaining()
    {
       return true;
    }


    public enum Sectors {
        CAT1 ("cat1Label", 0),
        CAT2 ("cat2Label", 1),
        CAT3 ("cat3Label", 2),
        CAT4 ("cat4Label", 3),
        CAT5 ("cat5Label", 4),
        CAT6 ("cat6Label", 5),
        CAT7 ("cat7Label", 6),
        CAT8 ("cat8Label", 7),
        CAT9 ("cat9Label", 8),
        CAT10 ("cat10Label", 9),
        CAT11 ("cat11Label", 10),
        CAT12 ("cat12Label", 11),
        CAT13 ("Spin Again", 12),
        CAT14 ("Opponent’s Choice", 13),
        CAT15 ("Player’s Choice", 14),
        CAT16 ("Bankrupt", 15),
        CAT17 ("Free Turn", 16),
        CAT18 ("Lose Turn", 17);

        private final String label;
        private final int index;

        Sectors(String label, int index) {
            this.label=label;
            this.index=index;
        }

        String getCat(int ind) {
            for(Sectors cat: Sectors.values()) {
                if(cat.index==ind) return cat.values()[ind].label;
            }
            return "Not a Match";
        }
    }

    public String spinWheel() { //public Sector spinWheel(Player p)
        
        // Create instance of Random class
        Random rand = new Random();
  
        // Generate random integers in range 0 to 17
        int rand_int1 = rand.nextInt(17);
        //System.out.println(this.secs[rand_int1].getCat(rand_int1));
        spinCounter--;


        return this.secs[rand_int1].getCat(rand_int1);
    }

    public int getSpinCounter() {
        return spinCounter;
    }

    public static void main(String[] args) {
        Wheel wheel1=new Wheel();
        for (int i=0; i<18;i++) {
            System.out.println(wheel1.spinWheel());
        }
        System.out.println(wheel1.getSpinCounter());
    }

}