import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;


public class Board {
    //store the questions for each category in a linked list

    public ArrayList<Category> categories;

    /**
     * Board constructor. Read the questions.json file to get all of the questions that will
     * be used before loading them into a linked list of questions, to be accessed when a
     * question is asked.
     * @throws IOException
     * @throws ParseException
     */
    public Board(boolean round1) throws IOException, ParseException
    {
        categories = new ArrayList<Category>();
        JSONParser parser = new JSONParser();
        Path jsonFile;
        if(round1)
        {
        	jsonFile = Path.of("C:\\Users\\shaun\\Documents\\GitHub\\WheelOfJeopardy\\questions_round1.json");
        }
        else
        {
        	jsonFile = Path.of("C:\\Users\\shaun\\Documents\\GitHub\\WheelOfJeopardy\\questions_round2.json");
        }        	
        //Path.of("questions.json");
        String jsonString = Files.readString(jsonFile);
        JSONObject jsonData = (JSONObject) parser.parse(jsonString);
        JSONObject r1 = (JSONObject) jsonData.get("questions");
        JSONArray categories = (JSONArray) r1.get("categories");
        
        for(int i = 0; i < categories.size(); i++) {
            this.categories.add(new Category((JSONObject) categories.get(i)));
        }
    }
    public boolean questionsLeft()
    {
    	for(int i=0; i<6; i++)
    	{
    		if(categories.get(i).questionsLeft())
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }


    public Question askQuestion(Sector category) {
        Category c = null;
        //System.out.println("Board is asking a question.");

        switch (category) {

            case CATEGORY1:
            {
                c = categories.get(0);
                break;
            }
            case CATEGORY2:
            {
                c = categories.get(1);
                break;
            }
            case CATEGORY3:
            {
                c = categories.get(2);
                break;
            }
            case CATEGORY4:
            {
                c = categories.get(3);
                break;
            }
            case CATEGORY5:
            {
                c = categories.get(4);
                break;
            }
            case CATEGORY6:
            {
                c = categories.get(5);
            }
        }

        
        if(!c.questionsLeft())
        {
            //System.out.println("No questions left in category \"" + c.getCategoryName() + "\".");
            return null;
        }
        //System.out.println("Your category is: " + c.getCategoryName());
        Question q = c.questions.pollFirst();

        return q;

        /*System.out.println(q.getQuestion() + "\n\n");
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
    */
    }

}