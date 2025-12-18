import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionPanel extends JPanel {
    private JPanel panel;
    private JPanel q;
    private JPanel a;
    private JTextArea question;
    private JButton one;
    private JButton two;
    private JButton three;
    private JButton four;
    private JLabel progressLabel;
    private boolean answered = false;

    public QuestionPanel(Display display, String ques, String[] answers, int correctAnswerIndex, int questionNum, int totalQuestions) {
        setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);

        // Progress indicator at top
        progressLabel = new JLabel("Question " + questionNum + " of " + totalQuestions);
        progressLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        progressLabel.setForeground(Color.WHITE);
        progressLabel.setBackground(new Color(25, 100, 25));
        progressLabel.setOpaque(true);
        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        progressLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(progressLabel, BorderLayout.NORTH);

        q = new JPanel();
        q.setLayout(new BorderLayout());
        q.setBackground(new Color(34, 139, 34));
        q.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));

        question = new JTextArea(ques);
        question.setFont(new Font("Courier New", Font.BOLD, 30));
        question.setForeground(Color.WHITE);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setEditable(false);
        question.setBackground(new Color(34, 139, 34));
        question.setFocusable(false);
        q.add(question, BorderLayout.CENTER);

        a = new JPanel();
        a.setLayout(new GridLayout(2, 2, 10, 10));
        a.setBackground(new Color(34, 139, 34));
        a.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        ActionListener answerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (answered) return; // Prevent multiple clicks
                answered = true;

                JButton clickedButton = (JButton) e.getSource();
                int selectedAnswer = -1;

                if (clickedButton == one) {
                    selectedAnswer = 0;
                } else if (clickedButton == two) {
                    selectedAnswer = 1;
                } else if (clickedButton == three) {
                    selectedAnswer = 2;
                } else if (clickedButton == four) {
                    selectedAnswer = 3;
                }

                boolean isCorrect = selectedAnswer == correctAnswerIndex;

                // Visual feedback
                JButton[] buttons = {one, two, three, four};

                if (isCorrect) {
                    // Immediately move to next question on correct answer
                    display.nextQuestion(isCorrect);

                    // Reset for next time this panel is shown
                    answered = false;
                } else {
                    // Show visual feedback for incorrect answer
                    clickedButton.setBackground(new Color(244, 67, 54)); // Red
                    clickedButton.setForeground(Color.WHITE);
                    // Show correct answer
                    buttons[correctAnswerIndex].setBackground(new Color(76, 175, 80)); // Green
                    buttons[correctAnswerIndex].setForeground(Color.WHITE);

                    // Disable all buttons
                    for (JButton btn : buttons) {
                        btn.setEnabled(false);
                    }

                    // Show error dialog
                    JOptionPane.showMessageDialog(
                            display,
                            "Incorrect. The correct answer was: " + answers[correctAnswerIndex],
                            "Wrong Answer",
                            JOptionPane.ERROR_MESSAGE
                    );

                    // Move to next question after dialog closes
                    display.nextQuestion(isCorrect);

                    // Reset for next time this panel is shown
                    answered = false;
                    for (JButton btn : buttons) {
                        btn.setEnabled(true);
                        btn.setBackground(Color.WHITE);
                        btn.setForeground(new Color(34, 139, 34));
                    }
                }
            }
        };

        one = createStyledButton(answers[0], answerListener);
        a.add(one);

        two = createStyledButton(answers[1], answerListener);
        a.add(two);

        three = createStyledButton(answers[2], answerListener);
        a.add(three);

        four = createStyledButton(answers[3], answerListener);
        a.add(four);

        panel.add(q, BorderLayout.CENTER);
        panel.add(a, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFont(new Font("Courier New", Font.PLAIN, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(34, 139, 34));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.addActionListener(listener);
        return button;
    }
}