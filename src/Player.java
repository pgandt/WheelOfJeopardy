
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
    public void setPlayer(int newPlayer){
        this.nextPlayer = nextPlayer;
    }
    
    //get Player
    public int getPlayer() {
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
    
    //add FreeTurn
    public int getFreeTurn(){
        return this.freeTurns
    }
    
    //choose category
    public void chooseCategory(){
        System.out.println("Choose category");
        return null;
    }
    
    //set Score
    public void setScore(int newScore){
        this.score = score;
    }
    //get Score
    public int getScore() {
        return this.score;
    }

}
