import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Display extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ArrayList<QuestionData> questions;
    private int currentQuestionIndex;
    private int score;

    public Display() {
        setSize(550, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Bonus Questions Review");

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        TitlePanel titlePanel = new TitlePanel(this);
        cardPanel.add(titlePanel, "Title");

        questions = loadQuestionsFromFile("Questions.txt");

        for (int i = 0; i < questions.size(); i++) {
            QuestionData qd = questions.get(i);
            QuestionPanel qPanel = new QuestionPanel(this, qd.questionText, qd.answers, qd.correctAnswerIndex);
            cardPanel.add(qPanel, "Question" + (i + 1));
        }

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
        score = 0; // Reset score for a new quiz
        if (!questions.isEmpty()) {
            cardLayout.show(cardPanel, "Question1");
        } else {
            cardLayout.show(cardPanel, "Title");
        }
    }

    public void nextQuestion(boolean isCorrect) {
        if (isCorrect) {
            score++;
        }
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            cardLayout.show(cardPanel, "Question" + (currentQuestionIndex + 1));
        } else {
            saveScore();
            cardLayout.show(cardPanel, "EndPanel");
        }
    }

    public void restartQuiz() {
        cardLayout.show(cardPanel, "Title");
    }

    private void saveScore() {
        try (FileWriter writer = new FileWriter("HighScores.txt", true)) {
            writer.write("Score: " + score + "/" + questions.size() + "\n");
        } catch (IOException e) {
            showError("Unable to save score to file.");
        }
    }

    public String getHighScores() {
        StringBuilder scores = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("HighScores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            return "No high scores yet!";
        } catch (IOException e) {
            showError("Unable to read high scores.");
        }
        return scores.toString();
    }
}