import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    public TitlePanel(Display display) {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 130, 180));

        JLabel titleLabel = new JLabel("Welcome to the Bonus Questions Review");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton startButton = new JButton("Start Quiz");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setPreferredSize(new Dimension(150, 50));
        // Changed to call startQuiz()
        startButton.addActionListener(e -> display.startQuiz());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);

        add(titleLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
    }
}