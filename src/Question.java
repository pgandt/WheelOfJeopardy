import org.json.simple.JSONObject;

public class Question {
    
    int points;
    String q;
    String correctAnswer;
    String wrongAnswer1;
    String wrongAnswer2;

    public Question(JSONObject j) {
        this.points = Math.toIntExact((long) j.get("points"));
        this.correctAnswer = (String) j.get("correctAnswer");
        this.wrongAnswer1 = (String) j.get("wrongAnswer1");
        this.wrongAnswer2 = (String) j.get("wrongAnswer2");
    }

    public int getPoints() {
        return points;
    }

    public String getQuestion() {
        return this.q;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

}
