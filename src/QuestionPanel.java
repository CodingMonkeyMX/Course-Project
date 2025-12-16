import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionPanel extends JPanel {
    JPanel panel;
    JPanel q;
    JPanel a;
    JTextArea question;
    JButton one;
    JButton two;
    JButton three;
    JButton four;

    public QuestionPanel(Display display, String ques, String[] answers, int correctAnswerIndex) {
        setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        this.add(panel, BorderLayout.CENTER);

        q = new JPanel();
        q.setLayout(new BorderLayout());
        q.setBackground(new Color(34, 139, 34)); // Green background
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

                if (selectedAnswer == correctAnswerIndex) {
                    display.nextQuestion();
                } else {
                    JOptionPane.showMessageDialog(
                            display,
                            "Incorrect. Please try again.",
                            "Wrong Answer",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        };

        // Buttons with consistent styling
        one = createStyledButton(answers[0], answerListener);
        a.add(one);

        two = createStyledButton(answers[1], answerListener);
        a.add(two);

        three = createStyledButton(answers[2], answerListener);
        a.add(three);

        four = createStyledButton(answers[3], answerListener);
        a.add(four);

        panel.add(q);
        panel.add(a);
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFont(new Font("Courier New", Font.PLAIN, 14));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(34, 139, 34));
        button.addActionListener(listener);
        return button;
    }
}