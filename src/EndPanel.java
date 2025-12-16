import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EndPanel extends JPanel {

    public EndPanel(Display display) {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 139, 34));

        JLabel endLabel = new JLabel("Quiz Complete! Well Done!");
        endLabel.setFont(new Font("Arial", Font.BOLD, 28));
        endLabel.setForeground(Color.WHITE);
        endLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restartButton = createStyledButton("Restart Quiz", e -> display.restartQuiz());
        restartButton.setPreferredSize(new Dimension(400, 100));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(restartButton);

        add(endLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
    }
    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFont(new Font("Arial", Font.PLAIN, 26));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(34, 139, 34));
        button.addActionListener(listener);
        return button;
    }
}