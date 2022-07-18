import java.util.Scanner;

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

   int round = 1;

   /**
    * Initializes and starts the WoJ game
    * 
    * @param player1Name - String representing player 1's name
    * @param player2Name - String representing player 2's name
    */
   public Game(String player1Name , String player2Name)
   {
      this.wheel = new Wheel();
      this.board = new Board();
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

      String sector = "";
      String chosenCategory = "";
      
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
      sector = wheel.spinWheel(p);

      switch (sector)
      {
      case "Lose Turn" :
         // print lose turn here or in spin wheel? Probably in spin wheel
         break;

      case "Free Turn" :
         // p.addFreeTurn
         this.takeTurn(p);
         break;

      case "Bankrupt" :

         if (p.getScore() > 0)
         {
            // bankrupt player by setting score to 0
            p.setScore(0);
         }
         break;

      case "Player's Choice" :

         // chosenCategory = p.chooseCategory
         this.board.askQuestion(chosenCategory);
         break;

      case "Opponent's Choice" :

         // chosenCategory = p.nextPlayer.chooseCategory
         this.board.askQuestion(chosenCategory);
         break;

      case "Spin Again" :
         
         this.takeTurn(p);
         break;

         // all remaining sectors
      default :
         
         int questionPointValue = this.board.getPoints(sector);

         // sector will be the category name
         switch(this.board.askQuestion(sector))
         {
         // no questions remain in the category
         case -1:
            // do nothing
            
            System.out.println("No questions are remaining for this category");
            
         // question was answered wrong
         case 0:
            
            if(p.getFreeTurns > 0)
            {
               System.out.println("Question was answered incorrectly, would you like to use a free turn?");
               
               if(answer == yes)
               { // player uses token
                  
                  this.takeTurn(p);
               }
            
            }
            
            p.subtractScore(questionPointValue)

         // question was answered correctly
         case 1:
            p.addScore(questionPointValue)
            
         }

      }
      
      takeTurn(p.nextPlayer);


   }

   // round change

}