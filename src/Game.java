import java.io.IOException;
import java.util.Scanner;
import org.json.simple.parser.ParseException;

/**
 * The game class directs the execution of the entire game.
 * 
 * @author shaun
 *
 */
public class Game
{

   private Wheel wheel;
   private Board board;
   private Player player1;
   private Player player2;
   private Scanner inputReader;

   int round = 1;

   /**
    * Initializes and starts the WoJ game
    * 
    * @param player1Name - String representing player 1's name
    * @param player2Name - String representing player 2's name
    */
   public Game(String player1Name , String player2Name, Scanner inputReader) throws IOException, ParseException
   {
      this.inputReader = inputReader;
      this.wheel = new Wheel();
      this.board = new Board(inputReader);
      this.player1 = new Player(player1Name);
      this.player2 = new Player(player2Name);

      this.player1.nextPlayer = this.player2;
      this.player2.nextPlayer = this.player1;

      // initialize GUI

      this.takeTurn(this.player1);

   }

   /**
    * Executes a full turn of one player. Given that spins remain in the
    * round.
    * 
    * @param p - Player object to execute a turn on
    */
   public void takeTurn(Player p)
   {
      
      if (!this.wheel.spinsRemaining() && this.round == 1)
      { // if no spins remaining and we're in round 1

         this.round = 2;
         
         //this.board.setRound(this.round);
         
         takeTurn(p.nextPlayer);

      }
      else if (!this.wheel.spinsRemaining() && this.round == 2)
      { // if no spins remaining and we're in round 2

         // game ends
         // determine who the winner is
         
      }


      // prompt the user within this function to spin the wheel
      Sector sector = wheel.spinWheel();

      switch (sector)
      {
      case LOSE_TURN :
         // print lose turn here or in spin wheel? Probably in spin wheel
         break;

      case FREE_TURN :
         // p.addFreeTurn
         this.takeTurn(p);
         break;

      case BANKRUPT :

         if (p.getScore() > 0)
         {
            // bankrupt player by setting score to 0
            p.setScore(0);
         }
         break;

      case PLAYER_CHOICE :

         // chosenCategory = p.chooseCategory
         //this.board.askQuestion(chosenCategory);
         break;

      case OPPONENT_CHOICE :

         // chosenCategory = p.nextPlayer.chooseCategory
         //this.board.askQuestion(chosenCategory);
         break;

      case SPIN_AGAIN :
         
         this.takeTurn(p);
         break;

         // all remaining sectors
      default :

         // sector will be the category name
         int netScore = this.board.askQuestion(sector);

         if(netScore == 0) {
            System.out.println("Category had no questions left, skipping turn.");
         }
         else {
            //check if the score is negative and, if it is, prompt the user to get a 
            p.setScore(p.getScore() + netScore);
         }

      }
      
      System.out.println("Turn is over, moving to next player's turn.");
      takeTurn(p.nextPlayer);


   }

   // round change

}