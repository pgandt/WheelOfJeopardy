import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.Scanner;


public class Board {
    //store the questions for each category in a linked list

    public static void main(String args[]) throws IOException, ParseException {
        Scanner input = new Scanner(System.in);
        Board b = new Board(input);
        int v = b.askQuestion(null);
        System.out.println("Gained " + v + "points.");
    }

    public ArrayList<Category> categories;
    Scanner input;

    /**
     * Board constructor. Read the questions.json file to get all of the questions that will
     * be used before loading them into a linked list of questions, to be accessed when a
     * question is asked.
     * @throws IOException
     * @throws ParseException
     */
    public Board(Scanner input) throws IOException, ParseException
    {
        this.input = input;
        categories = new ArrayList<Category>();
        JSONParser parser = new JSONParser();
        Path jsonFile = Path.of("questions.json");
        String jsonString = Files.readString(jsonFile);
        JSONObject jsonData = (JSONObject) parser.parse(jsonString);
        JSONObject r1 = (JSONObject) jsonData.get("round1Questions");
        JSONArray categories = (JSONArray) r1.get("categories");
        for(int i = 0; i < categories.size(); i++) {
            this.categories.add(new Category((JSONObject) categories.get(i)));
        }
    }


    public int askQuestion(Sector category) {
        Category c = categories.get(0);
        
        if(!c.questionsLeft())
        {
            return 0;
        }
        Question q = c.questions.pollFirst();
        System.out.println(q.getQuestion() + "\n\n");
        System.out.println("Enter the number of the answer you would like to select:");
        System.out.println("\t 1.) " + q.getCorrectAnswer());
        System.out.println("\t 2.) " + q.getWrongAnswer1());
        System.out.println("\t 3.) " + q.getWrongAnswer2());


        int response = input.nextInt();
        if(response == 1) {
            return q.getPoints();
        }
        else {
            return -1 * q.getPoints();
        }
    }

}