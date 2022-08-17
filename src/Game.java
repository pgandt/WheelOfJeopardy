import java.io.IOException;
import java.util.Scanner;
import org.json.simple.parser.ParseException;

/**
 * The game class directs the execution of the entire game.
 * 
 * @author shaun
 *
 */
public class Game {

   private Wheel wheel;
   private Board board;
   private Player player1;
   private Player player2;
   private Scanner inputReader;

   Player currentPlayer;

   //question parts
   //Label question = new Label();
   //Button answer1 = new Button();
   //Button answer2 = new Button();
   //Button answer3 = new Button();

   //button to spin wheel
   //Button spinWheelButton = new Button();

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
      this.board = new Board(inputReader);
      this.player1 = new Player(player1Name, inputReader);
      this.player2 = new Player(player2Name, inputReader);

      this.player1.nextPlayer = this.player2;
      this.player2.nextPlayer = this.player1;

      takeTurn(this.player1);

   }

   public Game() throws IOException, ParseException{
      String player1Name = "";
      String player2Name = "";

      System.out.println("Enter Player 1's Name:");

      // create a new scanner
      Scanner inputReader = new Scanner(System.in);

      // accept player 1's name
      player1Name = ( inputReader.nextLine() );

      System.out.println("Enter Player 2's Name:");

      // accept player 2's name
      player2Name = ( inputReader.nextLine() );

      //pass reference to the board, player and wheel
      this.inputReader = inputReader;
      this.wheel = new Wheel();
      this.board = new Board(inputReader);
      this.player1 = new Player(player1Name, inputReader);
      this.player2 = new Player(player2Name, inputReader);

      this.player1.nextPlayer = this.player2;
      this.player2.nextPlayer = this.player1;

      //takeTurn(this.player1);
      currentPlayer = player1;

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
      System.out.println("Player " + p.getName() + " your turn!");
      System.out.println("Click the button to spin the wheel!");
      //spinWheelButton.setVisible(true);
      inputReader.nextLine();
      wheel.spinWheel(ew);

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
            System.out.println("Use free turn? y/n");
            String answer = inputReader.nextLine();
            if(answer.equalsIgnoreCase("y"))
            {
               p.subtractFreeTurn();
               return;
            }
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
            while(decision < 1 || decision > 3) {
               decision = inputReader.nextInt();
               if(decision < 1 || decision > 3) {
                  System.out.println("Please select a valid answer:");
               }
            }

            if(decision == 1) {
               System.out.println("Correct!");
               i.i = 1;
            }
            else {
               System.out.println("Incorrect...");
               i.i = -1;
               if(p.getFreeTurn() != 0)
               {
                  System.out.println("Use free turn? y/n");
                  String answer = inputReader.nextLine();
                  if(answer.equalsIgnoreCase("y"))
                  {
                     p.subtractFreeTurn();
                     return;
                  }
               }
            }

            p.setScore(p.getScore() + i.i * q.points);
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
            while(decision < 1 || decision > 3) {
               decision = inputReader.nextInt();
               if(decision < 1 || decision > 3) {
                  System.out.println("Please select a valid answer:");
               }
            }

            if(decision == 1) {
               System.out.println("Correct!");
               i.i = 1;
            }
            else {
               System.out.println("Incorrect...");
               i.i = -1;
               if(p.getFreeTurn() != 0)
               {
                  System.out.println("Use free turn? y/n");
                  String answer = inputReader.nextLine();
                  if(answer.equalsIgnoreCase("y"))
                  {
                     p.subtractFreeTurn();
                     return;
                  }
               }
            }

            p.setScore(p.getScore() + i.i * q.points);

            //question.setVisible(false);
            //answer1.setVisible(false);
            //answer2.setVisible(false);
            //answer3.setVisible(false);
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

            int decision = 0;
            while(decision < 1 || decision > 3) {
               decision = inputReader.nextInt();
               if(decision < 1 || decision > 3) {
                  System.out.println("Please select a valid answer:");
               }
            }

            if(decision == 1) {
               System.out.println("Correct!");
               i.i = 1;
            }
            else {
               System.out.println("Incorrect...");
               i.i = -1;
               if(p.getFreeTurn() != 0)
               {
                  System.out.println("Use free turn? y/n");
                  inputReader.nextLine();
                  String answer = inputReader.nextLine();
                  if(answer.equalsIgnoreCase("y"))
                  {
                     p.subtractFreeTurn();
                     return;
                  }
               }
            }

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