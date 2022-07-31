ava.io.IOException;
import java.util.Scanner;
import org.json.simple.parser.ParseException;

public class WheelOfJeopardy
{

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

      Game game = new Game(player1Name , player2Name, inputReader);
   }

}