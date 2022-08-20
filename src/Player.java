import java.util.Scanner;

public class Player {

    private int score;
    private String name;
    private int freeTurns;
    public Player nextPlayer;
    Scanner input;

    
    public Player(String name) {
        this.score = 0;
        this.name = name;
        this.freeTurns = 0;
    }
    
    //set Player
    public void setPlayer(Player newPlayer){
        this.nextPlayer = newPlayer;
    }
    
    //get Player
    public Player getPlayer() {
        return this.nextPlayer;
        
    }
    //add FreeTurn
    public void addFreeTurn(){
        //System.out.println("Player added a free turn token.");
        this.freeTurns++;
    }
    
    //subtract FreeTurn
    public void subtractFreeTurn(){
        //System.out.println("Player used a free turn");
        this.freeTurns--;
    }

    public int getFreeTurns() {
        return this.freeTurns;
    }
    
    //add FreeTurn
    public int getFreeTurn(){
        return this.freeTurns;
    }
    
    //choose category
    public Sector chooseCategory(){
        System.out.println(this.name + "please select the category:");
        int choice = 0;
        Sector c = null;
        while (choice <= 0 || choice >=7)
        {
            choice = this.input.nextInt();
            switch (choice)
            {
                case 1:
                {
                    c = Sector.CATEGORY1;
                    break;
                }
                case 2:
                {
                    c = Sector.CATEGORY2;
                    break;
                }
                case 3:
                {
                    c = Sector.CATEGORY3;
                    break;
                }
                case 4:
                {
                    c = Sector.CATEGORY4;
                    break;
                }
                case 5:
                {
                    c = Sector.CATEGORY5;
                    break;
                }
                case 6:
                {
                    c = Sector.CATEGORY6;
                    break;
                }
                default:
                {
                    System.out.println("You chose an invalid category, please choose a valid category:");
                }
            }
        }
        return c;
    }
    
    //set Score
    public void setScore(int newScore){
        //System.out.println("Player's score is now" + newScore);
        this.score = newScore;
    }
    //get Score
    public int getScore() {
        return this.score;
    }

    public String getName() {
        return this.name;
    }

}
