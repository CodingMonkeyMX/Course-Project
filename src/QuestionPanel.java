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
        q.setBackground(Color.lightGray);
        q.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));

        question = new JTextArea(ques);
        question.setFont(new Font("Arial", Font.BOLD, 16));
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setEditable(false);
        question.setBackground(Color.lightGray);
        question.setFocusable(false);
        q.add(question, BorderLayout.CENTER);

        a = new JPanel();
        a.setLayout(new GridLayout(2, 2, 10, 10));
        a.setBackground(Color.gray);
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

        one = new JButton("<html><center>" + answers[0] + "</center></html>");
        one.setFont(new Font("Arial", Font.PLAIN, 14));
        one.addActionListener(answerListener);
        a.add(one);

        two = new JButton("<html><center>" + answers[1] + "</center></html>");
        two.setFont(new Font("Arial", Font.PLAIN, 14));
        two.addActionListener(answerListener);
        a.add(two);

        three = new JButton("<html><center>" + answers[2] + "</center></html>");
        three.setFont(new Font("Arial", Font.PLAIN, 14));
        three.addActionListener(answerListener);
        a.add(three);

        four = new JButton("<html><center>" + answers[3] + "</center></html>");
        four.setFont(new Font("Arial", Font.PLAIN, 14));
        four.addActionListener(answerListener);
        a.add(four);

        panel.add(q);
        panel.add(a);
    }
}