import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    public TitlePanel(Display display) {
        setLayout(new BorderLayout());

        JTextArea title = new JTextArea("Welcome to\nMr. Lauder's\nBonus Questions Review");
        title.setFont(new Font("Arial", Font.BOLD, 38));
        title.setLineWrap(true);
        title.setEditable(false);
        title.setBackground(new Color(20, 155, 60));
        add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Quiz");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setPreferredSize(new Dimension(150, 50));

        startButton.addActionListener(e -> display.startQuiz());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(20, 155, 60));
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}