import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TitlePanel extends JPanel {
    public TitlePanel(Display display) {
        setLayout(new BorderLayout());

        // Centered title with white text on green background
        JTextArea title = new JTextArea("Welcome to\nMr. Lauder's\nBonus Questions Review");
        title.setFont(new Font("Courier New", Font.BOLD, 38));
        title.setForeground(Color.WHITE); // White text
        title.setLineWrap(true);
        title.setEditable(false);
        title.setBackground(new Color(34, 139, 34)); // Green theme
        title.setWrapStyleWord(true); // Improves readability
        add(title, BorderLayout.CENTER);

        // Styled start button
        JButton startButton = createStyledButton("Start Quiz", e-> display.startQuiz());
        startButton.setPreferredSize(new Dimension(400, 100));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(34, 139, 34)); // Match title background
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFont(new Font("Courier New", Font.PLAIN, 30));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(34, 139, 34));
        button.addActionListener(listener);
        return button;
    }
}