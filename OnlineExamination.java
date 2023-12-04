package OnlineExamination;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class OnlineExamination extends JFrame {
    private JTextField usernameF;
    private JPasswordField passwordF;
    private JTextArea questionArea;
    private ButtonGroup buttonGroup;
    private JButton loginButton;
    private JButton cancelButton;
    private JButton nextButton;
    private int currentQuestionIndex;
    private int correctAnswers;
    private String[] questions = {
            "1. What is inheritance in Java?\n"
                    + "a) The process of acquiring properties and behaviors of one class by another\n"
                    + "b) The process of creating objects\n"
                    + "c) The process of encapsulation\n"
                    + "d) The process of overloading methods\n",
            "2. In Java, which keyword is used to implement inheritance between classes?\n"
                    + "a) inheritsFrom\n"
                    + "b) inherits\n"
                    + "c) implements\n"
                    + "d) extends\n",

            "3. What is a superclass in Java?\n"
                    + "a) The class that inherits properties and behaviors\n"
                    + "b) The child class\n"
                    + "c) The class that is inherited from\n"
                    + "d) Short to int\n",

            "4. In Java, can a subclass inherit constructors from its superclass?\n"
                    + "a) Only if the subclass is marked as \"final\"\n"
                    + "b) Yes, a subclass inherits constructors from its superclass\n"
                    + "c) Only if the superclass is marked as \"static\"\n"
                    + "d) Only if the subclass is marked as \"final\"\n",

            "5. In Java, which class is used to represent a sequence of characters as a string?\n"
                    + "a) String\n"
                    + "b) StringBuilder\n"
                    + "c) StringSequence\n"
                    + "d) StringArray\n"
    };
    private char[] answers = {'a', 'd', 'b', 'b', 'a'};

    public OnlineExamination() {
        setTitle("OnlineExamination");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 1));

        JLabel usernameLabel = new JLabel("Username:");
        usernameF = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordF = new JPasswordField();
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameF);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordF);
        loginPanel.add(loginButton);
        loginPanel.add(cancelButton);

        add(loginPanel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameF.getText();
                String password = new String(passwordF.getPassword());

                if (validateLogin(username, password)) {
                    showQuestion();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials. Exiting...");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showQuestion() {
        getContentPane().removeAll();

        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BorderLayout());

        questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(new Font("Arial", Font.PLAIN, 16));

        buttonGroup = new ButtonGroup();

        JPanel answersPanel = new JPanel();
        answersPanel.setLayout(new GridLayout(4, 1));

        for (char option = 'a'; option <= 'd'; option++) {
            JRadioButton radioButton = new JRadioButton(String.valueOf(option));
            radioButton.setFont(new Font("Arial", Font.PLAIN, 14));
            buttonGroup.add(radioButton);
            answersPanel.add(radioButton);
        }

        nextButton = new JButton("Next");

        questionPanel.add(questionArea, BorderLayout.NORTH);
        questionPanel.add(answersPanel, BorderLayout.CENTER);
        questionPanel.add(nextButton, BorderLayout.SOUTH);

        add(questionPanel, BorderLayout.CENTER);
        

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                currentQuestionIndex++;

                if (currentQuestionIndex < questions.length) {
                    displayCurrentQuestion();
                } else {
                    showResult();
                }
            }
        });

        currentQuestionIndex = 0;
        correctAnswers = 0;
        displayCurrentQuestion();
        revalidate();
        repaint();
    }

    private void displayCurrentQuestion() {
        questionArea.setText(questions[currentQuestionIndex]);
        buttonGroup.clearSelection();
    }

    private void checkAnswer() {
        char userAnswer = 0;

        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                userAnswer = button.getText().charAt(0);
                break;
            }
        }

        if (userAnswer == answers[currentQuestionIndex]) {
            correctAnswers++;
        }
    }

    private void showResult() {
        getContentPane().removeAll();

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel resultLabel = new JLabel("Result:");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultPanel.add(resultLabel, gbc);

        gbc.gridy++;
        JLabel totalQuestionsLabel = new JLabel("Total Questions: " + questions.length);
        totalQuestionsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultPanel.add(totalQuestionsLabel, gbc);

        gbc.gridy++;
        JLabel correctAnswersLabel = new JLabel("Correct Answers: " + correctAnswers);
        correctAnswersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultPanel.add(correctAnswersLabel, gbc);

        gbc.gridy++;
        JLabel percentageLabel = new JLabel("Percentage: " + (correctAnswers * 100) / questions.length + "%");
        percentageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultPanel.add(percentageLabel, gbc);

        gbc.gridy++;
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        resultPanel.add(exitButton, gbc);

        add(resultPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private boolean validateLogin(String username, String password) {
        return username.equals("apurva") && password.equals("apurva123");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OnlineExamination();
            }
        });
    }
}