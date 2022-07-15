
public class Player {

    private int score;
    private String name;
    private int freeTurns;
    public int nextPlayer;
    
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
       
    //set Score
    public void setScore(int newScore){
        this.score = score;
    }
    //get Score
    public int getScore() {
        return this.score;
    }

}
