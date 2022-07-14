

public class Game {

    enum Sector = {
        category1,
        category2
    }

    Wheel wheel;
    Board board;
    Player player1;
    Player player2;

    public Game(String player1Name, String player2Name) {
        this.Wheel = new Wheel();
        this.board = new Board();
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);

        this.player1.nextPlayer = this.player2;
        this.player2.nextPlayer = this.player1;

        //initialize GUI

        takeTurn(this.player1);

    }

    public void takeTurn(Player p) {
        //prompt the user within this function to spin the wheel
        wheel.spinWheel(p);

        //use the sectors to determine what needs to be done
            //if a category, call the board
            //so on

        takeTurn(p.nextPlayer);

    }

    //round change



}