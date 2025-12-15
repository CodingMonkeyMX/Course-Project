// QuestionData.java
public class QuestionData {
    String questionText;
    String[] answers;
    int correctAnswerIndex;

    public QuestionData(String questionText, String[] answers, int correctAnswerIndex) {
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}