import java.io.IOException;
import java.util.Scanner;
import org.json.simple.parser.ParseException;

//javafx imports
import javafx.application.*;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;

/**
 * The game class directs the execution of the entire game.
 * 
 * @author shaun
 *
 */
public class Game extends Application {

   private Wheel wheel;
   private Board board;
   private Player player1;
   private Player player2;
   private Scanner inputReader;
   
   //GUI using JavaFX
   Label roundCounter = new Label("Round");
   //labels to add: spins remaining, player scores, current player, free tokens

   //button to spin wheel

   //table for board

   //figure out a pop up for questions

   int round = 1;

   public static void main(String args[]) throws IOException, ParseException
   {

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

      //launch(args);

      //Game game = new Game(player1Name , player2Name, inputReader);

      launch(args);

   }


   /**
    * Initializes and starts the WoJ game
    * 
    * @param player1Name - String representing player 1's name
    * @param player2Name - String representing player 2's name
    */
   public Game(String player1Name , String player2Name, Scanner inputReader) throws IOException, ParseException
   {
      //pass reference to the board, player and wheel
      this.inputReader = inputReader;
      this.wheel = new Wheel();
      this.board = new Board(inputReader);
      this.player1 = new Player(player1Name, inputReader);
      this.player2 = new Player(player2Name, inputReader);

      this.player1.nextPlayer = this.player2;
      this.player2.nextPlayer = this.player1;

      takeTurn(this.player1);

   }

   public Game() {

   }

   public void start(Stage myStage) {
      myStage.setTitle("Wheel of Jeopardy");
      GridPane rootNode = new GridPane();
      Scene myScene = new Scene(rootNode, 400, 300);
      
      //set 
      rootNode.add(roundCounter, 0, 0);
      roundCounter.setText("Round " + Integer.toString(round));

      myStage.setScene(myScene);
      myStage.show();
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

      System.out.println("Player " + p.getName() + " your turn!");
      System.out.println("Press enter to spin the wheel!");
      inputReader.nextLine();

      // prompt the user within this function to spin the wheel
      Sector sector = wheel.spinWheel();
      Sector chosenCategory; //initialize in case it is player or opponent's choice

      switch (sector)
      {
      case LOSE_TURN :
         System.out.println("You lose this turn!");
         // print lose turn here or in spin wheel? Probably in spin wheel
         break;

      case FREE_TURN :
         p.addFreeTurn();
         System.out.println("You get a free turn token!");
         this.takeTurn(p);
         break;

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
         chosenCategory = p.chooseCategory();
         this.board.askQuestion(chosenCategory);
         break;

      case OPPONENT_CHOICE :

         System.out.println("Opponent's choice");
         chosenCategory = p.nextPlayer.chooseCategory();
         this.board.askQuestion(chosenCategory);
         break;

      case SPIN_AGAIN :
         
         System.out.println("Spin again!");
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