import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionPanel extends JPanel {
    private JPanel panel;
    private JPanel q;
    private JPanel a;
    private JTextArea question;
    private JButton one;
    private JButton two;
    private JButton three;
    private JButton four;
    private JLabel progressLabel;
    private boolean answered = false;

    public QuestionPanel(Display display, String ques, String[] answers, int correctAnswerIndex, int questionNum, int totalQuestions) {
        setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);

        // Top panel with progress and factorial button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(25, 100, 25));

        progressLabel = new JLabel("Question " + questionNum + " of " + totalQuestions);
        progressLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        progressLabel.setForeground(Color.WHITE);
        progressLabel.setBackground(new Color(25, 100, 25));
        progressLabel.setOpaque(true);
        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        progressLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton factorialButton = new JButton("Factorial");
        factorialButton.setFont(new Font("Courier New", Font.PLAIN, 12));
        factorialButton.setBackground(Color.YELLOW);
        factorialButton.setForeground(new Color(34, 139, 34));
        factorialButton.setFocusPainted(false);
        factorialButton.setMargin(new Insets(5, 10, 5, 10));
        factorialButton.addActionListener(e -> showFactorialCalculator());

        JButton arrayButton = new JButton("Array Tools");
        arrayButton.setFont(new Font("Courier New", Font.PLAIN, 12));
        arrayButton.setBackground(Color.CYAN);
        arrayButton.setForeground(new Color(34, 139, 34));
        arrayButton.setFocusPainted(false);
        arrayButton.setMargin(new Insets(5, 10, 5, 10));
        arrayButton.addActionListener(e -> showArrayTools());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(new Color(25, 100, 25));
        buttonPanel.add(arrayButton);
        buttonPanel.add(factorialButton);

        topPanel.add(progressLabel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        panel.add(topPanel, BorderLayout.NORTH);

        q = new JPanel();
        q.setLayout(new BorderLayout());
        q.setBackground(new Color(34, 139, 34));
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
                if (answered) return; // Prevent multiple clicks
                answered = true;

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

                boolean isCorrect = selectedAnswer == correctAnswerIndex;

                // Visual feedback
                JButton[] buttons = {one, two, three, four};

                if (isCorrect) {
                    // Immediately move to next question on correct answer
                    display.nextQuestion(isCorrect);

                    // Reset for next time this panel is shown
                    answered = false;
                } else {
                    // Show visual feedback for incorrect answer
                    clickedButton.setBackground(new Color(244, 67, 54)); // Red
                    clickedButton.setForeground(Color.WHITE);
                    // Show correct answer
                    buttons[correctAnswerIndex].setBackground(new Color(76, 175, 80)); // Green
                    buttons[correctAnswerIndex].setForeground(Color.WHITE);

                    // Disable all buttons
                    for (JButton btn : buttons) {
                        btn.setEnabled(false);
                    }

                    // Show error dialog
                    JOptionPane.showMessageDialog(
                            display,
                            "Incorrect. The correct answer was: " + answers[correctAnswerIndex],
                            "Wrong Answer",
                            JOptionPane.ERROR_MESSAGE
                    );

                    // Move to next question after dialog closes
                    display.nextQuestion(isCorrect);

                    // Reset for next time this panel is shown
                    answered = false;
                    for (JButton btn : buttons) {
                        btn.setEnabled(true);
                        btn.setBackground(Color.WHITE);
                        btn.setForeground(new Color(34, 139, 34));
                    }
                }
            }
        };

        one = createStyledButton(answers[0], answerListener);
        a.add(one);

        two = createStyledButton(answers[1], answerListener);
        a.add(two);

        three = createStyledButton(answers[2], answerListener);
        a.add(three);

        four = createStyledButton(answers[3], answerListener);
        a.add(four);

        panel.add(q, BorderLayout.CENTER);
        panel.add(a, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFont(new Font("Courier New", Font.PLAIN, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(34, 139, 34));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.addActionListener(listener);
        return button;
    }

    private void showFactorialCalculator() {
        String input = JOptionPane.showInputDialog(
                this,
                "Enter a number to calculate its factorial:",
                "Factorial Calculator",
                JOptionPane.QUESTION_MESSAGE
        );

        if (input != null && !input.trim().isEmpty()) {
            try {
                int n = Integer.parseInt(input.trim());
                if (n < 0) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Factorial is not defined for negative numbers!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else if (n > 20) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Number too large! Please enter a number between 0 and 20.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    long result = factorial(n);
                    JOptionPane.showMessageDialog(
                            this,
                            n + "! = " + result,
                            "Factorial Result",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid integer!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private long factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    private void showArrayTools() {
        String[] options = {"Sort Array", "Search Array"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "What would you like to do?",
                "Array Tools",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            showSortMenu();
        } else if (choice == 1) {
            showSearchMenu();
        }
    }

    private void showSortMenu() {
        String[] sortOptions = {"Bubble Sort", "Selection Sort", "Insertion Sort"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose a sorting algorithm:",
                "Sort Array",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                sortOptions,
                sortOptions[0]
        );

        if (choice >= 0) {
            String input = JOptionPane.showInputDialog(
                    this,
                    "Enter numbers separated by spaces (e.g., 5 2 8 1 9):",
                    "Enter Array",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input != null && !input.trim().isEmpty()) {
                try {
                    String[] parts = input.trim().split("\\s+");
                    int[] arr = new int[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        arr[i] = Integer.parseInt(parts[i]);
                    }

                    String original = arrayToString(arr);

                    switch (choice) {
                        case 0:
                            bubbleSort(arr);
                            break;
                        case 1:
                            selectionSort(arr);
                            break;
                        case 2:
                            insertionSort(arr);
                            break;
                    }

                    String sorted = arrayToString(arr);
                    JOptionPane.showMessageDialog(
                            this,
                            "Original: " + original + "\n" +
                                    "Sorted:   " + sorted + "\n" +
                                    "Algorithm: " + sortOptions[choice],
                            "Sort Result",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter valid integers!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    private void showSearchMenu() {
        String[] searchOptions = {"Linear Search", "Binary Search"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose a search algorithm:",
                "Search Array",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                searchOptions,
                searchOptions[0]
        );

        if (choice >= 0) {
            String input = JOptionPane.showInputDialog(
                    this,
                    "Enter numbers separated by spaces (e.g., 5 2 8 1 9):",
                    "Enter Array",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input != null && !input.trim().isEmpty()) {
                try {
                    String[] parts = input.trim().split("\\s+");
                    int[] arr = new int[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        arr[i] = Integer.parseInt(parts[i]);
                    }

                    String target = JOptionPane.showInputDialog(
                            this,
                            "Enter the number to search for:",
                            "Search Target",
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (target != null && !target.trim().isEmpty()) {
                        int searchValue = Integer.parseInt(target.trim());
                        int result;

                        if (choice == 0) {
                            result = linearSearch(arr, searchValue);
                        } else {
                            // Binary search requires sorted array
                            bubbleSort(arr);
                            result = binarySearch(arr, searchValue);
                        }

                        String message;
                        if (result != -1) {
                            message = "Found " + searchValue + " at index " + result + "!\n" +
                                    "Array: " + arrayToString(arr) + "\n" +
                                    "Algorithm: " + searchOptions[choice];
                        } else {
                            message = searchValue + " was not found in the array.\n" +
                                    "Array: " + arrayToString(arr) + "\n" +
                                    "Algorithm: " + searchOptions[choice];
                        }

                        JOptionPane.showMessageDialog(
                                this,
                                message,
                                "Search Result",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter valid integers!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    // Sorting algorithms
    private void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    private void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }

    private void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    // Search algorithms
    private int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    private int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            }
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    private String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}