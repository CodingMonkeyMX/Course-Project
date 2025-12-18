import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TitlePanel extends JPanel {
    public TitlePanel(Display display) {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 139, 34));

        // Center panel with title
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(34, 139, 34));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 30, 20));

        JLabel welcomeLabel = new JLabel("Welcome to");
        welcomeLabel.setFont(new Font("Courier New", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Mr. Lauder's");
        titleLabel.setFont(new Font("Courier New", Font.BOLD, 42));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Bonus Questions Review");
        subtitleLabel.setFont(new Font("Courier New", Font.BOLD, 28));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(subtitleLabel);

        // Bottom panel with button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(34, 139, 34));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20));

        JButton startButton = createStyledButton("Start Quiz", e -> display.startQuiz());
        startButton.setPreferredSize(new Dimension(300, 80));
        buttonPanel.add(startButton);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFont(new Font("Courier New", Font.BOLD, 28));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(34, 139, 34));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.addActionListener(listener);
        return button;
    }
}