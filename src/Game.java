import java.io.IOException;
import java.util.Scanner;
import org.json.simple.parser.ParseException;

//javafx imports
import javafx.application.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
   Label roundCounter = new Label();
   Label spins = new Label();
   Label p1Score = new Label();
   Label p2Score = new Label();
   Label currentPlayerLabel = new Label();
   Label p1Tokens = new Label();
   Label p2Tokens = new Label();

   //start game button
   Button nextTurn = new Button();

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

   public static void main(String args[]) throws IOException, ParseException
   {

      /*String player1Name = "";
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
*/
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

   public void start(Stage myStage) {
      myStage.setTitle("Wheel of Jeopardy");
      GridPane rootNode = new GridPane();
      Scene myScene = new Scene(rootNode, 800, 500);

      rootNode.setPadding(new Insets(30));
      
      //set up round counter
      rootNode.add(roundCounter, 0, 0);
      roundCounter.setText("Round " + Integer.toString(round));

      //set up spin counter
      rootNode.add(spins, 1, 0);
      spins.setText("Spins remaining: " + Integer.toString(this.wheel.getSpinCounter()));

      //set up player score fields:
      rootNode.add(this.p1Score, 0, 2);
      p1Score.setText(this.player1.getName() + "'s score: " + this.player1.getScore());
      rootNode.add(this.p2Score, 0, 3);
      p2Score.setText(this.player2.getName() + "'s score: " + this.player2.getScore());

      //set up free token counters
      rootNode.add(this.p1Tokens, 0, 4);
      p1Tokens.setText(this.player1.getName() + "'s tokens: " + this.player1.getFreeTurn());
      rootNode.add(this.p2Tokens, 0, 5);
      p2Tokens.setText(this.player2.getName() + "'s tokens: " + this.player2.getFreeTurn());

      //set up current player label
      rootNode.add(this.currentPlayerLabel, 5, 0);
      currentPlayerLabel.setText("Current player: " + this.player1.getName());

      //rootNode.add(spinWheelButton, 0, 7);
      //spinWheelButton.setText("Spin Wheel");
      //spinWheelButton.setOnAction(event -> this.wheel.spinWheel(ew));

      //rootNode.setMaxSize(100, 100);

      //GridPane.setConstraints(question, 75, 50);
      //rootNode.getChildren().addAll(question);

      //rootNode.add(question, 0, 99);
      //question.setText("What color is the sky?");
      //rootNode.add(answer1, 75, 51);
      //answer1.setText("No more questions");
      //answer1.setOnAction(event -> i.i = 1);
      //rootNode.add(answer2, 75, 52);
      //answer2.setText("Bring it back!");
      //answer2.setOnAction(event -> i.i= -1);
      //rootNode.add(answer3, 75, 53);
      //answer3.setText("Purple");
      //answer3.setOnAction(event -> i.i = -1);
      //question.setVisible(false);
      //answer1.setVisible(false);
      //answer2.setVisible(false);
      //answer3.setVisible(false);

      myStage.setScene(myScene);
      myStage.show();

      //this.takeTurn(player1);

      rootNode.add(nextTurn, 75, 54);
      nextTurn.setText("Next Turn");
      nextTurn.setOnAction(event -> takeTurn(currentPlayer));

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

      ew.myEnum = null;
      System.out.println("Player " + p.getName() + " your turn!");
      System.out.println("Click the button to spin the wheel!");
      //spinWheelButton.setVisible(true);
      inputReader.nextLine();
      wheel.spinWheel(ew);
      spins.setText("Spins remaining: " + Integer.toString(this.wheel.getSpinCounter()));

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
               p1Tokens.setText(this.player1.getName() + "'s tokens: " + this.player1.getFreeTurn());
               p2Tokens.setText(this.player2.getName() + "'s tokens: " + this.player2.getFreeTurn());
               return;
            }
         }
         // print lose turn here or in spin wheel? Probably in spin wheel
         break;

      case FREE_TURN :
         p.addFreeTurn();
         p1Tokens.setText(this.player1.getName() + "'s tokens: " + this.player1.getFreeTurn());
         p2Tokens.setText(this.player2.getName() + "'s tokens: " + this.player2.getFreeTurn());
         System.out.println("You get a free turn token!");
         return;

      case BANKRUPT :

         System.out.println("You've gone bankrupt!");
         if (p.getScore() > 0)
         {
            // bankrupt player by setting score to 0
            p.setScore(0);
         }
         p1Score.setText(this.player1.getName() + "'s score: " + this.player1.getScore());
         p2Score.setText(this.player2.getName() + "'s score: " + this.player2.getScore());
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
         return;

         // all remaining sectors
      default :

         // sector will be the category name
         Question q = this.board.askQuestion(ew.myEnum);

         

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
                     p1Tokens.setText(this.player1.getName() + "'s tokens: " + this.player1.getFreeTurn());
                     p2Tokens.setText(this.player2.getName() + "'s tokens: " + this.player2.getFreeTurn());
                     return;
                  }
               }
            }

            p.setScore(p.getScore() + i.i * q.points);

            p1Score.setText(this.player1.getName() + "'s score: " + this.player1.getScore());
            p2Score.setText(this.player2.getName() + "'s score: " + this.player2.getScore());

            //question.setVisible(false);
            //answer1.setVisible(false);
            //answer2.setVisible(false);
            //answer3.setVisible(false);
         }
      }
      
      System.out.println("Turn is over, moving to next player's turn.");
      //takeTurn(p.nextPlayer);
      currentPlayer = p.nextPlayer;
      currentPlayerLabel.setText("Current player: " + this.currentPlayer.getName());

   }

   // round change

   public class enumWrapper {
      public Sector myEnum;
   }
   private class IntWrapper {
      public int i;
   }
}