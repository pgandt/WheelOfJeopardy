import java.util.Random;

public class Wheel {

    private int spinCounter;
    public Sector[] wheelSec1; 
    //hold all of the sectors in a data structure: will be in an array of Sectors

    public Wheel(String[] catagories) { //catagories="cat1", "cat2", .., "cat6"
        this.spinCounter = 50;
        wheelSec1=new Sector[12];

        for (int i=0; i<catagories.length; i++) {
            wheelSec1[i]=new Sector(catagories[i], i);
        }

        for (int i=0; i<(catagories.length); i++) {
            wheelSec1[6+i]=new Sector(catagories[i], 6+i);
        }

    }

    public Sector getCat(int ID) {
        System.out.println(this.wheelSec1[ID].getSecID());
        return wheelSec1[ID];
    }

    public Sector spinWheel() { //public Sector spinWheel(Player p)
        
        // Create instance of Random class
        Random rand = new Random();
  
        // Generate random integers in range 0 to 12
        int rand_int1 = rand.nextInt(13);
        System.out.println(this.wheelSec1[rand_int1].dispSec());
        System.out.println(this.wheelSec1[rand_int1].getSecID());
        spinCounter--;

        return this.wheelSec1[rand_int1];
    }

    public int getSpinCounter() {
        return spinCounter;
    }

    public static void main(String[] args) {
        String[] cats = {"cat1", "cat2", "cat3", "cat4", "cat5", "cat6"};
        Wheel wheel1=new Wheel(cats);
        System.out.println(wheel1.spinWheel());
    }

}