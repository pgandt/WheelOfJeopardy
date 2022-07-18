import java.util.LinkedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Category {
    LinkedList<Question> questions;
    private String name;
    int categoryNum;

    public Category(JSONObject obj) {
        questions = new LinkedList<Question>();
        this.name = (String) obj.get("name");
        this.categoryNum = Math.toIntExact((long) obj.get("category"));
        JSONArray qs = (JSONArray) obj.get("questions");
        for(int i = 0; i < qs.size(); i++) {
            this.questions.add(new Question((JSONObject) qs.get(i)));
        }
    }

    public boolean questionsLeft() {
        return questions.size() != 0;
    }

    public String getCategoryName() {
        return this.name;
    }

}
