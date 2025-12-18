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
            QuestionPanel qPanel = new QuestionPanel(this, qd.questionText, qd.answers, qd.correctAnswerIndex, i + 1, questions.size());
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
                questionLine = questionLine.trim();
                if (questionLine.isEmpty()) continue; // Skip empty lines

                String[] answers = new String[4];
                for (int i = 0; i < 4; i++) {
                    String answer = reader.readLine();
                    if (answer == null) {
                        throw new IOException("Incomplete question data");
                    }
                    answers[i] = answer.trim();
                }

                String correctIndexLine = reader.readLine();
                if (correctIndexLine == null) {
                    throw new IOException("Missing correct answer index");
                }
                int correctIndex = Integer.parseInt(correctIndexLine.trim());

                questionsList.add(new QuestionData(questionLine, answers, correctIndex));

                // Read blank line separator if it exists
                reader.readLine();
            }
        } catch (FileNotFoundException e) {
            showError("Questions file not found: " + filename);
        } catch (IOException | NumberFormatException e) {
            showError("Error reading or parsing the questions file. Ensure it's formatted correctly.\n" + e.getMessage());
        }
        return questionsList;
    }

    private void showError(String message) {
        System.err.println(message);
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void startQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        if (!questions.isEmpty()) {
            cardLayout.show(cardPanel, "Question1");
        } else {
            showError("No questions available!");
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
            // Update the EndPanel with the final score before showing it
            for (Component comp : cardPanel.getComponents()) {
                if (comp instanceof EndPanel) {
                    ((EndPanel) comp).updateScore(score, questions.size());
                    break;
                }
            }
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
        return scores.toString().trim();
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}