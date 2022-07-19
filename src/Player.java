
public class Player {

    private int score;
    private String name;
    private int freeTurns;
    public Player nextPlayer;

    
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
        this.freeTurns++;
    }
    
    //subtract FreeTurn
    public void subtractFreeTurn(){
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
    public void chooseCategory(){
        System.out.println("Choose category");
    }
    
    //set Score
    public void setScore(int newScore){
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
