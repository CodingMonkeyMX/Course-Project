import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Display extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ArrayList<QuestionData> questions;
    private int currentQuestionIndex;

    public Display() {
        setSize(500, 450); // Increased size for better text display
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Bonus Questions Review");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create and add title panel
        TitlePanel titlePanel = new TitlePanel(this);
        cardPanel.add(titlePanel, "Title");

        // Load questions and answers from file
        questions = loadQuestionsFromFile("Questions.txt");

        // Create question panels dynamically
        for (int i = 0; i < questions.size(); i++) {
            QuestionData qd = questions.get(i);
            QuestionPanel qPanel = new QuestionPanel(this, qd.questionText, qd.answers, qd.correctAnswerIndex);
            cardPanel.add(qPanel, "Question" + (i + 1));
        }

        // Create and add the end panel
        EndPanel endPanel = new EndPanel(this);
        cardPanel.add(endPanel, "EndPanel");

        add(cardPanel);
        cardLayout.show(cardPanel, "Title");
        setVisible(true);
    }

    private ArrayList<QuestionData> loadQuestionsFromFile(String filename) {
        ArrayList<QuestionData> questionsList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String questionLine;
            while ((questionLine = reader.readLine()) != null) {
                String[] answers = new String[4];
                answers[0] = reader.readLine();
                answers[1] = reader.readLine();
                answers[2] = reader.readLine();
                answers[3] = reader.readLine();
                int correctIndex = Integer.parseInt(reader.readLine().trim());

                questionsList.add(new QuestionData(questionLine, answers, correctIndex));
            }
        } catch (FileNotFoundException e) {
            showError("Questions file not found: " + filename);
        } catch (IOException | NumberFormatException e) {
            showError("Error reading or parsing the questions file. Ensure it's formatted correctly.");
        }
        return questionsList;
    }

    private void showError(String message) {
        System.err.println(message);
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void startQuiz() {
        currentQuestionIndex = 0;
        if (!questions.isEmpty()) {
            cardLayout.show(cardPanel, "Question1");
        } else {
            cardLayout.show(cardPanel, "Title"); // Stay on title if no questions
        }
    }

    public void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            cardLayout.show(cardPanel, "Question" + (currentQuestionIndex + 1));
        } else {
            // Quiz is over
            cardLayout.show(cardPanel, "EndPanel");
        }
    }

    public void restartQuiz() {
        cardLayout.show(cardPanel, "Title");
    }
}