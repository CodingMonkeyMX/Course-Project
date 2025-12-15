import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel {

    public EndPanel(Display display) {
        setLayout(new BorderLayout());
        setBackground(new Color(60, 179, 113)); // A nice green color

        JLabel endLabel = new JLabel("Quiz Complete! Well Done!");
        endLabel.setFont(new Font("Arial", Font.BOLD, 28));
        endLabel.setForeground(Color.WHITE);
        endLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restartButton = new JButton("Restart Quiz");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        restartButton.setPreferredSize(new Dimension(200, 50));
        restartButton.addActionListener(e -> display.restartQuiz());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(restartButton);

        add(endLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
    }
}