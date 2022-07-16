
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

    //maybe update score?
    public void setScore(int newScore){
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

}