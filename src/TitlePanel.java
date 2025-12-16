import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    public TitlePanel(Display display) {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 130, 180));

        JTextArea title = new JTextArea("Welcome to the Bonus Questions Review");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setLineWrap(true);
        title.setWrapStyleWord(true);
        title.setEditable(false);
        title.setBackground(Color.lightGray);
        title.setFocusable(false);
        add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Quiz");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.setPreferredSize(new Dimension(150, 50));

        startButton.addActionListener(e -> display.startQuiz());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}