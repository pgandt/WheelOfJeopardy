import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 * The game class directs the execution of the entire game.
 * 
 * @author shaun
 *
 */
public class Game {

   public Wheel wheel;
   public Board board;
   public Player player1;
   public Player player2;
   private Player currentPlayer;

   //wrapper for enum in spinWheel
   public enumWrapper ew = new enumWrapper();
   public IntWrapper i = new IntWrapper();

   //table for board

   //figure out a pop up for questions

   int round = 1;

   /**
    * Initializes and starts the WoJ game
    * 
    * @param player1Name - String representing player 1's name
    * @param player2Name - String representing player 2's name
    */
   public Game(String player1Name , String player2Name) throws IOException, ParseException
   {

      this.wheel = new Wheel();
      this.board = new Board();
      this.player1 = new Player(player1Name);
      this.player2 = new Player(player2Name);
      
      this.currentPlayer = this.player1;

      this.player1.nextPlayer = this.player2;
      this.player2.nextPlayer = this.player1;


   }
   
   public void endTurn(Player player)
   {
	   if(this.player1.getName() == player.getName())
	   {
		   this.currentPlayer = this.player2;
	   }
	   else
	   {
		   this.currentPlayer = this.player1;
	   }
   }
   
   public Player getCurrentPlayer()
   {
	   return this.currentPlayer;
   }

   /**
    * Executes a full turn of one player. Given that spins remain in the
    * round.
    *
    * @param p - Player object to execute a turn on
    */
   public void takeTurn(Player p)
   {
      Question q = null;
      
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

      ew.myEnum = null;
      //spinWheelButton.setVisible(true);
      //wheel.spinWheel(ew);

      while(ew.myEnum == null)
      {
         //wait until the user spins the wheel
      }

      // prompt the user within this function to spin the wheel
      //Sector sector = wheel.spinWheel();
      Sector chosenCategory; //initialize in case it is player or opponent's choice

      switch (ew.myEnum)
      {
      case LOSE_TURN :
         System.out.println("You lose this turn!");
         if(p.getFreeTurn() != 0)
         {
         }
         // print lose turn here or in spin wheel? Probably in spin wheel
         break;

      case FREE_TURN :
         p.addFreeTurn();
         System.out.println("You get a free turn token!");
         return;

      case BANKRUPT :

         System.out.println("You've gone bankrupt!");
         if (p.getScore() > 0)
         {
            // bankrupt player by setting score to 0
            p.setScore(0);
         }
         break;

      case PLAYER_CHOICE :
         System.out.println("Player's choice");
         ew.myEnum = p.chooseCategory();
         q = this.board.askQuestion(ew.myEnum);

         

         if(q == null) {
            System.out.println("Category had no questions left, skipping turn.");
         }
         else {

            System.out.println(q.getQuestion() + "\n\n");
            System.out.println("Enter the number of the answer you would like to select:");
            System.out.println("\t 1.) " + q.getCorrectAnswer());
            System.out.println("\t 2.) " + q.getWrongAnswer1());
            System.out.println("\t 3.) " + q.getWrongAnswer2());

            int decision = 0;
            }
         break;

      case OPPONENT_CHOICE :

         System.out.println("Opponent's choice");
         ew.myEnum = p.nextPlayer.chooseCategory();
         q = this.board.askQuestion(ew.myEnum);

         

         if(q == null) {
            System.out.println("Category had no questions left, skipping turn.");
         }
         else {

            System.out.println(q.getQuestion() + "\n\n");
            System.out.println("Enter the number of the answer you would like to select:");
            System.out.println("\t 1.) " + q.getCorrectAnswer());
            System.out.println("\t 2.) " + q.getWrongAnswer1());
            System.out.println("\t 3.) " + q.getWrongAnswer2());

            int decision = 0;

            if(decision == 1) {
               System.out.println("Correct!");
               i.i = 1;
            }
            else {
               System.out.println("Incorrect...");
               i.i = -1;
            }

            p.setScore(p.getScore() + i.i * q.points);
         }
         break;

      case SPIN_AGAIN :
         
         System.out.println("Spin again!");
         return;

         // all remaining sectors
      default :

         // sector will be the category name
         q = this.board.askQuestion(ew.myEnum);

         

         if(q == null) {
            System.out.println("Category had no questions left, skipping turn.");
         }
         else {

            System.out.println(q.getQuestion() + "\n\n");
            System.out.println("Enter the number of the answer you would like to select:");
            System.out.println("\t 1.) " + q.getCorrectAnswer());
            System.out.println("\t 2.) " + q.getWrongAnswer1());
            System.out.println("\t 3.) " + q.getWrongAnswer2());

            p.setScore(p.getScore() + i.i * q.points);
         }
      }
      
      System.out.println("Turn is over, moving to next player's turn.");
      //takeTurn(p.nextPlayer);
      currentPlayer = p.nextPlayer;

   }

   // round change

   public class enumWrapper {
      public Sector myEnum;
   }
   private class IntWrapper {
      public int i;
   }
}