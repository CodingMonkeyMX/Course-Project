import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EndPanel extends JPanel {
    private JLabel scoreLabel;
    private JTextArea highScoresArea;
    private Display display;

    public EndPanel(Display display) {
        this.display = display;
        setLayout(new BorderLayout());
        setBackground(new Color(34, 139, 34));

        // Top panel with completion message and score
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(34, 139, 34));
        topPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        JLabel endLabel = new JLabel("Quiz Complete!");
        endLabel.setFont(new Font("Courier New", Font.BOLD, 32));
        endLabel.setForeground(Color.WHITE);
        endLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreLabel = new JLabel("Score: 0/0");
        scoreLabel.setFont(new Font("Courier New", Font.BOLD, 40));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(endLabel);
        topPanel.add(Box.createVerticalStrut(15));
        topPanel.add(scoreLabel);

        // Center panel with high scores
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(34, 139, 34));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel highScoresTitle = new JLabel("High Scores:");
        highScoresTitle.setFont(new Font("Courier New", Font.BOLD, 20));
        highScoresTitle.setForeground(Color.WHITE);
        highScoresTitle.setHorizontalAlignment(SwingConstants.CENTER);

        highScoresArea = new JTextArea();
        highScoresArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        highScoresArea.setForeground(Color.WHITE);
        highScoresArea.setBackground(new Color(25, 100, 25));
        highScoresArea.setEditable(false);
        highScoresArea.setFocusable(false);
        highScoresArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(highScoresArea);
        scrollPane.setPreferredSize(new Dimension(400, 120));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        centerPanel.add(highScoresTitle, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(34, 139, 34));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton restartButton = createStyledButton("Restart Quiz", e -> display.restartQuiz());
        restartButton.setPreferredSize(new Dimension(200, 50));

        JButton exitButton = createStyledButton("Exit", e -> System.exit(0));
        exitButton.setPreferredSize(new Dimension(150, 50));

        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updateScore(int score, int total) {
        scoreLabel.setText("Score: " + score + "/" + total);

        // Add performance message
        double percentage = (score * 100.0) / total;
        String message;
        if (percentage == 100) {
            message = "Perfect! 🌟";
        } else if (percentage >= 80) {
            message = "Great Job! 👏";
        } else if (percentage >= 60) {
            message = "Good Effort! 👍";
        } else {
            message = "Keep Practicing! 📚";
        }
        scoreLabel.setText("<html><center>Score: " + score + "/" + total + "<br>" + message + "</center></html>");

        // Update high scores
        String highScores = display.getHighScores();
        if (highScores.isEmpty()) {
            highScoresArea.setText("No previous scores.");
        } else {
            // Show last 10 scores
            String[] scores = highScores.split("\n");
            StringBuilder recentScores = new StringBuilder();
            int start = Math.max(0, scores.length - 10);
            for (int i = start; i < scores.length; i++) {
                recentScores.append(scores[i]).append("\n");
            }
            highScoresArea.setText(recentScores.toString());
            highScoresArea.setCaretPosition(0);
        }
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFont(new Font("Courier New", Font.PLAIN, 20));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(34, 139, 34));
        button.setFocusPainted(false);
        button.addActionListener(listener);
        return button;
    }
}